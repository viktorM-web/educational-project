package org.viktor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.viktor.dto.CarCategoryCreateDto;
import org.viktor.dto.CarCategoryFilterDto;
import org.viktor.dto.CarCategoryReadDto;
import org.viktor.mapper.CarCategoryCreateMapper;
import org.viktor.mapper.CarCategoryReadMapper;
import org.viktor.repository.CarCategoryRepository;
import org.viktor.repository.QPredicate;

import java.util.List;
import java.util.Optional;

import static org.viktor.entity.QCarCategoryEntity.carCategoryEntity;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarCategoryService {

    private final CarCategoryRepository carCategoryRepository;
    private final CarCategoryReadMapper carCategoryReadMapper;
    private final CarCategoryCreateMapper carCategoryCreateMapper;

    public Page<CarCategoryReadDto> findAll(CarCategoryFilterDto filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.getCategory(), carCategoryEntity.category::containsIgnoreCase)
                .add(filter.getDayPrice(), carCategoryEntity.dayPrice::loe)
                .buildAnd();
        return carCategoryRepository.findAll(predicate, pageable)
                .map(carCategoryReadMapper::map);
    }

    public List<CarCategoryReadDto> findAll() {
        return carCategoryRepository.findAll().stream()
                .map(carCategoryReadMapper::map)
                .toList();
    }

    public Optional<CarCategoryReadDto> findById(Integer id) {
        return carCategoryRepository.findById(id)
                .map(carCategoryReadMapper::map);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public CarCategoryReadDto create(CarCategoryCreateDto carCategoryDto) {
        return Optional.of(carCategoryDto)
                .map(carCategoryCreateMapper::map)
                .map(carCategoryRepository::save)
                .map(carCategoryReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public Optional<CarCategoryReadDto> update(Integer id, CarCategoryCreateDto carCategoryDto) {
        return carCategoryRepository.findById(id)
                .map(entity -> carCategoryCreateMapper.map(carCategoryDto, entity))
                .map(carCategoryRepository::saveAndFlush)
                .map(carCategoryReadMapper::map);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean delete(Integer id) {
        return carCategoryRepository.findById(id)
                .map(entity -> {
                    carCategoryRepository.delete(entity);
                    carCategoryRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
