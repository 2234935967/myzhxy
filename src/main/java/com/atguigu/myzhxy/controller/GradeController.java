package com.atguigu.myzhxy.controller;

import com.atguigu.myzhxy.pojo.Grade;
import com.atguigu.myzhxy.service.GradeService;
import com.atguigu.myzhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shkstart
 * @create 2022-12-02 18:37
 * 年纪管理
 */
@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {
    @Autowired
    GradeService gradeService;

    @ApiOperation("查找全部年级")
    @GetMapping("/getGrades")
    public Result getGrades(){
        List<Grade> list = gradeService.list();
        return Result.ok(list);
    }

    @ApiOperation("删除grade")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(
          @ApiParam("要删除的ids")  @RequestBody List<Integer> ids
    ){
        gradeService.removeByIds(ids);
        return Result.ok();
    }


    @ApiOperation("更新或添加grade，带id表示更新")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(
          @ApiParam("要更新的grade数据(json返回)") @RequestBody Grade grade
    ) {
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    @ApiOperation("模糊查询数据分页返回")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(
         @ApiParam("第几页") @PathVariable("pageNo") Integer pageNo,
         @ApiParam("显示多少条") @PathVariable("pageSize")  Integer pageSize,
         @ApiParam("模糊查询")  String gradeName
    ){
        Page<Grade> page = new Page<Grade>(pageNo, pageSize);
        IPage<Grade> iPage= gradeService.getGradesByOpr(page,gradeName);
        return Result.ok(iPage);
    }
}
