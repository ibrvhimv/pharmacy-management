package fr.sup.galilee.pharmacy.repositories;

import fr.sup.galilee.pharmacy.entities.Cart;
import fr.sup.galilee.pharmacy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByEmailAndLastName(String email,String lastName);
    Optional<User> findByEmailAndPassword(String email,String password);
    Optional<User> findByEmail(String email);

}
