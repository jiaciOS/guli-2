package com.atguigu.guli.sysuser.controller;

import com.atguigu.guli.sysuser.entity.LoginVO;
import com.atguigu.guli.vo.ResultSet;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {
/*
    @PostMapping("login")
    public ResultSet login(
            @RequestParam("username") String username,
            @RequestParam("password") String password){
        if ("admin".equals(username) && "admin".equals(password)){
            return ResultSet.ok().data("token", "admin");
        } else {
            return ResultSet.error();
        }
    }*/

    @PostMapping("login")
    public ResultSet login(
            @RequestBody LoginVO loginVO){
        if ("admin".equals(loginVO.getUsername()) && "admin".equals(loginVO.getPassword())){
            return ResultSet.ok().data("token", "admin");
        } else {
            return ResultSet.error();
        }
    }

    @GetMapping("info")
    public ResultSet getInfo(String token){
        if ("admin".equals(token)) {


            return ResultSet.ok().data("roles", Arrays.asList("admin"))
                    .data("name", "admin")
                    .data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        } else {
            return ResultSet.error();
        }
    }

}
