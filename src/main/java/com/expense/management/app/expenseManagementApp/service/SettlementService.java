package com.expense.management.app.expenseManagementApp.service;

import com.expense.management.app.expenseManagementApp.entity.SettlementData;
import com.expense.management.app.expenseManagementApp.exception.ExpenseManagementException;

import java.math.BigDecimal;

public interface SettlementService {
    SettlementData settleAmount(Long expenseId, Long payerId, Long receiverId, BigDecimal amount) throws ExpenseManagementException;

}
