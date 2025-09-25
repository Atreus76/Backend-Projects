package com.example.expense_tracker.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "expenses")
public class Expense {
    @Id
    private String id;
    private double amount;
    private ExpenseCategory category;
    private LocalDate date;
    private String userId;
    private String description;
}
