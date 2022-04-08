package org.grid.dynamics.catalogservice.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class Product {
    @CsvBindByPosition(position = 0)
    private String uniqId;

    @CsvBindByPosition(position = 1)
    private String sku;

    @CsvBindByPosition(position = 2)
    private String nameTitle;

    @CsvBindByPosition(position = 3)
    private String description;

    @CsvBindByPosition(position = 4)
    private String listPrice;

    @CsvBindByPosition(position = 5)
    private String salePrice;

    @CsvBindByPosition(position = 6)
    private String category;

    @CsvBindByPosition(position = 7)
    private String categoryTree;

    @CsvBindByPosition(position = 8)
    private String averageProductRating;

    @CsvBindByPosition(position = 9)
    private String productUrl;

    @CsvBindByPosition(position = 10)
    private String productImageUrls;

    @CsvBindByPosition(position = 11)
    private String brand;

    @CsvBindByPosition(position = 12)
    private String totalNumberReviews;

    @CsvBindByPosition(position = 13)
    private String reviews;
}
