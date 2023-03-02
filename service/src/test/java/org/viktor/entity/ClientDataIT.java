package org.viktor.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientDataIT extends EntityTestBase {

    @Test
    void saveClientData() {
        UserEntity user = EntityUtil.buildUser();
        session.save(user);
        session.flush();
        ClientDataEntity clientData = EntityUtil.buildClientData(user);

        session.save(clientData);
        session.flush();

        assertThat(clientData.getId()).isNotNull();
    }

    @Test
    void getClientData() {
        UserEntity user = EntityUtil.buildUser();
        session.save(user);
        session.flush();
        ClientDataEntity expectedClientData = EntityUtil.buildClientData(user);
        session.save(expectedClientData);
        session.flush();
        session.clear();

        ClientDataEntity actualClientData = session.get(ClientDataEntity.class, expectedClientData.getId());

        assertThat(actualClientData).isEqualTo(expectedClientData);
    }

    @Test
    void update() {
        UserEntity user = EntityUtil.buildUser();
        session.save(user);
        session.flush();
        ClientDataEntity expectedClientData = EntityUtil.buildClientData(user);
        session.save(expectedClientData);
        session.flush();
        session.clear();

        expectedClientData.setDriverLicenceNo("12354ad");
        session.update(expectedClientData);
        session.flush();
        session.clear();

        ClientDataEntity actualClientData = session.get(ClientDataEntity.class, expectedClientData.getId());

        assertThat(actualClientData).isEqualTo(expectedClientData);
    }

    @Test
    void delete() {
        UserEntity user = EntityUtil.buildUser();
        session.save(user);
        session.flush();
        ClientDataEntity expectedClientData = EntityUtil.buildClientData(user);
        session.save(expectedClientData);
        session.flush();
        session.clear();

        session.delete(expectedClientData);
        session.flush();
        session.clear();

        ClientDataEntity actualClientData = session.get(ClientDataEntity.class, expectedClientData.getId());

        assertThat(actualClientData).isNull();
    }
}
