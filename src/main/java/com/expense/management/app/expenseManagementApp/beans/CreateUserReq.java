package com.expense.management.app.expenseManagementApp.beans;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserReq {
    private String userName;
    private String userEmail;
    private String userOauthId;
}
