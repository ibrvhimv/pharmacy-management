package fr.sup.galilee.pharmacy.controllers;

import fr.sup.galilee.pharmacy.dtos.FactureDTO;
import fr.sup.galilee.pharmacy.repositories.ProductCartRepository;
import fr.sup.galilee.pharmacy.service.CartService;
import fr.sup.galilee.pharmacy.service.FactureService;
import fr.sup.galilee.pharmacy.service.ProductCartService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("fact")
public class FactureController
{
    private final FactureService factureService;
    private final ProductCartService productCartService;
    private final CartService cartService;
    @GetMapping("user/{id}")
    public FactureDTO getFactureByIdUser(@PathVariable Long id)
    {
        return factureService.findOrCreateFact(id);
    }
    @GetMapping("{id}")
    public FactureDTO getFactureById(@PathVariable Long id)
    {
        return factureService.findById(id);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteFacture(@PathVariable Long id)
    {
        cartService.deleteAllProduct(cartService.findCartByUser(id).getId());
        factureService.deleteById(id);
    }

}
