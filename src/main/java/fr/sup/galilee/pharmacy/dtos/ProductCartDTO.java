package fr.sup.galilee.pharmacy.dtos;

import fr.sup.galilee.pharmacy.entities.Cart;
import fr.sup.galilee.pharmacy.entities.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class ProductCartDTO
{
    private Long id;
    private Integer quantity;
    private Cart cart;
    private Product product;
    public interface ProductCartCreation{}
    public interface ProductCartUpdate{}

}
