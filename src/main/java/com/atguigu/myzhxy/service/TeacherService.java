package com.atguigu.myzhxy.service;

import com.atguigu.myzhxy.pojo.LoginForm;
import com.atguigu.myzhxy.pojo.Teacher;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author shkstart
 * @create 2022-12-02 18:29
 */

public interface TeacherService extends IService<Teacher> {
    Teacher login(LoginForm loginForm);

    Teacher getTeacherById(Long userId);

    IPage<Teacher> getTeachers(Page<Teacher> teacherPage, Teacher teacher);

    Teacher getOneByPwdAndId(Long userId, String pwd);
}
