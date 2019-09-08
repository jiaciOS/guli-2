package com.atguigu.guli.edu.controller;


import com.atguigu.guli.edu.entity.Course;
import com.atguigu.guli.edu.query.CourseQuery;
import com.atguigu.guli.edu.service.CourseService;
import com.atguigu.guli.edu.vo.CourseInfo;
import com.atguigu.guli.edu.vo.CoursePublishVo;
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
 * 课程 前端控制器
 * </p>
 *
 * @author kevin
 * @since 2019-08-30
 */
@Api("课程管理")
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @ApiOperation("新增课程")
    @PostMapping("save-course-info")
    public ResultSet saveCourseInfo(
            @ApiParam(name = "courseInfo", value = "课程基本信息", required = true)
            @RequestBody CourseInfo courseInfo){
        // 保存课程信息
        String courseId =  courseService.saveCourseInfo(courseInfo);
        // 返回courseId
        return ResultSet.ok().data("courseId", courseId);
    }

    @ApiOperation(value = "分页课程列表")
    @GetMapping("{page}/{limit}")
    public ResultSet pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable("page") Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable("limit") Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象")
                    CourseQuery courseQuery){

        Page<Course> pageParam = new Page<>(page, limit);

        courseService.pageQuery(pageParam, courseQuery);
        List<Course> records = pageParam.getRecords();

        long total = pageParam.getTotal();

        return  ResultSet.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping("course-info/{id}")
    public ResultSet getById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){

        CourseInfo courseInfo = courseService.getCourseInfoById(id);
        return ResultSet.ok().data("item", courseInfo);
    }

    @ApiOperation(value = "更新课程")
    @PutMapping("update-course-info/{id}")
    public ResultSet updateCourseInfoById(
            @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfo courseInfo){
        courseService.updateCourseInfoById(courseInfo);
        return ResultSet.ok();
    }

    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("{id}")
    public ResultSet removeById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){
        courseService.removeCourseById(id);
        return ResultSet.ok();
    }

    @ApiOperation(value = "根据ID获取课程发布信息")
    @GetMapping("course-publish-info/{id}")
    public ResultSet getCoursePublishVoById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){

        CoursePublishVo courseInfoForm = courseService.getCoursePublishVoById(id);
        return ResultSet.ok().data("item", courseInfoForm);
    }

    @ApiOperation(value = "根据id发布课程")
    @PutMapping("publish-course/{id}")
    public ResultSet publishCourseById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){

        courseService.publishCourseById(id);
        return ResultSet.ok();
    }
}

