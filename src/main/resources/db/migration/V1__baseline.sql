CREATE TABLE country (
                         country_id UUID NOT NULL DEFAULT gen_random_uuid(),
                         country_name TEXT NOT NULL,
                         CONSTRAINT pk_country PRIMARY KEY (country_id)
);

CREATE TABLE client (
                        client_id UUID NOT NULL DEFAULT gen_random_uuid(),
                        username TEXT NOT NULL,
                        mobile BIGINT NOT NULL,
                        CONSTRAINT pk_client PRIMARY KEY (client_id)
);

CREATE TABLE item (
                      item_id UUID NOT NULL DEFAULT gen_random_uuid(),
                      item_name TEXT NOT NULL,
                      CONSTRAINT pk_item PRIMARY KEY (item_id)
);

CREATE TABLE organization (
                              org_id UUID NOT NULL,
                              org_name TEXT NOT NULL,
                              org_address TEXT,
                              org_contact BIGINT,
                              br_number TEXT,
                              country_id UUID NOT NULL,
                              additional_declaration JSONB,
                              CONSTRAINT pk_organization PRIMARY KEY (org_id),
                              CONSTRAINT fk_organization_country FOREIGN KEY (country_id)
                                  REFERENCES country (country_id)
);

CREATE INDEX idx_organization_additional_declaration
    ON organization USING GIN (additional_declaration);

CREATE TABLE client_organization (
                                     client_id UUID NOT NULL,
                                     org_id UUID NOT NULL,
                                     CONSTRAINT pk_client_organization PRIMARY KEY (client_id, org_id),
                                     CONSTRAINT fk_client_organization_client FOREIGN KEY (client_id)
                                         REFERENCES client (client_id),
                                     CONSTRAINT fk_client_organization_org FOREIGN KEY (org_id)
                                         REFERENCES organization (org_id)
);

ALTER TABLE country ADD CONSTRAINT uq_country_name UNIQUE (country_name);

CREATE TABLE catalog_item (
                              catalog_item_id UUID NOT NULL DEFAULT gen_random_uuid(),
                              item_id UUID NOT NULL,
                              org_id UUID NOT NULL,
                              price NUMERIC(19, 2) NOT NULL,
                              discounted_price NUMERIC(19, 2),
                              img_url TEXT,
                              description TEXT,
                              CONSTRAINT pk_catalog_item PRIMARY KEY (catalog_item_id),
                              CONSTRAINT fk_catalog_item_item FOREIGN KEY (item_id)
                                  REFERENCES item (item_id),
                              CONSTRAINT fk_catalog_item_org FOREIGN KEY (org_id)
                                  REFERENCES organization (org_id)
);

ALTER TABLE catalog_item ADD CONSTRAINT uq_catalog_item_item_org UNIQUE (item_id, org_id);