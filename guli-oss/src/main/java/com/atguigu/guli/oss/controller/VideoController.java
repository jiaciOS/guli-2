package com.atguigu.guli.oss.controller;

import com.atguigu.guli.oss.service.VideoService;
import com.atguigu.guli.vo.ResultSet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api("上传视频到阿里云oss")
@RestController
@CrossOrigin
@RequestMapping("/oss/video")
public class VideoController {

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @ApiOperation("视频上传")
    @PostMapping("upload")
    public ResultSet videoUpload(
            @ApiParam(name = "file", value = "视频文件", required = true)
            @RequestParam("file") MultipartFile file){

        // 上传视频到阿里云视频点播
        String videoSourceId = videoService.uploadVideo(file);
        return ResultSet.ok().data("videoSourceId", videoSourceId);
    }

    @ApiOperation("删除视频")
    @DeleteMapping("{videoSourceId}")
    public ResultSet videoRemove(
            @ApiParam(name = "videoSourceId", value = "视频id", required = true)
            @PathVariable("videoSourceId") String videoSourceId){
        videoService.removeVideoByVideoSourceId(videoSourceId);
        return ResultSet.ok();
    }

    @ApiOperation("根据videoSourceId获取playAuth,用于加密视频播放")
    @GetMapping("get-play-auth/{videoSourceId}")
    public ResultSet getVideoPlayAuth(
            @ApiParam(name = "videoSourceId", value = "视频id", required = true)
            @PathVariable("videoSourceId") String videoSourceId){
        String playAuth = videoService.getPlayAuthByVideoSourceId(videoSourceId);
        return ResultSet.ok().data("playAuth", playAuth);
    }
}
