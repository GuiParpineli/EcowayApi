package com.ecoway.api.model.dto;

import com.ecoway.api.model.Vehicle;
import com.ecoway.api.model.VehicleModel;
import com.ecoway.api.model.enums.CategoryName;
import com.fasterxml.jackson.annotation.JsonGetter;
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
public class VehicleDTO {

    private VehicleModel model;
    private String categoryName;
    private String city;

    public VehicleDTO(Vehicle vehicle) {
        this.categoryName = vehicle.getCategory().getName();
        this.city = vehicle.getCity().getCityName();
        this.model = vehicle.getModel();
    }
}
