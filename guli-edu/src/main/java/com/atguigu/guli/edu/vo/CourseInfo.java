package com.atguigu.guli.edu.vo;

import com.atguigu.guli.edu.entity.Course;
import com.atguigu.guli.edu.entity.CourseDescription;
import lombok.Data;

@Data
public class CourseInfo {

    private Course course;
    private CourseDescription courseDescription;
}
