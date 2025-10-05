package com.expense.management.app.expenseManagementApp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admin")
public class AdminManagementController {
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete-group")
    public String deleteGroup(@RequestParam Long groupId) {
        return "Admin Dashboard";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reset-balances")
    public String resetBalance(@RequestParam Long groupId) {
        return "Admin Dashboard";
    }
}
