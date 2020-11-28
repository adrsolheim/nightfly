package no.adrsolheim.nightfly.model;

import no.adrsolheim.nightfly.util.TokenConverter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.time.Instant;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "verification_token")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, columnDefinition = "BINARY(16)")
    @Convert(converter = TokenConverter.class)
    private String token;
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user")
    private User user;
    @Column(name = "expiry_date")
    private Instant expiryDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }
}
