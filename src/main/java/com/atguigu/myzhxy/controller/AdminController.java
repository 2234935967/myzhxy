package com.atguigu.myzhxy.controller;

import com.atguigu.myzhxy.pojo.Admin;
import com.atguigu.myzhxy.service.AdminService;
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
@RequestMapping("/sms/adminController")
public class AdminController {
    @Autowired
    AdminService adminService;

    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(
            @RequestBody List<Integer> ids
    ){
        adminService.removeByIds(ids);
        return Result.ok();
    }

    //saveOrUpdateAdmin
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(
            @RequestBody Admin admin
    ){
        if (admin.getId() == null || admin.getId()==0) {
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(
        @PathVariable("pageNo") Integer pageNo,
        @PathVariable("pageSize") Integer pageSize,
        String adminName
    ){
        Page<Admin> adminPage = new Page<>(pageNo, pageSize);
        IPage<Admin> iPage= adminService.getAllAdmin(adminPage,adminName);
        return Result.ok(iPage);
    }
}
