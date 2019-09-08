package com.atguigu.guli.statistics.controller;


import com.atguigu.guli.statistics.service.DailyService;
import com.atguigu.guli.vo.ResultSet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @ApiOperation("统计某一天的注册量")
    @GetMapping("{day}")
    public ResultSet createStatisticsByDate(
            @ApiParam(name = "day", value = "创建统计某一天的注册量的对应的日期", required = true)
            @PathVariable("day") String day) {
        dailyService.createStatisticsByDay(day);
        return ResultSet.ok();
    }

    @ApiOperation("获取某个时间段的某一项信息的统计值")
    @GetMapping("show-chart/{begin}/{end}/{type}")
    public ResultSet showChart(
            @ApiParam(name = "begin", value = "起始时间,例如: 2019-01-01", required = true)
            @PathVariable String begin,
            @ApiParam(name = "end", value = "结束时间,例如: 2019-01-10", required = true)
            @PathVariable String end,
            @ApiParam(name = "type", value = "要查找的统计项,例如: register_num, login_num, video_view_num, course_num", required = true)
            @PathVariable String type){
        Map<String, Object> map = dailyService.getChartData(begin, end, type);
        return ResultSet.ok().data(map);
    }
}

