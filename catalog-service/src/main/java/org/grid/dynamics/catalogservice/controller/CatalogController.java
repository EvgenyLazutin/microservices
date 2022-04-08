package org.grid.dynamics.catalogservice.controller;


import lombok.extern.slf4j.Slf4j;
import org.grid.dynamics.catalogservice.dto.Product;
import org.grid.dynamics.catalogservice.exception.ProductNotFoundException;
import org.grid.dynamics.catalogservice.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/vi/catalog")
@RestController
@Slf4j
public class CatalogController {

    private final CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/products")
    public List<Product> getAllProductsBySku(@RequestParam String sku,
                                             @RequestParam(defaultValue = "0") String delay) {
        List<Product> allProductsBySku = catalogService.getAllProductsBySku(sku, Long.valueOf(delay));
        if (allProductsBySku.isEmpty()) throw new ProductNotFoundException(String.format("Products by sku %s not found", sku));
        return allProductsBySku;
    }

    @GetMapping("/product")
    public Product getProductById(@RequestParam String uniqid,
                                  @RequestParam(defaultValue = "0") String delay) {
        return catalogService.getProductById(uniqid, Long.valueOf(delay))
                .orElseThrow(() -> new ProductNotFoundException(String.format("Product by id %s not found", uniqid)));
    }
}
