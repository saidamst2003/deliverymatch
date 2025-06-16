package delivery.example.backend.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class CustomExeptionHandler {
    @ExceptionHandler( value = { PasswordIncorrectException.class } )
    public ResponseEntity<?> handlePasswordIncorrectException (
            PasswordIncorrectException ex,
            WebRequest req
    ) {

        Map<String, String> body = new HashMap<>();
        body.put("password", ex.getMessage());

        return new ResponseEntity<>( body, HttpStatus.BAD_REQUEST );

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleInputValidationException (
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getAllErrors()
                .forEach( err -> {
                    String fieldName = ((FieldError) err ).getField();
                    String errMsg = err.getDefaultMessage();
                    errors.put(fieldName, errMsg);
                });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}