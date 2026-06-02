package com.example.demo.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

public class SharedPostgresContainer extends PostgreSQLContainer<SharedPostgresContainer> {

    private static final String IMAGE_VERSION = "postgres:18.0";
    private static SharedPostgresContainer container;
    private static final Logger log = LoggerFactory.getLogger(SharedPostgresContainer.class);

    private SharedPostgresContainer() {
        super(IMAGE_VERSION);
    }

    public static SharedPostgresContainer getInstance() {
        if (container == null) {
            container = new SharedPostgresContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        // set configuration properties
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());
        System.setProperty("spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults", "false");
        log.info("postgres container jdbc url: {}", container.getJdbcUrl());

        // add log consumer
        container.followOutput(new Slf4jLogConsumer(log));
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
