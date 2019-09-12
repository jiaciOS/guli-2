package com.atguigu.guli.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface VideoService {
    String uploadVideo(MultipartFile file);

    void removeVideoByVideoSourceId(String videoSourceId);

    String getPlayAuthByVideoSourceId(String videoSourceId);
}
