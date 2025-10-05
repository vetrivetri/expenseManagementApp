package com.expense.management.app.expenseManagementApp.service;

import com.expense.management.app.expenseManagementApp.beans.ExpenseRequestBean;
import com.expense.management.app.expenseManagementApp.entity.ExpenseData;
import com.expense.management.app.expenseManagementApp.exception.ExpenseManagementException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ExpenseManageService {
    ExpenseData addExpense(ExpenseRequestBean request) throws ExpenseManagementException;
    Page<ExpenseData> getExpensesForGroup(Long groupId, Pageable page) throws ExpenseManagementException;

}
