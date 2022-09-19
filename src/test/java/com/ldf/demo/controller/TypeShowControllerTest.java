package com.ldf.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ldf.demo.mapper.TypeMapper;
import com.ldf.demo.pojo.Type;
import com.ldf.demo.service.TypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 刘威甫
 * @date 2022/9/19 15:25
 * @description
 */
@SpringBootTest
class TypeShowControllerTest {

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private TypeService typeService;

    @Test
    public void listType(){
        int pageNum = 1;
        int pageSize = 5;


        List<Type> types = typeService.listTypes();

        PageHelper.startPage(pageNum, pageSize);
        System.out.println(types.toString());

        PageInfo<Type> pageInfo = new PageInfo<>(types);
        System.out.println(pageInfo.toString());
    }

}