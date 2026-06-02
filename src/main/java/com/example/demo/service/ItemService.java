package com.example.demo.service;

import com.example.demo.model.Item;

import java.util.Optional;
import java.util.UUID;

public interface ItemService {
    Item findByItemName(String itemName);

    Item findById(UUID id);

    Optional<Item> findByName(String name);

    Optional<Item> findByID(UUID id);

    Item createItem(Item item);

}
