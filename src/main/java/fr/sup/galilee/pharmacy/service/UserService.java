package fr.sup.galilee.pharmacy.service;

import fr.sup.galilee.pharmacy.dtos.UserCredentialsDTO;
import fr.sup.galilee.pharmacy.dtos.UserDTO;
import fr.sup.galilee.pharmacy.mapper.UserMapper;
import fr.sup.galilee.pharmacy.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final UserMapper userrMapper;

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        return
                userRepository.findAll()
                .stream()
                .map(userrMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        return userRepository.findById(id)
                .map(userrMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public UserDTO insert(UserDTO userDTO) {
        userRepository.save(userrMapper.toEntity(userDTO));
        return userDTO;
    }

    @Transactional
    public void update(UserDTO userDTO) {
        userRepository.findById(userDTO.getId())
                .ifPresent(
                        user -> {
                            user.setActive(userDTO.isActive());
                            user.setFirstname(userDTO.getFirstname());
                            user.setLastName(userDTO.getLast_name());
                            user.setEmail(userDTO.getEmail());
                            user.setId(userDTO.getId());
                            user.setPassword(userDTO.getPassword());
                            userRepository.save(user);
                        }
                );
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserDTO findByEmailAndPassword(UserCredentialsDTO credentials) {
        return userRepository.findByEmailAndPassword(credentials.getEmail(),credentials.getPassword()).map(userrMapper::toDto).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findByEmail(String email) {
        return userRepository.findByEmail(email).map(userrMapper::toDto).orElseThrow(EntityNotFoundException::new);
    }
}
