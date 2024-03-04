package fr.sup.galilee.pharmacy.repositories;

import fr.sup.galilee.pharmacy.entities.ProductCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductCartRepository extends JpaRepository<ProductCart,Long>
{
    List<ProductCart> findByCartId(String id);
    @Query("SELECT SUM(pc.quantity * p.prix) FROM ProductCart pc JOIN pc.product p WHERE pc.cart.id = :id")
    Float calculateTotalValueByCartId(@Param("id") String id);

    void deleteAllByCart_Id(String id);

    // Dans ProductCartRepository
    Optional<ProductCart> findByCartIdAndProductId(String cartId, Long productId);
    void deleteProductCartByCart_IdAndAndProduct_Id(String cartId, Long productId);


}
