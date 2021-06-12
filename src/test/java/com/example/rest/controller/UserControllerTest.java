package com.example.rest.controller;

import com.example.rest.entity.UserEntity;
import com.example.rest.model.User;
import com.example.rest.repository.UserRepo;
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
@Sql(value = {"/migrations/users-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private Gson gson;

    @Test
    void show() throws Exception {
        UserEntity model = new UserEntity();

        model.setPassword("password");
        model.setUsername("test");

        userRepo.save(model);

        String json = gson.toJson(User.toModel(model));

        RequestBuilder request = MockMvcRequestBuilders
                .get("/users/" + model.getId())
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(StringContains.containsString(json)))
                .andReturn();
    }

    @Test
    void store() throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername("test1");
        user.setPassword("test");

        String params = gson.toJson(user);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/users")
                .content(params)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(StringContains.containsString("User has been created.")))
            .andReturn();
    }

    @Test
    void delete() throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername("test1");
        user.setPassword("test");
        userRepo.save(user);

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(StringContains.containsString("User has been deleted.")))
                .andReturn();
    }
}