package com.ecoway.api.controller;

import com.ecoway.api.exceptions.ResourceNotFoundException;
import com.ecoway.api.exceptions.SaveErrorException;
import com.ecoway.api.model.Booking;
import com.ecoway.api.repository.BookingRepository;
import com.ecoway.api.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/private/booking", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookingController {
    private final BookingService service;

    @Autowired
    public BookingController(BookingService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() throws ResourceNotFoundException {
        return service.getAll();
    }
@GetMapping("byid")
    public ResponseEntity<?> getById(@RequestParam("id") UUID id) throws ResourceNotFoundException {
        return service.getById(id);
    }

    @GetMapping("byuserid")
    public ResponseEntity<?> getByUserId(@RequestParam("id") UUID id) throws ResourceNotFoundException {
        return service.getByUserId(id);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Booking booking) throws SaveErrorException, ResourceNotFoundException {
        return service.save(booking);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Booking booking) throws SaveErrorException {
        return service.update(booking);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam("id") UUID id) throws ResourceNotFoundException {
        return service.delete(id);
    }
}
