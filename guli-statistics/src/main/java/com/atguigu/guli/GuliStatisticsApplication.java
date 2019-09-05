package com.atguigu.guli;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
// @EnableFeignClients会扫描当前包及其子包中标注有@FeignClient注解的接口,必须是接口.
@EnableFeignClients
@MapperScan("com.atguigu.guli.statistics.mapper")
@SpringBootApplication
public class GuliStatisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuliStatisticsApplication.class, args);
    }

}
