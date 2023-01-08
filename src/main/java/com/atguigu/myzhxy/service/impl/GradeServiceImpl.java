package com.atguigu.myzhxy.service.impl;

import com.atguigu.myzhxy.mapper.GradeMapper;
import com.atguigu.myzhxy.pojo.Grade;
import com.atguigu.myzhxy.service.GradeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author shkstart
 * @create 2022-12-02 18:34
 */
@Service
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
    @Override
    public IPage<Grade> getGradesByOpr(Page<Grade> page, String gradeName) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(gradeName)){
            queryWrapper.like("name", gradeName);
        }
        queryWrapper.orderByDesc("id");
        Page<Grade> page1 = baseMapper.selectPage(page, queryWrapper);
        //System.out.println(".......................................................................................");
        return page1;
    }
}
