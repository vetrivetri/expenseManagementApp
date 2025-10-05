package com.expense.management.app.expenseManagementApp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseManagementException extends Exception{
    private String errorMessage;
    private String errorCode;

}
