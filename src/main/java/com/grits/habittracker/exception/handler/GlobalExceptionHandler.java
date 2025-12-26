package com.grits.habittracker.exception.handler;

import com.grits.habittracker.exception.HabitAlreadyCompletedException;
import com.grits.habittracker.exception.HabitNotFoundException;
import com.grits.habittracker.exception.HabitUpdateFailedException;
import com.grits.habittracker.exception.InvalidCredentialsException;
import com.grits.habittracker.exception.UserAlreadyExistsException;
import com.grits.habittracker.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public @ResponseBody ErrorResponse handleInternalServerError(Exception ex) {
        return ErrorResponse.create(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(value = InvalidCredentialsException.class)
    public @ResponseBody ErrorResponse handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return ErrorResponse.create(ex, HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public @ResponseBody ErrorResponse handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ErrorResponse.create(ex, HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public @ResponseBody ErrorResponse handleUserNotFoundException(UserNotFoundException ex) {
        return ErrorResponse.create(ex, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(value = HabitNotFoundException.class)
    public @ResponseBody ErrorResponse handleHabitNotFoundException(HabitNotFoundException ex) {
        return ErrorResponse.create(ex, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(value = HabitUpdateFailedException.class)
    public @ResponseBody ErrorResponse handleHabitUpdateFailedException(HabitUpdateFailedException ex) {
        return ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(value = HabitAlreadyCompletedException.class)
    public @ResponseBody ErrorResponse handleHabitAlreadyCompletedException(HabitAlreadyCompletedException ex) {
        return ErrorResponse.create(ex, HttpStatus.CONFLICT, ex.getMessage());
    }
}
