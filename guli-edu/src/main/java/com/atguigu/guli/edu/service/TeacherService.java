package com.atguigu.guli.edu.service;

import com.atguigu.guli.edu.entity.Teacher;
import com.atguigu.guli.edu.query.TeacherQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author kevin
 * @since 2019-08-30
 */
public interface TeacherService extends IService<Teacher> {

    Page<Teacher> pageQuery(TeacherQuery teacherQuery);
}
