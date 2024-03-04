package fr.sup.galilee.pharmacy.service;

import fr.sup.galilee.pharmacy.dtos.CartDTO;
import fr.sup.galilee.pharmacy.dtos.ProductCartDTO;
import fr.sup.galilee.pharmacy.entities.Cart;
import fr.sup.galilee.pharmacy.entities.Product;
import fr.sup.galilee.pharmacy.entities.ProductCart;
import fr.sup.galilee.pharmacy.entities.User;
import fr.sup.galilee.pharmacy.mapper.CartMapper;
import fr.sup.galilee.pharmacy.mapper.ProductCartMapper;
import fr.sup.galilee.pharmacy.repositories.CartRepository;
import fr.sup.galilee.pharmacy.repositories.ProductCartRepository;
import fr.sup.galilee.pharmacy.repositories.ProductRepository;
import fr.sup.galilee.pharmacy.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductCartService
{
    private final ProductCartRepository productCartRepository;
    private final ProductCartMapper productCartMapper;
    private final CartService cartService;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;
    @Transactional(readOnly=true)
    public ProductCartDTO findById(Long id)
    {
        return productCartRepository.findById(id)
                .map(productCartMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void insert(ProductCartDTO productCartDTO) {
        productCartRepository.save(productCartMapper.toEntity(productCartDTO));
    }
    @Transactional(readOnly = true)
    public ProductCartDTO findByProductId(Long productId,String cartId) {
        return
                productCartRepository.findByCartIdAndProductId(cartId, productId)
                        .map(productCartMapper::toDto)
                        .orElseThrow(EntityNotFoundException::new);

    }
    @Transactional
    public void update(ProductCartDTO productCartDTO) {
        productCartRepository.findById(productCartDTO.getId())
                .ifPresent(
                        productCart -> {
                            productCart.setCart(productCartDTO.getCart());
                            productCart.setProduct(productCartDTO.getProduct());
                            productCart.setId(productCartDTO.getId());
                            productCart.setQuantity(productCartDTO.getQuantity());
                            productCartRepository.save(productCart);
                        }
                );
    }
    @Transactional
    public List<ProductCartDTO> getProductsByCartId(String cartId) {
        return productCartRepository.findByCartId(cartId).stream().map(productCartMapper::toDto).toList();
    }

    @Transactional
    public void deleteById(Long id) {
        productCartRepository.deleteById(id);
    }

    @Transactional
    public float getTotal(String id)
    {
        return productCartRepository.calculateTotalValueByCartId(id);
    }
    @Transactional
    public float getTotal(Long id)
    {
        return productCartRepository.calculateTotalValueByCartId(cartMapper.toEntity(cartService.findOrCreateCartByUser(id)).getId());
    }
    @Transactional
    public void deleteProducts(String id )
    {
        productCartRepository.deleteAllByCart_Id(id);
    }
    @Transactional
    public void addProductPToCart(String cartId, Long productId, Integer quantity) {
        Cart cart = cartMapper.toEntity(cartService.findById(cartId));
        if (cart == null) {
            throw new EntityNotFoundException("Cart not found with id: " + cartId);
        }
        productCartRepository.findByCartIdAndProductId(cartId, productId).ifPresentOrElse(
                productCart ->
                {
                    productCart.setQuantity(productCart.getQuantity() + quantity);
                    productCart.getProduct().setQuantity(productCart.getProduct().getQuantity()-1);
                    productCartRepository.save(productCart);
                },
                () -> {
                    Product product = productRepository.findById(productId)
                            .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
                    ProductCart productCart = new ProductCart();
                    productCart.setCart(cart);
                    productCart.setProduct(product);
                    productCart.setQuantity(quantity);
                    productCartRepository.save(productCart);
                }
        );
    }
}
