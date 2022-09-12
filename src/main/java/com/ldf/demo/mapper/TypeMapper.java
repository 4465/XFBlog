package com.ldf.demo.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldf.demo.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: 清峰
 * @date: 2020/9/22 15:48
 * @code: 愿世间永无Bug!
 * @description:
 */
@Repository
@Mapper
public interface TypeMapper extends BaseMapper<Type> {

    List<Type> listTypes();

    Type getType(Long typeId);

    int saveType(Type type);

    Type getTypeByName(String name);

    int updateType(Type type);

    int deleteById(Long id);

    List<Type> listTypesAndBlogs();

}
