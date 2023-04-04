package org.viktor.dao;

import org.springframework.stereotype.Repository;
import org.viktor.entity.ClientDataEntity;

import javax.persistence.EntityManager;

@Repository
public class ClientDataRepository extends RepositoryBase<Integer, ClientDataEntity> {

    public ClientDataRepository(EntityManager entityManager) {
        super(ClientDataEntity.class, entityManager);
    }
}
