package com.expense.management.app.expenseManagementApp.service.impl;

import com.expense.management.app.expenseManagementApp.beans.ErrorEnum;
import com.expense.management.app.expenseManagementApp.config.ExpenseManagementConstants;
import com.expense.management.app.expenseManagementApp.entity.ExpenseData;
import com.expense.management.app.expenseManagementApp.entity.GroupsEntity;
import com.expense.management.app.expenseManagementApp.entity.UserData;
import com.expense.management.app.expenseManagementApp.exception.ExpenseManagementException;
import com.expense.management.app.expenseManagementApp.repository.BalanceSheetRepository;
import com.expense.management.app.expenseManagementApp.repository.ExpenseDataRepository;
import com.expense.management.app.expenseManagementApp.repository.GroupDataRepository;
import com.expense.management.app.expenseManagementApp.repository.SplitDataRepository;
import com.expense.management.app.expenseManagementApp.service.AdminService;
import com.expense.management.app.expenseManagementApp.service.ExpenseManageService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    GroupDataRepository groupDataRepository;
    @Autowired
    SplitDataRepository splitRepo;
    @Autowired
    ExpenseDataRepository expenseDataRepository;
    @Autowired
    BalanceSheetRepository balanceSheetRepository;
    @Override
    @Transactional
    public void deleteGroup(Long groupId) throws ExpenseManagementException {
        try {
            GroupsEntity group = groupDataRepository.findById(groupId)
                    .orElseThrow(() -> new ExpenseManagementException(ExpenseManagementConstants.NO_DATA_FOUND_GROUP, ExpenseManagementConstants.NO_DATA_FOUND));
            group.getExpenses().stream().map(x -> {
                return x.getSplits();
            }).collect(Collectors.toList()).stream().forEach( x ->splitRepo.deleteAll(x));
            if(group.getBalanceSheet() != null && group.getExpenses() != null) {
                groupDataRepository.deleteByBalanceSheetAndExpenses(group.getBalanceSheet(), group.getExpenses());
            }else{
                groupDataRepository.delete(group);
            }

           // groupDataRepository.deleteByGroupPkId(groupId);
        }catch(Exception ex){
            logger.error("Failed to process get expense For the group", ex);
            throw new ExpenseManagementException(ErrorEnum.GENERIC_EXCEPTION.retVal, ExpenseManagementConstants.DB_GENERIC_EXCEPTION);
        }
    }

    @Override
    @Transactional
    public void restBalance(Long groupId) throws ExpenseManagementException {
        try {
            GroupsEntity group = groupDataRepository.findById(groupId)
                    .orElseThrow(() -> new ExpenseManagementException(ExpenseManagementConstants.NO_DATA_FOUND_GROUP, ExpenseManagementConstants.NO_DATA_FOUND));
            group.getExpenses().stream().map(x -> {
               return x.getSplits();
            }).collect(Collectors.toList()).stream().forEach( x ->splitRepo.deleteAll(x));
            group.getExpenses().stream().forEach(x -> expenseDataRepository.delete(x) );
            balanceSheetRepository.findByGroup(group).orElseThrow(() -> {
                        return new ExpenseManagementException
                                (ErrorEnum.GENERIC_EXCEPTION.retVal, ExpenseManagementConstants.DB_GENERIC_EXCEPTION);
                    })
                    .stream().forEach(x -> balanceSheetRepository.delete(x));
            //expenseDataRepository.deleteByBalanceSheetAndExpenses(group.getBalanceSheet(), group.getExpenses());
        }catch(Exception ex){
            logger.error("Failed to process get expense For the group", ex);
            throw new ExpenseManagementException(ErrorEnum.GENERIC_EXCEPTION.retVal, ExpenseManagementConstants.DB_GENERIC_EXCEPTION);
        }
        }
}
