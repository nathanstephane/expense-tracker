package com.example.expense_tracker.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
@Data
public class ApiError {

    private HttpStatus status;
    private LocalDate timestamp;
    private String message;
    private String debug_message;
    private List<SubErrors> sub_errors;

    private ApiError(){
        timestamp = LocalDate.now();
    }
     public ApiError(HttpStatus status){
        this();
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable why){
        this();
        this.status = status;
        this.message = "unexpected error";
        this.debug_message = why.getLocalizedMessage(); // provides a more readable human friendly message. by default uses the same message as getMessage()
    }

    public ApiError(HttpStatus status, String message, Throwable why){
        this();
        this.status = status;
        this.message = message;
        this.debug_message = why.getLocalizedMessage();
    }
}

abstract class SubErrors{

}

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
class ApiValidationErrors extends SubErrors
{
    private String object;
    private String field;
    private String message;
    private Object rejectedValue;

    ApiValidationErrors(String object, String message){
        this.object = object;
        this.message = message;
    }

}
