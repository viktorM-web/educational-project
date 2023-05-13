package org.viktor.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.viktor.dto.ClientCreatDto;
import org.viktor.dto.ClientFilter;
import org.viktor.dto.PageResponse;
import org.viktor.dto.UserReadDto;
import org.viktor.service.ClientService;

@Controller
@RequestMapping("/clients")
@SessionAttributes("user")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/registration")
    public String registration(Model model,
                               @ModelAttribute("user") UserReadDto user,
                               @ModelAttribute(value = "client") ClientCreatDto client) {
        model.addAttribute("client", client);
        return "client/registration";
    }

    @PostMapping("/{userId}")
    public String create(@Validated ClientCreatDto client,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("client", client);
            return "redirect:/clients/registration";
        }
        var clientReadDto = clientService.create(client);
        return "redirect:/users/" + clientReadDto.getUser().getId();
    }

    @GetMapping
    public String findAll(Model model,
                          ClientFilter filter,
                          Pageable pageable) {
        var page = clientService.findAll(filter, pageable);
        model.addAttribute("clients", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "client/clients";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id,
                           Model model) {
        return clientService.findById(id)
                .map(client -> {
                    model.addAttribute("client", client);
                    return "client/client";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @Validated ClientCreatDto client) {
        return clientService.update(id, client)
                .map(it -> "redirect:/clients/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (clientService.delete(id)) {
            new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/clients";
    }
}
