package org.grid.dynamics.inventoryservice.controller;


import lombok.extern.slf4j.Slf4j;

import org.grid.dynamics.inventoryservice.dto.ProductInventory;
import org.grid.dynamics.inventoryservice.exception.ProductInventoryNotFoundException;
import org.grid.dynamics.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/vi/inventory")
@RestController
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService catalogService) {
        this.inventoryService = catalogService;
    }

    @GetMapping("/products")
    public List<ProductInventory> getAllProductsBySku(@RequestParam List<String> ids,
                                                      @RequestParam(defaultValue = "0") String delay) {
        List<ProductInventory> productInventories = inventoryService.getProductInventoriesByUniqIds(ids, Long.valueOf(delay));
        if (productInventories.isEmpty()) throw new ProductInventoryNotFoundException(String.format("Products inventory by ids %s not found", ids));
        return productInventories;
    }

}
