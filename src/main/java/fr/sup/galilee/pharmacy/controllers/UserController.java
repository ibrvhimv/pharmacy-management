package fr.sup.galilee.pharmacy.controllers;

import fr.sup.galilee.pharmacy.dtos.UserCredentialsDTO;
import fr.sup.galilee.pharmacy.dtos.UserDTO;
import fr.sup.galilee.pharmacy.enums.UserProfile;
import fr.sup.galilee.pharmacy.exceptions.UserDataNoValidException;
import fr.sup.galilee.pharmacy.exceptions.UserIdRequiredException;
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
@RequestMapping("user")
@AllArgsConstructor
public class UserController
{
    private final UserService userService;
    @GetMapping
    public List<UserDTO> getAll() {
        return userService.findAll();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getById(@PathVariable Long id)
    {
        return userService.findById(id);
    }

    @GetMapping(path = "user/mail/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getById(@PathVariable String email)
    {
        return userService.findByEmail(email);
    }



    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(@RequestBody @Validated(UserDTO.UserCreation.class) UserDTO userDTO, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        UserDTO createdUser = userService.insert(userDTO);
        UserDTO abc = userService.findByEmail(createdUser.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(abc);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addUser1(@ModelAttribute @Validated(UserDTO.UserCreation.class) UserDTO userDTO, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        userService.insert(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody @Validated(UserDTO.UserUpdate.class) UserDTO userDTO, Errors errors) {
        if (errors.hasErrors()) {
            if (errors.getFieldError("id") != null){
                throw new UserIdRequiredException(userDTO);
            }
            throw new UserDataNoValidException();
        }
        userService.update(userDTO);
    }
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PostMapping(path = "/account", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getByEmailAndPassword(@RequestBody UserCredentialsDTO credentials) {
        return userService.findByEmailAndPassword(credentials);
    }
}
