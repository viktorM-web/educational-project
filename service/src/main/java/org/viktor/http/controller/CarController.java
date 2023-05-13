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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.viktor.dto.CarCreateDto;
import org.viktor.dto.CarFilterDto;
import org.viktor.dto.PageResponse;
import org.viktor.entity.Role;
import org.viktor.entity.Status;
import org.viktor.security.UserSecurity;
import org.viktor.service.CarCategoryService;
import org.viktor.service.CarService;
import org.viktor.service.ClientService;

@Controller
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final ClientService clientService;
    private final CarCategoryService categoryServiceService;

    @GetMapping
    public String findAll(Model model,
                          CarFilterDto filter,
                          Pageable pageable) {
        var page = carService.findAll(filter, pageable);
        model.addAttribute("cars", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "car/cars";
    }

    @GetMapping("/create")
    public String registration(Model model,
                               @ModelAttribute("car") CarCreateDto car) {
        model.addAttribute("car", car);
        model.addAttribute("categories", categoryServiceService.findAll());
        return "car/create";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id,
                           Model model,
                           @AuthenticationPrincipal UserDetails userDetails) {
        return carService.findById(id)
                .map(car -> {
                    model.addAttribute("user", (UserSecurity) userDetails);
                    model.addAttribute("admin", Role.ADMIN);
                    model.addAttribute("car", car);
                    model.addAttribute("client", clientService.findByUserId(((UserSecurity) userDetails).getId()));
                    model.addAttribute("status", Status.PROCESSING);
                    model.addAttribute("categories", categoryServiceService.findAll());
                    return "car/car";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(@Validated CarCreateDto car,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("car", car);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/cars/create";
        }
        return "redirect:/cars/" + carService.create(car).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @Validated CarCreateDto car) {
        return carService.update(id, car)
                .map(it -> "redirect:/cars/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (carService.delete(id)) {
            new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/cars";
    }
}
