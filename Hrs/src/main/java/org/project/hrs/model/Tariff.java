package org.project.hrs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "tariff")
public class Tariff {
    @Id
    Long id;
    String name;
    Integer basePrice;
}