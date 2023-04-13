package com.ecoway.api.service;

import com.ecoway.api.exceptions.ResourceNotFoundException;
import com.ecoway.api.exceptions.SaveErrorException;
import com.ecoway.api.model.Feature;
import com.ecoway.api.repository.FeatureRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FeatureService {
    private final FeatureRepository repository;
    private final Logger log = Logger.getLogger(FeatureService.class);

    @Autowired
    public FeatureService(FeatureRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<?> getAll() throws ResourceNotFoundException {
        List<Feature> founded = repository.findAll();
        if(founded.isEmpty()) throw new ResourceNotFoundException("None vehicles founded");
        return ResponseEntity.ok(founded);
    }

    public ResponseEntity<?> get(String id) throws ResourceNotFoundException {
        Feature founded = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("None vehicles founded"));
        return ResponseEntity.ok(founded);
    }

    public ResponseEntity<?> save(Feature input) throws SaveErrorException {
        Feature saved;
        try{
            saved = repository.save(input);
            log.info("input:" + input);
        }catch (Exception e){
            log.info(e.getMessage());
            throw new SaveErrorException("Error, not save");
        }
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<?> update(Feature input) throws SaveErrorException {
        Feature saved;
        try{
            saved = repository.saveAndFlush(input);
        } catch (Exception e){
            throw new SaveErrorException("Error, not save");
        }
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<?> delete(String id) throws ResourceNotFoundException {
        Feature founded = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("None vehicles founded"));
        repository.deleteById(founded.getId());
        return ResponseEntity.ok("Feature: "+ founded.getName() + " deleted successfully");
    }
}
