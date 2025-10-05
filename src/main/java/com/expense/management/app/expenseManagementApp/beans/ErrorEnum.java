package com.expense.management.app.expenseManagementApp.beans;

public enum ErrorEnum {
    NO_DATA_FOUND("No Data Found For the Given Identifier"),
    GENERIC_EXCEPTION("Generic Exception Occured During API Call, Kindly contact SYSTEM ADMIN"),
    DATABASE_ERROR("Database Error Kindly contact DBA"),
    SESSION_AVAILABLE_EXP("User Session is already available");
    public String retVal;
    private ErrorEnum(String data){
        this.retVal=data;
    }
}
