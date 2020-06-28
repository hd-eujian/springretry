package com.eujian.springretry.service;

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
        throw new RuntimeException("异常");
    }
}
