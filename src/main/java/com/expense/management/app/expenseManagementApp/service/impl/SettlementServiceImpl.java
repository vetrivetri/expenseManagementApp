package com.expense.management.app.expenseManagementApp.service.impl;

import com.expense.management.app.expenseManagementApp.beans.ErrorEnum;
import com.expense.management.app.expenseManagementApp.config.ExpenseManagementConstants;
import com.expense.management.app.expenseManagementApp.entity.*;
import com.expense.management.app.expenseManagementApp.exception.ExpenseManagementException;
import com.expense.management.app.expenseManagementApp.repository.*;
import com.expense.management.app.expenseManagementApp.service.ExpenseManageService;
import com.expense.management.app.expenseManagementApp.service.SettlementService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SettlementServiceImpl implements SettlementService {
    private static final Logger logger = LoggerFactory.getLogger(SettlementServiceImpl.class);

    @Autowired
    private SettlementDataRepository settlementRepo;

    @Autowired
    private ExpenseDataRepository expenseRepo;

    @Autowired
    private UserDataRepository userRepo;

    @Autowired
    private BalanceSheetRepository balanceSheetRepository;

    @Autowired
    private SplitDataRepository splitDataRepository;

    @Transactional
    @Override
    public SettlementData settleAmount(Long expenseId, Long payerId, Long receiverId, BigDecimal amount) throws ExpenseManagementException{
        try {
            ExpenseData expense = expenseRepo.findById(expenseId)
                    .orElseThrow(() ->new ExpenseManagementException("Expense Not Found",ExpenseManagementConstants.NO_DATA_FOUND));
            GroupsEntity group = expense.getGroup();
            UserData payer = userRepo.findById(payerId)
                    .orElseThrow(() -> new ExpenseManagementException(ExpenseManagementConstants.NO_DATA_FOUND_USER,ExpenseManagementConstants.NO_DATA_FOUND));
            UserData receiver = userRepo.findById(receiverId)
                    .orElseThrow(() -> new ExpenseManagementException(ExpenseManagementConstants.NO_DATA_FOUND_USER,ExpenseManagementConstants.NO_DATA_FOUND));

            List<SplitEntity> splits = splitDataRepository.findByExpenseAndUser(expense, payer);
            BigDecimal totalOwed = splits.stream()
                    .map(SplitEntity::getShare)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (totalOwed.compareTo(amount) < 0) {
                throw new ExpenseManagementException("Cannot settle more than owed for this expense",ExpenseManagementConstants.GENERIC_ERROR_CODE);
            }

            // 1. Fetch current balance
            BalanceSheet balance = balanceSheetRepository
                    .findByGroupAndFromUserAndToUser(group, payer, receiver)
                    .orElseThrow(() -> new ExpenseManagementException("NO Balance Found",ExpenseManagementConstants.GENERIC_ERROR_CODE)
);

            // 2. Validate settlement amount
            if (balance.getAmount().compareTo(amount) < 0) {
                throw new ExpenseManagementException("Cannot settle more than owed",ExpenseManagementConstants.GENERIC_ERROR_CODE);
            }

            SettlementData settlement = new SettlementData();
            settlement.setAmount(amount);
            settlement.setExpense(expense);
            settlement.setPayer(payer);
            settlement.setReceiver(receiver);

            BalanceSheet balanceRes = balanceSheetRepository
                    .findByGroupAndFromUserAndToUser(group, payer, receiver)
                    .orElseGet(() -> {
                        BalanceSheet newBalance = new BalanceSheet();
            newBalance.setGroup(group);
            newBalance.setFromUser(payer);
            newBalance.setToUser(receiver);
            newBalance.setAmount(BigDecimal.ZERO);
            return newBalance;
                    });

            balanceRes.setAmount(balance.getAmount().subtract(amount));
            balanceSheetRepository.save(balance);

            return settlementRepo.save(settlement);
        }catch (ExpenseManagementException ex){
            throw ex  ;      }
        catch(Exception ex){
            logger.error("Issue in Settlement API Service", ex);
            throw new ExpenseManagementException(ErrorEnum.GENERIC_EXCEPTION.retVal, ExpenseManagementConstants.DB_GENERIC_EXCEPTION);
        }
    }
}

