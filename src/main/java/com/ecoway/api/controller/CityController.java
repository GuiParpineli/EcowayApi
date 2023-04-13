package com.ecoway.api.controller;

import com.ecoway.api.exceptions.ResourceNotFoundException;
import com.ecoway.api.exceptions.SaveErrorException;
import com.ecoway.api.model.City;
import com.ecoway.api.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "api/", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController {
    private final CityService service;

    @Autowired
    public CityController(CityService service) {
        this.service = service;
    }

    @GetMapping("public/city")
    public ResponseEntity<?> get() throws ResourceNotFoundException {
        return service.getAll();
    }
    @GetMapping("public/city/id")
    public ResponseEntity<?> getById(@RequestParam("id") String id) throws ResourceNotFoundException {
        return service.get(id);
    }

    @PostMapping("private/city")
    public ResponseEntity<?> save(@RequestBody City input) throws SaveErrorException {
        return service.save(input);
    }

    @PutMapping("private/city")
    public  ResponseEntity<?> update(@RequestBody City input) throws SaveErrorException {
        return service.update(input);
    }

    @DeleteMapping("private/city")
    public ResponseEntity<?> delete(@RequestParam("id") String id) throws ResourceNotFoundException {
        return service.delete(id);
    }
}
