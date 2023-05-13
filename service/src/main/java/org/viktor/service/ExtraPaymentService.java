package org.viktor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.viktor.dto.ExtraPaymentCreateDto;
import org.viktor.dto.ExtraPaymentReadDto;
import org.viktor.mapper.ExtraPaymentCreateMapper;
import org.viktor.mapper.ExtraPaymentReadMapper;
import org.viktor.repository.ExtraPaymentRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExtraPaymentService {

    private final ExtraPaymentRepository extraPaymentRepository;
    private final ExtraPaymentCreateMapper extraPaymentCreateMapper;
    private final ExtraPaymentReadMapper extraPaymentReadMapper;

    public Optional<ExtraPaymentReadDto> findById(Integer id) {
        return extraPaymentRepository.findById(id)
                .map(extraPaymentReadMapper::map);
    }

    public Optional<ExtraPaymentReadDto> findByOrderId(Integer id) {
        return extraPaymentRepository.findByOrderId(id)
                .map(extraPaymentReadMapper::map);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public ExtraPaymentReadDto create(ExtraPaymentCreateDto paymentDto) {
        return Optional.of(paymentDto)
                .map(extraPaymentCreateMapper::map)
                .map(extraPaymentRepository::save)
                .map(extraPaymentReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public Optional<ExtraPaymentReadDto> update(Integer id, ExtraPaymentCreateDto extraPaymentCreateDto) {
        return extraPaymentRepository.findById(id)
                .map(entity -> extraPaymentCreateMapper.map(extraPaymentCreateDto, entity))
                .map(extraPaymentRepository::saveAndFlush)
                .map(extraPaymentReadMapper::map);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
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
