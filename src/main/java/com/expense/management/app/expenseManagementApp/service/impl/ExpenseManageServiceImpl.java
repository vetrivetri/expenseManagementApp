package com.expense.management.app.expenseManagementApp.service.impl;

import com.expense.management.app.expenseManagementApp.beans.ErrorEnum;
import com.expense.management.app.expenseManagementApp.beans.ExpenseRequestBean;
import com.expense.management.app.expenseManagementApp.config.ExpenseManagementConstants;
import com.expense.management.app.expenseManagementApp.entity.ExpenseData;
import com.expense.management.app.expenseManagementApp.entity.GroupsEntity;
import com.expense.management.app.expenseManagementApp.entity.SplitEntity;
import com.expense.management.app.expenseManagementApp.entity.UserData;
import com.expense.management.app.expenseManagementApp.exception.ExpenseManagementException;
import com.expense.management.app.expenseManagementApp.repository.ExpenseDataRepository;
import com.expense.management.app.expenseManagementApp.repository.GroupDataRepository;
import com.expense.management.app.expenseManagementApp.repository.SplitDataRepository;
import com.expense.management.app.expenseManagementApp.repository.UserDataRepository;
import com.expense.management.app.expenseManagementApp.service.BalanceSheetService;
import com.expense.management.app.expenseManagementApp.service.ExpenseManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ExpenseManageServiceImpl implements ExpenseManageService {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseManageService.class);


    @Autowired
    private ExpenseDataRepository expenseRepo;

    @Autowired
    private SplitDataRepository splitDataRepository;

    @Autowired
    private GroupDataRepository groupDataRepository;

    @Autowired
    private UserDataRepository userRepo;
    @Autowired
    private BalanceSheetService balanceSheetService;
    @Override
    public ExpenseData addExpense(ExpenseRequestBean request) throws ExpenseManagementException {
        ExpenseData expense = new ExpenseData();

        try {
            GroupsEntity group = groupDataRepository.findById(request.getGroupId())
                    .orElseThrow(() -> new ExpenseManagementException(ExpenseManagementConstants.NO_DATA_FOUND_GROUP,ExpenseManagementConstants.NO_DATA_FOUND));

            UserData createdBy = userRepo.findById(request.getUserId())
                    .orElseThrow(() -> new ExpenseManagementException(ExpenseManagementConstants.NO_DATA_FOUND_USER,ExpenseManagementConstants.NO_DATA_FOUND));


            expense.setExpenseAmount(request.getTotalAmount());
            expense.setCreatedBy(createdBy);
            expense.setExpenseCreatedDate(LocalDate.now());
            expense.setGroup(group);
            expense = expenseRepo.save(expense);

            Set<UserData> members = group.getMembers();
            if (members.isEmpty()) {
                throw new ExpenseManagementException("No members in this group",ExpenseManagementConstants.NO_DATA_FOUND);
            }

            Map<Long, BigDecimal> splitMap = request.getCustomSplits();

            if (splitMap == null || splitMap.isEmpty()) {

                double perUser = request.getTotalAmount().doubleValue() / members.size();
                for (UserData user : members) {
                    SplitEntity share = new SplitEntity();
                    share.setExpense(expense);
                    share.setUser(user);
                    share.setShare(BigDecimal.valueOf(perUser));
                    splitDataRepository.save(share);
                }
            } else {
                double totalCheck = splitMap.values().stream().mapToDouble(BigDecimal::doubleValue).sum();
                if (Math.abs(totalCheck - request.getTotalAmount().doubleValue()) > 0.01) {
                    throw new ExpenseManagementException("Custom splits do not add up to total amount","");
                }

                for (Map.Entry<Long, BigDecimal> entry : splitMap.entrySet()) {
                    UserData user = userRepo.findById(entry.getKey())
                            .orElseThrow(() -> new RuntimeException("User not found for ID: " + entry.getKey()));

                    SplitEntity share = new SplitEntity();
                    share.setExpense(expense);
                    share.setUser(user);
                    share.setShare(entry.getValue());
                    splitDataRepository.save(share);
                }

            }
            List<SplitEntity> splits = splitDataRepository.findByExpense(expense);
            balanceSheetService.updateBalancesOnExpense(expense,splits);
        }catch (ExpenseManagementException ex){
            throw  ex;
        }
        return expense;
    }

    @Override
    public Page<ExpenseData> getExpensesForGroup(Long groupId, Pageable pageRequest) throws ExpenseManagementException{
        try {
          return expenseRepo.findByGroup_GroupPkId(groupId,pageRequest);
        }
        catch (SQLException sqEx){
            logger.error("Db failure", sqEx.getMessage());
            throw new ExpenseManagementException(ErrorEnum.DATABASE_ERROR.retVal, ExpenseManagementConstants.DB_GENERIC_EXCEPTION);
        }catch(Exception ex){
            logger.error("Failed to process get expense For the group", ex);
            throw new ExpenseManagementException(ErrorEnum.GENERIC_EXCEPTION.retVal, ExpenseManagementConstants.DB_GENERIC_EXCEPTION);
        }
    }
}
