package drkeller.mail.mailapi.exception;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler  {

    @ExceptionHandler(ConversionFailedException.class)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConnversion(RuntimeException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MailNotFoundException.class)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleMailNotFound(RuntimeException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}