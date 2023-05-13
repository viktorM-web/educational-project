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
import static org.viktor.dto.OrderCreateDto.Fields.carId;
import static org.viktor.dto.OrderCreateDto.Fields.expirationDate;
import static org.viktor.dto.OrderCreateDto.Fields.startDateUse;
import static org.viktor.dto.OrderCreateDto.Fields.status;
import static org.viktor.dto.OrderCreateDto.Fields.userId;


@AutoConfigureMockMvc
@RequiredArgsConstructor
class LoginControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void loginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/login"));
    }
}