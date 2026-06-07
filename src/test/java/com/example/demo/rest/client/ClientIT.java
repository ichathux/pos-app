package com.example.demo.rest.client;

import com.example.demo.ITBase;
import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import com.example.demo.transfer.create.CreateClient;
import com.example.demo.util.StandardResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class ClientIT extends ITBase {

    @Autowired
    private ClientRepository clientRepository;

    final long mobile = ThreadLocalRandom.current()
            .nextLong(1_000_000_000L, 10_000_000_000L);
    final String username = RandomStringUtils.random(8) + "@email.com";

    @Override
    @BeforeEach
    public void setup() {
        super.setup();
    }

    @Override
    @AfterEach
    public void cleanup() {
        super.cleanup();
        clientRepository.deleteAll();
    }

    @Test
    void testCreateClient() throws Exception {

        CreateClient createClient = new CreateClient(
                username,
                mobile
        );
        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletResponse response =
                mockMvc.perform(post("/api/client/register")
                                .header("CU-Authorized-Username", "Test user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(String.valueOf(objectMapper.valueToTree(createClient)))
                        )
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse();

        var savedClients = clientRepository.findAll();
        Assertions.assertFalse(savedClients.isEmpty());
        Assertions.assertEquals(201, response.getStatus());
        var savedClient = savedClients.getFirst();
        Assertions.assertNotNull(savedClient.getId());
        Assertions.assertEquals(username, savedClient.getUsername());
        Assertions.assertEquals(mobile, savedClient.getMobile());
    }

    @Test
    void testCreateClient_existingUsername() throws Exception {
        var created = this.createNewClient();
        CreateClient createClient = new CreateClient(
                created.getUsername(),
                mobile
        );
        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletResponse response =
                mockMvc.perform(post("/api/client/register")
                                .header("CU-Authorized-Username", "Test user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(String.valueOf(objectMapper.valueToTree(createClient)))
                        )
                        .andExpect(status().isConflict())
                        .andReturn()
                        .getResponse();
        StandardResponse standardResponse = objectMapper.readValue(response.getContentAsString(), StandardResponse.class);

        Assertions.assertEquals(409, response.getStatus());
        Assertions.assertEquals("Provided data already registered. Please Check inputs", standardResponse.getMessage());
        Assertions.assertEquals("Username already registered.", standardResponse.getData());
    }

    @Test
    void testCreateClient_existingMobile() throws Exception {
        var created = this.createNewClient();
        CreateClient createClient = new CreateClient(
                username,
                created.getMobile()
        );
        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletResponse response =
                mockMvc.perform(post("/api/client/register")
                                .header("CU-Authorized-Username", "Test user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(String.valueOf(objectMapper.valueToTree(createClient)))
                        )
                        .andExpect(status().isConflict())
                        .andReturn()
                        .getResponse();
        StandardResponse standardResponse = objectMapper.readValue(response.getContentAsString(), StandardResponse.class);

        Assertions.assertEquals(409, response.getStatus());
        Assertions.assertEquals("Provided data already registered. Please Check inputs", standardResponse.getMessage());
        Assertions.assertEquals("Mobile number already registered.", standardResponse.getData());
    }

    @Test
    void testCreateClient_emptyMobile() throws Exception {
        CreateClient createClient = new CreateClient(
                username,
                null
        );
        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletResponse response =
                mockMvc.perform(post("/api/client/register")
                                .header("CU-Authorized-Username", "Test user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(String.valueOf(objectMapper.valueToTree(createClient)))
                        )
                        .andExpect(status().isBadRequest())
                        .andReturn()
                        .getResponse();
        Assertions.assertEquals(400, response.getStatus());
        Assertions.assertEquals("Invalid request content.", response.getErrorMessage());
    }

    @Test
    void testCreateClient_emptyUsername() throws Exception {
        CreateClient createClient = new CreateClient(
                username,
                null
        );
        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletResponse response =
                mockMvc.perform(post("/api/client/register")
                                .header("CU-Authorized-Username", "Test user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(String.valueOf(objectMapper.valueToTree(createClient)))
                        )
                        .andExpect(status().isBadRequest())
                        .andReturn()
                        .getResponse();
        Assertions.assertEquals(400, response.getStatus());
        Assertions.assertEquals("Invalid request content.", response.getErrorMessage());
    }

//    @Test
//    void testGetClient() throws Exception {
//        Client createdClient = createNewClient();
//        ObjectMapper objectMapper = new ObjectMapper();
//        MockHttpServletResponse response =
//                mockMvc.perform(get("/api/client/{clientID}", createdClient.getId())
//                                .header("CU-Authorized-Username", "Test user")
//                                .contentType(MediaType.APPLICATION_JSON)
//                        )
//                        .andExpect(status().isOk())
//                        .andReturn()
//                        .getResponse();
//        Assertions.assertEquals(200, response.getStatus());
//        StandardResponse standardResponse = objectMapper.readValue(response.getContentAsString(), StandardResponse.class);
//
//        Object data =  standardResponse.getData();
//        Client client = objectMapper.convertValue(data, Client.class);
//        Assertions.assertEquals(createdClient.getId(), client.getId());
//        Assertions.assertEquals(createdClient.getUsername(), client.getUsername());
//        Assertions.assertEquals(createdClient.getMobile(), client.getMobile());
//    }



    private Client createNewClient() {
        return clientRepository.save(
                new Client(RandomStringUtils.random(8) + "@email.com", ThreadLocalRandom.current()
                        .nextLong(1_000_000_000L, 10_000_000_000L))
        );
    }
}
