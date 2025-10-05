package com.expense.management.app.expenseManagementApp.repository;

import com.expense.management.app.expenseManagementApp.entity.ExpenseData;
import com.expense.management.app.expenseManagementApp.entity.GroupsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupDataRepository extends JpaRepository<GroupsEntity, Long> {
}
