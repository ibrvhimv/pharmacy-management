package fr.sup.galilee.pharmacy.controllers;

import fr.sup.galilee.pharmacy.dtos.ProductDTO;
import fr.sup.galilee.pharmacy.dtos.UserDTO;
import fr.sup.galilee.pharmacy.exceptions.ProductIdRequiredException;
import fr.sup.galilee.pharmacy.exceptions.UserDataNoValidException;
import fr.sup.galilee.pharmacy.exceptions.UserIdRequiredException;
import fr.sup.galilee.pharmacy.service.ProductService;
import fr.sup.galilee.pharmacy.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("stock")
@AllArgsConstructor
public class ProductController
{
    private final ProductService productService;

    @GetMapping
    public List<ProductDTO> getAll() {
        return productService.findAll();
    }

    @GetMapping(path = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO getById(@PathVariable Long id)
    {
        return productService.findById(id);
    }



    @PostMapping(path = "/product",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(@RequestBody @Validated(ProductDTO.ProductCreation.class) ProductDTO productDTO, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        productService.insert(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addUser1(@ModelAttribute @Validated(ProductDTO.ProductCreation.class) ProductDTO productDTO, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        productService.insert(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping(path = "/product",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody @Validated(ProductDTO.ProductUpdate.class) ProductDTO productDTO, Errors errors) {
        if (errors.hasErrors()) {
            if (errors.getFieldError("id") != null){
                throw new ProductIdRequiredException(productDTO);
            }
            throw new UserDataNoValidException();
        }
        productService.update(productDTO);
    }
    @DeleteMapping(path = "/product/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

}
