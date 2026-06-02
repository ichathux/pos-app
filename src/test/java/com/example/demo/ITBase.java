package com.example.demo;

import com.example.demo.container.SharedPostgresContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.profiles.active=test"})
//@EntityScan(basePackages = {"com.pcu.stock_transaction.stock_transaction_service.model"})
@AutoConfigureMockMvc
public class ITBase {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    protected static PostgreSQLContainer<?> postgresContainer;

    @BeforeAll
    public static void beforeAll() {
        postgresContainer = SharedPostgresContainer.getInstance();
        postgresContainer.start();
    }

    public void setup() {

    }

    public void cleanup() {
    }

}
