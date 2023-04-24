package org.viktor.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.viktor.dto.OrderCreateDto;
import org.viktor.dto.OrderFilter;
import org.viktor.dto.PageResponse;
import org.viktor.service.OrderService;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public String findAll(Model model,
                          OrderFilter filter,
                          Pageable pageable) {
        var page = orderService.findAll(filter, pageable);
        model.addAttribute("orders", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "order/orders";
    }

    @PostMapping
    public String create(@Validated OrderCreateDto order) {
        return "redirect:/orders/" + orderService.create(order).getId();
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return orderService.findById(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    return "order/order";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id, @Validated OrderCreateDto order) {
        return orderService.update(id, order)
                .map(it -> "redirect:/orders/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (orderService.delete(id)) {
            new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/orders";
    }
}
