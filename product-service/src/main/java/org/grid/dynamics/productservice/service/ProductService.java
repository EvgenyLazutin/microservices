package org.grid.dynamics.productservice.service;

import org.grid.dynamics.productservice.dto.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getAvailableProductById(String uniqid, Long delay);

    List<Product> getAvailableProductsBySku(String sku, Long delay);
}
