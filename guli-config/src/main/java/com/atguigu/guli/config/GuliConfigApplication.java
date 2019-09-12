package com.atguigu.guli.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class GuliConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuliConfigApplication.class, args);
    }

}
