package com.example.expense_tracker.model;

import jakarta.persistence.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table
public class Expense {
    public Expense(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String item;
    private String description;
    @NotEmpty
    private Float amount;
    @NotEmpty
    private String category;
    @NotNull(message="Specify the data when the expense was made.")
    private LocalDate date;

    public void setItem(String item) {
        this.item = item;
    }
    public String getItem() {
        return item;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setAmount(Float amount){
        this.amount = amount;
    }
    public Float getAmount() {
        return amount;
    }

    public void setCategory(String category){
        this.category = category;
    }
    public String getCategory(){
        return category;
    }

    //your data date will be null in the database if you do not initialize it through the
    // constructor or much worse if you do not have any setter or getter(?)
    public void setDate(LocalDate date){
        this.date = date;
    }

    public LocalDate getDate(){
        return date;
    }

}
