package fr.sup.galilee.pharmacy.mapper;

import fr.sup.galilee.pharmacy.dtos.ProductDTO;
import fr.sup.galilee.pharmacy.dtos.UserDTO;
import fr.sup.galilee.pharmacy.entities.Product;
import fr.sup.galilee.pharmacy.entities.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ProductMapper {
    public ProductDTO toDto(Product product) {
        ProductDTO.ProductDTOBuilder productDTOBuilder = ProductDTO.builder()
                .name(product.getName())
                .prix(product.getPrix())
                .quantity(product.getQuantity())
                .id(product.getId());
        return productDTOBuilder.build();
    }

    public Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrix(productDTO.getPrix());
        product.setQuantity(productDTO.getQuantity());
        return product;
    }
}
