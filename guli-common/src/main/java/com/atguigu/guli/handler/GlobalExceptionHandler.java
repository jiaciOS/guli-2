package com.atguigu.guli.handler;

import com.atguigu.guli.constant.ResultCodeEnum;
import com.atguigu.guli.exception.GuliException;
import com.atguigu.guli.util.ExceptionUtil;
import com.atguigu.guli.vo.ResultSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultSet error(Exception e) {
        log.error(ExceptionUtil.getMessage(e));
        return ResultSet.error();
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public ResultSet error(BadSqlGrammarException e) {
        log.error(ExceptionUtil.getMessage(e));
        return ResultSet.setResult(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResultSet error(HttpMessageNotReadableException e) {
        log.error(ExceptionUtil.getMessage(e));
        return ResultSet.setResult(ResultCodeEnum.JSON_PARSE_ERROR);
    }

    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public ResultSet error(GuliException e) {
        log.error(ExceptionUtil.getMessage(e));
        return ResultSet.error().message(e.getMessage()).code(e.getCode());
    }
}