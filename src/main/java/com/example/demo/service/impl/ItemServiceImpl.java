package com.example.demo.service.impl;

import com.example.demo.exception.EntryNotFoundException;
import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;
import com.example.demo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public Item findByItemName(String itemName) {
        return this.findByName(itemName)
                .orElseThrow(() -> new EntryNotFoundException(itemName + " not found"));
    }

    @Override
    public Item findById(UUID id) {
        return this.findByID(id)
                .orElseThrow(() -> new EntryNotFoundException("Item not found"));
    }

    @Override
    public Optional<Item> findByName(String name) {
        return itemRepository.findByItemName(name);
    }

    @Override
    public Optional<Item> findByID(UUID id) {
        return itemRepository.findById(id);
    }

    @Override
    public Item createItem(Item item) {
        return itemRepository.save(item);
    }
}
