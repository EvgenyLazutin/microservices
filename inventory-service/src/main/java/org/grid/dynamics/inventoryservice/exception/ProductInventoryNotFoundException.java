package org.grid.dynamics.inventoryservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductInventoryNotFoundException extends RuntimeException {

    public ProductInventoryNotFoundException(String message) {
        super(message);
    }
}
