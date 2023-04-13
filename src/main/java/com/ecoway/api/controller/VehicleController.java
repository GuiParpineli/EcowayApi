package com.ecoway.api.controller;

import com.ecoway.api.exceptions.ResourceNotFoundException;
import com.ecoway.api.exceptions.SaveErrorException;
import com.ecoway.api.model.City;
import com.ecoway.api.model.Vehicle;
import com.ecoway.api.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/", produces = MediaType.APPLICATION_JSON_VALUE)
public class VehicleController {

    private final VehicleService service;

    @Autowired
    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @GetMapping("public/vehicle")
    public ResponseEntity<?> get() throws ResourceNotFoundException {
        return service.getAll();
    }
    @GetMapping("public/vehicle/id")
    public ResponseEntity<?> getById(@RequestParam("id") String id) throws ResourceNotFoundException {
        return service.get(id);
    }

    @GetMapping("public/vehicle/bycity")
    public ResponseEntity<?> getByCity(@RequestParam("city") City city) throws ResourceNotFoundException {
        return service.getByCity(city.getId());
    }

    @PostMapping("private/vehicle")
    public ResponseEntity<?> save(@RequestBody Vehicle input) throws SaveErrorException {
        return service.save(input);
    }

    @PutMapping("private/vehicle")
    public  ResponseEntity<?> update(@RequestBody Vehicle input) throws SaveErrorException {
        return service.update(input);
    }

    @DeleteMapping("private/vehicle")
    public ResponseEntity<?> delete(@RequestParam("id") String id) throws ResourceNotFoundException {
        return service.delete(id);
    }
}
