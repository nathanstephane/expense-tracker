package com.example.expense_tracker.exception;

public class ExpenseItemEmptyException extends Exception{
    public ExpenseItemEmptyException(String message){
        super(message);
    }
    public ExpenseItemEmptyException(String message, Throwable why){
        super(message, why);
    }
}
