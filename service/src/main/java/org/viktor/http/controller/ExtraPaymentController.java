package org.viktor.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.viktor.dto.ExtraPaymentCreateDto;
import org.viktor.entity.Role;
import org.viktor.service.ExtraPaymentService;

@Controller
@RequestMapping("/extra-payments")
@RequiredArgsConstructor
public class ExtraPaymentController {

    private final ExtraPaymentService extraPaymentService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id,
                           Model model) {
        return extraPaymentService.findById(id)
                .map(payment -> {
                    model.addAttribute("extraPayment", payment);
                    model.addAttribute("admin", Role.ADMIN);
                    return "extraPayment/extraPayment";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(@Validated ExtraPaymentCreateDto extraPayment) {
        return "redirect:/orders/" + extraPaymentService.create(extraPayment).getOrder().getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @Validated ExtraPaymentCreateDto extraPayment) {
        return extraPaymentService.update(id, extraPayment)
                .map(it -> "redirect:/extra-payments/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (!extraPaymentService.delete(id)) {
            new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/extra-payments";
    }
}
