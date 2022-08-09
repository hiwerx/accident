package com.lq.accident;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lq.accident.mapper")
public class AccidentApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccidentApplication.class, args);
    }

}
