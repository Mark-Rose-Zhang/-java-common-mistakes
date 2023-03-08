package org.zhang.mistakes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author NanCheng
 * @version 1.0
 * @date 2023/3/7 11:55
 */
@SpringBootApplication
@EnableTransactionManagement
public class StartApp {
    public static void main(String[] args) {
        SpringApplication.run(StartApp.class, args);
    }
}
