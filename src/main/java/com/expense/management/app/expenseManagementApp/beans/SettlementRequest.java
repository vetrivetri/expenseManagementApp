package com.expense.management.app.expenseManagementApp.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SettlementRequest {
    private Long payerId;
    private Long receiverId;
    private Long expenseId;
    private BigDecimal amount;

}
