package com.expense.management.app.expenseManagementApp.repository;

import com.expense.management.app.expenseManagementApp.entity.ExpenseData;
import com.expense.management.app.expenseManagementApp.entity.SplitEntity;
import com.expense.management.app.expenseManagementApp.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SplitDataRepository extends JpaRepository<SplitEntity,Long> {
    List<SplitEntity> findByExpense(ExpenseData expense);

    List<SplitEntity> findByExpenseAndUser(ExpenseData expense, UserData user);
}
