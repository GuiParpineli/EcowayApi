package com.ecoway.api.service;

import com.ecoway.api.exceptions.ResourceNotFoundException;
import com.ecoway.api.exceptions.SaveErrorException;
import com.ecoway.api.model.Category;
import com.ecoway.api.model.Image;
import com.ecoway.api.model.VehicleModel;
import com.ecoway.api.model.dto.InputVehicleModel;
import com.ecoway.api.repository.CategoryRepository;
import com.ecoway.api.repository.ImageRepository;
import com.ecoway.api.repository.VehicleModelRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleModelService {
    private final VehicleModelRepository repository;

    private final Logger log = Logger.getLogger(VehicleModelService.class);
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public VehicleModelService(VehicleModelRepository repository,
                               CategoryRepository categoryRepository,
                               ImageRepository imageRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
    }

    public ResponseEntity<?> getAll() throws ResourceNotFoundException {
        List<VehicleModel> founded = repository.findAll();
        if (founded.isEmpty()) throw new ResourceNotFoundException("None Vehicles founded");
        return ResponseEntity.ok(founded);
    }

    public ResponseEntity<?> get(String id) throws ResourceNotFoundException {
        VehicleModel founded = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("None vehicles founded"));
        return ResponseEntity.ok(founded);
    }

    public ResponseEntity<?> save(InputVehicleModel input) throws SaveErrorException {
        VehicleModel saved;
        Image image;
        VehicleModel newInput = new VehicleModel(input);
        try {
            image = imageRepository.save(input.getImage());
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new SaveErrorException("Image error, not save");
        }
        newInput.setImage(image);
        try {
            saved = repository.save(newInput);
            Category categorySaved = categoryRepository.findById(input.getCategoryName()).orElseThrow();
            categorySaved.getVehicles().add(saved);
            categoryRepository.saveAndFlush(categorySaved);
            log.info("input:" + input);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new SaveErrorException("Error, not save");
        }
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<?> update(VehicleModel input) throws SaveErrorException {
        VehicleModel saved;
        try {
            saved = repository.saveAndFlush(input);
        } catch (Exception e) {
            throw new SaveErrorException("Error, not save");
        }
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<?> delete(String id) throws ResourceNotFoundException {
        VehicleModel founded = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("None vehicles founded"));
        repository.deleteById(founded.getId());
        return ResponseEntity.ok("VehicleModel: " + founded.getModel() + " deleted successfully");
    }
}
