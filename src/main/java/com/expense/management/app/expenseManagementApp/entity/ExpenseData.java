package com.expense.management.app.expenseManagementApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="EXPENSE_DATA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXPENSE_DTL_SEQ")
    @SequenceGenerator(name = "EXPENSE_DTL_SEQ", sequenceName = "EXPENSE_DATA_SEQ", allocationSize = 1,initialValue = 1)
    @Column(name = "EXPENSE_PK_ID")
    private Long expensePkId;
    @Column(name = "AMOUNT")
    private BigDecimal expenseAmount;
    @Column(name = "EXPENSE_CREATED_BY")
    private String exepenceCreatedBy;
    @Column(name = "EXPENSE_GROUP_ID")
    private Long expenseGroupId;
    @Column(name = "EXPENSE_CREATED_DATE")
    private  LocalDate expenseCreatedDate;
    @Column(name = "EXPENSE_DESCRIPTION")
    private String description;
    @ManyToOne
    @JoinColumn(name = "USER_PK_ID")
    private UserData createdBy;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "GROUP_PK_ID", referencedColumnName = "GROUP_PK_ID")
    private GroupsEntity group;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL)
    private List<SettlementData> settlementData;

}
