package org.viktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.viktor.entity.ClientDataEntity;
import org.viktor.entity.UserEntity;

public interface ClientDataRepository extends JpaRepository<ClientDataEntity, Integer>,
                                                QuerydslPredicateExecutor<ClientDataEntity> {
}
