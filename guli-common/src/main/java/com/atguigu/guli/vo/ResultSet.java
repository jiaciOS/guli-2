package com.atguigu.guli.vo;


import com.atguigu.guli.constant.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@ApiModel(value = "全局统一返回结果")
public class ResultSet {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<>();

    private ResultSet(){}

    public static ResultSet ok(){
        ResultSet r = new ResultSet();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    public static ResultSet error(){
        ResultSet r = new ResultSet();
        r.setSuccess(ResultCodeEnum.UNKNOWN_REASON.getSuccess());
        r.setCode(ResultCodeEnum.UNKNOWN_REASON.getCode());
        r.setMessage(ResultCodeEnum.UNKNOWN_REASON.getMessage());
        return r;
    }

    public static ResultSet setResult(ResultCodeEnum resultCodeEnum){
        ResultSet r = new ResultSet();
        r.setSuccess(resultCodeEnum.getSuccess());
        r.setCode(resultCodeEnum.getCode());
        r.setMessage(resultCodeEnum.getMessage());
        return r;
    }

    public ResultSet success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public ResultSet message(String message){
        this.setMessage(message);
        return this;
    }

    public ResultSet code(Integer code){
        this.setCode(code);
        return this;
    }

    public ResultSet data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public ResultSet data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}
