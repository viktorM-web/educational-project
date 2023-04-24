package org.viktor.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.viktor.dto.ClientCreatDto;
import org.viktor.entity.ClientDataEntity;
import org.viktor.entity.UserEntity;
import org.viktor.repository.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientCreateMapper implements Mapper<ClientCreatDto, ClientDataEntity> {

    private final UserRepository userRepository;

    @Override
    public ClientDataEntity map(ClientCreatDto fromObject, ClientDataEntity toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public ClientDataEntity map(ClientCreatDto object) {
        var client = new ClientDataEntity();
        copy(object, client);
        return client;
    }

    private void copy(ClientCreatDto object, ClientDataEntity client) {
        client.setUser(getUser(object.getUserId()));
        client.setFirstname(object.getFirstname());
        client.setLastname(object.getLastname());
        client.setBirthday(object.getBirthday());
        client.setDriverLicenceNo(object.getDriverLicenceNo());
        client.setDateExpiry(object.getDateExpiry());
        client.setDriverExperience(object.getDriverExperience());
    }

    private UserEntity getUser(Integer userId) {
        return Optional.ofNullable(userId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }
}
