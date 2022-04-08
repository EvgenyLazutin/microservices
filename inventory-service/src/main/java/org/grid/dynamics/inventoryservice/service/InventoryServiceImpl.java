package org.grid.dynamics.inventoryservice.service;

import lombok.extern.slf4j.Slf4j;

import org.grid.dynamics.inventoryservice.dto.ProductInventory;
import org.grid.dynamics.inventoryservice.resource.InventoryResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryResource inventoryResource;

    @Autowired
    public InventoryServiceImpl(InventoryResource inventoryResource) {
        this.inventoryResource = inventoryResource;
    }

    @Override
    public List<ProductInventory> getProductInventoriesByUniqIds(List<String> uniqIds, Long delay) {
        addDelay(delay);
        var allProductInventory = inventoryResource.getAllProductInventories();
        return uniqIds.stream()
                .filter(allProductInventory::containsKey)
                .map(allProductInventory::get)
                .collect(Collectors.toList());
    }

    private void addDelay(long secondsToSleep) {
        log.info(String.format("Delay %s was added", secondsToSleep));
        try {
            TimeUnit.SECONDS.sleep(secondsToSleep);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}
