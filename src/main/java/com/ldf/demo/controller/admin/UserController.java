package com.ldf.demo.controller.admin;

import com.ldf.demo.mapper.UserMapper;
import com.ldf.demo.pojo.User;
import com.ldf.demo.service.UserService;
import com.ldf.demo.service.impl.UserServiceImpl;
import com.ldf.demo.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
//@RequestMapping("/admin")
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/addUser")
    @ApiOperation("添加用户")
    public String addUser(@RequestParam String username,
                          @RequestParam String password){

//        System.out.println("输入的密码为：" + password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Utils.code(password));
//        System.out.println("用户信息" + user);

        userService.addUser(user);
        return "";
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出用户信息")
    public void exportUser(HttpServletRequest request, HttpServletResponse response){

        try{

            //创建文件对象
            HSSFWorkbook wb = new HSSFWorkbook();

            //创建表对象
            HSSFSheet sheet = wb.createSheet("user");

            //标题栏数据
            String[] titles = {"编号", "昵称", "用户名","密码","邮箱","头像","角色", "创建事件","更新时间"};

            //创建标题栏
            HSSFRow titleRow = sheet.createRow(0);

            //在标题栏写入数据
            for (int i = 0; i < titles.length; i++) {
                HSSFCell cell = titleRow.createCell(i);
                cell.setCellValue(titles[i]);
            }

            List<User> userList = new ArrayList<>();
            userList = userService.listUser();
            //循环写入用户数据
            if(userList != null && !userList.isEmpty()){
                for (int i = 0; i < userList.size(); i++) {
                    HSSFRow row2 = sheet.createRow(i+1);
                    row2.createCell(0).setCellValue(String.valueOf(userList.get(i).getId() == null ? "" : userList.get(i).getId()));
                    row2.createCell(1).setCellValue(userList.get(i).getNickname() == null ? "" : userList.get(i).getNickname());
                    row2.createCell(2).setCellValue(userList.get(i).getUsername() == null ? "" : userList.get(i).getUsername());
                    row2.createCell(3).setCellValue(userList.get(i).getPassword() == null ? "" : userList.get(i).getPassword());
                    row2.createCell(4).setCellValue(userList.get(i).getEmail() == null ? "" : userList.get(i).getEmail());
                    row2.createCell(5).setCellValue(userList.get(i).getAvatar() == null ? "" : userList.get(i).getAvatar());
                    row2.createCell(6).setCellValue((String) (userList.get(i).getType() == null ? "" : userList.get(i).getType()));
                    row2.createCell(7).setCellValue((String) (userList.get(i).getCreateTime() == null ? "" : userList.get(i).getCreateTime()));
                    row2.createCell(8).setCellValue((String) (userList.get(i).getUpdateTime() == null ? "" : userList.get(i).getUpdateTime()));
                }
            }

            OutputStream outputStream = response.getOutputStream();
            //清楚缓存
            response.reset();

            //定义浏览器相应表头
            String fileName = URLEncoder.encode("用户信息.xls", "UTF-8");
            response.setHeader("Content-Disposition","attachment;filename="+fileName);
            //定义下载的类型，标明是Excel文件
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.addHeader("Access-Control-Allow-Origin","*");
            response.addHeader("Access-Control-Allow-Methods","GET,POST，PUT,DLETE");
            response.addHeader("Access-Control-Allow-Headers","Content-Type");

            //把创建好的excel写入到输出流
            wb.write(outputStream);
            outputStream.close();
//            for (int i = 0; i < userList.size(); i++) {
//                System.out.println(userList.get(i));
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
