package com.atguigu.myzhxy.service;

import com.atguigu.myzhxy.pojo.Clazz;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author shkstart
 * @create 2022-12-02 18:27
 */
public interface ClazzService extends IService<Clazz> {
    IPage<Clazz> getClazzsByOpr(Page<Clazz> page1, Clazz clazz);
}
