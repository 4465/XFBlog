package com.ldf.demo.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HotListTool {

    private static String weiBo_api = "https://api.vvhan.com/api/hotlist?type=wbHot";

    private RestTemplate restTemplate = new RestTemplate();

    public String getWeiBoHotList(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(HotListTool.weiBo_api, String.class);

        String resp = responseEntity.getBody();

        return resp;

    }


    public static void main(String[] args) {
        HotListTool hotListTool = new HotListTool();
        hotListTool.getWeiBoHotList();
    }
}
