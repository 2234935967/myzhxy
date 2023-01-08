package com.atguigu.myzhxy.service.impl;

import com.atguigu.myzhxy.mapper.TeacherMapper;
import com.atguigu.myzhxy.pojo.Admin;
import com.atguigu.myzhxy.pojo.LoginForm;
import com.atguigu.myzhxy.pojo.Teacher;
import com.atguigu.myzhxy.service.TeacherService;
import com.atguigu.myzhxy.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author shkstart
 * @create 2022-12-02 18:36
 */
@Service
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Override
    public Teacher login(LoginForm loginForm) {
        QueryWrapper<Teacher> Wrapper = new QueryWrapper<>();
        Wrapper.eq("name", loginForm.getUsername());
        Wrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        Teacher teacher = baseMapper.selectOne(Wrapper);
        return teacher;
    }

    @Override
    public Teacher getTeacherById(Long userId) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        Teacher teacher = baseMapper.selectOne(queryWrapper);
        return teacher;
    }

    @Override
    public IPage<Teacher> getTeachers(Page<Teacher> teacherPage, Teacher teacher) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        String clazzName = teacher.getClazzName();
        String name = teacher.getName();
        if (!StringUtils.isEmpty(name)) {
           queryWrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(clazzName)) {
            queryWrapper.eq("clazz_name",clazzName);
        }
        queryWrapper.orderByDesc("id");
        Page<Teacher> page = baseMapper.selectPage(teacherPage, queryWrapper);
        return page;

    }

    @Override
    public Teacher getOneByPwdAndId(Long userId, String pwd) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        queryWrapper.eq("password",pwd);
        Teacher Teacher = baseMapper.selectOne(queryWrapper);
        return Teacher;
    }
}
