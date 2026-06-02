package com.example.demo.service;

import com.example.demo.model.CatalogItem;
import com.example.demo.transfer.CreateCatalogItem;

import java.util.List;
import java.util.UUID;

public interface CatalogService {

    CatalogItem createCatalogItem(CreateCatalogItem catalogItem);

    List<CatalogItem> getAllCatalogItems(UUID orgID);

    CatalogItem getCatalogItem(UUID catalogItemID);
}
