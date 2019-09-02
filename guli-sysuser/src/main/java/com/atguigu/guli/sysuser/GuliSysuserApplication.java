package com.atguigu.guli.sysuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(value = {"com.atguigu.guli.handler", "com.atguigu.guli.sysuser"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GuliSysuserApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuliSysuserApplication.class, args);
    }

}
