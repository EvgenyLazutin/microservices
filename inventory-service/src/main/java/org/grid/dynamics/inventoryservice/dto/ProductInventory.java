package org.grid.dynamics.inventoryservice.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class ProductInventory {

    @CsvBindByPosition(position = 0)
    private String uniqId;

    private Boolean available;
}
