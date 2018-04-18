package cn.xiaoxige.firstssm.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AopLog {

    @Before(value = "execution(* cn.xiaoxige.firstssm.controller.UserController.*(..))")
    public void before(JoinPoint joinPoint) {

        String name = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println(name + "before ..." + args);
    }

}
