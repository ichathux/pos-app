package com.example.demo.service;

import com.example.demo.model.Country;

import java.util.List;
import java.util.UUID;

public interface CountryService {

    List<Country> findAll();

    Country findCountry(UUID countryId);
}
