package org.project.brtservice.repository;

import org.project.brtservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByTariffId(Long tariffId);
}
