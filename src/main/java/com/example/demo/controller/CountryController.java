package com.example.demo.controller;

import com.example.demo.service.CountryService;
import com.example.demo.util.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<StandardResponse> getAllCountry() {
        return new ResponseEntity<>(
                new StandardResponse("Success !", countryService.findAll()),
                HttpStatus.OK
        );
    }

    @GetMapping("{countryId}")
    public ResponseEntity<StandardResponse> getAllCountry(@PathVariable UUID  countryId) {
        return new ResponseEntity<>(
                new StandardResponse("Success !", countryService.findCountry(countryId)),
                HttpStatus.OK
        );
    }
}
