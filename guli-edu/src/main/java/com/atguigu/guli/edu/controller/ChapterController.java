package com.atguigu.guli.edu.controller;


import com.atguigu.guli.edu.entity.Chapter;
import com.atguigu.guli.edu.service.ChapterService;
import com.atguigu.guli.edu.vo.ChapterVo;
import com.atguigu.guli.vo.ResultSet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author kevin
 * @since 2019-08-30
 */
@Api("课程章节管理")
@CrossOrigin
@RestController
@RequestMapping("/edu/chapter")
public class ChapterController {

    private final ChapterService chapterService;

    @Autowired
    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @ApiOperation(value = "新增章节")
    @PostMapping
    public ResultSet save(
            @ApiParam(name = "chapter", value = "章节对象", required = true)
            @RequestBody Chapter chapter){

        chapterService.save(chapter);
        return ResultSet.ok();
    }

    @ApiOperation(value = "根据ID查询章节")
    @GetMapping("{id}")
    public ResultSet getById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id){

        Chapter chapter = chapterService.getById(id);
        return ResultSet.ok().data("item", chapter);
    }
    @ApiOperation(value = "根据ID修改章节")
    @PutMapping("{id}")
    public ResultSet updateById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id,

            @ApiParam(name = "chapter", value = "章节对象", required = true)
            @RequestBody Chapter chapter){

        chapter.setId(id);
        chapterService.updateById(chapter);
        return ResultSet .ok();
    }

    @ApiOperation(value = "根据ID删除章节")
    @DeleteMapping("{id}")
    public ResultSet removeById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id){

        chapterService.removeChapterById(id);
        return ResultSet.ok();
    }

    @ApiOperation(value = "嵌套章节数据列表")
    @GetMapping("nested-list/{courseId}")
    public ResultSet nestedListByCourseId(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId){

        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);
        return ResultSet.ok().data("items", chapterVoList);
    }
}

