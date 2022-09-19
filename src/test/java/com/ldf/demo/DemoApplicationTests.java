package com.ldf.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldf.demo.mapper.TypeMapper;
import com.ldf.demo.pojo.Type;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpSession;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private TypeMapper typeMapper;

    @Test
    void contextLoads() {

    }

    @Test
    public void testGetByPage(){
        IPage page = new Page(1,4);
        typeMapper.selectPage(page, null);
        System.out.println(page.getPages());
        System.out.println(page.getTotal());
        System.out.println(page.getCurrent());
        System.out.println(page.getSize());
        System.out.println(page.getRecords());

    }

    @Test
    public void testGetByWrapper(){
        QueryWrapper<Type> qu = new QueryWrapper<Type>();
        qu.select("name");
        List<Type> list = typeMapper.selectList(qu);
        System.out.println(list);
    }



}
