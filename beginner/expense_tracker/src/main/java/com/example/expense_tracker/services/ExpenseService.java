package com.example.expense_tracker.services;

import com.example.expense_tracker.dto.ExpenseRequest;
import com.example.expense_tracker.models.Expense;
import com.example.expense_tracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense createExpense(ExpenseRequest request,
                                 String userId){
        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate() != null ? request.getDate() : LocalDate.now());
        expense.setDescription(request.getDescription());
        expense.setUserId(userId);
        return expenseRepository.save(expense);
    }
    public List<Expense> getExpense(String userId, String filter, LocalDate startDate,
                                    LocalDate endDate){
        LocalDate now = LocalDate.now();
        switch (filter.toLowerCase()){
            case "week":
                startDate = now.minusDays(7);
                endDate = now;
                break;
            case "month":
                startDate = now.minusMonths(1);
                endDate = now;
                break;
            case "3months":
                startDate = now.minusMonths(3);
                endDate = now;
                break;
            case "custom":
                if (startDate == null || endDate == null){
                    throw new IllegalArgumentException("Start and end dates required for filter");
                }
                break;
            default:
                return expenseRepository.findByUserId(userId);
        }
        return expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }
    public Expense updateExpense(String id, ExpenseRequest request, String userId){
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found."));
        if (!expense.getUserId().equals(userId)){
            throw new RuntimeException("Unauthorized");
        }
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());
        expense.setDescription(request.getDescription());
        return expenseRepository.save(expense);
    }
    public void deleteExpense(String id, String userId){
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found."));
        if(!expense.getUserId().equals(userId)){
            throw new RuntimeException("Unauthorized");
        }
        expenseRepository.deleteById(id);
    }
}
