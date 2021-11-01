package com.payconiq.stock.handler;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@Aspect
public class LoggingHandler {

    @Pointcut("within(com.luan.payconiq.stock.controller.*Controller)")
    public void controller() {
        // controllers
    }

    @Pointcut("execution(public * *(..))")
    protected void publicMethod() {
        // public methods
    }

    @Pointcut("within(@com.luan.payconiq.stock.handler.Loggable *)")
    public void loggable() {
        // Loggable interface
    }

    @Before("controller() && publicMethod()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Executing Method [{}] for [{}]", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringTypeName());

        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        if (nonNull(codeSignature) && nonNull(codeSignature.getParameterNames())) {
            for (int i = 0; i < codeSignature.getParameterNames().length; i++) {
                logParameter(codeSignature.getParameterNames()[i], joinPoint.getArgs()[i]);
            }
        }
    }

    @AfterReturning(pointcut = "(controller() || loggable()) && publicMethod()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        log.info("Response for [{}] in [{}] : [{}]", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringTypeName(), result);
    }

    private void logParameter(String field, Object value) {
        log.info("Request parameter : {}={}", field, value);
    }
}
