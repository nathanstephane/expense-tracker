package com.example.expense_tracker.service;

import com.example.expense_tracker.exception.ExpenseItemNotFoundException;
import com.example.expense_tracker.exception.ExpenseItemEmptyException;
import com.example.expense_tracker.exception.ExpenseItemExistsException;
import com.example.expense_tracker.exception.NullValueExpenseException;
import com.example.expense_tracker.model.Expense;
import com.example.expense_tracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    //get all expenses or all the ones that matches a certain item name
    public List<Expense> getAllExpenses() throws NullValueExpenseException {
        Optional<List<Expense>> expenses = Optional.of(expenseRepository.findAll());
        if(expenses.isEmpty()){
            throw new NullValueExpenseException("null value");
        }
        return expenses.get();
    }

    public List<Expense> findAboveAmount(Float amount) throws ExpenseItemEmptyException, NullValueExpenseException {
        Optional<List<Expense>> aboveAmount = expenseRepository.findAboveAmount(amount);
        if (aboveAmount.isEmpty()){
            throw new NullValueExpenseException("null value when finding the amount(s) above threshold");
        }
        if(aboveAmount.get().isEmpty()){
            throw new ExpenseItemEmptyException("no items founds above the given expense");
        }
        return aboveAmount.get();
    }

    //find and return all expenses of the same name
    public List<Expense> findExpense(String item){
        Expense expense = new Expense();
        expense.setItem(item);
        Example<Expense> example_predicate = Example.of(expense);
        return expenseRepository.findAll(example_predicate);
    }

    public void createExpense(Expense newExpense) throws ExpenseItemExistsException
    {
        Optional<Expense> existingExpense = expenseRepository.findExpenseByItem(newExpense.getItem());
        if(existingExpense.isPresent()){
            //expense exists already.
            throw new ExpenseItemExistsException("This expense is already registered");
        }
        expenseRepository.save(newExpense);
    }

    //update expense
    public ResponseEntity<Expense> updateExpense(Long old_expense_id, Expense new_expense)
            throws ExpenseItemNotFoundException
    {
        Optional<Expense> existing_expense = expenseRepository.findById(old_expense_id);
        if(existing_expense.isEmpty()){
            throw new ExpenseItemNotFoundException("Item does not exist");
        }
        existing_expense.get().setItem(new_expense.getItem());
        existing_expense.get().setAmount(new_expense.getAmount());
        existing_expense.get().setCategory(new_expense.getCategory());
        existing_expense.get().setDate(new_expense.getDate());
        existing_expense.get().setDescription(new_expense.getDescription());

        expenseRepository.save(existing_expense.get());
        return ResponseEntity.ok(existing_expense.get());
    }
    //delete expense
    public void deleteExpense(Long expenseId) throws ExpenseItemNotFoundException {
        Optional<Expense> expense = expenseRepository.findById(expenseId);
        if(expense.isEmpty()){
            throw new ExpenseItemNotFoundException("Item does not exist");
        }
         expenseRepository.deleteById(expenseId);

    }

}
