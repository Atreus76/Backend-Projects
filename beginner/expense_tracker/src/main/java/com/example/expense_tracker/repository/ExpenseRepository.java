package com.example.expense_tracker.repository;

import com.example.expense_tracker.models.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends MongoRepository<Expense, String> {
    List<Expense> findByUserIdAndDateBetween(String userId, LocalDate startDate,
                                             LocalDate endDate);
    List<Expense> findByUserId(String userId);
}
