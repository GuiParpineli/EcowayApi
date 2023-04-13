package com.ecoway.api.service;

import com.ecoway.api.exceptions.ResourceNotFoundException;
import com.ecoway.api.exceptions.SaveErrorException;
import com.ecoway.api.model.Category;
import com.ecoway.api.model.Vehicle;
import com.ecoway.api.model.dto.VehicleDTO;
import com.ecoway.api.model.mapper.VehicleHateoasMapper;
import com.ecoway.api.repository.CategoryRepository;
import com.ecoway.api.repository.VehicleRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    private final VehicleRepository repository;
    private final Logger log = Logger.getLogger(VehicleService.class);
    private final CategoryRepository categoryRepository;

    @Autowired
    public VehicleService(VehicleRepository repository,
                          CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<?> getAll() throws ResourceNotFoundException {
        List<Vehicle> founded = repository.findAll();
        log.info(founded.toString());
        if (founded.isEmpty()) throw new ResourceNotFoundException("None vehicles founded");
        List<VehicleDTO> vehicleDTOS = founded.stream().map(VehicleDTO::new).toList();
        return ResponseEntity.ok(vehicleDTOS);
    }

    public ResponseEntity<?> get(String id) throws ResourceNotFoundException {
        Vehicle founded = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("None vehicles founded"));
        VehicleDTO vehicleDTO = new VehicleDTO(founded);
        return ResponseEntity.ok(vehicleDTO);
    }

    public ResponseEntity<?> getByCity(String city) throws ResourceNotFoundException {
        List<Vehicle> founded = repository.findVehicleByCity_Id(city);
        if (founded.isEmpty()) throw new ResourceNotFoundException("None vehicles founded");

        List<VehicleDTO> vehicleDTOS = founded.stream().map(VehicleDTO::new).toList();
        List<EntityModel<VehicleDTO>> outputVehicle = vehicleDTOS.stream()
                .map(VehicleHateoasMapper::linksVehicle).toList();

        return ResponseEntity.ok(outputVehicle);
    }

    public ResponseEntity<?> save(Vehicle input) throws SaveErrorException {
        Vehicle saved;
        Category category = categoryRepository.findByVehicleModel(input.getModel().getId()).orElseThrow();
        input.setCategory(category);
        try {
            log.info("input: " + input);
            saved = repository.save(input);
            log.info("saved:" + saved);
        } catch (Exception e) {
            log.info("input: " + input);
            log.info(e.getMessage());
            throw new SaveErrorException("Error, not save");
        }
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<?> update(Vehicle input) throws SaveErrorException {
        Vehicle saved;
        try {
            log.info("input: " + input);
            saved = repository.saveAndFlush(input);
            log.info(saved.toString());
        } catch (Exception e) {
            log.info("input: " + input);
            log.info(e.getMessage());
            throw new SaveErrorException("Error, not save");
        }
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<?> delete(String id) throws ResourceNotFoundException {
        Vehicle founded = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("None vehicles founded"));
        repository.deleteById(founded.getId());
        return ResponseEntity.ok("Vehicle " + "deleted successfully");
    }
}