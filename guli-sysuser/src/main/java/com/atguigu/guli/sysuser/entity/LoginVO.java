package com.atguigu.guli.sysuser.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(value="用户登录对象", description="sysuser")
@Data
public class LoginVO {
   @ApiModelProperty(value = "用户名")
   private String username;
   @ApiModelProperty(value = "密码")
   private String password;
}
