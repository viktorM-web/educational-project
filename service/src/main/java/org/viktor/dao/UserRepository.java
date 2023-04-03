package org.viktor.dao;

import org.springframework.stereotype.Repository;
import org.viktor.entity.UserEntity;

import javax.persistence.EntityManager;

@Repository
public class UserRepository extends RepositoryBase<Integer, UserEntity> {

    public UserRepository(EntityManager entityManager) {
        super(UserEntity.class, entityManager);
    }
}
