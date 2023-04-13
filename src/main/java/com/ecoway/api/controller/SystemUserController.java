package com.ecoway.api.controller;

import com.ecoway.api.exceptions.EmailExistsException;
import com.ecoway.api.exceptions.ResourceNotFoundException;
import com.ecoway.api.exceptions.SaveErrorException;
import com.ecoway.api.exceptions.UserLoginException;
import com.ecoway.api.model.SystemUser;
import com.ecoway.api.model.dto.SystemUserDTO;
import com.ecoway.api.model.dto.SystemUserInput;
import com.ecoway.api.security.JwtUtil;
import com.ecoway.api.security.SystemUserRoles;
import com.ecoway.api.service.SystemUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class SystemUserController {
    private final SystemUserService service;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private static final Logger log = Logger.getLogger(SystemUserController.class);

    @Autowired
    public SystemUserController(SystemUserService service, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("public/user/login")
    public ResponseEntity<?> login(@RequestBody SystemUserInput systemUser) throws UserLoginException {
        ObjectMapper mapper = new ObjectMapper();
        SystemUser saved = service.loadUserByUsername(systemUser.getEmail());

        log.info("Tentando efetuar login com:  " + systemUser.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(systemUser.getUsername(),
                systemUser.getPassword()));

        final UserDetails userDetails = service.loadUserByUsername(systemUser.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        log.info(saved.getUsername());
        saved.setJwt(jwt);
        SystemUserDTO userDTO = mapper.convertValue(saved, SystemUserDTO.class);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("private/user")
    public ResponseEntity<?> getAll() throws ResourceNotFoundException {
        return service.getAll();
    }

    @PostMapping("/public/user/register")
    public ResponseEntity<?> save(@RequestBody SystemUser systemUser) throws SaveErrorException, EmailExistsException {
        return service.save(systemUser);
    }

    @PostMapping("/private/user/admin/register")
    public ResponseEntity<?> saveAdmin(@RequestBody SystemUser user) throws SaveErrorException {
        return service.saveAdmin(user);
    }

    @GetMapping("/private/user/byid")
    public ResponseEntity<?> getById(@RequestParam("id") UUID id) throws ResourceNotFoundException {
        return service.getById(id);
    }
    @GetMapping("/private/user/byemail")
    public ResponseEntity<?> getById(@RequestParam("email") String email) throws ResourceNotFoundException {
        return service.getByEmail(email);
    }

    @PutMapping("private/user")
    public ResponseEntity<?> update(@RequestBody SystemUser user) throws SaveErrorException, ResourceNotFoundException {
        return service.update(user);
    }

    @DeleteMapping("private/user")
    public ResponseEntity<?> delete(@RequestParam("id") UUID id) throws ResourceNotFoundException {
        return service.delete(id);
    }
}
