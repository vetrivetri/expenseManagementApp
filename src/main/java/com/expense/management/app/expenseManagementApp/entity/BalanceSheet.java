package com.expense.management.app.expenseManagementApp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Entity
@Table(name = "BALANCE_SHEET")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BalanceSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID", nullable = false)
    private GroupsEntity group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FROM_USER_ID", nullable = false)
    private UserData fromUser; // Debtor

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TO_USER_ID", nullable = false)
    private UserData toUser;   // Creditor

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

}
