package org.grid.dynamics.productservice.controller;

import lombok.extern.slf4j.Slf4j;

import org.grid.dynamics.productservice.dto.Product;
import org.grid.dynamics.productservice.exception.ProductNotFoundException;
import org.grid.dynamics.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/vi/product/available")
@RestController
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/uniqid")
    public Product getAllProductsById(@RequestParam String uniqid,
                                      @RequestParam(defaultValue = "0") String delay) {
        return productService.getAvailableProductById(uniqid, Long.valueOf(delay))
                .orElseThrow(() -> new ProductNotFoundException(String.format("Product by id %s not found", uniqid)));
    }

    @GetMapping("/sku")
    public List<Product> getAllProductsBySku(@RequestParam String sku,
                                             @RequestParam(defaultValue = "0") String delay) {
        List<Product> products = this.productService.getAvailableProductsBySku(sku, Long.valueOf(delay));
        if (products.isEmpty()) throw new ProductNotFoundException(String.format("Products by sku %s not found", sku));
        return products;
    }

}
