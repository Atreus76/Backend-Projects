package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.ExpenseRequest;
import com.example.expense_tracker.models.Expense;
import com.example.expense_tracker.services.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private static final Logger log = LoggerFactory.getLogger(ExpenseController.class);
    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public Expense createExpense(@RequestBody ExpenseRequest request, @RequestParam String userId){
        log.info("POST /api/expenses - Creating expense for userId: {}", userId);
        Expense expense = expenseService.createExpense(request, userId);
        log.info("Created expense with id: {}", expense.getId());
        return expense;
    }

    @GetMapping
    public List<Expense> getExpenses(
            @RequestParam String userId,
            @RequestParam(defaultValue = "all") String filter,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("GET /api/expenses - Fetching expenses for userId: {}, filter: {}", userId, filter);
        List<Expense> expenses = expenseService.getExpenses(userId, filter, startDate, endDate);
        log.info("Returning {} expenses for userId: {}", expenses.size(), userId);
        return expenses;
    }
    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable String id, @RequestBody ExpenseRequest request, @RequestParam String userId) {
        log.info("PUT /api/expenses/{} - Updating expense for userId: {}", id, userId);
        Expense expense = expenseService.updateExpense(id, request, userId);
        log.info("Updated expense with id: {}", id);
        return expense;
    }
    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable String id, @RequestParam String userId) {
        log.info("DELETE /api/expenses/{} - Deleting expense for userId: {}", id, userId);
        expenseService.deleteExpense(id, userId);
        log.info("Deleted expense with id: {}", id);
    }
}
