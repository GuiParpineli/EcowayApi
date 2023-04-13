package com.ecoway.api.controller;

import com.ecoway.api.exceptions.ResourceNotFoundException;
import com.ecoway.api.exceptions.SaveErrorException;
import com.ecoway.api.model.Image;
import com.ecoway.api.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@RequestMapping(value = "api/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageController {
    private final ImageService service;

    @Autowired
    public ImageController(ImageService service) {
        this.service = service;
    }

    @GetMapping("public/image")
    public ResponseEntity<?> get() throws ResourceNotFoundException {
        return service.getAll();
    }
    @GetMapping("public/image/id")
    public ResponseEntity<?> getById(@RequestParam("id") String id) throws ResourceNotFoundException {
        return service.get(id);
    }

    @PostMapping("private/image")
    public ResponseEntity<?> save(@RequestBody Image input) throws SaveErrorException {
        return service.save(input);
    }

    @PutMapping("private/image")
    public  ResponseEntity<?> update(@RequestBody Image input) throws SaveErrorException {
        return service.update(input);
    }

    @DeleteMapping("private/image")
    public ResponseEntity<?> delete(@RequestParam("id") String id) throws ResourceNotFoundException {
        return service.delete(id);
    }
}
