package com.example.it211ss08hw02.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSanitizationAspect {

    @Around("execution(* com.example.it211ss08hw02.service.TicketService.bookTicket(..))")
    public Object sanitizePassengerName(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String) {
                String name = (String) args[i];
                if (name != null) {
                    args[i] = name.trim().toUpperCase();
                }
            }
        }
        return joinPoint.proceed(args);
    }
}
