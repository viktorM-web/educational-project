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
import static org.viktor.dto.ExtraPaymentCreateDto.Fields.orderId;
import static org.viktor.dto.ExtraPaymentCreateDto.Fields.description;
import static org.viktor.dto.ExtraPaymentCreateDto.Fields.price;


@AutoConfigureMockMvc
@RequiredArgsConstructor
class ExtraPaymentControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/extra-payments").with(csrf())
                        .param(orderId, "2")
                        .param(description, "tests")
                        .param(price, "111.11"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/orders/2")
                );
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/extra-payments/1")
                        .with(user(
                                new UserSecurity("ivan@mail.ru","123", List.of(Role.ADMIN), 1))))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("extraPayment/extraPayment"))
                .andExpect(model().attributeExists("extraPayment"))
                .andExpect(model().attributeExists("admin"));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/extra-payments/1/update")
                        .with(csrf())
                .param(orderId, "1")
                .param(description, "1")
                .param(price, "111.05"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/extra-payments/1")
                );
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/extra-payments/1/delete")
                        .with(csrf()))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/extra-payments")
                );
    }
}