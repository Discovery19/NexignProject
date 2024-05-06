package org.project.brtservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "user_table")
public class User {
    @Id
    private Long id;
    @Column(name = "balance")
    private Integer balance;
    @Column(name = "tariff_id")
    private Long tariffId;
}
