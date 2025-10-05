package com.expense.management.app.expenseManagementApp.repository;

import com.expense.management.app.expenseManagementApp.entity.ExpenseData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.SQLException;

public interface ExpenseDataRepository extends JpaRepository<ExpenseData, Long> {
    Page<ExpenseData> findByGroup_GroupPkId(Long groupId, Pageable pageRequest) throws SQLException;
}
