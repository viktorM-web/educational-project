package org.viktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.viktor.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>, QuerydslPredicateExecutor<UserEntity> {

    Optional<UserEntity> findByEmailAndPassword(String email, String password);
}
