package com.expense.management.app.expenseManagementApp.service;

import com.expense.management.app.expenseManagementApp.beans.CreateUserReq;
import com.expense.management.app.expenseManagementApp.beans.GroupRequestBean;
import com.expense.management.app.expenseManagementApp.entity.GroupsEntity;
import com.expense.management.app.expenseManagementApp.entity.UserData;
import com.expense.management.app.expenseManagementApp.exception.ExpenseManagementException;

import java.util.List;

public interface GroupManageService {
    GroupsEntity createGroup(GroupRequestBean groupsEntity) throws ExpenseManagementException;
    GroupsEntity addUserToGroup(Long groupId, Long userId) throws ExpenseManagementException;
    GroupsEntity removeUserFromGroup(Long groupId, Long userId) throws ExpenseManagementException;
    List<GroupsEntity> getGroupsForUser(Long userId) throws ExpenseManagementException;
    UserData addUserToSystem(CreateUserReq createUserReq) throws ExpenseManagementException;
}
