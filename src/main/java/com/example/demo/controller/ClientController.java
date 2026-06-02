package com.example.demo.controller;

import com.example.demo.service.ClientService;
import com.example.demo.transfer.create.CreateClient;
import com.example.demo.util.StandardResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("{clientID}")
    public ResponseEntity<StandardResponse> getClient(@PathVariable UUID clientID) {
        return new ResponseEntity<>(
                new StandardResponse("Success", clientService.getClient(clientID)),
                HttpStatus.OK);
    }

    @GetMapping("{username}")
    public ResponseEntity<StandardResponse> getClient(@PathVariable String username) {
        return new ResponseEntity<>(
                new StandardResponse("Success", clientService.getClientByUsername(username)),
                HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<StandardResponse> registerClient(@RequestBody @Valid CreateClient input) {
        return new ResponseEntity<>(
                new StandardResponse("Success", clientService.createClient(input)),
                HttpStatus.CREATED
        );
    }
}
