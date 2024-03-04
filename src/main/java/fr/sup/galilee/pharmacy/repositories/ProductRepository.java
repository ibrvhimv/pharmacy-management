package fr.sup.galilee.pharmacy.repositories;

import fr.sup.galilee.pharmacy.entities.Product;
import fr.sup.galilee.pharmacy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>
{
}
