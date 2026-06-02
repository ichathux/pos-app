package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "catalog_item")
public class CatalogItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "catalog_item_id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @NotNull
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "discounted_price")
    private BigDecimal discountedPrice;

    @Column(name = "img_url", length = Integer.MAX_VALUE)
    private String imgUrl;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    public CatalogItem(Item item, Organization org, BigDecimal price, BigDecimal discountedPrice, String imgUrl, String description) {
        this.item = item;
        this.org = org;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.imgUrl = imgUrl;
        this.description = description;
    }
}