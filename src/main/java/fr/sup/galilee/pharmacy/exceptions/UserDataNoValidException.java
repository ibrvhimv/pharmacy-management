package fr.sup.galilee.pharmacy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User data not valid")
public class UserDataNoValidException extends RuntimeException {
    @Override
    public String getMessage() {
        return "User data not valid";
    }
}

