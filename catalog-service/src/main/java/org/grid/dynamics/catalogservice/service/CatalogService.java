package org.grid.dynamics.catalogservice.service;

import org.grid.dynamics.catalogservice.dto.Product;

import java.util.List;
import java.util.Optional;

public interface CatalogService {

    List<Product> getAllProductsBySku(String sku, Long delay);

    Optional<Product> getProductById(String uniqid, Long delay);
}
