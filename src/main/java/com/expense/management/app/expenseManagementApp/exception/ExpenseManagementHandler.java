package com.expense.management.app.expenseManagementApp.exception;

import com.expense.management.app.expenseManagementApp.beans.GenericResponse;
import com.expense.management.app.expenseManagementApp.config.ExpenseManagementConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExpenseManagementHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpenseManagementException.class)
    public ResponseEntity<GenericResponse> handleCustomAppExceptionErrors(ExpenseManagementException ex) {
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setResponseCode(ex.getErrorCode());
        genericResponse.setResponseMessage(ex.getErrorMessage());
        return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse> handleGenericException(Exception ex) {
            GenericResponse genericResponse = new GenericResponse();
        genericResponse.setResponseCode(ExpenseManagementConstants.GENERIC_ERROR_CODE);
        genericResponse.setResponseMessage(ex.getMessage());
        return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
