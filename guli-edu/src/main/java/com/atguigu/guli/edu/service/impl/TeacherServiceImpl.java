package com.atguigu.guli.edu.service.impl;

import com.atguigu.guli.edu.entity.Teacher;
import com.atguigu.guli.edu.mapper.TeacherMapper;
import com.atguigu.guli.edu.query.TeacherQuery;
import com.atguigu.guli.edu.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author kevin
 * @since 2019-08-30
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {


    @Override
    public Page<Teacher> pageQuery(TeacherQuery teacherQuery) {
        // 无论如何都要进行分页查询,只关心是否有查询条件
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        Long current = teacherQuery.getCurrentPage();
        Long size = teacherQuery.getSize();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }

        if (!StringUtils.isEmpty(level) ) {
            queryWrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }

        Page<Teacher> page = new Page<>(current, size);
        baseMapper.selectPage(page, queryWrapper);
        return page;
    }

}
