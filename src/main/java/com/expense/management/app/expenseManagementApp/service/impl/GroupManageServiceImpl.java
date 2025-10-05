package com.expense.management.app.expenseManagementApp.service.impl;

import com.expense.management.app.expenseManagementApp.beans.CreateUserReq;
import com.expense.management.app.expenseManagementApp.beans.ErrorEnum;
import com.expense.management.app.expenseManagementApp.beans.GroupRequestBean;
import com.expense.management.app.expenseManagementApp.config.ExpenseManagementConstants;
import com.expense.management.app.expenseManagementApp.entity.GroupsEntity;
import com.expense.management.app.expenseManagementApp.entity.UserData;
import com.expense.management.app.expenseManagementApp.exception.ExpenseManagementException;
import com.expense.management.app.expenseManagementApp.repository.GroupDataRepository;
import com.expense.management.app.expenseManagementApp.repository.UserDataRepository;
import com.expense.management.app.expenseManagementApp.service.ExpenseManageService;
import com.expense.management.app.expenseManagementApp.service.GroupManageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupManageServiceImpl implements GroupManageService {
    private static final Logger logger = LoggerFactory.getLogger(GroupManageServiceImpl.class);

    @Autowired
    UserDataRepository userDataRepository;
    @Autowired
    GroupDataRepository groupDataRepository;
    @Override
    public GroupsEntity createGroup(GroupRequestBean groupsEntity) throws ExpenseManagementException{
        try {
            ObjectMapper mapper = new ObjectMapper();
            UserData userData=userDataRepository.findById(groupsEntity.getCreatorId()).orElseThrow(() ->new ExpenseManagementException(ErrorEnum.GENERIC_EXCEPTION.retVal, ExpenseManagementConstants.DB_GENERIC_EXCEPTION));
           GroupsEntity entityObj = mapper.convertValue(groupsEntity,GroupsEntity.class);
            entityObj.setGroupCreatedDate(LocalDate.now());
            Set<UserData> dataMember = new HashSet<>();
            dataMember.add(userData);
            entityObj.setMembers(dataMember);
            return groupDataRepository.save(entityObj);
        }catch(Exception ex){
            logger.error("Error In Creating Group", ex);
            throw new ExpenseManagementException(ErrorEnum.GENERIC_EXCEPTION.retVal, ExpenseManagementConstants.DB_GENERIC_EXCEPTION);
        }
    }

    @Override
    public GroupsEntity addUserToGroup(Long groupId, Long userId,String userEmail) throws ExpenseManagementException{
        try {

            GroupsEntity group = groupDataRepository.findById(groupId)
                    .orElseThrow(() -> new ExpenseManagementException(ExpenseManagementConstants.NO_DATA_FOUND_GROUP,ExpenseManagementConstants.NO_DATA_FOUND));
            UserData user = userDataRepository.findById(userId)
                    .orElseThrow(() -> new ExpenseManagementException(ExpenseManagementConstants.NO_DATA_FOUND_USER,ExpenseManagementConstants.NO_DATA_FOUND));
            UserData loggedInUser =userDataRepository.findByUserEmailAndGroups(userEmail,group).orElseThrow(
                    () -> new ExpenseManagementException("User Cannot Add others to a Group which he dosen't belong",ExpenseManagementConstants.NO_DATA_FOUND));

            group.getMembers().add(user);
            return groupDataRepository.save(group);
        }catch (ExpenseManagementException ex){
           throw ex  ;      }
        catch(Exception ex){
            logger.error("Failed to Add users to group", ex);
            throw new ExpenseManagementException(ErrorEnum.GENERIC_EXCEPTION.retVal, ExpenseManagementConstants.DB_GENERIC_EXCEPTION);
        }
    }

    @Override
    public GroupsEntity removeUserFromGroup(Long groupId, Long userId,String apiUserEmail) throws ExpenseManagementException{
try{
    GroupsEntity group = groupDataRepository.findById(groupId)
            .orElseThrow(() -> new ExpenseManagementException(ExpenseManagementConstants.NO_DATA_FOUND_GROUP,ExpenseManagementConstants.NO_DATA_FOUND));
    UserData user = userDataRepository.findById(userId)
            .orElseThrow(() -> new ExpenseManagementException(ExpenseManagementConstants.NO_DATA_FOUND_USER,ExpenseManagementConstants.NO_DATA_FOUND));
    UserData loggedInUser =userDataRepository.findByUserEmailAndGroups(apiUserEmail,group).orElseThrow(
            () -> new ExpenseManagementException("User Cannot remove others from a  Group which he dosen't belong",ExpenseManagementConstants.NO_DATA_FOUND));
   Double totalOwedInGroup= group.getBalanceSheet().stream().filter( x -> Objects.equals(x.getFromUser().getUserPkId(), userId))
            .mapToDouble(x -> x.getAmount().doubleValue()).sum();
   if(totalOwedInGroup>0){
       throw new ExpenseManagementException("User Owes Amount In This group and cannot leave this group",ExpenseManagementConstants.NO_DATA_FOUND_USER);

   }

    if (!group.getMembers().contains(user)) {
        throw new ExpenseManagementException("User is not a member of this group",ExpenseManagementConstants.NO_DATA_FOUND_USER);
    }
if(apiUserEmail.equals(group.getGroupCreatedByEmail()) || apiUserEmail.equals(user.getUserEmail())) {
    group.getMembers().remove(user);
}else{
    throw new ExpenseManagementException("Remove User Option Only for Group Creater User Or USer who wants to Leave the group",ExpenseManagementConstants.NO_DATA_FOUND_USER);

}
    return groupDataRepository.save(group);
}catch (ExpenseManagementException ex){
    throw ex  ;      }
catch(Exception ex){
    logger.error("Failed to Add users to group", ex);
    throw new ExpenseManagementException(ErrorEnum.GENERIC_EXCEPTION.retVal, ExpenseManagementConstants.DB_GENERIC_EXCEPTION);
}
    }

    @Override
    public List<GroupsEntity> getGroupsForUser(Long userId) throws ExpenseManagementException{
       try {
           UserData user = userDataRepository.findById(userId)
                   .orElseThrow(() -> new ExpenseManagementException(ExpenseManagementConstants.NO_DATA_FOUND_GROUP, ExpenseManagementConstants.NO_DATA_FOUND));
           return new ArrayList<>(user.getGroups());
       } catch (ExpenseManagementException ex){
           throw ex  ;      }
       catch(Exception ex){
           logger.error("Failed to get Users For a Group", ex);
           throw new ExpenseManagementException(ErrorEnum.GENERIC_EXCEPTION.retVal, ExpenseManagementConstants.DB_GENERIC_EXCEPTION);
       }
    }

    @Override
    public UserData addUserToSystem(CreateUserReq createUserReq) throws ExpenseManagementException {
        try{
            UserData user = userDataRepository.findByUserEmail(createUserReq.getUserEmail())
                    .orElse(null);
            if(user == null) {
                ObjectMapper mapper = new ObjectMapper();
                UserData entityObj = mapper.convertValue(createUserReq, UserData.class);
                entityObj.setCreatedDate(LocalDate.now());
                return userDataRepository.save(entityObj);
            }else{
                return user;
            }
        }catch(Exception ex){
            logger.error("Error In Creating Group", ex);
            throw new ExpenseManagementException(ErrorEnum.GENERIC_EXCEPTION.retVal, ExpenseManagementConstants.DB_GENERIC_EXCEPTION);
        }

    }

    @Override
    public Page<UserData> getAllUser(PageRequest pageRequest) throws ExpenseManagementException {
        try{
            return userDataRepository.findAll(pageRequest);
        }catch(Exception ex){
            logger.error("Error In Creating Group", ex);
            throw new ExpenseManagementException(ErrorEnum.GENERIC_EXCEPTION.retVal, ExpenseManagementConstants.DB_GENERIC_EXCEPTION);
        }
    }
}
