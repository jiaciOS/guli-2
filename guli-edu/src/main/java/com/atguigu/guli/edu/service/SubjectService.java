package com.atguigu.guli.edu.service;

import com.atguigu.guli.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author kevin
 * @since 2019-08-30
 */
public interface SubjectService extends IService<Subject> {


    List<String> batchImport(MultipartFile file);
}
