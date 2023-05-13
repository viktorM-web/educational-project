package org.viktor.spring.integration.http.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.viktor.entity.Role;
import org.viktor.security.UserSecurity;
import org.viktor.spring.integration.IntegrationTestBase;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.viktor.dto.ClientCreatDto.Fields.userId;
import static org.viktor.dto.ClientCreatDto.Fields.firstname;
import static org.viktor.dto.ClientCreatDto.Fields.lastname;
import static org.viktor.dto.ClientCreatDto.Fields.birthday;
import static org.viktor.dto.ClientCreatDto.Fields.driverLicenceNo;
import static org.viktor.dto.ClientCreatDto.Fields.dateExpiry;
import static org.viktor.dto.ClientCreatDto.Fields.driverExperience;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class ClientControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void registration() throws Exception {
        mockMvc.perform(get("/clients/registration")
                        .with(user(
                                new UserSecurity("sveta@mail.ru","123", List.of(Role.ADMIN), 2))))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("client/registration"))
                .andExpect(model().attributeExists("client"));
    }

    @Test
    void createIfWrongData() throws Exception {
        mockMvc.perform(post("/clients/2")
                        .with(csrf())
                        .param(userId, "2")
                        .param(firstname, "test")
                        .param(lastname, "test")
                        .param(birthday, "2029-01-01")
                        .param(driverLicenceNo, "1234test")
                        .param(dateExpiry, "2030-01-01")
                        .param(driverExperience, "1")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/clients/registration"),
                        flash().attributeExists("errors", "client")
                        );
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/clients/2")
                .with(csrf())
                .param(userId, "2")
                .param(firstname, "test")
                .param(lastname, "test")
                .param(birthday, "2000-01-01")
                .param(driverLicenceNo, "1234test")
                .param(dateExpiry, "2030-01-01")
                .param(driverExperience, "1")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users/2")
                );
    }

    @Test
    void findByIdIfNotClient() throws Exception {
        mockMvc.perform(get("/clients/2")
                        .with(user(
                                new UserSecurity("sveta@mail.ru","123", List.of(Role.ADMIN), 2))))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/clients/registration"));
    }

    @Test
    void findByIdIfClientExist() throws Exception {
        mockMvc.perform(get("/clients/1")
                        .with(user(
                                new UserSecurity("ivan@mail.ru","123", List.of(Role.ADMIN), 1))))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("client/client"))
                .andExpect(model().attributeExists("client"));

    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/clients/1/update")
                        .with(csrf())
                        .param(userId, "1")
                        .param(firstname, "test")
                        .param(lastname, "test")
                        .param(birthday, "2000-01-01")
                        .param(driverLicenceNo, "1111qweqt")
                        .param(dateExpiry, "2030-01-01")
                        .param(driverExperience, "1"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/clients/1")
                );
    }
}