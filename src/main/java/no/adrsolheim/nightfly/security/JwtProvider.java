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

import static io.jsonwebtoken.Jwts.parser;

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

    public boolean validateToken(String jws) {
        // will throw a SignatureException here if it fails
        Jwts.parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(jws);
        return true;
    }

    public String getUsernameFromJwt(String jws) {
        return Jwts.parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(jws).getBody().getSubject();
    }

    private Key getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("nightfly", "nightfly".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new NightflyException("Exception occured while retrieving public key from keystore");
        }
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("nightfly").getPublicKey();
        } catch (KeyStoreException e) {
            throw new NightflyException("Could not retrieve public key from keystore");
        }
    }
}
