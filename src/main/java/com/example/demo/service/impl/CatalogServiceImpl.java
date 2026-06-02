package com.example.demo.service.impl;

import com.example.demo.exception.EntryAlreadyExistsException;
import com.example.demo.exception.EntryNotFoundException;
import com.example.demo.model.CatalogItem;
import com.example.demo.model.Item;
import com.example.demo.model.Organization;
import com.example.demo.repository.CatalogRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.service.CatalogService;
import com.example.demo.service.ItemService;
import com.example.demo.service.OrganizationService;
import com.example.demo.transfer.CreateCatalogItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;

    private final ItemService itemService;

    private final OrganizationService organizationService;

    @Override
    public CatalogItem createCatalogItem(CreateCatalogItem catalogItem) {
        Item item;
        if (catalogItem.getItemID() == null) {
            item = itemService.findByName(catalogItem.getItemName().trim())
                    .orElse(itemService.createItem(new Item(
                            catalogItem.getItemName()
                    )));
        } else {
            item = itemService.findByID(catalogItem.getItemID())
                    .orElse(itemService.createItem(new Item(
                            catalogItem.getItemName()
                    )));
        }
        if (catalogRepository.existsByItem(item)){
            throw new EntryAlreadyExistsException("Item already exists");
        }
        Organization org = organizationService.getOrganization(catalogItem.getOrgID());
        return catalogRepository.save(new CatalogItem(
                item,
                org,
                catalogItem.getPrice(),
                catalogItem.getDisPrice(),
                catalogItem.getImgUrl(),
                catalogItem.getDescription()
        ));
    }

    @Override
    public List<CatalogItem> getAllCatalogItems(UUID orgID) {
        return catalogRepository.findAllByOrg_Id(orgID);
    }

    @Override
    public CatalogItem getCatalogItem(UUID catalogItemID) {
        return catalogRepository.findById(catalogItemID)
                .orElseThrow(() -> new EntryNotFoundException("Catalog Item Not Found"));
    }
}
