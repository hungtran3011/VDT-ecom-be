package com.hungng3011.ecom.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hungng3011.ecom.user.User;
import com.hungng3011.ecom.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetOrder() throws Exception {
        // Create a User reference (assuming user with id=1 exists)
        User user = new User();
        user.setId(1L);

        // Create a Product reference (assuming product with id=1 exists)
        Product product = new Product();
        product.setId(1L);

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(2);

        Order order = Order.builder()
                .user(user)
                .shippingAddress("123 Main St")
                .items(Collections.singletonList(item))
                .build();

        // Create order
        String response = mockMvc.perform(post("/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Order created = objectMapper.readValue(response, Order.class);

        // Get order by id
        mockMvc.perform(get("/v1/orders/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(1L));
    }

    @Test
    void testListOrders() throws Exception {
        mockMvc.perform(get("/v1/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}

