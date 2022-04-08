package org.grid.dynamics.productservice.service;

import lombok.extern.slf4j.Slf4j;

import org.grid.dynamics.productservice.dto.Product;
import org.grid.dynamics.productservice.dto.ProductInventory;
import org.grid.dynamics.productservice.resource.ProductResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductResource productResource;

    @Autowired
    public ProductServiceImpl(ProductResource productResource) {
        this.productResource = productResource;
    }


    @Override
    public Optional<Product> getAvailableProductById(String uniqid, Long delay) {
        Product productByUniqId = productResource.getProductByUniqId(uniqid, delay);
        List<ProductInventory> productInventoryByIds = productResource.getProductInventoryByIds(
                List.of(productByUniqId.getUniqId()), delay);
        if (!productInventoryByIds.isEmpty() && productInventoryByIds.get(0).getAvailable())
            return Optional.of(productByUniqId);
        return Optional.empty();
    }

    @Override
    public List<Product> getAvailableProductsBySku(String sku, Long delay) {
        List<Product> productBySku = productResource.getProductBySku(sku, delay);

        List<String> idsList = productBySku
                .stream()
                .map(Product::getUniqId)
                .collect(Collectors.toList());

        Map<String, ProductInventory> productInventoryByIds = productResource
                .getProductInventoryByIds(idsList, delay)
                .stream()
                .collect(Collectors.toMap(ProductInventory::getUniqId, productInventory -> productInventory));

        return productBySku.stream()
                .filter(product -> productInventoryByIds.containsKey(product.getUniqId()))
                .filter(product -> productInventoryByIds.get(product.getUniqId()).getAvailable())
                .collect(Collectors.toList());
    }
}
