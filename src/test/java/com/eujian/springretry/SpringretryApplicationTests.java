package com.eujian.springretry;

import com.eujian.springretry.service.RetryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringretryApplicationTests {
    @Autowired
    private RetryService retryService;

    @Test
    void contextLoads() {
        retryService.retry();
    }

    @Test
    void myRetry() {
        retryService.myRetry();
    }
}
