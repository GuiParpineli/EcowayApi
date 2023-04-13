package com.ecoway.api.model;

import com.ecoway.api.model.dto.InputVehicleModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "modelos")
public class VehicleModel {
    @Id
    private String id;
    @Column(name = "marca")
    private String brand;
    @Column(name = "modelo")
    private String model;
    @Column(name = "autonomia")
    private int autonomy;
    @Column(name = "tempo_recarga")
    private int rechargeTime;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_imagem")
    private Image image;

    public VehicleModel(InputVehicleModel inputModel) {
        this.id = inputModel.getId();
        this.brand = inputModel.getBrand();
        this.model = inputModel.getModel();
        this.autonomy = inputModel.getAutonomy();
        this.rechargeTime = inputModel.getRechargeTime();
    }
}
