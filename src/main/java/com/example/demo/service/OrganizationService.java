package com.example.demo.service;

import com.example.demo.model.ClientOrganization;
import com.example.demo.model.Organization;
import com.example.demo.transfer.create.CreateOrganization;
import com.example.demo.transfer.update.AddUser;
import jakarta.validation.Valid;

import java.util.UUID;

public interface OrganizationService {
    Organization createOrganization(@Valid CreateOrganization input);

    Organization getOrganization(UUID organizationID);

    ClientOrganization addUser(@Valid AddUser input);
}
