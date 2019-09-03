package com.atguigu.guli.sysuser.controller;

import com.atguigu.guli.sysuser.entity.LoginVO;
import com.atguigu.guli.vo.ResultSet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Api("后台用户登录,获取信息和退出")
@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {

    @ApiOperation("用户登录")
    @PostMapping("login")
    public ResultSet login(
            @ApiParam(name = "loginVO", value = "用户登录username和password", required = true)
            @RequestBody LoginVO loginVO){
        if ("admin".equals(loginVO.getUsername()) && "admin".equals(loginVO.getPassword())){
            return ResultSet.ok().data("token", "admin");
        } else {
            return ResultSet.error();
        }
    }

    @ApiOperation("获取用户信息")
    @GetMapping("info")
    public ResultSet getInfo(
            @ApiParam(name = "token", value = "令牌", required = true)
            String token){
        if ("admin".equals(token)) {

            return ResultSet.ok().data("roles", Arrays.asList("admin"))
                    .data("name", "admin")
                    .data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        } else {
            return ResultSet.error();
        }
    }

    @ApiOperation(value = "用户登出")
    @PostMapping("logout")
    public ResultSet logout(){
        return ResultSet.ok();
    }
}
