package com.atguigu.guli.edu.controller;


import com.atguigu.guli.edu.service.VideoService;
import com.atguigu.guli.edu.vo.VideoInfoForm;
import com.atguigu.guli.vo.ResultSet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author kevin
 * @since 2019-08-30
 */
@Api(description = "课时管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/edu/video")
public class VideoController {

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @ApiOperation(value = "新增课时")
    @PostMapping("save-video-info")
    public ResultSet save(
            @ApiParam(name = "videoForm", value = "课时对象", required = true)
            @RequestBody VideoInfoForm videoInfoForm) {

        videoService.saveVideoInfo(videoInfoForm);
        return ResultSet.ok();
    }

    @ApiOperation(value = "根据ID查询课时")
    @GetMapping("video-info/{id}")
    public ResultSet getVideInfoById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){

        VideoInfoForm videoInfoForm = videoService.getVideoInfoFormById(id);
        return ResultSet.ok().data("item", videoInfoForm);
    }

    @ApiOperation(value = "更新课时")
    @PutMapping("update-video-info")
    public ResultSet updateCourseInfoById(
            @ApiParam(name = "VideoInfoForm", value = "课时基本信息", required = true)
            @RequestBody VideoInfoForm videoInfoForm){

        videoService.updateVideoInfoById(videoInfoForm);
        return ResultSet.ok();
    }

    @ApiOperation(value = "根据ID删除课时")
    @DeleteMapping("{id}")
    public ResultSet removeById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){

        videoService.removeVideoById(id);
        return ResultSet.ok();
    }
}

