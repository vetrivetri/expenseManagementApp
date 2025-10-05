package com.expense.management.app.expenseManagementApp.repository;

import com.expense.management.app.expenseManagementApp.entity.ExpenseData;
import com.expense.management.app.expenseManagementApp.entity.SettlementData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementDataRepository extends JpaRepository<SettlementData, Long> {
}
