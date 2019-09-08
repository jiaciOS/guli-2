package com.atguigu.guli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GuliSysuserApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuliSysuserApplication.class, args);
    }

}
