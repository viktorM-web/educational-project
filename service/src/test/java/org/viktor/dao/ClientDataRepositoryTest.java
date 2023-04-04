package org.viktor.dao;

import org.junit.jupiter.api.Test;
import org.viktor.entity.ClientDataEntity;
import org.viktor.entity.EntityUtil;
import org.viktor.entity.UserEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClientDataRepositoryTest extends RepositoryTestBase {

    private final ClientDataRepository clientDataRepository = context.getBean(ClientDataRepository.class);
    private final UserRepository userRepository = context.getBean(UserRepository.class);

    @Test
    void save() {
        UserEntity user = EntityUtil.buildUser();
        userRepository.save(user);
        ClientDataEntity clientData = EntityUtil.buildClientData(user);
        clientDataRepository.save(clientData);

        assertThat(clientData.getId()).isNotNull();
    }

    @Test
    void delete() {
        ClientDataEntity expectedClientData = clientDataRepository.findById(1).get();

        clientDataRepository.delete(expectedClientData);
        entityManager.clear();

        assertThat(clientDataRepository.findById(expectedClientData.getId())).isEmpty();
    }

    @Test
    void update() {
        ClientDataEntity expectedClientData = clientDataRepository.findById(1).get();

        expectedClientData.setDriverLicenceNo("12354aa");
        clientDataRepository.update(expectedClientData);
        entityManager.clear();

        assertThat(clientDataRepository.findById(expectedClientData.getId()).get()).isEqualTo(expectedClientData);
    }

    @Test
    void findById() {
        ClientDataEntity expectedClientData = clientDataRepository.findById(1).get();
        entityManager.clear();

        assertThat(expectedClientData.getDriverLicenceNo()).isEqualTo("123456AB");
    }

    @Test
    void findAll() {
        List<ClientDataEntity> expectedClientData = clientDataRepository.findAll();
        entityManager.clear();

        assertThat(expectedClientData).hasSize(1);
    }
}
