package org.grid.dynamics.inventoryservice.service;

import org.grid.dynamics.inventoryservice.dto.ProductInventory;

import java.util.List;

public interface InventoryService {

    List<ProductInventory> getProductInventoriesByUniqIds(List<String> uniqIds, Long delay);
}
