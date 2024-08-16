package com.example.expense_tracker.exception;

import com.example.expense_tracker.error.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError){
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(ExpenseItemNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(ExpenseItemNotFoundException e){
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(e.getMessage());
        apiError.setDebug_message(e.getCause().toString());
        return buildResponseEntity(apiError);
    }
}
