package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.ExpenseRequest;
import com.example.expense_tracker.models.Expense;
import com.example.expense_tracker.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public Expense createExpense(@RequestBody ExpenseRequest request){
        return expenseService.createExpense(request,"userID");
    }
}
