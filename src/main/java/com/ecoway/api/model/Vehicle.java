package com.ecoway.api.model;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "veiculos")
public class Vehicle {
    @Id
    private String id;
    @Column(name = "cor")
    private String color;
    @OneToOne
    @JoinColumn(name = "id_modelo")
    private VehicleModel model;
    @OneToOne
    @JoinColumn(name = "id_categoria")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "id_cidade")
    private City city;
}
