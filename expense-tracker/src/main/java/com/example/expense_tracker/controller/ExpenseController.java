package com.example.expense_tracker.controller;

import com.example.expense_tracker.exception.ExpenseItemNotFoundException;
import com.example.expense_tracker.exception.ExpenseItemEmptyException;
import com.example.expense_tracker.exception.ExpenseItemExistsException;
import com.example.expense_tracker.exception.NullValueExpenseException;
import com.example.expense_tracker.model.Expense;
import com.example.expense_tracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeParseException;
import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping(path= "api/v1/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;
    @Autowired
    public ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }

    @GetMapping
    public List<Expense> getAllExpenses() throws NullValueExpenseException {
        try{
            return expenseService.getAllExpenses();
        }
        catch (NullValueExpenseException e)
        {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }
    } 

    @GetMapping("/filter")
    public List<Expense> getExpenseAboveAmount(@Valid @RequestParam(required = true)  Float amount)
            throws ExpenseItemEmptyException, NullValueExpenseException
    {
        try{
            return expenseService.findAboveAmount(amount);
        }catch (ExpenseItemEmptyException e){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }catch (NullValueExpenseException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    //create expense. POST. read data from request body
    @PostMapping
    public void createExpense(@Valid @RequestBody Expense expense) throws ExpenseItemExistsException {
        try{
            expenseService.createExpense(expense);
            ResponseEntity.ok();
        }catch (DateTimeParseException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping(path = "update/{expenseID}")
    public ResponseEntity<Expense> updateExpense(
            @PathVariable("expenseID") Long expenseID,
            @Valid @RequestBody Expense expense_to_update)
            throws ExpenseItemNotFoundException
    {
        try{
            return expenseService.updateExpense(expenseID, expense_to_update);
        }
        catch (ExpenseItemNotFoundException e){
            throw new ExpenseItemNotFoundException("Expense item does not exist for id " + expenseID, e);
        }

    }

    @DeleteMapping(path = "remove/{expenseID}")
    public void deleteExpense(@Valid @PathVariable("expenseID") Long expenseID) throws ExpenseItemNotFoundException
    {
        try{ expenseService.deleteExpense(expenseID);}
        catch (ExpenseItemNotFoundException e)
        {
            throw new ExpenseItemNotFoundException("Expense item does not exist for id " + expenseID, e);
        }
    }
}
