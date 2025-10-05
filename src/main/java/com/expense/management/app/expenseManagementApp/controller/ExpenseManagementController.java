package com.expense.management.app.expenseManagementApp.controller;

import com.expense.management.app.expenseManagementApp.beans.ExpenseRequestBean;
import com.expense.management.app.expenseManagementApp.entity.ExpenseData;
import com.expense.management.app.expenseManagementApp.exception.ExpenseManagementException;
import com.expense.management.app.expenseManagementApp.service.ExpenseManageService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/v1/expense")
public class ExpenseManagementController {

    @Autowired
    private ExpenseManageService expenseService;

    @PostMapping("/add-expense")
    public ResponseEntity<ExpenseData> addExpense(@RequestBody ExpenseRequestBean request) throws ExpenseManagementException{
        return ResponseEntity.ok(expenseService.addExpense(request));
    }

    @GetMapping("/get-expense-group/{groupId}")
    public ResponseEntity<Page<ExpenseData>> getExpenseForGroup(@NotBlank @PathVariable Long groupId ,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) throws ExpenseManagementException{
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(expenseService.getExpensesForGroup(groupId,pageable),HttpStatusCode.valueOf(200));
    }
}
