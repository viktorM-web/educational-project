package org.viktor.http.controller;

import lombok.RequiredArgsConstructor;
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
import org.viktor.service.ClientService;
import org.viktor.service.UserService;

@Controller
@RequestMapping("/clients")
@SessionAttributes("user")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;

    @GetMapping("/registration")
    public String registration(Model model,
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
        clientService.create(client);
        return "redirect:/users/{userId}";
    }

    @GetMapping("/{userId}")
    public String findById(@PathVariable("userId") Integer id,
                           Model model) {

        return clientService.findByUserId(id)
                .map(client -> {
                    model.addAttribute("client", client);
                    return "client/client";
                })
                .orElseGet(() -> {
                    return "redirect:/clients/registration";
                });
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @Validated ClientCreatDto client) {
        return clientService.update(id, client)
                .map(it -> "redirect:/clients/"+it.getUser().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
