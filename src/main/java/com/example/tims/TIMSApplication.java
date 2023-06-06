package com.example.tims;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement // 开启注解事务管理
@MapperScan("com.example.tims.dao")
public class TIMSApplication {

    public static void main(String[] args) {
        SpringApplication.run(TIMSApplication.class, args);
    }

}
