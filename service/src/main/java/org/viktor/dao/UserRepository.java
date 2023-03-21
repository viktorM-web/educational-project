package org.viktor.dao;

import org.viktor.entity.UserEntity;

import javax.persistence.EntityManager;

public class UserRepository extends RepositoryBase<Integer, UserEntity> {

    public UserRepository(EntityManager entityManager) {
        super(UserEntity.class, entityManager);
    }
}
