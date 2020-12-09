package no.adrsolheim.nightfly.service;

import no.adrsolheim.nightfly.dto.RegisterRequest;
import no.adrsolheim.nightfly.exception.NightflyException;
import no.adrsolheim.nightfly.model.NotificationMail;
import no.adrsolheim.nightfly.model.User;
import no.adrsolheim.nightfly.model.VerificationToken;
import no.adrsolheim.nightfly.repository.UserRepository;
import no.adrsolheim.nightfly.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private MailService mailService;

    @Transactional
    public void signup(RegisterRequest registerRequest) throws NightflyException {

        if (userRepository.existsUserByUsername(registerRequest.getUsername()) || userRepository.existsUserByEmail(registerRequest.getEmail())) {
            throw new NightflyException("Username or email already registered!");
        }
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationMail("Please verify your email.", user.getEmail(),
                "Thank you for registering at adrsolheim.no! Click on the follwing URL to activate your account: "
                        + "http://localhost:6060/api/auth/accountVerification/" + token));
    }

    @Transactional
    public void verify(String token) throws NightflyException {
        VerificationToken vtoken = verificationTokenRepository.findByToken(token).orElseThrow(
                () -> new NightflyException("Attempted to verify an account, but invalid token."));

        String username = vtoken.getUser().getUsername();
        User account = userRepository.findByUsername(username).orElseThrow(
                () -> new NightflyException("User not found: " + username));
        account.setEnabled(true);
        userRepository.save(account);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(Instant.now());

        verificationTokenRepository.save(verificationToken);
        return token;
    }
}
