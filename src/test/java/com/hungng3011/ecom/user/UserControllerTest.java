package com.hungng3011.ecom.user;

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
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createGetUpdateDeleteUser() throws Exception {
        // Create user
        User user = new User();
        user.setName("Test User");
        user.setEmail("testuser@example.com");
        String createResponse = mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test User"))
                .andReturn().getResponse().getContentAsString();
        User created = objectMapper.readValue(createResponse, User.class);
        assertThat(created.getId()).isNotNull();

        // Get user by id
        mockMvc.perform(get("/api/v1/users/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test User"));

        // Update user
        created.setName("Updated User");
        String updateResponse = mockMvc.perform(put("/api/v1/users/" + created.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated User"))
                .andReturn().getResponse().getContentAsString();
        User updated = objectMapper.readValue(updateResponse, User.class);
        assertThat(updated.getName()).isEqualTo("Updated User");

        // List users
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Delete user
        mockMvc.perform(delete("/api/v1/users/" + created.getId()))
                .andExpect(status().isOk());
        // Confirm deletion
        mockMvc.perform(get("/api/v1/users/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}

