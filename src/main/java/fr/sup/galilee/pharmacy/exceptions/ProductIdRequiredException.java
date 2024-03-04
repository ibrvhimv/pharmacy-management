package fr.sup.galilee.pharmacy.exceptions;

import fr.sup.galilee.pharmacy.dtos.ProductDTO;
import fr.sup.galilee.pharmacy.dtos.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductIdRequiredException extends RuntimeException
{
    public ProductDTO productDTO;

}
