package com.expense.management.app.expenseManagementApp.repository;

import com.expense.management.app.expenseManagementApp.entity.BalanceSheet;
import com.expense.management.app.expenseManagementApp.entity.GroupsEntity;
import com.expense.management.app.expenseManagementApp.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BalanceSheetRepository extends JpaRepository<BalanceSheet,Long> {
   Optional< BalanceSheet> findByGroupAndFromUserAndToUser(GroupsEntity group, UserData userData, UserData receiver);
    Optional<List<BalanceSheet>> findByGroup(GroupsEntity group);

}
