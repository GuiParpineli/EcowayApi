package com.ecoway.api.service;


import com.ecoway.api.exceptions.ResourceNotFoundException;
import com.ecoway.api.exceptions.SaveErrorException;
import com.ecoway.api.model.City;
import com.ecoway.api.repository.CityRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class CityService {
    private final CityRepository repository;
    private final Logger log = Logger.getLogger(CityService.class);

    @Autowired
    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<?> getAll() throws ResourceNotFoundException {
        List<City> founded = repository.findAll();
        if(founded.isEmpty()) throw new ResourceNotFoundException("None vehicles founded");
        return ResponseEntity.ok(founded);
    }

    public ResponseEntity<?> get(String id) throws ResourceNotFoundException {
        City founded = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("None vehicles founded"));
        return ResponseEntity.ok(founded);
    }

    public ResponseEntity<?> save(City input) throws SaveErrorException {
        City saved;
        try{
            saved = repository.save(input);
            log.info("input:" + input);
        }catch (Exception e){
            log.info(e.getMessage());
            throw new SaveErrorException("Error, not save");
        }
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<?> update(City input) throws SaveErrorException {
        City saved;
        try{
            saved = repository.saveAndFlush(input);
        } catch (Exception e){
            throw new SaveErrorException("Error, not save");
        }
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<?> delete(String id) throws ResourceNotFoundException {
        City founded = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("None vehicles founded"));
        repository.deleteById(founded.getId());
        return ResponseEntity.ok("City: "+ founded.getCityName() + " deleted successfully");
    }
}
