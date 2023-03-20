package org.viktor.dao;

import org.junit.jupiter.api.Test;
import org.viktor.entity.ClientDataEntity;
import org.viktor.entity.EntityUtil;
import org.viktor.entity.UserEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientDataRepositoryTest extends RepositoryTestBase {

    private static final ClientDataRepository clientDataRepository = new ClientDataRepository(session);
    private static final UserRepository userRepository = new UserRepository(session);

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
        session.clear();

        assertThat(clientDataRepository.findById(expectedClientData.getId())).isEmpty();
    }

    @Test
    void update() {
        ClientDataEntity expectedClientData = clientDataRepository.findById(1).get();

        expectedClientData.setDriverLicenceNo("12354aa");
        clientDataRepository.update(expectedClientData);
        session.clear();

        assertThat(clientDataRepository.findById(expectedClientData.getId()).get()).isEqualTo(expectedClientData);
    }

    @Test
    void findById() {
        ClientDataEntity expectedClientData = clientDataRepository.findById(1).get();
        session.clear();

        assertThat(expectedClientData.getDriverLicenceNo()).isEqualTo("123456AB");
    }

    @Test
    void findAll() {
        List<ClientDataEntity> expectedClientData = clientDataRepository.findAll();
        session.clear();

        assertThat(expectedClientData).hasSize(1);
    }
}
