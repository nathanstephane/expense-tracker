package com.example.expense_tracker.repository;

import com.example.expense_tracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT e FROM Expense e WHERE e.item =?1")
    Optional<Expense> findExpenseByItem(String item);

    @Query("SELECT e FROM Expense e WHERE e.amount >=?1")
    Optional<List<Expense>> findAboveAmount(Float amount);
}
