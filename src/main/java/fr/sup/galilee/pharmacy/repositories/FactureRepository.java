package fr.sup.galilee.pharmacy.repositories;

import fr.sup.galilee.pharmacy.entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FactureRepository extends JpaRepository<Facture,Long> {
    Optional<Facture> findByUserId(Long id);
}
