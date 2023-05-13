package org.viktor.http.rest;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.viktor.dto.CarCreateDto;
import org.viktor.dto.CarFilterDto;
import org.viktor.dto.CarReadDto;
import org.viktor.dto.PageResponse;
import org.viktor.service.CarService;

import static org.springframework.http.ResponseEntity.*;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("api/v1/cars")
@AllArgsConstructor
public class CarRestController {

    private final CarService carService;

    @GetMapping
    public PageResponse<CarReadDto> findAll(CarFilterDto filter, Pageable pageable) {
        var page = carService.findAll(filter, pageable);
        return PageResponse.of(page);
    }

    @GetMapping("/{id}")
    public CarReadDto findById(@PathVariable("id") Integer id) {
        return carService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "{id}/avatar")
    public ResponseEntity<byte[]> findAvatar(@PathVariable("id") Integer id) {
        return carService.findAvatar(id)
                .map(content -> ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(notFound()::build);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarReadDto create(@Validated @RequestBody CarCreateDto car) {
        return carService.create(car);
    }

    @PutMapping("/{id}/update")
    public CarReadDto update(@PathVariable("id") Integer id,
                             @Validated @RequestBody CarCreateDto car) {
        return carService.update(id, car)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return carService.delete(id)
                ? noContent().build()
                : notFound().build();
    }
}
