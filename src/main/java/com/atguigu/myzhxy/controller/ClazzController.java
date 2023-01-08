package com.atguigu.myzhxy.controller;

import com.atguigu.myzhxy.pojo.Clazz;
import com.atguigu.myzhxy.service.ClazzService;
import com.atguigu.myzhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shkstart
 * @create 2022-12-02 18:37
 */
@Api(tags = "班级控制器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    ClazzService clazzService;

    //getClazzs
    @GetMapping("/getClazzs")
    public Result getClazzs() {
        List<Clazz> list = clazzService.list();
        return Result.ok(list);
    }

    //deleteClazz
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(
            @RequestBody List<Integer> ids
    ){
        clazzService.removeByIds(ids);
        return Result.ok();
    }

    //saveOrUpdateClazz
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(
            @RequestBody Clazz clazz
    ){
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    ///getClazzsByOpr/1/3
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize,
            Clazz clazz
    ){
        Page<Clazz> page1 = new Page<>(pageNo,pageSize);
        IPage<Clazz> page= clazzService.getClazzsByOpr(page1,clazz);
        return Result.ok(page);
    }
}

