package org.project.hrs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.project.hrs.model.TariffComponent;
import java.util.List;
import java.util.Optional;

public interface TariffComponentRepository extends JpaRepository<TariffComponent, Long> {
    Optional<List<TariffComponent>> findAllByTariffId(Long tariffId);
}
