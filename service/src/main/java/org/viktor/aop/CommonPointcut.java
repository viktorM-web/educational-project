package org.viktor.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommonPointcut {

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isServiceLayer() {
    }
}
