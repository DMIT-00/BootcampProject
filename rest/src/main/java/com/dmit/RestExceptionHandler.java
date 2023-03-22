package com.dmit;

import com.dmit.exception.AlreadyExistsException;
import com.dmit.exception.InvalidFieldException;
import com.dmit.exception.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleException(NotFoundException exception)
    {
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AlreadyExistsException.class, InvalidFieldException.class})
    public ResponseEntity<Object> handleException(RuntimeException exception)
    {
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
