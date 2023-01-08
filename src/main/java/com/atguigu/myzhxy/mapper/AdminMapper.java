package com.atguigu.myzhxy.mapper;

import com.atguigu.myzhxy.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Repository;

/**
 * @author shkstart
 * @create 2022-12-02 18:20
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin> {
}
