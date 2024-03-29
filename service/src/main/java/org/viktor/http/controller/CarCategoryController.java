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
import org.viktor.dto.CarCategoryCreateDto;
import org.viktor.dto.CarCategoryFilterDto;
import org.viktor.dto.PageResponse;
import org.viktor.service.CarCategoryService;

@Controller
@RequestMapping("/carCategories")
@RequiredArgsConstructor
public class CarCategoryController {

    private final CarCategoryService carCategoryService;

    @GetMapping
    public String findAll(Model model,
                          CarCategoryFilterDto filter,
                          Pageable pageable) {
        var page = carCategoryService.findAll(filter, pageable);
        model.addAttribute("carCategories", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "carCategory/carCategories";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id,
                           Model model) {
        return carCategoryService.findById(id)
                .map(carCategory -> {
                    model.addAttribute("carCategory", carCategory);
                    return "carCategory/carCategory";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(@Validated CarCategoryCreateDto carCategory) {
        return "redirect:/carCategories/" + carCategoryService.create(carCategory).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @Validated CarCategoryCreateDto carCategory) {
        return carCategoryService.update(id, carCategory)
                .map(it -> "redirect:/carCategories/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (carCategoryService.delete(id)) {
            new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/carCategories";
    }
}
