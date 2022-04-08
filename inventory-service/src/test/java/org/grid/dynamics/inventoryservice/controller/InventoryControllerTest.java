package org.grid.dynamics.inventoryservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnProductInventoryByIds() throws Exception {

        this.mockMvc.perform(get("/vi/inventory/products?ids=b6c0b6bea69c722939585baeac73c13d,93e5272c51d8cce02597e3ce67b7ad0a,013e320f2f2ec0cf5b3ff5418d688528")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$[0].uniqId").value("b6c0b6bea69c722939585baeac73c13d"))
                .andExpect(jsonPath("$[1].uniqId").value("93e5272c51d8cce02597e3ce67b7ad0a"))
                .andExpect(jsonPath("$[2].uniqId").value("013e320f2f2ec0cf5b3ff5418d688528"));
    }
}