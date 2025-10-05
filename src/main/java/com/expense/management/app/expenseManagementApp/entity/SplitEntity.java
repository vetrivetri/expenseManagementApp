package com.expense.management.app.expenseManagementApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "splits")
@Data
public class SplitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The expense this split belongs to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_id", nullable = false)
    private ExpenseData expense;

    // The user who owes this share
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserData user;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal share;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public SplitEntity() {}

    public SplitEntity(ExpenseData expense, UserData user, BigDecimal share) {
        this.expense = expense;
        this.user = user;
        this.share = share;
    }

    // Getters and Setters
    // (You can use Lombok @Getter/@Setter if preferred)
}