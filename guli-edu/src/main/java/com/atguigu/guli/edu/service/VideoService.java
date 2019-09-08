package com.atguigu.guli.edu.service;

import com.atguigu.guli.edu.entity.Video;
import com.atguigu.guli.edu.vo.VideoInfoForm;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author kevin
 * @since 2019-08-30
 */
public interface VideoService extends IService<Video> {

    void saveVideoInfo(VideoInfoForm videoInfoForm);

    void updateVideoInfoById(VideoInfoForm videoInfoForm);

    VideoInfoForm getVideoInfoFormById(String id);

    void removeVideoById(String id);
}
