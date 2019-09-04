package com.atguigu.guli.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.atguigu.guli.handler", "com.atguigu.guli.oss"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GuliOssApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuliOssApplication.class, args);
    }

}
