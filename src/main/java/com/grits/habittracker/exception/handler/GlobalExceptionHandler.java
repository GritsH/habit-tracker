package com.grits.habittracker.exception.handler;

import com.grits.habittracker.exception.HabitAlreadyCompletedException;
import com.grits.habittracker.exception.HabitNotFoundException;
import com.grits.habittracker.exception.HabitUpdateFailedException;
import com.grits.habittracker.exception.InvalidCredentialsException;
import com.grits.habittracker.exception.UserAlreadyExistsException;
import com.grits.habittracker.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody String handleInternalServerError(Exception ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody String handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody String handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody String handleUserNotFoundException(UserNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = HabitNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody String handleHabitNotFoundException(HabitNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = HabitUpdateFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String handleHabitUpdateFailedException(HabitUpdateFailedException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = HabitAlreadyCompletedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody String handleHabitAlreadyCompletedException(HabitAlreadyCompletedException ex) {
        return ex.getMessage();
    }
}
