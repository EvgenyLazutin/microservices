package org.grid.dynamics.catalogservice.resource;


import com.opencsv.bean.CsvToBeanBuilder;
import org.grid.dynamics.catalogservice.dto.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class CatalogResource {

    private List<Product> products;

    @Value("${csv.file.name}")
    private String fileName;

    @PostConstruct
    private void postConstruct() {
        this.products = loadProductsFromFile(fileName);
    }

    public List<Product> getAllProducts() {
        return products;
    }

    private List<Product> loadProductsFromFile(String fileName) {
        InputStream in = getClass().getResourceAsStream("/" + fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        List<Product> products = new CsvToBeanBuilder(reader)
                .withType(Product.class)
                .build()
                .parse();

        return products;
    }
}
