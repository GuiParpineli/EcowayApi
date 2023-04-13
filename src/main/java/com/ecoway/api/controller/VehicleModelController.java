package com.ecoway.api.controller;

import com.ecoway.api.exceptions.ResourceNotFoundException;
import com.ecoway.api.exceptions.SaveErrorException;
import com.ecoway.api.model.VehicleModel;
import com.ecoway.api.model.dto.InputVehicleModel;
import com.ecoway.api.service.VehicleModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/private/vehiclemodel", produces = MediaType.APPLICATION_JSON_VALUE)
public class VehicleModelController {
   private final VehicleModelService service;
   @Autowired
    public VehicleModelController(VehicleModelService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<?> getAll() throws ResourceNotFoundException {
        return service.getAll();
    }

    @GetMapping("byid")
    public ResponseEntity<?> getById(@RequestParam("id") String id) throws ResourceNotFoundException {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody InputVehicleModel vehicleModel) throws SaveErrorException, ResourceNotFoundException {
        return service.save(vehicleModel);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody VehicleModel vehicleModel) throws SaveErrorException {
        return service.update(vehicleModel);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam("id") String id) throws ResourceNotFoundException {
        return service.delete(id);
    }
}
