package com.example.expense_tracker.dto;

import com.example.expense_tracker.models.ExpenseCategory;
import lombok.Data;

import java.time.LocalDate;
@Data
public class ExpenseRequest {
    private double amount;
    private ExpenseCategory category;
    private LocalDate date;
    private String description;
}
