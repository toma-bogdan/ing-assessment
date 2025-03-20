package ing.assessment.controller;

import jakarta.persistence.OptimisticLockException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponse notFound(NoSuchElementException e) {
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse outOfStock(IllegalArgumentException e){
        return ErrorResponse.create(e, HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, e.getMessage());
    }
    @ExceptionHandler(OptimisticLockException.class)
    public ErrorResponse optimisticLock (OptimisticLockException e) {
        return ErrorResponse.create(e, HttpStatus.CONFLICT, "Stock has changed in the meantime, please try again");
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse generic(Exception e) {
        return ErrorResponse.create(e, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

}
