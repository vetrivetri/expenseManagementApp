package com.expense.management.app.expenseManagementApp.config;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
public class ExpenseAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseAspect.class);

    // Pointcut to match all controller methods
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}

    // Advice that runs after successful execution of controller methods
    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void logAfterController(JoinPoint joinPoint, Object result) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String controller = joinPoint.getSignature().getDeclaringTypeName();
        String action = joinPoint.getSignature().getName();

        logger.info("✅ API Request Completed: [{} {}] -> {}.{}()", method, uri, controller, action);
        logger.info("🔁 Response: {}", result);
    }

        @AfterThrowing(pointcut = "controllerMethods()", throwing = "ex")
        public void logControllerException (JoinPoint joinPoint, Throwable ex){
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            String method = request.getMethod();
            String uri = request.getRequestURI();
            String controller = joinPoint.getSignature().getDeclaringTypeName();
            String action = joinPoint.getSignature().getName();

            logger.error("❌ API Request Failed: [{} {}] -> {}.{}()", method, uri, controller, action);
            logger.error("💥 Exception: {}", ex.getMessage(), ex);
        }


    }
