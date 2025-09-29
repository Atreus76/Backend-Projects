package com.example.expense_tracker.services;

import com.example.expense_tracker.dto.ExpenseRequest;
import com.example.expense_tracker.models.Expense;
import com.example.expense_tracker.repository.ExpenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {
    private static final Logger log = LoggerFactory.getLogger(ExpenseService.class);

    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense createExpense(ExpenseRequest request, String username) {
        log.info("Creating expense for username: {}, amount: {}, category: {}", username, request.getAmount(), request.getCategory());
        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate() != null ? request.getDate() : LocalDate.now());
        expense.setDescription(request.getDescription());
        expense.setUserId(username); // Store username as userId
        Expense savedExpense = expenseRepository.save(expense);
        log.info("Expense created with id: {}", savedExpense.getId());
        return savedExpense;
    }

    public List<Expense> getExpenses(String username, String filter, LocalDate startDate, LocalDate endDate) {
        log.info("Fetching expenses for username: {}, filter: {}, startDate: {}, endDate: {}", username, filter, startDate, endDate);
        LocalDate now = LocalDate.now();
        switch (filter.toLowerCase()) {
            case "week":
                startDate = now.minusDays(7);
                endDate = now;
                log.debug("Applying week filter: startDate={}, endDate={}", startDate, endDate);
                break;
            case "month":
                startDate = now.minusMonths(1);
                endDate = now;
                log.debug("Applying month filter: startDate={}, endDate={}", startDate, endDate);
                break;
            case "3months":
                startDate = now.minusMonths(3);
                endDate = now;
                log.debug("Applying 3months filter: startDate={}, endDate={}", startDate, endDate);
                break;
            case "custom":
                if (startDate == null || endDate == null) {
                    log.error("Start and end dates required for custom filter");
                    throw new IllegalArgumentException("Start and end dates required for custom filter");
                }
                log.debug("Applying custom filter: startDate={}, endDate={}", startDate, endDate);
                break;
            default:
                log.debug("Fetching all expenses for username: {}", username);
                return expenseRepository.findByUserId(username);
        }
        List<Expense> expenses = expenseRepository.findByUserIdAndDateBetween(username, startDate, endDate);
        log.info("Retrieved {} expenses for username: {}", expenses.size(), username);
        return expenses;
    }

    public Expense updateExpense(String id, ExpenseRequest request, String username) {
        log.info("Updating expense with id: {} for username: {}", id, username);
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Expense not found with id: {}", id);
                    return new RuntimeException("Expense not found");
                });
        if (!expense.getUserId().equals(username)) {
            log.error("Unauthorized attempt to update expense id: {} by username: {}", id, username);
            throw new RuntimeException("Unauthorized");
        }
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());
        expense.setDescription(request.getDescription());
        Expense updatedExpense = expenseRepository.save(expense);
        log.info("Expense updated with id: {}", updatedExpense.getId());
        return updatedExpense;
    }

    public void deleteExpense(String id, String username) {
        log.info("Deleting expense with id: {} for username: {}", id, username);
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Expense not found with id: {}", id);
                    return new RuntimeException("Expense not found");
                });
        if (!expense.getUserId().equals(username)) {
            log.error("Unauthorized attempt to delete expense id: {} by username: {}", id, username);
            throw new RuntimeException("Unauthorized");
        }
        expenseRepository.deleteById(id);
        log.info("Expense deleted with id: {}", id);
    }
}
