package com.ldf.demo.scheduled;

import com.ldf.demo.service.BlogService;
import com.ldf.demo.utils.RedisUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author 刘威甫
 * @date 2022/9/27 13:52
 * @description
 */
@Component
public class RedisToMySQL {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private BlogService blogService;

    /**
     * 每6个小时将redis的key添加道mysql中
     * 先读取，将数据量统计并落盘
     * 后删除键，等待下一次调用
     */
    @Scheduled(initialDelay = 5000, fixedDelay = 6* DateUtil.MINUTES_PER_HOUR)
    private void redisToMySQL(){

    }


}
