package com.example.expense_tracker.exception;

public class ExpenseItemNotFoundException extends Exception{
    public ExpenseItemNotFoundException(String message)
    {
        super(message);
    }

    public ExpenseItemNotFoundException(String message, Throwable why)
    {
        super(message, why);
    }
}
