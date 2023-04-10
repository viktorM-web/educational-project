package org.viktor.spring.integration.database.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.viktor.repository.ClientDataRepository;
import org.viktor.repository.UserRepository;
import org.viktor.entity.ClientDataEntity;
import org.viktor.spring.integration.IntegrationTestBase;
import org.viktor.util.EntityUtil;
import org.viktor.entity.UserEntity;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class ClientDataRepositoryTest extends IntegrationTestBase {

    private final ClientDataRepository clientDataRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @Test
    void save() {
        ClientDataEntity clientData = saveClient();

        assertThat(clientData.getId()).isNotNull();
    }

    @Test
    void delete() {
        ClientDataEntity clientData = saveClient();
        entityManager.clear();

        ClientDataEntity expectedClientData = clientDataRepository.findById(clientData.getId()).get();

        clientDataRepository.delete(expectedClientData);
        entityManager.flush();
        entityManager.clear();

        assertThat(clientDataRepository.findById(expectedClientData.getId())).isEmpty();
    }

    @Test
    void update() {
        ClientDataEntity clientData = saveClient();
        entityManager.clear();

        ClientDataEntity expectedClientData = clientDataRepository.findById(clientData.getId()).get();

        expectedClientData.setDriverLicenceNo("XXXXXXXX");
        clientDataRepository.saveAndFlush(expectedClientData);
        entityManager.clear();

        assertThat(clientDataRepository.findById(expectedClientData.getId()).get()).isEqualTo(expectedClientData);
    }

    @Test
    void findById() {
        ClientDataEntity clientData = saveClient();
        entityManager.clear();

        ClientDataEntity expectedClientData = clientDataRepository.findById(clientData.getId()).get();
        entityManager.clear();

        assertThat(expectedClientData.getDriverLicenceNo()).isEqualTo("7777777777");
    }

    @Test
    void findAll() {
        List<ClientDataEntity> expectedClientData = clientDataRepository.findAll();
        entityManager.clear();

        assertThat(expectedClientData).hasSize(1);
    }

    private ClientDataEntity saveClient() {
        UserEntity user = EntityUtil.buildUser();
        userRepository.save(user);
        ClientDataEntity clientData = EntityUtil.buildClientData(user);
        clientDataRepository.save(clientData);
        return clientData;
    }
}
