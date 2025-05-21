package com.hungng3011.ecom.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createGetUpdateDeleteProduct() throws Exception {
        // Create product
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("A test product");
        // No price field in Product entity, so skip setPrice

        String createResponse = mockMvc.perform(post("/api/v1/products/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andReturn().getResponse().getContentAsString();
        Product created = objectMapper.readValue(createResponse, Product.class);
        assertThat(created.getId()).isNotNull();

        // Get product by id
        mockMvc.perform(get("/api/v1/products/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));

        // Update product
        created.setName("Updated Product");
        created.setDescription("Updated description");
        String updateResponse = mockMvc.perform(put("/api/v1/products/" + created.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andReturn().getResponse().getContentAsString();
        Product updated = objectMapper.readValue(updateResponse, Product.class);
        assertThat(updated.getName()).isEqualTo("Updated Product");

        // List products
        mockMvc.perform(get("/api/v1/products/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Delete product
        mockMvc.perform(delete("/api/v1/products/" + created.getId()))
                .andExpect(status().isNoContent());
        // Confirm deletion
        mockMvc.perform(get("/api/v1/products/" + created.getId()))
                .andExpect(status().isNotFound());
    }
}

