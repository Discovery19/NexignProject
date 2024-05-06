package org.project.hrs.model;

import jakarta.persistence.*;
import lombok.Data;

//@Entity
//@Data
//public class TariffComponent {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    Long id;
//    //    @OneToMany(fetch = FetchType.EAGER)
////    @JoinColumn(name = "type_id")
////    private Set<TypeComponent> components;
//    @ManyToOne
//    @JoinColumn(name = "tariff_id")
//    private Tariff tariff;
//    @ManyToOne
//    @JoinColumn(name = "type_component_id")
//    private TypeComponent typeComponent;
//    Integer quantity;
//    Double price;
////    @ManyToOne(fetch = FetchType.LAZY)
////    @JoinColumn(name = "measure_id")
////    Measure measure;
//}
@Entity
@Data
@Table(name = "tariff_component")
public class TariffComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    Integer quantity;
    Double price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_component_id")
    private TypeComponent typeComponent;
}