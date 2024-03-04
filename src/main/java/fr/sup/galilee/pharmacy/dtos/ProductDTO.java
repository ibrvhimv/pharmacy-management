package fr.sup.galilee.pharmacy.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductDTO
{
    private Long id;
    private String name;
    private float prix;
    private int quantity;
    public interface ProductCreation{}
    public interface ProductUpdate{}

}
