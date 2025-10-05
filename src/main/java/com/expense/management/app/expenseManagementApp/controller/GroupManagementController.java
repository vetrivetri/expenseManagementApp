package com.expense.management.app.expenseManagementApp.controller;

import com.expense.management.app.expenseManagementApp.beans.CreateUserReq;
import com.expense.management.app.expenseManagementApp.beans.GroupRequestBean;
import com.expense.management.app.expenseManagementApp.entity.GroupsEntity;
import com.expense.management.app.expenseManagementApp.entity.UserData;
import com.expense.management.app.expenseManagementApp.exception.ExpenseManagementException;
import com.expense.management.app.expenseManagementApp.service.GroupManageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/v1/group")
@EnableAspectJAutoProxy
public class GroupManagementController {
    @Autowired
    GroupManageService groupManageService;
    @PostMapping("/create-new-group")
    public ResponseEntity<?> createNewGroup(@RequestBody GroupRequestBean groupReqBean) throws ExpenseManagementException {
        GroupsEntity group = groupManageService.createGroup(groupReqBean);
        return new ResponseEntity<>( group,HttpStatusCode.valueOf(200));
    }

    @PostMapping("/add-user")
    public ResponseEntity<?> addUserToSystem(@RequestBody CreateUserReq createUserReq) throws ExpenseManagementException {
        UserData user = groupManageService.addUserToSystem(createUserReq);
        return new ResponseEntity<>( user,HttpStatusCode.valueOf(200));
    }
    @PostMapping("/add-user/{userId}/to-group/{groupId}")
    public ResponseEntity<?> addUserToGroup(@NotBlank @PathVariable Long userId,
                                            @NotBlank @PathVariable Long groupId,
                                            HttpServletRequest request

     ) throws ExpenseManagementException {
        GroupsEntity group = groupManageService.addUserToGroup(groupId,userId,request.getAttribute("x-api-user").toString());
        return new ResponseEntity<>( group,HttpStatusCode.valueOf(200));
    }
    @DeleteMapping("/remove-user/{userId}/from-group/{groupId}")
    public ResponseEntity<?> removeUserFromGroup(@NotBlank @PathVariable Long userId,
                                            @NotBlank @PathVariable Long groupId,
                                                  HttpServletRequest request) throws ExpenseManagementException {
        GroupsEntity group = groupManageService.removeUserFromGroup(groupId,userId,request.getAttribute("x-api-user").toString());
        return new ResponseEntity<>( group,HttpStatusCode.valueOf(200));
    }
    @GetMapping("/get-user-groups/{userId}")
    public ResponseEntity<?> getGroupsForUser(@NotBlank @PathVariable Long userId) throws ExpenseManagementException {
        List<GroupsEntity> group = groupManageService.getGroupsForUser(userId);
        return new ResponseEntity<>( group,HttpStatusCode.valueOf(200));
    }

    /**
     *
     * @param page
     * @param size
     * @return
     * @throws ExpenseManagementException
     * Before Adding Any Request This API can be used to get all the users who
     * are successfully available in Expense Management Eco System
     */
    @GetMapping("/get-all-user")
    public ResponseEntity<?> getAllUser(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) throws ExpenseManagementException {
        Page<UserData> userData = groupManageService.getAllUser(PageRequest.of(page, size));
        return new ResponseEntity<>( userData,HttpStatusCode.valueOf(200));
    }
}
