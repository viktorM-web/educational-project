package org.viktor.spring.integration.http.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.viktor.entity.Role;
import org.viktor.security.UserSecurity;
import org.viktor.spring.integration.IntegrationTestBase;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.viktor.dto.UserCreateDto.Fields.email;
import static org.viktor.dto.UserCreateDto.Fields.password;
import static org.viktor.dto.UserCreateDto.Fields.role;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void registration() throws Exception {
        mockMvc.perform(get("/users/registration"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/registration"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("roles"));
    }

    @Test
    void createWithError() throws Exception {
        mockMvc.perform(post("/users").with(csrf())
                        .param(email, "test")
                        .param(password, "Test")
                        .param(role, "ADMIN")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users/registration")
                );
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users").with(csrf())
                        .param(email, "test@gmail.com")
                        .param(password, "Test")
                        .param(role, "ADMIN")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/login")
                );
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/users/1")
                        .with(user(
                                new UserSecurity("ivan@mail.ru","123", List.of(Role.ADMIN), 1))))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/user"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/users/1/delete")
                        .with(csrf()))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/login")
                );
    }
}