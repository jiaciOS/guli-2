package com.atguigu.guli.client;


import com.atguigu.guli.vo.ResultSet;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("guli-ucenter")
public interface UcenterClient {
    // 注意: 这里的映射路径要写全
    @GetMapping("/ucenter/member/count-register/{day}")
    @ApiOperation("当天注册数")
    ResultSet registerCount(@PathVariable("day") String day);
}
