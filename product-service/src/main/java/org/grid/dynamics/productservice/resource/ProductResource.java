package org.grid.dynamics.productservice.resource;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.grid.dynamics.productservice.dto.Product;
import org.grid.dynamics.productservice.dto.ProductInventory;
import org.grid.dynamics.productservice.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Component
@Slf4j
public class ProductResource {

    private final RestTemplate restTemplate;

    @Autowired
    public ProductResource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @HystrixCommand(fallbackMethod = "getHystrixProductByUniqId",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
            }
    )
    public Product getProductByUniqId(String id, Long delay) {
        log.info(String.format("getProductByUniqId:%s, delay: %s", id, delay));
        ResponseEntity<Product> productResponseEntity =
                restTemplate.getForEntity("http://catalog-service/vi/catalog/product?uniqid={id}&delay={delay}",
                        Product.class,
                        id, delay);
        if(productResponseEntity.getStatusCode() == HttpStatus.OK) {
           return productResponseEntity.getBody();
        }
        throw new ProductNotFoundException("Product bu id not found" + id);

    }

    @HystrixCommand(fallbackMethod = "getHystrixProductBySku",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            }
    )
    public List<Product> getProductBySku(String sku, Long delay) {
        log.info("getProductBySku: " + sku);
        ResponseEntity<Product[]> productEntity =
                restTemplate.getForEntity("http://catalog-service/vi/catalog/products?sku={sku}&delay={delay}",
                        Product[].class,
                        sku, delay);
        if(productEntity.getStatusCode() == HttpStatus.OK) {
            Product[] products = productEntity.getBody();
            return Arrays.asList(products);
        }
        throw new ProductNotFoundException("Product bu sku not found" + sku);

    }


    public List<ProductInventory> getProductInventoryByIds(List<String> ids, Long delay) {
        log.info("getProductInventoryByIds: " + ids);

        ResponseEntity<ProductInventory[]> productsInventory =
                restTemplate.getForEntity("http://inventory-service/vi/inventory/products?ids={ids}&delay={delay}",
                        ProductInventory[].class,
                        String.join(",", ids), delay);
        if(productsInventory.getStatusCode() == HttpStatus.OK) {
            ProductInventory[] products = productsInventory.getBody();
            return Arrays.asList(products);
        }

        throw new ProductNotFoundException("ProductInventory ids not found" + ids);
    }

    private Product getHystrixProductByUniqId(String id, Long delay) {
        log.info(String.format("getHystrixProductByUniq Id:%s, delay: %s", id, delay));
        Product product = new Product();
        product.setUniqId("93e5272c51d8cce02597e3ce67b7ad0a");
        product.setSku("pp5006380337");
        product.setNameTitle("HystrixProductByUniqId");

        return product;
    }

    private List<Product> getHystrixProductBySku(String sku, Long delay) {
        log.info(String.format("getHystrixProductBySku sku:%s, delay: %s", sku, delay));
        Product product = new Product();
        product.setUniqId("93e5272c51d8cce02597e3ce67b7ad0a");
        product.setSku("pp5006380337");
        product.setNameTitle("HystrixProductBySku");

        return Collections.singletonList(product);
    }

}
