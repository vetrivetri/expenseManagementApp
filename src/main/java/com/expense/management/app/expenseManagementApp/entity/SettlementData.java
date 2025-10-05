package com.expense.management.app.expenseManagementApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="SETTLEMENT_DATA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettlementData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SETTLEMENT_DTL_SEQ")
    @SequenceGenerator(name = "SETTLEMENT_DTL_SEQ", sequenceName = "SETTLEMENT_DATA_SEQ", allocationSize = 1,initialValue = 1)
    @Column(name = "SETTLE_PK_ID")
    private Long settlementPkId;
    /*@Column(name = "RECEIVER_ID")
    private String receiverId;
    @Column(name = "Amount")
    private Double settledAmount;
    @Column(name = "EXPENSE_Id")
    private Long expenseId;*/
    @ManyToOne
    @JoinColumn(name = "payer_id")
    private UserData payer;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UserData receiver;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "EXPENSE_PK_ID")
    private ExpenseData expense;

    private LocalDateTime timestamp;

}
