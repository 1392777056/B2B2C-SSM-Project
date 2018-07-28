package cn.demo;


import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/27 19:56
 */
public class DemoHttpClinetSms {

    public static void main(String[] args) throws Exception{

        HttpClient httpClient = new HttpClient("http://localhost:7788/smsInterface/smsSend");
       /*httpClient.addParameter("phoneNumbers","XXXXX");
        httpClient.addParameter("signName","XXXX");
        httpClient.addParameter("templateCode","XXXXXX");
        java.lang.String numeric = RandomStringUtils.randomNumeric(4);
        httpClient.addParameter("templateParam",numeric);*/
        Map<String,String> map = new HashMap();
        map.put("phoneNumbers","XXXX");
        map.put("signName","XXXX");
        map.put("templateCode","XXXXX");
        Map map1 = new HashMap();

        String code = RandomStringUtils.randomNumeric(4);
        map1.put("code",code);
        map.put("templateParam", JSON.toJSONString(map1));
        httpClient.setParameter(map);
        httpClient.post();

    }
}
