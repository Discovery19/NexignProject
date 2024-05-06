package org.project.hrs.repositories;

import org.project.hrs.model.TariffStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TariffStatusRepository extends JpaRepository<TariffStatus, Long> {
    Optional<List<TariffStatus>> findAllByPhone(Long number);
}
