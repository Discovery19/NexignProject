package org.project.cdrservice.repository;


import org.project.cdrservice.model.Cdr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CdrRepository extends JpaRepository<Cdr, Long> {

}
