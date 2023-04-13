package com.ecoway.api.repository;

import com.ecoway.api.model.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleModelRepository extends JpaRepository<VehicleModel, String> {
    VehicleModel findVehicleModelByModel(String model);
}
