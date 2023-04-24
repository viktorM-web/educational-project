package org.viktor.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.viktor.dto.ClientReadDto;
import org.viktor.entity.ClientDataEntity;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientReadMapper implements Mapper<ClientDataEntity, ClientReadDto> {

    private final UserReadMapper userReadMapper;

    @Override
    public ClientReadDto map(ClientDataEntity object) {
        var userReadDto = Optional.ofNullable(object.getUser())
                .map(userReadMapper::map)
                .orElse(null);

        return new ClientReadDto(
                object.getId(),
                userReadDto,
                object.getFirstname(),
                object.getLastname(),
                object.getBirthday(),
                object.getDriverLicenceNo(),
                object.getDateExpiry(),
                object.getDriverExperience()
        );
    }
}
