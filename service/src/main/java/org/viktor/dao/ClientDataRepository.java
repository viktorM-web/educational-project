package org.viktor.dao;

import org.viktor.entity.ClientDataEntity;

import javax.persistence.EntityManager;

public class ClientDataRepository extends RepositoryBase<Integer, ClientDataEntity> {

    public ClientDataRepository(EntityManager entityManager) {
        super(ClientDataEntity.class, entityManager);
    }
}
