package com.eujian.springretry.service;

import com.eujian.springretry.myanno.MyBackoff;
import com.eujian.springretry.myanno.MyRetryable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableRetry
public class RetryService  {


    @Retryable(maxAttempts = 5,backoff = @Backoff(multiplier = 2,value = 2000L,maxDelay = 10000L))
    public void retry(){
        System.out.println(new Date());
        throw new RuntimeException("retry异常");
    }


    @MyRetryable(maxAttempts = 5,backoff = @MyBackoff(multiplier = 2,value = 2000L,maxDelay = 10000L))
    public void myRetry(){
        System.out.println(new Date());
        throw new RuntimeException("myRetry异常");
    }
}
