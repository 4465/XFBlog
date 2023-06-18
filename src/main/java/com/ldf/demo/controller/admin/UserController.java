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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
//@RequestMapping("/admin")
@Api(tags = "用户管理模块")
public class UserController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/addUser")
    @ApiOperation("添加用户")
    public String register(@RequestParam String username,
                          @RequestParam String password){

//        System.out.println("输入的密码为：" + password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Utils.code(password));
//        System.out.println("用户信息" + user);

        userService.register(user);
        return "";
    }

    @ApiOperation(value = "转向文件上传界面")
    @GetMapping("/toImport")
    public String toUpLoad(){
        return "upload";
    }

    @ApiOperation(value = "导入用户信息")
    @PostMapping(value = "/import")
    public ModelAndView uploadUser(MultipartFile file) {
        ModelAndView mv = new ModelAndView();

        try {
            //创建输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名
            String fileName = file.getOriginalFilename();
            System.out.println("文件名：" + fileName);

            //判断是什么版本
            Workbook workbook = this.getWorkBook(fileName, inputStream);


            if (workbook == null) {
                mv.setViewName("uploadInfo");
                mv.addObject("msg", "文件类型错误");
            }

            //获取文件中的表格
            Sheet sheetAt = workbook.getSheetAt(0);
            //获取文件中的长度，从0开始
            int lastRowNum = sheetAt.getLastRowNum();

            for (int i = 0; i < lastRowNum; i++) {
                Row row = sheetAt.getRow(i + 1);

                if(i+1 > lastRowNum){
                    break;
                }

                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);

                User user = new User();

                user.setNickname(row.getCell(1).getStringCellValue());
                user.setUsername(row.getCell(2).getStringCellValue());
                user.setPassword(MD5Utils.code(row.getCell(3).getStringCellValue()));
                user.setEmail(row.getCell(4).getStringCellValue());
                user.setAvatar(row.getCell(5).getStringCellValue());
                if(!row.getCell(6).getStringCellValue().equals("")){
                    user.setType(Integer.valueOf(row.getCell(6).getStringCellValue()));
                }
                if(!row.getCell(7).getStringCellValue().equals("")){
                    user.setCreateTime(new Date(row.getCell(7).getStringCellValue()));
                }
                if(!row.getCell(8).getStringCellValue().equals("")){
                    user.setUpdateTime(new Date(row.getCell(8).getStringCellValue()));
                }
                userService.addUser(user);
                mv.addObject("msg", "上传成功");
                mv.setViewName("uploadInfo");
            }
        } catch (IOException e) {
            e.printStackTrace();
            mv.addObject("msg", "内部错误");
            mv.setViewName("uploadInfo");
        }
        return mv;
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
            String[] titles = {"编号", "昵称", "用户名","密码","邮箱","头像","角色", "创建时间","更新时间"};

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

    public Workbook getWorkBook(String fileName, InputStream inputStream) throws IOException {
        Workbook workbook = null;
        if(fileName.endsWith(".xls")){
            workbook = new HSSFWorkbook(inputStream);
        }else if(fileName.endsWith("xlsx")){
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }

}
