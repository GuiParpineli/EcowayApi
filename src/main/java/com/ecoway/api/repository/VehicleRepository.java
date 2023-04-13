package com.ecoway.api.repository;

import com.ecoway.api.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    List<Vehicle> findVehicleByCity_Id(String city_id);
}

