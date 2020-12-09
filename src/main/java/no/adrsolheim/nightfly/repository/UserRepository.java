package no.adrsolheim.nightfly.repository;

import no.adrsolheim.nightfly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAll();
    User findById(int id);
    Optional<User> findByUsername(String username);
    User save(User user);
    void deleteById(int id);
    boolean existsUserByUsername(String name);
    boolean existsUserByEmail(String email);
}
