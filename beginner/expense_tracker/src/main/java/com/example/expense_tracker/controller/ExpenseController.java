package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.ExpenseRequest;
import com.example.expense_tracker.models.Expense;
import com.example.expense_tracker.services.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
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
    public Expense createExpense(@RequestBody ExpenseRequest request, Authentication authentication) {
        String username = authentication.getName(); // Get username from JWT
        log.info("POST /api/expenses - Creating expense for username: {}", username);
        Expense expense = expenseService.createExpense(request, username);
        log.info("Created expense with id: {}", expense.getId());
        return expense;
    }

    @GetMapping
    public List<Expense> getExpenses(
            Authentication authentication,
            @RequestParam(defaultValue = "all") String filter,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        String username = authentication.getName();
        log.info("GET /api/expenses - Fetching expenses for username: {}, filter: {}", username, filter);
        List<Expense> expenses = expenseService.getExpenses(username, filter, startDate, endDate);
        log.info("Returning {} expenses for username: {}", expenses.size(), username);
        return expenses;
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable String id, @RequestBody ExpenseRequest request, Authentication authentication) {
        String username = authentication.getName();
        log.info("PUT /api/expenses/{} - Updating expense for username: {}", id, username);
        Expense expense = expenseService.updateExpense(id, request, username);
        log.info("Updated expense with id: {}", id);
        return expense;
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable String id, Authentication authentication) {
        String username = authentication.getName();
        log.info("DELETE /api/expenses/{} - Deleting expense for username: {}", id, username);
        expenseService.deleteExpense(id, username);
        log.info("Deleted expense with id: {}", id);
    }

    @GetMapping("/test")
    public String test() {
        log.info("Test endpoint called");
        return "API is running!";
    }
}
