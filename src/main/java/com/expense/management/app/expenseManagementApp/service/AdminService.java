package com.expense.management.app.expenseManagementApp.service;

import com.expense.management.app.expenseManagementApp.exception.ExpenseManagementException;

public interface AdminService {
    void deleteGroup(Long groupId) throws ExpenseManagementException;
    void restBalance(Long groupId) throws ExpenseManagementException;
}
