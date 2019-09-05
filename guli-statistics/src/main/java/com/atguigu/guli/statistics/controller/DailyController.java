package com.atguigu.guli.statistics.controller;


import com.atguigu.guli.statistics.service.DailyService;
import com.atguigu.guli.vo.ResultSet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author kevin
 * @since 2019-09-04
 */
@Api("统计每日的注册人数")
@RestController
@RequestMapping("/statistics/daily")
@CrossOrigin
public class DailyController {

    private final DailyService dailyService;

    @Autowired
    public DailyController(DailyService dailyService) {
        this.dailyService = dailyService;
    }

    @GetMapping("{day}")
    public ResultSet createStatisticsByDate(
            @ApiParam(name = "day", value = "创建统计某一天的注册量的对应的日期", required = true)
            @PathVariable("day") String day) {
        dailyService.createStatisticsByDay(day);
        return ResultSet.ok();
    }

}

