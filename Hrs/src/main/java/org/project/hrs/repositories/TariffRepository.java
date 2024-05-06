package org.project.hrs.repositories;

import org.project.hrs.model.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TariffRepository extends JpaRepository<Tariff, Long>{
    @Query("SELECT t FROM Tariff t WHERE t.basePrice IS NOT NULL AND t.basePrice != 0")
    List<Tariff> findAllWithNonNullBasePrice();
}
