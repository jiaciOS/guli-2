package com.atguigu.guli;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

// 包扫描覆盖了SpringBootApplication的默认扫描策略,因此必须手动指定扫描com.atguigu.guli.edu
@MapperScan("com.atguigu.guli.edu.mapper")
@SpringBootApplication
@EnableFeignClients
public class GuliEduApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuliEduApplication.class, args);
    }

}
