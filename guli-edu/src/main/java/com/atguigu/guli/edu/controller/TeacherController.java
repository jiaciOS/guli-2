package com.atguigu.guli.edu.controller;


import com.atguigu.guli.constant.ResultCodeEnum;
import com.atguigu.guli.edu.entity.Teacher;
import com.atguigu.guli.edu.query.TeacherQuery;
import com.atguigu.guli.edu.service.TeacherService;
import com.atguigu.guli.exception.GuliException;
import com.atguigu.guli.vo.ResultSet;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author kevin
 * @since 2019-08-30
 */

@CrossOrigin
@Api("讲师管理")
@RestController
@RequestMapping("/edu/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @ApiOperation("讲师列表")
    @GetMapping("list")
    public ResultSet list() {
        //return ResultSet.setResult(ResultCodeEnum.SUCCESS).data("items", teacherService.list(null));
        List<Teacher> list = teacherService.list(null);
        return ResultSet.ok().data("items", list);
    }

    // 在表中一行数据已经被逻辑删除的情况下,再次删除,返回值为true;
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public ResultSet deleteById(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable("id") String id) {
        teacherService.removeById(id);
        return ResultSet.ok();
    }


    @ApiOperation(value = "分页条件查询讲师列表,使用postAJAX")
    @PostMapping("page_query")
    public ResultSet pageQuery(
            @ApiParam(name = "teacherQuery", value = "分页查询教师")
            @RequestBody(required = false) TeacherQuery teacherQuery) {

        Page<Teacher> page;
        if (teacherQuery == null) {
            // 无条件查询,分页
            System.err.println("没有传入任何条件");
            page = new Page<>(1L, 10L);
            teacherService.page(page, null);

        } else {
            if (teacherQuery.getPage() < 1 || teacherQuery.getLimit() < 1) {
                //throw new GuliException(21003, "参数不正确1");
                throw new GuliException(ResultCodeEnum.PARAM_ERROR);
            }
            page = teacherService.pageQuery(teacherQuery);
        }
        // teacherQuery != null
        List<Teacher> records = page.getRecords();
        long total = page.getTotal();
        return ResultSet.ok().data("total", total).data("items", records);
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping()
    public ResultSet save(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher){

        teacherService.save(teacher);
        return ResultSet.ok();
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("{id}")
    public ResultSet getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){

        Teacher teacher = teacherService.getById(id);
        return ResultSet.ok().data("item", teacher);
    }

    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("{id}")
    public ResultSet updateById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable("id") String id,

            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher){

        teacher.setId(id);
        teacherService.updateById(teacher);
        return ResultSet.ok();
    }
}

