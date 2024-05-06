package org.project.hrs.model;//package org.project.brtservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TariffStatus {
    @Id
            @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(name = "phone")
    private Long phone;

    @ManyToOne
    @JoinColumn(name = "type_component_id")
    private TypeComponent typeId;

    private Integer quantitySpent;
}
