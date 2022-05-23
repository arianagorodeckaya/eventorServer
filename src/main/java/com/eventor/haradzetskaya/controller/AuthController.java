package com.eventor.haradzetskaya.controller;

import com.eventor.haradzetskaya.exceptionHandler.EmailAlreadyExistException;
import com.eventor.haradzetskaya.model.ErrorResponse;
import com.eventor.haradzetskaya.model.JwtRequest;
import com.eventor.haradzetskaya.model.JwtResponse;
import com.eventor.haradzetskaya.model.User;
import com.eventor.haradzetskaya.myEnum.Role;
import com.eventor.haradzetskaya.security.JwtTokenUtil;
import com.eventor.haradzetskaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(path = "/api/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }


    @PostMapping(path = "/api/registration")
    ResponseEntity<?> saveUser(@RequestBody User newUser) {
        User user = userService.getByEmail(newUser.getEmail());
        if(user!=null){
            throw new EmailAlreadyExistException("That email address is already exist");
        }
        newUser.setRole(Role.USER);
        newUser.setId(0);
        newUser.setPw_hash(passwordEncoder.encode(newUser.getPw_hash()));
        userService.saveUser(newUser);
        return ResponseEntity.ok(new ErrorResponse(HttpStatus.OK.value(), "User was registered", System.currentTimeMillis()));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
