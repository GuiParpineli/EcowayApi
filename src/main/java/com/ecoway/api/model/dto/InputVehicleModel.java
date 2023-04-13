package com.ecoway.api.model.dto;

import com.ecoway.api.model.Image;
import com.ecoway.api.model.VehicleModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InputVehicleModel {
    private String id;
    private String brand;
    private String model;
    private int autonomy;
    private int rechargeTime;
    private Image image;
    private String categoryName;
}
