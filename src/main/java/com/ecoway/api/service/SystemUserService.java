package com.ecoway.api.service;

import com.ecoway.api.exceptions.EmailExistsException;
import com.ecoway.api.exceptions.ResourceNotFoundException;
import com.ecoway.api.exceptions.SaveErrorException;
import com.ecoway.api.exceptions.UserLoginException;
import com.ecoway.api.model.SystemUser;
import com.ecoway.api.repository.UserRepository;
import com.ecoway.api.security.SystemUserRoles;
import com.ecoway.api.service.util.EmailSenderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SystemUserService implements UserDetailsService {
    private final UserRepository repository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final EmailSenderService emailSenderService;
    final static Logger log = Logger.getLogger(SystemUser.class);

    @Autowired
    public SystemUserService(UserRepository repository, EmailSenderService emailSenderService) {
        this.repository = repository;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public SystemUser loadUserByUsername(String username) {
        return repository.findByEmail(username)
                .orElseThrow(() -> new UserLoginException("None users founded"));
    }

    public ResponseEntity<?> getAll() throws ResourceNotFoundException {
        List<SystemUser> founded = repository.findAll();
        if (founded.isEmpty()) throw new ResourceNotFoundException("None vehicles founded");
        return ResponseEntity.ok(founded);
    }

    public ResponseEntity<?> get(UUID id) throws ResourceNotFoundException {
        SystemUser founded = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("None vehicles founded"));
        return ResponseEntity.ok(founded);
    }

    public ResponseEntity<?> save(SystemUser input) throws EmailExistsException, SaveErrorException {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (repository.findByEmail(input.getEmail()).isPresent()) {
            throw new EmailExistsException("Email already registered");
        }
        try {
            input.setSystemUserRoles(SystemUserRoles.ROLE_USER);
            input.setPassword(bCryptPasswordEncoder.encode(input.getPassword()));
            repository.save(input);
            emailSenderService.sendEmail(input.getEmail(),
                    "Conta criada com sucesso",
                    "Olá " + input.getName() + ", sua conta foi criada com sucesso!");
            log.info("input:" + input);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new SaveErrorException("Error, not save");
        }
        return new ResponseEntity<>("User Created successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<?> saveAdmin(SystemUser input) throws SaveErrorException {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        try {
            input.setSystemUserRoles(SystemUserRoles.ROLE_ADMIN);
            input.setPassword(bCryptPasswordEncoder.encode(input.getPassword()));
            repository.save(input);
            log.info("input:" + input);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new SaveErrorException("Error, not save");
        }
        return new ResponseEntity<>("User Created successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<?> getById(UUID id) throws ResourceNotFoundException {
        SystemUser user = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("None users foudned"));
        return ResponseEntity.ok(user);
    }
    public ResponseEntity<?> getByEmail(String email) throws ResourceNotFoundException {
        SystemUser user = repository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("None users foudned"));
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> update(SystemUser input) throws SaveErrorException, ResourceNotFoundException {
        try {
            repository.updateNameAndLastnameAndPhoneAndCpfAndCnhAndBirthdayBy(input.getName(), input.getLastname(),
                    input.getPhone(), input.getCpf(), input.getCnh(), input.getBirthday());
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new SaveErrorException("Error, not save");
        }

        return ResponseEntity.ok("User Updated");
    }

    public ResponseEntity<?> delete(UUID id) throws ResourceNotFoundException {
        SystemUser founded = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("None vehicles founded"));
        repository.deleteById(founded.getId());
        return ResponseEntity.ok("SystemUser: " + founded.getUsername() + " deleted successfully");
    }

}
