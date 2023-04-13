package com.ecoway.api.controller;

import com.ecoway.api.exceptions.ResourceNotFoundException;
import com.ecoway.api.exceptions.SaveErrorException;
import com.ecoway.api.model.Feature;
import com.ecoway.api.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "api/", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeatureController {
    private final FeatureService service;

    @Autowired
    public FeatureController(FeatureService service) {
        this.service = service;
    }

    @GetMapping("public/feature")
    public ResponseEntity<?> get() throws ResourceNotFoundException {
        return service.getAll();
    }
    @GetMapping("public/feature/id")
    public ResponseEntity<?> getById(@RequestParam("id") String id) throws ResourceNotFoundException {
        return service.get(id);
    }

    @PostMapping("private/feature")
    public ResponseEntity<?> save(@RequestBody Feature input) throws SaveErrorException {
        return service.save(input);
    }

    @PutMapping("private/feature")
    public  ResponseEntity<?> update(@RequestBody Feature input) throws SaveErrorException {
        return service.update(input);
    }

    @DeleteMapping("private/feature")
    public ResponseEntity<?> delete(@RequestParam("id") String id) throws ResourceNotFoundException {
        return service.delete(id);
    }
}
