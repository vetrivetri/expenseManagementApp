package com.expense.management.app.expenseManagementApp.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Oauth2ResponseBean {
    private String jwtAccesToken;
    private String userName;
    private String userEmail;
    private Long userId;

}
