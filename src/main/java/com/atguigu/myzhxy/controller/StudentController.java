package com.atguigu.myzhxy.controller;

import com.atguigu.myzhxy.pojo.Student;
import com.atguigu.myzhxy.service.StudentService;
import com.atguigu.myzhxy.util.MD5;
import com.atguigu.myzhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shkstart
 * @create 2022-12-02 18:37
 */
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
    @Autowired
    StudentService studentService;

    @DeleteMapping("/delStudentById")
    public Result deleteStudentById(@RequestBody List<Integer> ids){
        studentService.removeByIds(ids);
        return Result.ok(ids);
    }

    //addOrUpdateStudent
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(
         @RequestBody Student student
    ){
        if (student.getId() == null||student.getId()==0){
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        studentService.saveOrUpdate(student);
        return Result.ok();
    }


    //getStudentByOpr/1/3
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(
           @PathVariable("pageNo") Integer pageNo,
           @PathVariable("pageSize") Integer pageSize,
             Student Student
    ){
        Page<com.atguigu.myzhxy.pojo.Student> page = new Page<>(pageNo, pageSize);
        IPage<Student> iPage= studentService.getStudentByOpr(page,Student);
        return Result.ok(iPage);
    }
}
