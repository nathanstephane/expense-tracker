package com.example.expense_tracker.exception;

public class NullValueExpenseException extends Exception{
    public NullValueExpenseException(String message)
    {
        super(message);
    }

    public NullValueExpenseException(String message, Throwable why)
    {
        super(message, why);
    }
}
