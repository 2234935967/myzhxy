package com.atguigu.myzhxy.service.impl;

import com.atguigu.myzhxy.mapper.ClazzMapper;
import com.atguigu.myzhxy.pojo.Clazz;
import com.atguigu.myzhxy.service.ClazzService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author shkstart
 * @create 2022-12-02 18:27
 */
@Service
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper,Clazz> implements   ClazzService {

    @Override
    public IPage<Clazz> getClazzsByOpr(Page<Clazz> page1, Clazz clazz) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        String gradeName = clazz.getGradeName();
        String name = clazz.getName();
        if (!StringUtils.isEmpty(gradeName)){
            queryWrapper.eq("grade_name", gradeName);
        }
        if (!StringUtils.isEmpty(name)){
            queryWrapper.like("name", name);
        }
        queryWrapper.orderByDesc("id");
        Page<Clazz> page = baseMapper.selectPage(page1, queryWrapper);
        return page;
    }
}
