package com.example.rest.controller;

import com.example.rest.entity.CategoryEntity;
import com.google.gson.Gson;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/migrations/categories-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Gson gson;

    @Test
    void store() throws Exception {
        CategoryEntity model = new CategoryEntity();
        model.setBody("<p>This is a body</p>");
        model.setName("Test category");
        model.setActive(true);

        String params = gson.toJson(model);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/categories")
                .content(params)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(StringContains.containsString("User has been created.")))
            .andReturn();
    }
}
