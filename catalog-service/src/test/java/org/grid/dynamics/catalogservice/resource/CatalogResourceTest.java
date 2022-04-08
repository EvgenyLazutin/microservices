package org.grid.dynamics.catalogservice.resource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
class CatalogResourceTest {

    @Autowired
    private CatalogResource catalogResource;

    @Test
    void shouldLoadAllData() {
        assertEquals(10122, catalogResource.getAllProducts().size());
    }

}