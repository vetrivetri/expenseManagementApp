package com.expense.management.app.expenseManagementApp.repository;

import com.expense.management.app.expenseManagementApp.entity.ExpenseData;
import com.expense.management.app.expenseManagementApp.entity.GroupsEntity;
import com.expense.management.app.expenseManagementApp.entity.UserData;
import org.apache.catalina.Group;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
    Optional<UserData> findByUserEmail(String emailId);
    Optional<UserData> findByUserEmailAndGroups(String emailId, GroupsEntity groupsEntity);
}
