package io.github.dudursn.dscatalog.controllers.exceptions;

import io.github.dudursn.dscatalog.services.exceptions.DataBaseException;
import io.github.dudursn.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){

        StandardError standardError = new StandardError();
        HttpStatus status = HttpStatus.NOT_FOUND;

        standardError.setTimestamp(Instant.now());
        standardError.setStatus(status.value());
        standardError.setMsg(e.getMessage());
        standardError.setError("Resource not found");
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status.value()).body(standardError);
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandardError> integrityViolationDatabaseError(DataBaseException e, HttpServletRequest request){

        StandardError standardError = new StandardError();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        standardError.setTimestamp(Instant.now());
        standardError.setStatus(status.value());
        standardError.setMsg(e.getMessage());
        standardError.setError("Database exception");
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(status.value()).body(standardError);
    }
}
