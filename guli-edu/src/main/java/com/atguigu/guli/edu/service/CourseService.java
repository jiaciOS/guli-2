package com.atguigu.guli.edu.service;

import com.atguigu.guli.edu.entity.Course;
import com.atguigu.guli.edu.query.CourseQuery;
import com.atguigu.guli.edu.vo.CourseInfo;
import com.atguigu.guli.edu.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author kevin
 * @since 2019-08-30
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfo(CourseInfo courseInfo);

    void pageQuery(Page<Course> pageParam, CourseQuery courseQuery);

    CourseInfo getCourseInfoById(String id);

    void updateCourseInfoById(CourseInfo courseInfo);

    void removeCourseById(String id);

    CoursePublishVo getCoursePublishVoById(String id);

    void publishCourseById(String id);
}
