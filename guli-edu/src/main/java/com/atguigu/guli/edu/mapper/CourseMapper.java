package com.atguigu.guli.edu.mapper;

import com.atguigu.guli.edu.entity.Course;
import com.atguigu.guli.edu.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author kevin
 * @since 2019-08-30
 */
public interface CourseMapper extends BaseMapper<Course> {
    CoursePublishVo selectCoursePublishVoById(String id);
}
