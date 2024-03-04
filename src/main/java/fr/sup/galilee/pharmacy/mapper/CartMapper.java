package fr.sup.galilee.pharmacy.mapper;

import fr.sup.galilee.pharmacy.dtos.CartDTO;
import fr.sup.galilee.pharmacy.entities.Cart;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CartMapper {
    public CartDTO toDTO(Cart cart)
    {
        CartDTO.CartDTOBuilder cartDTOBuilder =CartDTO.builder()
                .id(cart.getId())
                .user(cart.getUser());
        return cartDTOBuilder.build();
    }
    public Cart toEntity(CartDTO cartDTO)
    {
        Cart cart =new Cart();
        cart.setUser(cartDTO.getUser());
        cart.setId(cartDTO.getId());
        return cart;
    }
}
