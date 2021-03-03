package com.moss.project.eneasy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan({ "com.moss.project.*" })
public class EasyTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyTemplateApplication.class, args);
    }

}
