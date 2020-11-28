package no.adrsolheim.nightfly.repository;

import no.adrsolheim.nightfly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAll();
    User findById(int id);
    User save(User user);
    void deleteById(int id);
    boolean existsUserByUsername(String name);
    boolean existsUserByEmail(String email);
}
