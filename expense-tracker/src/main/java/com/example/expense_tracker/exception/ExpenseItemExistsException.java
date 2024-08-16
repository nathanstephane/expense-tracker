package com.example.expense_tracker.exception;

public class ExpenseItemExistsException extends Exception {
    public ExpenseItemExistsException(String message){
        super(message);
    }

    public ExpenseItemExistsException(String message, Throwable why){
        super(message, why);
    }
}
