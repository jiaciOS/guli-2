package com.atguigu.guli.oss.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.guli.constant.ResultCodeEnum;
import com.atguigu.guli.exception.GuliException;
import com.atguigu.guli.oss.config.OssProperties;
import com.atguigu.guli.oss.service.VideoService;
import com.atguigu.guli.oss.util.AliyunVodSDKUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class VideoServiceImpl implements VideoService {

    private final OssProperties ossProperties;

    @Autowired
    public VideoServiceImpl(OssProperties ossProperties) {
        this.ossProperties = ossProperties;
    }

    /**
     * 上传视频
     *
     * @param file 文件对象
     * @return 返回视频上传成功之后的videoSourceId
     */
    @Override
    public String uploadVideo(MultipartFile file) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            //文件名必须包含扩展名
            String fileName = file.getOriginalFilename();
            //视频标题(必选)
            String title = null;
            if (fileName != null) {
                title = fileName.substring(0, fileName.lastIndexOf("."));
            }
            //本地文件上传
            UploadStreamRequest request = new UploadStreamRequest(ossProperties.getKeyId(), ossProperties.getKeySecret(), title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            if (response.isSuccess()) {
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                return response.getVideoId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 通过videoSourceId删除视频
     *
     * @param videoSourceId 视频上传成功之后的videoSourceId
     */
    @Override
    public void removeVideoByVideoSourceId(String videoSourceId) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ossProperties.getKeyId(), ossProperties.getKeySecret());

            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoSourceId);
            client.getAcsResponse(request);
        } catch (ClientException e) {
            throw new GuliException(ResultCodeEnum.VIDEO_DELETE_ALIYUN_ERROR);
        }
    }

    /**
     * 根据videoSourceId获取playAuth,用于加密视频播放
     *
     * @param videoSourceId String类型,video上传成功后返回的id
     * @return String类型 返回用于播放该视频的playAuth.
     */
    @Override
    public String getPlayAuthByVideoSourceId(String videoSourceId) {
        //初始化客户端、请求对象和相应对象

        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ossProperties.getKeyId(), ossProperties.getKeySecret());
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //设置请求参数
            request.setVideoId(videoSourceId);
            // 设置令牌有效时间
            request.setAuthInfoTimeout(500L);
            //获取请求响应
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            //输出请求结果
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            return response.getPlayAuth();
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
            return null;
        }

    }
}
