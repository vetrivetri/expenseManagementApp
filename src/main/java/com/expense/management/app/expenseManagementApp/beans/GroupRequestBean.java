package com.expense.management.app.expenseManagementApp.beans;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupRequestBean {

    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Session ID must be alphanumeric no special characters allowed")
    private String groupName;
    private GroupTypeEnum groupType;
    @JsonAlias("createdBy")
    private String groupCreatedBy;
    @JsonAlias("createdByEmail")
    private String groupCreatedByEmail;
    private Long creatorId;

}
