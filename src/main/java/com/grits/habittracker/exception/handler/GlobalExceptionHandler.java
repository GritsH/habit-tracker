package com.grits.habittracker.exception.handler;

import com.grits.habittracker.exception.HabitCategoryNotFoundException;
import com.grits.habittracker.exception.HabitFrequencyNotFoundException;
import com.grits.habittracker.exception.HabitNotFoundException;
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

    @ExceptionHandler(value = HabitCategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody String handleHabitCategoryNotFoundException(HabitCategoryNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = HabitFrequencyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody String handleHabitFrequencyNotFoundException(HabitFrequencyNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = HabitNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody String handleHabitNotFoundException(HabitNotFoundException ex) {
        return ex.getMessage();
    }
}
