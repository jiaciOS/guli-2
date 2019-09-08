package com.atguigu.guli.oss.controller;


import com.atguigu.guli.oss.config.OssProperties;
import com.atguigu.guli.oss.service.FileUploadService;
import com.atguigu.guli.vo.ResultSet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api("上传图片到阿里云oss")
@RestController
@CrossOrigin
@RequestMapping("/oss")
//@EnableConfigurationProperties(OssProperties.class) 这个只能加在配置类中才能将该对象注入
public class FileUpload {

    private final FileUploadService fileUploadService;

    @Autowired
    public FileUpload(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @ApiOperation("图片上传")
    @PostMapping("upload")
    public ResultSet fileUpload(
            @ApiParam(name = "host", value = "文件上传所在文件夹")
            @RequestParam(name = "host", required = false) String fileHost,
            @ApiParam(name = "file", value = "图片", required = true)
            @RequestParam("file") MultipartFile file) {
        // 校验文件信息
        if (file != null) {
            if (!StringUtils.isEmpty(fileHost)) {
                OssProperties.fileHost = fileHost;
            }
            String url = fileUploadService.upload(file);
            if (!StringUtils.isEmpty(url)) {
                System.err.println("图片上传成功,url: " + url);
                return ResultSet.ok().data("url", url);
            }
        }
        return ResultSet.error().message("未获取到上传文件数据或是图片上传失败");
    }
}
