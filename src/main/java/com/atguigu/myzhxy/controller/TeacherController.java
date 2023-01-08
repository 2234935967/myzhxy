package com.atguigu.myzhxy.controller;

import com.atguigu.myzhxy.pojo.Teacher;
import com.atguigu.myzhxy.service.TeacherService;
import com.atguigu.myzhxy.util.MD5;
import com.atguigu.myzhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shkstart
 * @create 2022-12-02 18:40
 */
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(
            @RequestBody List<Integer> ids
    ){
        teacherService.removeByIds(ids);
        return Result.ok();
    }

    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(
          @RequestBody  Teacher teacher
    ){
        if (teacher.getId() == null || teacher.getId()==0) {
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }


    //getTeachers/1/3
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize,
            Teacher teacher
    ){
        Page<Teacher> teacherPage = new Page<>(pageNo, pageSize);
        IPage<Teacher> iPage= teacherService.getTeachers(teacherPage,teacher);
        return Result.ok(iPage);
    }
}
