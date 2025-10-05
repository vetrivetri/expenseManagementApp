package com.expense.management.app.expenseManagementApp.repository;

import com.expense.management.app.expenseManagementApp.entity.BalanceSheet;
import com.expense.management.app.expenseManagementApp.entity.ExpenseData;
import com.expense.management.app.expenseManagementApp.entity.GroupsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupDataRepository extends JpaRepository<GroupsEntity, Long> {
    void deleteByGroupPkId(Long groupPkId);
    void deleteByBalanceSheetAndExpenses(List<BalanceSheet> balanceSheetList, List<ExpenseData> expenseData);
}
