package fr.sup.galilee.pharmacy.repositories;

import fr.sup.galilee.pharmacy.entities.Cart;
import fr.sup.galilee.pharmacy.entities.Product;
import fr.sup.galilee.pharmacy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository  extends JpaRepository<Cart, String>
{
    Optional<Cart> findByUserId(Long userId);
    Optional<Cart> findByUserEmail(String email);

}
