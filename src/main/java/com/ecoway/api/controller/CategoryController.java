package com.ecoway.api.controller;

import com.ecoway.api.exceptions.ResourceNotFoundException;
import com.ecoway.api.exceptions.SaveErrorException;
import com.ecoway.api.model.Category;
import com.ecoway.api.model.enums.CategoryName;
import com.ecoway.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping(value = "api/", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {
    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("public/category")
    public ResponseEntity<?> getAll() throws ResourceNotFoundException {
        return service.getAll();
    }

    @GetMapping("public/category/id")
    public ResponseEntity<?> getById(@RequestParam("id") String id) throws ResourceNotFoundException {
        return service.get(id);
    }

    @GetMapping("public/category/available")
    public ResponseEntity<?> getStock(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam("dayIn") LocalDate dayIn,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam("dayOut") LocalDate dayOut,
            @RequestParam("city") String city)
     {
        return service.getStock(dayIn, dayOut, city);
    }

    @GetMapping("public/category/byname")
    public ResponseEntity<?> getByCategoryName(@RequestParam("name") String name) throws ResourceNotFoundException {
        return service.getByCategoryName(name);
    }

    @PostMapping("private/category/")
    public ResponseEntity<?> save(@RequestBody Category input) throws SaveErrorException {
        return service.save(input);
    }

    @PutMapping("private/category")
    public ResponseEntity<?> update(@RequestBody Category input) throws SaveErrorException {
        return service.update(input);
    }

    @DeleteMapping("private/category")
    public ResponseEntity<?> delete(@RequestParam("id") String id) throws ResourceNotFoundException {
        return service.delete(id);
    }
}