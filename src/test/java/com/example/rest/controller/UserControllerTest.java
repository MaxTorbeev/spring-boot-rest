package com.example.rest.controller;

import com.example.rest.entity.UserEntity;
import com.google.gson.Gson;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void all() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/users/all")
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(StringContains.containsString("Ok")))
                .andReturn();
    }

    @Test
    void registration() throws Exception {
        Gson gson = new Gson();

        UserEntity user = new UserEntity();
        user.setUsername("test1");
        user.setPassword("test");

        String params = gson.toJson(user);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/users")
                .content(params)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(StringContains.containsString("Пользователь успешно сохранен.")))
                .andReturn();
    }
}