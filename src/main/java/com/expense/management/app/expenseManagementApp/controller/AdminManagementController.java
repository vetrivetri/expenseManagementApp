package com.expense.management.app.expenseManagementApp.controller;

import com.expense.management.app.expenseManagementApp.exception.ExpenseManagementException;
import com.expense.management.app.expenseManagementApp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin")
public class AdminManagementController {

    @Autowired
    AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-group")
    public ResponseEntity<?> deleteGroup(@RequestParam Long groupId) throws ExpenseManagementException {
         adminService.deleteGroup(groupId);
        return new ResponseEntity<>( HttpStatusCode.valueOf(200));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/reset-balances")
    public ResponseEntity<?> resetBalance(@RequestParam Long groupId) throws ExpenseManagementException {
        adminService.restBalance(groupId);
          return new ResponseEntity<>( HttpStatusCode.valueOf(200));
    }
}
