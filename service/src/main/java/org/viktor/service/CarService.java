package org.viktor.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.viktor.dto.CarCreateDto;
import org.viktor.dto.CarFilterDto;
import org.viktor.dto.CarReadDto;
import org.viktor.entity.CarEntity;
import org.viktor.mapper.CarCreateMapper;
import org.viktor.mapper.CarReadMapper;
import org.viktor.repository.CarRepository;
import org.viktor.repository.QPredicate;

import java.util.List;
import java.util.Optional;

import static org.viktor.entity.QCarEntity.carEntity;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarService {

    private final CarRepository carRepository;
    private final CarCreateMapper carCreateMapper;
    private final CarReadMapper carReadMapper;
    private final ImageService imageService;

    public Page<CarReadDto> findAll(CarFilterDto filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.getCategory(), carEntity.carCategory.category::containsIgnoreCase)
                .add(filter.getMaxDayPrice(), carEntity.carCategory.dayPrice::loe)
                .add(filter.getBrand(), carEntity.brand::containsIgnoreCase)
                .add(filter.getModel(), carEntity.model::containsIgnoreCase)
                .add(filter.getOlderYearIssue(), carEntity.yearIssue::goe)
                .add(filter.getColour(), carEntity.colour::containsIgnoreCase)
                .add(filter.getMinSeatsQuantity(), carEntity.seatsQuantity::goe)
                .buildAnd();
        return carRepository.findAll(predicate, pageable)
                .map(carReadMapper::map);
    }

    public List<CarReadDto> findAll() {
        return carRepository.findAll().stream()
                .map(carReadMapper::map)
                .toList();
    }

    public Optional<CarReadDto> findById(Integer id) {
        return carRepository.findById(id)
                .map(carReadMapper::map);
    }

    public Optional<byte[]> findAvatar(Integer id) {
        return carRepository.findById(id)
                .map(CarEntity::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
    public CarReadDto create(CarCreateDto carDto) {
        return Optional.of(carDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return carCreateMapper.map(dto);
                })
                .map(carRepository::save)
                .map(carReadMapper::map)
                .orElseThrow();
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    @Transactional
    public Optional<CarReadDto> update(Integer id, CarCreateDto carCreateDto) {
        return carRepository.findById(id)
                .map(entity -> {
                    uploadImage(carCreateDto.getImage());
                    return carCreateMapper.map(carCreateDto, entity);
                })
                .map(carRepository::saveAndFlush)
                .map(carReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return carRepository.findById(id)
                .map(entity -> {
                    carRepository.delete(entity);
                    carRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
