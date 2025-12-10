package com.grits.habittracker.exception.handler;

import com.grits.habittracker.exception.InvalidCredentialsException;
import com.grits.habittracker.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody String handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody String handleUserAlreadyExistsException(SQLIntegrityConstraintViolationException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody String handleUserNotFoundException(UserNotFoundException ex) {
        return ex.getMessage();
    }

}
