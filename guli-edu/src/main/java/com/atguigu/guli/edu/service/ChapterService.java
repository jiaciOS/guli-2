package com.atguigu.guli.edu.service;

import com.atguigu.guli.edu.entity.Chapter;
import com.atguigu.guli.edu.vo.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author kevin
 * @since 2019-08-30
 */
public interface ChapterService extends IService<Chapter> {

    void removeChapterById(String id);

    List<ChapterVo> nestedList(String courseId);
}
