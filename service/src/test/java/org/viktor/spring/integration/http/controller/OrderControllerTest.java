package org.viktor.spring.integration.http.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.viktor.dto.OrderCreateDto;
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
import static org.viktor.dto.OrderCreateDto.Fields.userId;
import static org.viktor.dto.OrderCreateDto.Fields.carId;
import static org.viktor.dto.OrderCreateDto.Fields.startDateUse;
import static org.viktor.dto.OrderCreateDto.Fields.expirationDate;
import static org.viktor.dto.OrderCreateDto.Fields.status;


@AutoConfigureMockMvc
@RequiredArgsConstructor
class OrderControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/orders")
                        .with(user(
                                new UserSecurity("ivan@mail.ru","123", List.of(Role.ADMIN), 1))))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("order/orders"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attributeExists("filter"))
                .andExpect(model().attributeExists("cars"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("statuses"))
                .andExpect(model().attributeExists("role"));
    }

    @Test
    void createWithError() throws Exception {
        mockMvc.perform(post("/orders").with(csrf())
                        .param(userId, "1")
                        .param(carId, "1")
                        .param(startDateUse, "2020-09-01 00:00:00")
                        .param(expirationDate, "2020-09-02 00:00:00")
                        .param(status, "PROCESSING")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/cars/1")
                );
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/orders").with(csrf())
                        .param(userId, "1")
                        .param(carId, "1")
                        .param(startDateUse, "2023-09-01 00:00:00")
                        .param(expirationDate, "2023-09-02 00:00:00")
                        .param(status, "PROCESSING")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/orders")
                );
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/orders/1")
                        .with(user(
                                new UserSecurity("ivan@mail.ru","123", List.of(Role.ADMIN), 1))))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("order/order"))
                .andExpect(model().attributeExists("order"))
                .andExpect(model().attributeExists("cars"))
                .andExpect(model().attributeExists("statuses"))
                .andExpect(model().attributeExists("admin"))
                .andExpect(model().attributeExists("extraPayment"));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/orders/1/update")
                        .with(csrf()))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/orders/1")
                );
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/orders/1/delete")
                        .with(csrf()))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/orders")
                );
    }
}