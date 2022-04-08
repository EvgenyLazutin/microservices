package org.grid.dynamics.catalogservice.controller;

import org.grid.dynamics.catalogservice.exception.ProductNotFoundException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnProductById() throws Exception {
        this.mockMvc.perform(get("/vi/catalog/product?uniqid=b6c0b6bea69c722939585baeac73c13d")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uniqId").value("b6c0b6bea69c722939585baeac73c13d"))
                .andExpect(jsonPath("$.sku").value("pp5006380337"));
    }

    @Test
    void shouldReturnError_WhenProductByIdNotFound() throws Exception {
        this.mockMvc.perform(get("/vi/catalog/product?uniqid=errorId")
                        .contentType("application/json"))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException))
                .andExpect(result -> assertEquals("Product by id errorId not found", result.getResolvedException().getMessage()));
    }

    @Test
    void shouldReturnListOfProductBySku() throws Exception {
        this.mockMvc.perform(get("/vi/catalog/products?sku=pp5006380337")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(7)))
                .andExpect(jsonPath("$[0].sku").value("pp5006380337"))
                .andExpect(jsonPath("$[6].sku").value("pp5006380337"));
    }

    @Test
    void shouldReturnError_WhenProductBySkyNotFound() throws Exception {
        this.mockMvc.perform(get("/vi/catalog/products?sku=errorSku")
                        .contentType("application/json"))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException))
                .andExpect(result -> assertEquals("Products by sku errorSku not found", result.getResolvedException().getMessage()));
    }
}