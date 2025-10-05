package com.expense.management.app.expenseManagementApp.controller;

import com.expense.management.app.expenseManagementApp.beans.ExpenseRequestBean;
import com.expense.management.app.expenseManagementApp.beans.SettlementRequest;
import com.expense.management.app.expenseManagementApp.entity.ExpenseData;
import com.expense.management.app.expenseManagementApp.entity.SettlementData;
import com.expense.management.app.expenseManagementApp.exception.ExpenseManagementException;
import com.expense.management.app.expenseManagementApp.service.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/v1/settlement")
public class SettlementController {

    @Autowired
    SettlementService settlementService;


    @PostMapping("/settleUp")
    public ResponseEntity<SettlementData> settleUp(@RequestBody SettlementRequest request) throws ExpenseManagementException {
        return ResponseEntity.ok(settlementService.settleAmount(request.getExpenseId(),request.getPayerId(),request.getReceiverId(),
                request.getAmount()));
    }


}
