package com.ex.lifesemantics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ex.lifesemantics"})
@MapperScan(basePackages = {"com.ex.lifeSemantics"})
public class LifeSemanticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LifeSemanticsApplication.class, args);
    }

}
