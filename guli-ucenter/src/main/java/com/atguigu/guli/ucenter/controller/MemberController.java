package com.atguigu.guli.ucenter.controller;


import com.atguigu.guli.ucenter.entity.Member;
import com.atguigu.guli.ucenter.service.MemberService;
import com.atguigu.guli.vo.ResultSet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author kevin
 * @since 2019-09-04
 */
@Slf4j
@Api("用户注册数据中心")
@RestController
@RequestMapping("/ucenter/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("count-register/{day}")
    @ApiOperation("当天注册数")
    public ResultSet registerCount(
            @ApiParam(name = "day", value = "统计日期", required = true)
            @PathVariable("day") String day){
        log.error("测试日志...");
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.likeRight("gmt_create", day);
        int count = memberService.count(wrapper);
        return ResultSet.ok().data("countRegister", count);
    }

}

