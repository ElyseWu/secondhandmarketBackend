package team3.secondhand.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import team3.secondhand.exception.UserAlreadyExistException;
import team3.secondhand.exception.UserNotExistException;

@ControllerAdvice
public class CustomExceptionHandler {
//    @ExceptionHandler(UserAlreadyExistException.class)
//    public final ResponseEntity<String> handleUserAlreadyExistException(Exception ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler(UserNotExistException.class)
//    public final ResponseEntity<String> handleUserNotExistException(Exception ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
//    }

}
