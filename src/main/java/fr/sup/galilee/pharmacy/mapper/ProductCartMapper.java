package fr.sup.galilee.pharmacy.mapper;

import fr.sup.galilee.pharmacy.dtos.ProductCartDTO;
import fr.sup.galilee.pharmacy.entities.ProductCart;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ProductCartMapper {
    public ProductCartDTO toDto(ProductCart productCart)
    {
        ProductCartDTO.ProductCartDTOBuilder productCartDTOBuilder = ProductCartDTO.builder()
                .product(productCart.getProduct())
                .cart(productCart.getCart())
                .id(productCart.getId())
                .quantity(productCart.getQuantity());
        return productCartDTOBuilder.build();
    }
    public ProductCart toEntity(ProductCartDTO productCartDTO)
    {
        ProductCart productCart=new ProductCart();
        productCart.setCart(productCartDTO.getCart());
        productCart.setProduct(productCartDTO.getProduct());
        productCart.setId(productCartDTO.getId());
        productCart.setQuantity(productCartDTO.getQuantity());
        return productCart;
    }
}
