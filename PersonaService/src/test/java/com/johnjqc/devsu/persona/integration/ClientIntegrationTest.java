package com.johnjqc.devsu.persona.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClientIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenValidClient_whenCreateClient_thenReturnsCreatedClient() throws Exception {

        String request = """
                {
                  "name": "Jose Lema",
                  "gender": "MALE",
                  "age": 35,
                  "identification": "1723456782",
                  "address": "Otavalo sn y principal",
                  "phone": "098254785",
                  "password": "1234",
                  "active": true
                }
        """;

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jose Lema"))
                .andExpect(jsonPath("$.identification").value("1723456782"))
                .andExpect(jsonPath("$.active").value(true));
    }
}