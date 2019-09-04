package com.atguigu.guli.edu.service.impl;

import com.atguigu.guli.edu.entity.Subject;
import com.atguigu.guli.edu.mapper.SubjectMapper;
import com.atguigu.guli.edu.service.SubjectService;
import com.atguigu.guli.util.ExcelImportUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author kevin
 * @since 2019-08-30
 */
@Transactional
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public List<String> batchImport(MultipartFile file) {
        InputStream inputStream = null;
        List<String> errorMsgList = null;
        try {
            // 解析得到sheet表格对象
            inputStream = file.getInputStream();
            ExcelImportUtil excelImportUtil = new ExcelImportUtil(inputStream);
            HSSFSheet sheet = excelImportUtil.getSheet();

            // 逐个判断第一列和第二列中是否有空的数据,重复的数据,如果有,
            // 则生成错误信息str,存放到List中返回,其余的都上传到数据库
            // java中第0行对应第一级/第二级  最后一行 = lastIndex + 1;
            // 要从第二行,也就是index = 开始读
            // 在生成错误信息的时候行数得加+1
            //System.out.println(sheet.getLastRowNum());
            //System.out.println(sheet.getRow(0));
            // 如果第一列为空,则无需看第二列,因为不知道属于哪个一级分类;
            errorMsgList = new LinkedList<>();
            for (int i = 1; i < sheet.getLastRowNum(); ++i) {
                HSSFRow row = sheet.getRow(i);
                // 判断第一列: 一级分类
                if (row != null) {
                    HSSFCell cell = row.getCell(0);
                    if (cell == null || StringUtils.isEmpty(excelImportUtil.getCellValue(cell).trim())) {
                        errorMsgList.add("第" + (i + 1) + "行,第一列没有数据,添加失败");
                    } else {
                        // 添加数据
                        // 查看数据库中是否有相同的数据
                        String cellValue = excelImportUtil.getCellValue(cell).trim();
                        Subject subject = new Subject();
                        subject.setTitle(cellValue);
                        String parentId;
                        Subject querySubject = this.getByTitle(cellValue, "0");

                        if (querySubject == null) {
                            // 添加
                            baseMapper.insert(subject);
                            parentId = subject.getId();
                        } else {
                            parentId = querySubject.getId();
                        }
                        // 开始插入第二列
                        HSSFCell cell1 = row.getCell(1);
                        String cellValue1 = excelImportUtil.getCellValue(cell1).trim();
                        if (cell1 == null || StringUtils.isEmpty(cellValue1)) {
                            errorMsgList.add("第" + (i + 1) + "行,第二列没有数据,添加失败");
                        } else {
                            // 先查看是否有重复

                            Subject querySubject1 = this.getByTitle(cellValue1, parentId);
                            if (querySubject1 == null) {
                                // 添加
                                Subject subject1 = new Subject();
                                subject1.setTitle(cellValue);
                                subject1.setParentId(parentId);
                                subject1.setSort(i);
                                baseMapper.insert(subject1);
                            }
                        }
                    }
                } else {
                    errorMsgList.add("第" + (i + 1) + "行没有任何数据,添加失败");
                }
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

        return errorMsgList;
    }

    /**
     * 根据分类名称和父id查询分类否存在
     *
     * @param title subject的title属性
     * @return 数据库中查询到的subject对象, 无数据则返回null
     */
    private Subject getByTitle(String title, String parentId) {

        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", parentId);
        return baseMapper.selectOne(queryWrapper);
    }
}
