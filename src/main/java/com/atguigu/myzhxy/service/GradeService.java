package com.atguigu.myzhxy.service;

import com.atguigu.myzhxy.pojo.Grade;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author shkstart
 * @create 2022-12-02 18:27
 */
public interface GradeService extends IService<Grade> {
    IPage<Grade> getGradesByOpr(Page<Grade> page, String gradeName);
}
