package fr.sup.galilee.pharmacy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Product data not valid")
public class ProductDataNoValideException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Product data not valid";
    }
}