package com.eujian.springretry.myanno;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class Aop {
    protected org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(com.eujian.springretry.myanno.MyRetryable)")
    public void pointCutR() {
    }
    /**
     * 埋点拦截器具体实现
     */
    @Around("pointCutR()")
    public Object methodRHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        MyRetryable myRetryable = targetMethod.getAnnotation(MyRetryable.class);
        MyBackoff backoff = myRetryable.backoff();
        int maxAttempts = myRetryable.maxAttempts();
        long sleepSecond = backoff.value();
        Exception ex = null;
        int retryCount = 1;
        do{
            try {

                Object proceed = joinPoint.proceed();
                return proceed;
            }catch (Exception e){
                logger.info("睡眠{}毫秒",sleepSecond);
                Thread.sleep(sleepSecond);
                retryCount++;
                sleepSecond = (long)(sleepSecond*backoff.multiplier());
                if(sleepSecond>backoff.maxDelay()){
                    sleepSecond = backoff.maxDelay();
                    logger.info("睡眠时间太长，改成{}毫秒",sleepSecond);
                }
                ex = e;

            }
        }while (retryCount<maxAttempts);

        throw ex;
    }
}
