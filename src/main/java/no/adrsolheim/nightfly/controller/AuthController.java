package no.adrsolheim.nightfly.controller;

import no.adrsolheim.nightfly.dto.AuthenticationResponse;
import no.adrsolheim.nightfly.dto.LoginRequest;
import no.adrsolheim.nightfly.dto.RegisterRequest;
import no.adrsolheim.nightfly.exception.NightflyException;
import no.adrsolheim.nightfly.model.User;
import no.adrsolheim.nightfly.model.VerificationToken;
import no.adrsolheim.nightfly.repository.VerificationTokenRepository;
import no.adrsolheim.nightfly.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    // @RequestBody maps the incoming request (JSON?) to a RegisterRequest object
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        String registrationMessage = "User registration successful!\n";
        try {
            authService.signup(registerRequest);
        } catch (NightflyException e) {
            // Log
            registrationMessage = "Username or email is already registered. Please use a different username/email\n";
        }
        return new ResponseEntity<>(registrationMessage, HttpStatus.OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verify(token);
        return new ResponseEntity<>("Account successfully verified!\n", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
