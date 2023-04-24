package org.viktor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.viktor.dto.LoginDto;
import org.viktor.dto.UserCreateDto;
import org.viktor.dto.UserFilter;
import org.viktor.dto.UserReadDto;
import org.viktor.mapper.UserCreateMapper;
import org.viktor.mapper.UserReadMapper;
import org.viktor.repository.QPredicate;
import org.viktor.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.viktor.entity.QUserEntity.userEntity;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;

    public Optional<UserReadDto> login(LoginDto login) {
        return userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .map(userReadMapper::map);
    }

    public Page<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.getEmail(), userEntity.email::containsIgnoreCase)
                .add(filter.getRole(), userEntity.role::eq)
                .buildAnd();
        return userRepository.findAll(predicate, pageable)
                .map(userReadMapper::map);
    }

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::map)
                .toList();
    }

    public Optional<UserReadDto> findById(Integer id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    @Transactional
    public UserReadDto create(UserCreateDto userDto) {
        return Optional.of(userDto)
                .map(userCreateMapper::map)
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Integer id, UserCreateDto userDto) {
        return userRepository.findById(id)
                .map(entity -> userCreateMapper.map(userDto, entity))
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}