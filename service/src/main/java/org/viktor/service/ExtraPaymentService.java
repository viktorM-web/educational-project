package org.viktor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.viktor.dto.ExtraPaymentCreateDto;
import org.viktor.dto.ExtraPaymentFilter;
import org.viktor.dto.ExtraPaymentReadDto;
import org.viktor.mapper.ExtraPaymentCreateMapper;
import org.viktor.mapper.ExtraPaymentReadMapper;
import org.viktor.repository.ExtraPaymentRepository;
import org.viktor.repository.QPredicate;

import java.util.List;
import java.util.Optional;

import static org.viktor.entity.QExtraPaymentEntity.extraPaymentEntity;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExtraPaymentService {

    private final ExtraPaymentRepository extraPaymentRepository;
    private final ExtraPaymentCreateMapper extraPaymentCreateMapper;
    private final ExtraPaymentReadMapper extraPaymentReadMapper;

    public Page<ExtraPaymentReadDto> findAll(ExtraPaymentFilter filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.getOrderId(), extraPaymentEntity.order.id::eq)
                .add(filter.getPrice(), extraPaymentEntity.price::loe)
                .add(filter.getDescription(), extraPaymentEntity.description::containsIgnoreCase)
                .buildAnd();
        return extraPaymentRepository.findAll(predicate, pageable)
                .map(extraPaymentReadMapper::map);
    }

    public List<ExtraPaymentReadDto> findAll() {
        return extraPaymentRepository.findAll().stream()
                .map(extraPaymentReadMapper::map)
                .toList();
    }

    public Optional<ExtraPaymentReadDto> findById(Integer id) {
        return extraPaymentRepository.findById(id)
                .map(extraPaymentReadMapper::map);
    }

    @Transactional
    public ExtraPaymentReadDto create(ExtraPaymentCreateDto paymentDto) {
        return Optional.of(paymentDto)
                .map(extraPaymentCreateMapper::map)
                .map(extraPaymentRepository::save)
                .map(extraPaymentReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ExtraPaymentReadDto> update(Integer id, ExtraPaymentCreateDto extraPaymentCreateDto) {
        return extraPaymentRepository.findById(id)
                .map(entity -> extraPaymentCreateMapper.map(extraPaymentCreateDto, entity))
                .map(extraPaymentRepository::saveAndFlush)
                .map(extraPaymentReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return extraPaymentRepository.findById(id)
                .map(entity -> {
                    extraPaymentRepository.delete(entity);
                    extraPaymentRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
