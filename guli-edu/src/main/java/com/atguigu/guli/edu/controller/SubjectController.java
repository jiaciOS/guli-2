package com.atguigu.guli.edu.controller;


import com.atguigu.guli.constant.ResultCodeEnum;
import com.atguigu.guli.edu.entity.Subject;
import com.atguigu.guli.edu.service.SubjectService;
import com.atguigu.guli.exception.GuliException;
import com.atguigu.guli.vo.ResultSet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author kevin
 * @since 2019-08-30
 */
@Api("通过提交excel文件添加数据")
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @ApiOperation("导入Excel数据")
    @PostMapping("import")
    public ResultSet batchImport(
            @ApiParam(name = "file", value = "Excel文件", required = true)
            @RequestParam("file") MultipartFile file) {
        if (file == null) {
            return ResultSet.error().message("未能接收到传入的表格");
        }
        // 批量添加
        try {
            List<String> errorMsg = subjectService.batchImport(file);
            if (errorMsg.size() == 0) {
                return ResultSet.ok().message("批量导入成功");
            } else {
                return ResultSet.error().message("部分数据导入失败").data("errorMsgList", errorMsg);
            }

        } catch (Exception e) {
            //无论哪种异常，只要是在excel导入时发生的，一律用转成GuliException抛出
            throw new GuliException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }
    }

    @ApiOperation("获取subject分类列表")
    @GetMapping("list")
    public ResultSet getList(){
        List<Map<String, Object>> map = subjectService.getIdAndTitleList();
        return ResultSet.ok().data("items", map);
    }

    @ApiOperation("获取某一级分类列表")
    @GetMapping("list/{parentId}")
    public ResultSet getSubjectListByParentId(
            @ApiParam(name = "", value = "", required = true)
            @PathVariable("parentId") String parentId){
        List<Subject> list = subjectService.getSubjectListByParentId(parentId);
        return ResultSet.ok().data("items", list);
    }
}

