package com.ldf.demo.controller.tool;

import com.ldf.demo.tool.HotListTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotlist")
@Api(tags = "热搜模块")
public class HotlistController {

    @ApiOperation(value = "获取微博热搜")
    @RequestMapping("/weibo")
    public String weiBoHotList(){

        HotListTool hotListTool = new HotListTool();

        String list = hotListTool.getWeiBoHotList();

        return list;

    }

}
