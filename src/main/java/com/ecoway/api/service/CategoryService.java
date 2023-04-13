package com.ecoway.api.service;

import com.ecoway.api.exceptions.ResourceNotFoundException;
import com.ecoway.api.exceptions.SaveErrorException;
import com.ecoway.api.model.Booking;
import com.ecoway.api.model.Category;
import com.ecoway.api.model.Vehicle;
import com.ecoway.api.repository.CategoryRepository;
import com.ecoway.api.repository.CityRepository;
import com.ecoway.api.repository.VehicleRepository;
import com.ecoway.api.service.util.Reservation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository repository;
    private final BookingService bookingService;
    private final Logger log = Logger.getLogger(CategoryService.class);
    private final CityRepository cityRepository;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public CategoryService(CategoryRepository repository, BookingService bookingService,
                           CityRepository cityRepository,
                           VehicleRepository vehicleRepository) {
        this.repository = repository;
        this.bookingService = bookingService;
        this.cityRepository = cityRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public ResponseEntity<?> getAll() throws ResourceNotFoundException {
        List<Category> founded = repository.findAll();
        log.info(founded.toString());
        if (founded.isEmpty()) throw new ResourceNotFoundException("None vehicles founded`");
        return ResponseEntity.ok(founded);
    }

    public ResponseEntity<?> get(String id) throws ResourceNotFoundException {
        Category founded = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("None vehicles founded"));
        return ResponseEntity.ok(founded);
    }

    public ResponseEntity<?> getByCategoryName(String name) throws ResourceNotFoundException {
        Category founded = repository.findByName(name);
        return ResponseEntity.ok(founded);
    }

    public ResponseEntity<?> getStock(LocalDate dayIn, LocalDate dayOut, String city) {

        List<String> freeCategories = new ArrayList<>();

        List<Vehicle> vehicles = vehicleRepository.findVehicleByCity_Id(city);

        vehicles.forEach(a -> {
            try {
                List<Booking> total = bookingService.countBookingsActives(dayIn, dayOut, a.getCategory().getId(), city);
                Booking bookingUser = Booking.builder()
                        .category(repository.findById(a.getCategory().getId()).get())
                        .checkInDay(dayIn)
                        .checkOutDay(dayOut)
                        .city(cityRepository.findById(city).get())
                        .build();
                if (Reservation.isBookingPossible(bookingUser, total)) {
                    freeCategories.add(a.getCategory().getId());
                }
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        log.info(freeCategories);

        List<Category> categoriesFree = repository.findAllById(freeCategories);
        return ResponseEntity.ok(categoriesFree);
    }

    public ResponseEntity<?> save(Category input) throws SaveErrorException {
        Category saved;
        try {
            saved = repository.save(input);
            log.info("input: " + input);
            log.info(saved.toString());
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new SaveErrorException("Error, not save");
        }
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<?> update(Category input) throws SaveErrorException {
        Category saved;
        try {
            saved = repository.saveAndFlush(input);
            log.info("input: " + input);
            log.info(saved);
        } catch (Exception e) {
            throw new SaveErrorException("Error, not save");
        }
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<?> delete(String id) throws ResourceNotFoundException {
        Category founded = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("None vehicles founded"));
        repository.deleteById(founded.getId());
        return ResponseEntity.ok("Category " + founded.getName() + " deleted successfully");
    }

}
