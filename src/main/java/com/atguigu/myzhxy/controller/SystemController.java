package com.atguigu.myzhxy.controller;

import com.atguigu.myzhxy.pojo.Admin;
import com.atguigu.myzhxy.pojo.LoginForm;
import com.atguigu.myzhxy.pojo.Student;
import com.atguigu.myzhxy.pojo.Teacher;
import com.atguigu.myzhxy.service.AdminService;
import com.atguigu.myzhxy.service.StudentService;
import com.atguigu.myzhxy.service.TeacherService;
import com.atguigu.myzhxy.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author shkstart
 * @create 2022-12-02 18:39
 */
@RestController
@RequestMapping("/sms/system")
public class SystemController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

   // updatePwd/123456/1234567
    @PostMapping("/updatePwd/{password}/{updatapassword}")
    public Result updatePwd(
            @RequestHeader String token,
            @PathVariable("password") String password,
            @PathVariable("updatapassword") String updatapassword
    ){
        if (JwtHelper.isExpiration(token)) {
            return Result.fail().message("token失效");
        }
        Integer userType = JwtHelper.getUserType(token);
        Long userId = JwtHelper.getUserId(token);
        String pwd = MD5.encrypt(password);
        String newPwd = MD5.encrypt(updatapassword);
        switch (userType) {
            case 1:
                Admin admin= adminService.getOneByPwdAndId(userId,pwd);
                if (admin != null) {
                    admin.setPassword(newPwd);
                    adminService.saveOrUpdate(admin);
                }else {
                    return Result.fail().message("原密码有误");
                }
                break;
            case 2:
                Student Student= studentService.getOneByPwdAndId(userId,pwd);
                if (Student != null) {
                    Student.setPassword(newPwd);
                    studentService.saveOrUpdate(Student);
                }else {
                    return Result.fail().message("原密码有误");
                }
                break;
            case 3:
                Teacher Teacher= teacherService.getOneByPwdAndId(userId,pwd);
                if (Teacher != null) {
                    Teacher.setPassword(newPwd);
                    teacherService.saveOrUpdate(Teacher);
                }else {
                    return Result.fail().message("原密码有误");
                }
                break;
        }
        return Result.ok();
    }

    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(
           @RequestParam("multipartFile") MultipartFile multipartFile
    ){
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String newFileName =uuid.concat(originalFilename.substring(index));
        String protraitPath="G:/javabasics/project/myzhxy/target/classes/public/upload/".concat(newFileName);
        try {
            multipartFile.transferTo(new File(protraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path="upload/".concat(newFileName);
        return Result.ok(path);
    }

    @GetMapping("/getInfo")
    public Result getinfoByid(@RequestHeader("token") String token){
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration){
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        //查找id，返回类型
        Map<String,Object> map = new LinkedHashMap<>();
        switch (userType){
            case 1:
                Admin admin= adminService.getAdminById(userId);
                map.put("userType",1);
                map.put("user",admin);
                break;
            case 2:
                Student student= studentService.getStudentById(userId);
                map.put("userType",2);
                map.put("user",student);
                break;
            case 3:
                Teacher teacher = teacherService.getTeacherById(userId);
                map.put("userType",3);
                map.put("user",teacher);
                break;
        }
        return Result.ok(map);
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm,HttpServletRequest request){
        //验证码校验
        HttpSession session = request.getSession();
        String sessionVerifiCode = (String)session.getAttribute("verifiCode");
        String loginVerifiCode = loginForm.getVerifiCode();
        if("".equals(sessionVerifiCode) || null == sessionVerifiCode){
            return Result.fail().message("验证码失效,请刷新后重试");
        }
        if (!sessionVerifiCode.equalsIgnoreCase(loginVerifiCode)){
            return Result.fail().message("验证码有误,请小心输入后重试");
        }
        // 从session域中移除现有验证码
        session.removeAttribute("verifiCode");
        //什么身份登录
        Map<String,Object> map = new LinkedHashMap<>();
        switch (loginForm.getUserType()){
            case 1:
                try {
                    Admin admin = adminService.login(loginForm);
                    if (admin!=null){
                        // 用户的类型和用户id转换成一个密文,以token的名称向客户端反馈
                        map.put("token", JwtHelper.createToken(admin.getId().longValue(),1));
                    }else {
                        throw  new RuntimeException("用户名或密码错误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 2:
                try {
                    Student student = studentService.login(loginForm);
                    if (student!=null){
                        map.put("token", JwtHelper.createToken(student.getId().longValue(),2));
                    }else {
                        throw  new RuntimeException("用户名或密码错误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                try {
                    Teacher teacher = teacherService.login(loginForm);
                    if (teacher!=null){
                        map.put("token",JwtHelper.createToken(teacher.getId().longValue(),3));
                    }else {
                        throw  new RuntimeException("用户名或密码错误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }
        return Result.fail().message("查无此用户");
    }

    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){
        //获取验证码图片
        BufferedImage codeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取内容
        // 获取图片上的验证码
        String verifiCode =new String( CreateVerifiCodeImage.getVerifiCode());
        //将验证码保存到session域中
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode",verifiCode);
        //输出图片
        try {
            ImageIO.write(codeImage,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
