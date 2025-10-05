package com.expense.management.app.expenseManagementApp.beans;

import lombok.Data;

@Data
public class GoogleTokenInfo {
    private String issued_to;
    private String audience;
    private String user_id;
    private String scope;
    private int expires_in;
    private String access_type;

}
