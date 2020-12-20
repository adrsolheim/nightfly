package no.adrsolheim.nightfly.security;

import no.adrsolheim.nightfly.exception.NightflyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {

    private KeyStore keyStore;
    // TODO: Load secret from config

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("PKCS12");
            InputStream resourceAsStream = getClass().getResourceAsStream("/nightfly.p12");
            // the secret is the password to the keystore
            keyStore.load(resourceAsStream, "nightfly".toCharArray());

        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new NightflyException("Exception with loading keystore");
        }
    }

    public String generateToken(Authentication authentication) {

        // Get user information from authentication object
        User principal = (User) authentication.getPrincipal();

        return Jwts.builder().setSubject(principal.getUsername()).signWith(getPrivateKey()).compact();
    }

    private Key getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("nightfly", "nightfly".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new NightflyException("Exception occured while retrieving public key from keystore");
        }
    }
}
