package org.grid.dynamics.inventoryservice.resource;


import com.opencsv.bean.CsvToBeanBuilder;
import org.grid.dynamics.inventoryservice.dto.ProductInventory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Component
public class InventoryResource {

    private Map<String, ProductInventory> productInventories;

    private final Random random = new Random();

    @Value("${csv.file.name}")
    private String fileName;

    @PostConstruct
    private void postConstruct() {
        this.productInventories = loadProductsFromFile(fileName);
    }

    public Map<String, ProductInventory> getAllProductInventories() {
        return productInventories;
    }

    private Map<String, ProductInventory> loadProductsFromFile(String fileName) {
        InputStream in = getClass().getResourceAsStream("/" + fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        List<ProductInventory> productInventories = new CsvToBeanBuilder(reader)
                .withType(ProductInventory.class)
                .build()
                .parse();

        return productInventories.stream()
                .map(this::generateRandomAvailable)
                .collect(Collectors.toMap(ProductInventory::getUniqId, productInventory -> productInventory));
    }

    private ProductInventory generateRandomAvailable(ProductInventory productInventory) {
        productInventory.setAvailable(random.nextBoolean());
        return productInventory;
    }

}
