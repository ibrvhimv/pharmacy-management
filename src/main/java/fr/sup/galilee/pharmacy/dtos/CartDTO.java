package fr.sup.galilee.pharmacy.dtos;

import fr.sup.galilee.pharmacy.entities.User;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartDTO
{
    private String id;
    private User user;
    public interface CartCreation{}
    public interface CartUpdate{}

}
