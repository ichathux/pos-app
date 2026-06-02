package com.example.demo.service.impl;

import com.example.demo.exception.EntryAlreadyExistsException;
import com.example.demo.exception.EntryNotFoundException;
import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.ClientService;
import com.example.demo.transfer.create.CreateClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client createClient(CreateClient input) {
        if (clientRepository.existsClientByUsername(input.getUsername())){
            throw new EntryAlreadyExistsException("Username already registered.");
        }
        if (clientRepository.existsClientByMobile(input.getMobile())){
            throw new EntryAlreadyExistsException("Mobile number already registered.");
        }
        return clientRepository.save(new Client(input.getUsername(), input.getMobile()));
    }

    @Override
    public Client getClient(UUID clientID) {
        return clientRepository.findById(clientID)
                .orElseThrow(() -> new EntryNotFoundException("Client not found"));
    }

    @Override
    public List<Client> getAllClients(List<UUID> clientIDs) {
        return clientRepository.findAllById(clientIDs);
    }

    @Override
    public Client getClientByUsername(String username) {
        return clientRepository.findClientByUsername(username)
                .orElseThrow(() -> new EntryNotFoundException("User not found"));
    }
}
