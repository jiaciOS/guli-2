package com.atguigu.guli.edu.service.impl;

import com.atguigu.guli.edu.entity.Chapter;
import com.atguigu.guli.edu.entity.Course;
import com.atguigu.guli.edu.entity.CourseDescription;
import com.atguigu.guli.edu.entity.Video;
import com.atguigu.guli.edu.mapper.ChapterMapper;
import com.atguigu.guli.edu.mapper.CourseDescriptionMapper;
import com.atguigu.guli.edu.mapper.CourseMapper;
import com.atguigu.guli.edu.mapper.VideoMapper;
import com.atguigu.guli.edu.query.CourseQuery;
import com.atguigu.guli.edu.service.CourseDescriptionService;
import com.atguigu.guli.edu.service.CourseService;
import com.atguigu.guli.edu.vo.CourseInfo;
import com.atguigu.guli.edu.vo.CoursePublishVo;
import com.atguigu.guli.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author kevin
 * @since 2019-08-30
 */
@Transactional
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    private final VideoMapper videoMapper;
    private final ChapterMapper chapterMapper;
    private final CourseDescriptionService courseDescriptionService;

    private final CourseDescriptionMapper courseDescriptionMapper;
    @Autowired
    public CourseServiceImpl(CourseDescriptionService courseDescriptionService, CourseDescriptionMapper courseDescriptionMapper, VideoMapper videoMapper, ChapterMapper chapterMapper) {
        this.courseDescriptionService = courseDescriptionService;
        this.courseDescriptionMapper = courseDescriptionMapper;
        this.videoMapper = videoMapper;
        this.chapterMapper = chapterMapper;
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    @Override
    public void publishCourseById(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(Course.COURSE_NORMAL);
        baseMapper.updateById(course);
    }


    @Override
    public String saveCourseInfo(CourseInfo courseInfo) {
        Course course = courseInfo.getCourse();
        CourseDescription courseDescription = courseInfo.getCourseDescription();
        // 插入数据,获取id
        baseMapper.insert(course);
        String courseId = course.getId();
        courseDescription.setId(courseId);
        courseDescriptionService.save(courseDescription);
        return courseId;
    }

    @Override
    public void pageQuery(Page<Course> pageParam, CourseQuery courseQuery) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        if (courseQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.ge("subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public CourseInfo getCourseInfoById(String id) {
        //从course表中取数据
        Course course = baseMapper.selectById(id);
        if(course == null){
            throw new GuliException(20001, "数据不存在");
        }

        //从course_description表中取数据
        CourseDescription courseDescription = courseDescriptionMapper.selectById(id);
        if(courseDescription == null){
            throw new GuliException(20001, "数据不完整");
        }

        //创建courseInfo对象
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setCourse(course);
        courseInfo.setCourseDescription(courseDescription);
        return courseInfo;
    }

    @Override
    public void updateCourseInfoById(CourseInfo courseInfo) {
        //保存课程基本信息
        baseMapper.updateById(courseInfo.getCourse());
        //保存课程详情信息
        courseDescriptionMapper.updateById(courseInfo.getCourseDescription());
    }

    @Override
    public void removeCourseById(String id) {
        //根据id删除所有视频
        QueryWrapper<Video> queryWrapperVideo = new QueryWrapper<>();
        queryWrapperVideo.eq("course_id", id);
        videoMapper.delete(queryWrapperVideo);

        //根据id删除所有章节
        QueryWrapper<Chapter> queryWrapperChapter = new QueryWrapper<>();
        queryWrapperChapter.eq("course_id", id);
        chapterMapper.delete(queryWrapperChapter);

        //删除课程详情
        courseDescriptionMapper.deleteById(id);

        //根据id删除课程
        baseMapper.deleteById(id);
    }
}
