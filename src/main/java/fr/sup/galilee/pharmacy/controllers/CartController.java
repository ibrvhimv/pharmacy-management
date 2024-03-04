package fr.sup.galilee.pharmacy.controllers;

import fr.sup.galilee.pharmacy.dtos.CartDTO;
import fr.sup.galilee.pharmacy.dtos.ProductCartDTO;
import fr.sup.galilee.pharmacy.dtos.UserDTO;
import fr.sup.galilee.pharmacy.entities.Cart;
import fr.sup.galilee.pharmacy.entities.Product;
import fr.sup.galilee.pharmacy.entities.ProductCart;
import fr.sup.galilee.pharmacy.exceptions.UserDataNoValidException;
import fr.sup.galilee.pharmacy.exceptions.UserIdRequiredException;
import fr.sup.galilee.pharmacy.service.CartService;
import fr.sup.galilee.pharmacy.service.FactureService;
import fr.sup.galilee.pharmacy.service.ProductCartService;
import fr.sup.galilee.pharmacy.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@AllArgsConstructor
@RequestMapping("cart")
public class CartController
{
    private final CartService cartService;
    private final FactureService factureService;
    private final ProductCartService productCartService;
    @GetMapping
    public List<CartDTO> getAll() {
        return cartService.findAll();
    }
    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CartDTO getById(@PathVariable String id)
    {
        return cartService.findById(id);
    }
    @GetMapping("/{cartId}/products")
    public List<ProductCartDTO> getCartProducts(@PathVariable String cartId) {
        return productCartService.getProductsByCartId(cartId);
    }
    @GetMapping(path = "user/id/{UserID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CartDTO getByIdUser(@PathVariable Long UserID)
    {
        return cartService.findOrCreateCartByUser(UserID);
    }
    @GetMapping(path = "user/mail/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CartDTO getByUserMail(@PathVariable String email)
    {
        return cartService.findOrCreateCartByMail(email);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCart(@RequestBody @Validated(CartDTO.CartCreation.class) CartDTO cartDTO, Errors errors) {
        if (errors.hasErrors())
        {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        cartService.insert(cartDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartDTO);
    }
    @GetMapping("/{cartId}/products/{productId}/{quantity}")
    public void addProductToCat(@PathVariable String cartId,
                                 @PathVariable Long productId,
                                 @PathVariable Integer quantity) {
        productCartService.addProductPToCart(cartId,productId,quantity);
        factureService.update(cartService.findById(cartId).getUser().getId());
    }
    @DeleteMapping("/{cartId}/product/{productId}")
    public void deleteOneProductToCart(@PathVariable String cartId,
                                @PathVariable Long productId) {
        cartService.deleteProductToCart(cartId,productId);
        factureService.update(cartService.findById(cartId).getUser().getId());
    }
    @DeleteMapping("/{cartId}/products/{productId}")
    public void deleteProduct(@PathVariable String cartId,
                                @PathVariable Long productId) {
        cartService.deleteProduct(cartId,productId);
        factureService.update(cartService.findById(cartId).getUser().getId());
    }
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody @Validated(CartDTO.CartUpdate.class) CartDTO cartDTO, Errors errors)
    {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        cartService.update(cartDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable String id) {
        cartService.deleteById(id);
    }
}
