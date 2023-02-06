package com.ex.lifesemantics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.ex"})
public class LifeSemanticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LifeSemanticsApplication.class, args);
    }

}
