package org.viktor.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggerServiceLayerAspect {

    @Around("org.viktor.aop.CommonPointcut.isServiceLayer()")
    public Object addLoggingAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Around before - invoke method {} in class {}, with params {}", joinPoint.getSignature(), joinPoint.getTarget(), joinPoint.getArgs());
        try {
            var result = joinPoint.proceed();
            log.info("Around after returning - invoke method {} in class {}, result {}", joinPoint.getSignature(), joinPoint.getTarget(), result);
            return result;
        } catch (Throwable ex) {
            throw ex;
        }
    }
}
