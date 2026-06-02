package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.transfer.create.CreateClient;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    Client createClient(CreateClient input);

    Client getClient(UUID clientID);

    List<Client> getAllClients(List<UUID> clientIDs);

    Client getClientByUsername(String username);
}
