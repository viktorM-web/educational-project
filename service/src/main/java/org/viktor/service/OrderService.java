package org.viktor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.viktor.dto.OrderCreateDto;
import org.viktor.dto.OrderFilter;
import org.viktor.dto.OrderReadDto;
import org.viktor.mapper.OrderCreateMapper;
import org.viktor.mapper.OrderReadMapper;
import org.viktor.repository.OrderRepository;
import org.viktor.repository.QPredicate;

import java.util.List;
import java.util.Optional;

import static org.viktor.entity.QOrderEntity.orderEntity;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderCreateMapper orderCreateMapper;
    private final OrderReadMapper orderReadMapper;

    public Page<OrderReadDto> findAll(OrderFilter filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.getCarId(), orderEntity.car.id::eq)
                .add(filter.getUserId(), orderEntity.user.id::eq)
                .add(filter.getStartDateUse(), orderEntity.startDateUse::before)
                .add(filter.getExpirationDate(), orderEntity.expirationDate::after)
                .add(filter.getStatus(), orderEntity.status::eq)
                .buildAnd();
        return orderRepository.findAll(predicate, pageable)
                .map(orderReadMapper::map);
    }

    public List<OrderReadDto> findAll() {
        return orderRepository.findAll().stream()
                .map(orderReadMapper::map)
                .toList();
    }

    public Optional<OrderReadDto> findById(Integer id) {
        return orderRepository.findById(id)
                .map(orderReadMapper::map);
    }

    @Transactional
    public OrderReadDto create(OrderCreateDto orderDto) {
        return Optional.of(orderDto)
                .map(orderCreateMapper::map)
                .map(orderRepository::save)
                .map(orderReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<OrderReadDto> update(Integer id, OrderCreateDto orderCreateDto) {
        return orderRepository.findById(id)
                .map(entity -> orderCreateMapper.map(orderCreateDto, entity))
                .map(orderRepository::saveAndFlush)
                .map(orderReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return orderRepository.findById(id)
                .map(entity -> {
                    orderRepository.delete(entity);
                    orderRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
