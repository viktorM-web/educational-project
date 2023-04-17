package org.viktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.viktor.entity.ClientDataEntity;

public interface ClientDataRepository extends JpaRepository<ClientDataEntity, Integer> {
}
