package com.atguigu.guli.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.atguigu.guli.statistics.mapper")
@ComponentScan({"com.atguigu.guli.handler", "com.atguigu.guli.statistics"})
@SpringBootApplication
public class GuliStatisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuliStatisticsApplication.class, args);
    }

}
