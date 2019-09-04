package com.atguigu.guli.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan({"com.atguigu.guli.handler", "com.atguigu.guli.ucenter"})
@MapperScan("com.atguigu.guli.ucenter.mapper")
@SpringBootApplication
public class GuliUcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuliUcenterApplication.class, args);
    }

}
