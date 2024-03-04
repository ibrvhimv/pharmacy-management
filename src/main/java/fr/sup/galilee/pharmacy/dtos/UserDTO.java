package fr.sup.galilee.pharmacy.dtos;

import fr.sup.galilee.pharmacy.enums.UserProfile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTO
{
    private Long id;
    private String last_name;
    private String firstname;
    private String email;
    private String password;
    private boolean active = true; // Valeur par défaut
    private UserProfile profile;
    public interface UserCreation{}
    public interface UserUpdate{}
}
