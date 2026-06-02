package com.example.demo.rest.organization;

import com.example.demo.ITBase;
import com.example.demo.model.Client;
import com.example.demo.model.Country;
import com.example.demo.model.Organization;
import com.example.demo.repository.ClientOrganizationRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.CountryRepository;
import com.example.demo.repository.OrganizationRepository;
import com.example.demo.transfer.create.CreateOrganization;
import com.example.demo.transfer.update.AddUser;
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

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class OrganizationIT extends ITBase {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ClientOrganizationRepository clientOrganizationRepository;

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
    void testGetOrganization() throws Exception {

        Country country = this.createCOuntry();
        Organization org = this.createNewOrganization(country);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletResponse response =
                mockMvc.perform(get("/api/organization/{organizationID}", org.getId())
                                .header("CU-Authorized-Username", "Test user")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse();
        Assertions.assertEquals(200, response.getStatus());
        StandardResponse standardResponse = objectMapper.readValue(response.getContentAsString(), StandardResponse.class);

        Object data =  standardResponse.getData();
        Organization organization = objectMapper.convertValue(data, Organization.class);
        Assertions.assertEquals(org.getId(), organization.getId());
        Assertions.assertEquals(org.getOrgName(), organization.getOrgName());
        Assertions.assertEquals(org.getOrgAddress(), organization.getOrgAddress());
        Assertions.assertEquals(org.getOrgContact(), organization.getOrgContact());
        Assertions.assertEquals(org.getCountry().getId(), organization.getCountry().getId());
        Assertions.assertEquals(org.getCountry().getCountryName(), organization.getCountry().getCountryName());
    }

    @Test
    void testCreateOrganization() throws Exception {

        Country country = this.createCOuntry();
        Client saved = this.createNewClient();
        CreateOrganization createOrganization = new CreateOrganization(
                saved.getId(),
                RandomStringUtils.random(10),
                RandomStringUtils.random(20),
                mobile,
                RandomStringUtils.random(5),
                country.getId(),
                null,
                null
        );

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletResponse response =
                mockMvc.perform(post("/api/organization/register")
                                .header("CU-Authorized-Username", "Test user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(String.valueOf(objectMapper.valueToTree(createOrganization)))
                        )
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse();

        var savedOrgs = organizationRepository.findAll();
        Assertions.assertFalse(savedOrgs.isEmpty());
        Assertions.assertEquals(201, response.getStatus());
        var savedOrg = savedOrgs.getFirst();
        var savedobj = clientOrganizationRepository.findAllByOrg(savedOrg.getId());
        log.info("saved : {} ", response.getContentAsString());
        Assertions.assertNotNull(savedOrg.getId());
        Assertions.assertEquals(createOrganization.getName(), savedOrg.getOrgName());
        Assertions.assertEquals(createOrganization.getContact(), savedOrg.getOrgContact());
        Assertions.assertFalse(savedOrg.getClientOrganizations().isEmpty());
    }

    @Test
    void addUser() throws Exception {
        Client client = this.createNewClient();
        Country country = this.createCOuntry();
        Organization org = this.createNewOrganization(country);
        AddUser addUser = new AddUser(
                client.getId(),
                org.getId()
        );
        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletResponse response =
                mockMvc.perform(post("/api/organization/add-user")
                                .header("CU-Authorized-Username", "Test user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(String.valueOf(objectMapper.valueToTree(addUser)))
                        )
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse();
        log.info("response : {} ", response.getContentAsString());
        Assertions.assertEquals(201, response.getStatus());
        var orgUpdated = organizationRepository.findById(org.getId());
        Assertions.assertTrue(orgUpdated.isPresent());
        var updated = orgUpdated.get();
        Assertions.assertFalse(updated.getClientOrganizations().isEmpty());
    }

    private Client createNewClient() {
        return clientRepository.save(
                new Client(RandomStringUtils.random(8) + "@email.com", ThreadLocalRandom.current()
                        .nextLong(1_000_000_000L, 10_000_000_000L))
        );
    }

    private Organization createNewOrganization(Country country) {
        return organizationRepository.save(new Organization(
                        UUID.randomUUID(),
                        RandomStringUtils.random(8),
                        RandomStringUtils.random(8),
                        ThreadLocalRandom.current()
                                .nextLong(1_000_000_000L, 10_000_000_000L),
                        RandomStringUtils.random(8),
                        country,
                        null
                )
        );
    }

    private Country createCOuntry() {
        return countryRepository.save(new Country(
                RandomStringUtils.random(5)
        ));
    }
}
