package com.atguigu.guli;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.atguigu.guli.ucenter.mapper")
@SpringBootApplication
public class GuliUcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuliUcenterApplication.class, args);
    }

}
