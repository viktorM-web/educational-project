package org.viktor.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.viktor.dto.OrderCreateDto;
import org.viktor.dto.OrderFilter;
import org.viktor.dto.PageResponse;
import org.viktor.entity.Role;
import org.viktor.entity.Status;
import org.viktor.security.UserSecurity;
import org.viktor.service.CarService;
import org.viktor.service.ExtraPaymentService;
import org.viktor.service.OrderService;
import org.viktor.service.UserService;
import org.viktor.validation.group.CreateAction;

import javax.validation.groups.Default;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final CarService carService;
    private final ExtraPaymentService extraPaymentService;

    @GetMapping
    public String findAll(Model model,
                          OrderFilter filter,
                          Pageable pageable,
                          @AuthenticationPrincipal UserDetails userDetails) {
        if (!userDetails.getAuthorities().contains(Role.ADMIN)) {
            var user = (UserSecurity) userDetails;
            filter = new OrderFilter(user.getId(),
                    filter.getCarId(),
                    filter.getStartDateUse(),
                    filter.getExpirationDate(),
                    filter.getStatus());
        } else {
            model.addAttribute("users", userService.findAll());
        }
        var page = orderService.findAll(filter, pageable);
        model.addAttribute("orders", PageResponse.of(page));
        model.addAttribute("filter", filter);
        model.addAttribute("role", Role.ADMIN);
        model.addAttribute("cars", carService.findAll());
        model.addAttribute("statuses", Status.values());

        return "order/orders";
    }

    @PostMapping
    public String create(@Validated({Default.class, CreateAction.class}) OrderCreateDto order,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/cars/" + order.getCarId();
        }
        orderService.create(order);
        return "redirect:/orders";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return orderService.findById(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    model.addAttribute("cars", carService.findAll());
                    model.addAttribute("statuses", Status.values());
                    model.addAttribute("admin", Role.ADMIN);
                    model.addAttribute("extraPayment", extraPaymentService.findByOrderId(id));
                    return "order/order";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @Validated({Default.class, CreateAction.class}) OrderCreateDto order,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/orders/{id}";
        }
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
