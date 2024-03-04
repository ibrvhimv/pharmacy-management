package fr.sup.galilee.pharmacy.mapper;

import fr.sup.galilee.pharmacy.dtos.UserDTO;
import fr.sup.galilee.pharmacy.entities.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class UserMapper
{
    public UserDTO toDto(User user) {
        UserDTO.UserDTOBuilder userDTOBuilder =UserDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .last_name(user.getLastName())
                .active(user.isActive())
                .email(user.getEmail())
                .password(user.getPassword())
                .profile(user.getProfile());
        return userDTOBuilder.build();
    }

    public User toEntity (UserDTO userDTO)
    {
        User user =new User();
        user.setActive(userDTO.isActive());
        user.setFirstname(userDTO.getFirstname());
        user.setLastName(userDTO.getLast_name());
        user.setEmail(userDTO.getEmail());
        user.setId(userDTO.getId());
        user.setPassword(userDTO.getPassword());
        user.setProfile(userDTO.getProfile());
        return user;
    }
}
