package org.viktor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.viktor.dto.ClientCreatDto;
import org.viktor.dto.ClientFilter;
import org.viktor.dto.ClientReadDto;
import org.viktor.mapper.ClientCreateMapper;
import org.viktor.mapper.ClientReadMapper;
import org.viktor.repository.ClientDataRepository;
import org.viktor.repository.QPredicate;

import java.util.List;
import java.util.Optional;

import static org.viktor.entity.QClientDataEntity.clientDataEntity;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientService {

    private final ClientDataRepository clientRepository;
    private final ClientCreateMapper clientCreateMapper;
    private final ClientReadMapper clientReadMapper;

    public Page<ClientReadDto> findAll(ClientFilter filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.getFirstname(), clientDataEntity.firstname::containsIgnoreCase)
                .add(filter.getLastname(), clientDataEntity.lastname::containsIgnoreCase)
                .add(filter.getBirthday(), clientDataEntity.birthday::before)
                .add(filter.getDriverLicenceNo(), clientDataEntity.driverLicenceNo::containsIgnoreCase)
                .add(filter.getDateExpiry(), clientDataEntity.dateExpiry::after)
                .add(filter.getDriverExperience(), clientDataEntity.driverExperience::eq)
                .buildAnd();
        return clientRepository.findAll(predicate, pageable)
                .map(clientReadMapper::map);
    }

    public List<ClientReadDto> findAll() {
        return clientRepository.findAll().stream()
                .map(clientReadMapper::map)
                .toList();
    }

    public Optional<ClientReadDto> findById(Integer id) {
        return clientRepository.findById(id)
                .map(clientReadMapper::map);

    }public Optional<ClientReadDto> findByUserId(Integer id) {
        return clientRepository.findByUserId(id)
                .map(clientReadMapper::map);
    }

    @Transactional
    public ClientReadDto create(ClientCreatDto clientDto) {
        return Optional.of(clientDto)
                .map(clientCreateMapper::map)
                .map(clientRepository::save)
                .map(clientReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ClientReadDto> update(Integer id, ClientCreatDto clientDto) {
        return clientRepository.findById(id)
                .map(entity -> clientCreateMapper.map(clientDto, entity))
                .map(clientRepository::saveAndFlush)
                .map(clientReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return clientRepository.findById(id)
                .map(entity -> {
                    clientRepository.delete(entity);
                    clientRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
