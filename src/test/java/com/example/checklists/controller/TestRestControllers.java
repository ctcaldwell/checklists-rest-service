package com.example.checklists.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestRestControllers {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRespondsToExpectedRoutes() throws Exception {
        mockMvc.perform(post("/checklists")
                .content("\"name\":\"Test List 1\"")
                .contentType("application/json"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/checklists")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(post("/checklists/1/tasks")
                .content("\"description\":\"Test Task 1\"")
                .contentType("application/json"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/checklists/1/tasks")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(post("/checklists/1/instances")
                .contentType("application/json"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/checklists/1/instances")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
