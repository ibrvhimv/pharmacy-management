package fr.sup.galilee.pharmacy.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ProductExceptionsController {

    @ExceptionHandler(UserIdRequiredException.class)
    public ResponseEntity<String> userIdRequired(UserIdRequiredException e) {
        return ResponseEntity
                .badRequest()
                .body("The Product : " +e.getUserDTO().getFirstname() + "does not have a valid ID");
    }
}