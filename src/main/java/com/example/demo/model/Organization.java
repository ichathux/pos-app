package com.example.demo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "organization")
public class Organization {
    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "org_id", nullable = false)
    private UUID id;

    @Column(name = "org_name", nullable = false, length = Integer.MAX_VALUE)
    private String orgName;

    @Column(name = "org_address", length = Integer.MAX_VALUE)
    private String orgAddress;

    @Column(name = "org_contact")
    private Long orgContact;

    @Column(name = "br_number", length = Integer.MAX_VALUE)
    private String brNumber;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Column(name = "additional_declaration")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> additionalDeclaration;

    @OneToMany(mappedBy = "org", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ClientOrganization> clientOrganizations = new ArrayList<>();

    public Organization(UUID id, String orgName, String orgAddress, Long orgContact, String brNumber, Country country, Map<String, Object> additionalDeclaration) {
        this.id = id;
        this.orgName = orgName;
        this.orgAddress = orgAddress;
        this.orgContact = orgContact;
        this.brNumber = brNumber;
        this.country = country;
        this.additionalDeclaration = additionalDeclaration;
    }
}