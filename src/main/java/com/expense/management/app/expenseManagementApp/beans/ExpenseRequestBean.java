package com.expense.management.app.expenseManagementApp.beans;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRequestBean
{
    @NotBlank(message = "Group Id cannot be null")
    private Long groupId;
    @NotBlank(message = "User Id cannot be null")
    private Long userId;
    private BigDecimal totalAmount;
    @Size(max = 100, message = "Description Will be Maximum of 100 charcters")
    private String description;
    private HashMap<Long,BigDecimal> customSplits;
}
