package org.grid.dynamics.inventoryservice.resource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DirtiesContext
class InventoryResourceTest {

    @Autowired
    private InventoryResource inventoryResource;

    @Test
    void shouldLoadAllProductInventories() {
        assertEquals(10122, inventoryResource.getAllProductInventories().size());
    }
}