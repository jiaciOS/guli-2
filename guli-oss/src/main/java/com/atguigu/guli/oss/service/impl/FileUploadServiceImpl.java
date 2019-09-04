package com.atguigu.guli.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.guli.oss.config.OssProperties;
import com.atguigu.guli.oss.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private OssProperties ossProperties;

    @Override
    public String upload(MultipartFile file) {


        InputStream inputStream = null;
        OSS ossClient = null;
        try {
            inputStream = file.getInputStream();
            // 校验文件内容
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String lastExt = originalFilename.substring(originalFilename.lastIndexOf("."));
            String[] extArr = {".png", ".jpg", ".jpeg", ".bmp"};
            Set<String> exts = new HashSet<>(Arrays.asList(extArr));
            if (exts.add(lastExt)) {
                // 文件名后缀不符合要求
                return null;
            }

            BufferedImage image = ImageIO.read(inputStream);
            if (image.getWidth() <= 0 || image.getHeight() <= 0) {
                // 这个图片内容不正确
                return null;
            }
            // 校验完成后上传到阿里云oss
            String endPoint = ossProperties.getEndPoint();
            String keyId = ossProperties.getKeyId();
            String keySecret = ossProperties.getKeySecret();
            String bucketName = ossProperties.getBucketName();
            String fileHost = ossProperties.getFileHost();
            String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String key = fileHost + "/" + LocalDate.now().format(formatter) + "/" + fileName;
            ossClient = new OSSClientBuilder().build(endPoint, keyId, keySecret);

            // 重新获取InputStream 因为ImageIO已经把数据都读取过去了!
            inputStream = file.getInputStream();
            ossClient.putObject(bucketName, key, inputStream);
            // 返回图片路径
            return "https://" + bucketName + "." + endPoint + "/" + key;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
