package no.adrsolheim.nightfly.controller;

import no.adrsolheim.nightfly.dto.RegisterRequest;
import no.adrsolheim.nightfly.exception.NightflyException;
import no.adrsolheim.nightfly.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    // @RequestBody maps the incoming request (JSON?) to a RegisterRequest object
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        String registrationMessage = "User registration successful!";
        try {
            authService.signup(registerRequest);
        } catch (NightflyException e) {
            // Log
            registrationMessage = "Username or email is already registered. Please use a different username/email";
        }
        return new ResponseEntity<>(registrationMessage, HttpStatus.OK);
    }
}
