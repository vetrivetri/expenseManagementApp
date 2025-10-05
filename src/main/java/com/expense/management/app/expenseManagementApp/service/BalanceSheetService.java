package com.expense.management.app.expenseManagementApp.service;

import com.expense.management.app.expenseManagementApp.entity.*;
import com.expense.management.app.expenseManagementApp.repository.BalanceSheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BalanceSheetService {

    private final BalanceSheetRepository balanceRepo;

    public void updateBalancesOnExpense(ExpenseData expense, List<SplitEntity> splits) {
        UserData paidBy = expense.getCreatedBy();
        GroupsEntity group = expense.getGroup();

        for (SplitEntity split : splits) {
            UserData owedBy = split.getUser();
            BigDecimal share = split.getShare();

            if (!owedBy.equals(paidBy)) {
                BalanceSheet balance = balanceRepo
                        .findByGroupAndFromUserAndToUser(group, owedBy, paidBy)
                        .orElseGet(() -> {
                            BalanceSheet newBalance = new BalanceSheet();
                            newBalance.setGroup(group);
                            newBalance.setFromUser(owedBy);
                            newBalance.setToUser(paidBy);
                            newBalance.setAmount(BigDecimal.ZERO);
                            return newBalance;
                        });

                balance.setAmount(balance.getAmount().add(share));
                balanceRepo.save(balance);
            }
        }
    }
}
