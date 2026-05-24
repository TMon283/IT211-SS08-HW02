package com.example.it211ss08hw02.aspect;

import com.example.it211ss08hw02.entity.ErrorLog;
import com.example.it211ss08hw02.repository.ErrorLogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class FailureLoggingAspect {

    private final ErrorLogRepository errorLogRepository;

    @AfterThrowing(pointcut = "execution(* com.example.it211ss08hw02.service..*(..))", throwing = "ex")
    public void logServiceExceptions(JoinPoint joinPoint, Exception ex) {
        ErrorLog log = new ErrorLog();
        log.setTimestamp(LocalDateTime.now());
        log.setMethodName(joinPoint.getSignature().toShortString());
        log.setExceptionMessage(ex.getMessage());
        errorLogRepository.save(log);
    }
}

