package org.viktor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.viktor.dto.ClientCreatDto;
import org.viktor.dto.ClientReadDto;
import org.viktor.mapper.ClientCreateMapper;
import org.viktor.mapper.ClientReadMapper;
import org.viktor.repository.ClientDataRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientService {

    private final ClientDataRepository clientRepository;
    private final ClientCreateMapper clientCreateMapper;
    private final ClientReadMapper clientReadMapper;

    public Optional<ClientReadDto> findById(Integer id) {
        return clientRepository.findById(id)
                .map(clientReadMapper::map);
    }

    public Optional<ClientReadDto> findByUserId(Integer id) {
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
}
