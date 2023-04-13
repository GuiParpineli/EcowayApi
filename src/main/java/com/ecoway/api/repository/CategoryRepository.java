package com.ecoway.api.repository;

import com.ecoway.api.model.Category;
import com.ecoway.api.model.City;
import com.ecoway.api.model.Vehicle;
import com.ecoway.api.model.VehicleModel;
import com.ecoway.api.model.enums.CategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    @Query("SELECT c FROM Category  c JOIN c.vehicles v ON v.model = ?1")
    Optional<Category> findByVehicleModel(String id);
    Category findByName(String name);
}
