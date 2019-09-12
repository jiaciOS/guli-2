package com.atguigu.guli.client;

import com.atguigu.guli.vo.ResultSet;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("guli-oss")
public interface OssVideoClient {

    @ApiOperation("删除视频")
    @DeleteMapping("/oss/video/{videoSourceId}")
    ResultSet videoRemove(
            @ApiParam(name = "videoSourceId", value = "视频id", required = true)
            @PathVariable("videoSourceId") String videoSourceId);
}
