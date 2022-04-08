package org.grid.dynamics.catalogservice.service;

import lombok.extern.slf4j.Slf4j;
import org.grid.dynamics.catalogservice.dto.Product;
import org.grid.dynamics.catalogservice.resource.CatalogResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CatalogServiceImpl implements CatalogService {

    private final CatalogResource catalogResource;

    @Autowired
    public CatalogServiceImpl(CatalogResource catalogResource) {
        this.catalogResource = catalogResource;
    }

    @Override
    public List<Product> getAllProductsBySku(String sku, Long delay) {
        addDelay(delay);
        return catalogResource.getAllProducts()
                .stream()
                .filter(product -> sku.equalsIgnoreCase(product.getSku()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> getProductById(String uniqid, Long delay) {
        addDelay(delay);
        return catalogResource.getAllProducts()
                .stream()
                .filter(product -> uniqid.equalsIgnoreCase(product.getUniqId()))
                .findFirst();
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
