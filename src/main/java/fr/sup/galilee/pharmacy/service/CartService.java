package fr.sup.galilee.pharmacy.service;

import fr.sup.galilee.pharmacy.dtos.CartDTO;
import fr.sup.galilee.pharmacy.dtos.ProductCartDTO;
import fr.sup.galilee.pharmacy.entities.Cart;
import fr.sup.galilee.pharmacy.entities.Product;
import fr.sup.galilee.pharmacy.entities.ProductCart;
import fr.sup.galilee.pharmacy.entities.User;
import fr.sup.galilee.pharmacy.mapper.CartMapper;
import fr.sup.galilee.pharmacy.mapper.ProductCartMapper;
import fr.sup.galilee.pharmacy.mapper.UserMapper;
import fr.sup.galilee.pharmacy.repositories.CartRepository;
import fr.sup.galilee.pharmacy.repositories.ProductCartRepository;
import fr.sup.galilee.pharmacy.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService
{
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final ProductCartRepository productCartRepository;
    private final ProductRepository productRepository;
    private final ProductCartMapper productCartMapper;
    @Transactional(readOnly = true)
    public List<CartDTO> findAll() {
        return
                cartRepository.findAll()
                        .stream()
                        .map(cartMapper::toDTO)
                        .toList();
    }

    @Transactional(readOnly = true)
    public CartDTO findById(String id) {
        return cartRepository.findById(id)
                .map(cartMapper::toDTO)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public CartDTO findOrCreateCartByUser(Long userId) {
        return cartRepository.findByUserId(userId)
                .map(cartMapper::toDTO)
                .orElseGet(() ->
                {
                    User user = userMapper.toEntity(userService.findById(userId));
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    Cart savedCart = cartRepository.save(newCart);
                    return cartMapper.toDTO(savedCart);
                });
    }
    @Transactional
    public CartDTO findCartByUser(Long userId) {
        return cartRepository.findByUserId(userId)
                .map(cartMapper::toDTO).orElseThrow(EntityNotFoundException::new);
    }
    @Transactional
    public CartDTO findOrCreateCartByMail(String email) {
        return cartRepository.findByUserEmail(email)
                .map(cartMapper::toDTO)
                .orElseGet(() -> {
                    User user = userMapper.toEntity(userService.findByEmail(email));
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    Cart savedCart = cartRepository.save(newCart);
                    return cartMapper.toDTO(savedCart);
                });
    }
    @Transactional
    public void insert(CartDTO cartDTO) {
        cartRepository.save(cartMapper.toEntity(cartDTO));
    }

    @Transactional
    public void update(CartDTO cartDTO) {
        cartRepository.findById(cartDTO.getId())
                .ifPresent(
                        cart -> {
                            cart.setId(cartDTO.getId());
                            cart.setUser(cartDTO.getUser());
                            cartRepository.save(cart);
                        }
                );
    }
    @Transactional
    public void deleteById(String id) {
        cartRepository.deleteById(id);
    }
    @Transactional
    public void deleteProductToCart(String cartId, Long productId) {
        Cart cart = cartMapper.toEntity(this.findById(cartId));
        if (cart == null)
        {
            throw new EntityNotFoundException("Cart not found with id: " + cartId);
        }
        productCartRepository.findByCartIdAndProductId(cartId, productId).ifPresent(
                productCart -> {
                    if(productCart.getQuantity()>=2)
                    {
                        productCart.setQuantity(productCart.getQuantity() -1);
                        productCartRepository.save(productCart);
                    }
                    else
                    {
                        productCartRepository.deleteProductCartByCart_IdAndAndProduct_Id(cartId, productId);
                    }
                }
        );
    }
    @Transactional
    public void deleteProduct(String cartId, Long productId)
    {
        Cart cart = cartMapper.toEntity(this.findById(cartId));
        if (cart == null)
        {
            throw new EntityNotFoundException("Cart not found with id: " + cartId);
        }
        productCartRepository.deleteProductCartByCart_IdAndAndProduct_Id(cartId, productId);
    }
    public void deleteAllProduct(String cartId)
    {
        Cart cart = cartMapper.toEntity(this.findById(cartId));
        if (cart == null)
        {
            throw new EntityNotFoundException("Cart not found with id: " + cartId);
        }
        productCartRepository.deleteAllByCart_Id(cartId);
    }

}
