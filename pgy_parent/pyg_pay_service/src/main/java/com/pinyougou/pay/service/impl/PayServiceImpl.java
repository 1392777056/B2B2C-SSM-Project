package com.pinyougou.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.wxpay.sdk.WXPayUtil;
import com.pinyougou.pay.service.PayService;
import com.pinyougou.utils.HttpClient;
import com.pinyougou.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/8/4 18:06
 */
@Service
public class PayServiceImpl implements PayService {

    @Value("${appid}")
    private String appid;
    @Value("${partner}")
    private String partner;
    @Value("${partnerkey}")
    private String partnerkey;
    @Value("${notifyurl}")
    private String notifyurl;

    @Autowired
    private IdWorker idWorker;

    @Override
    public Map unifiedorder() {

        // 1.首先通过远程调用去调微信接口
        HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
        httpClient.setHttps(true);
        long out_trade_no = idWorker.nextId();
        Map<String,String> map = new HashMap();
        map.put("appid",appid);
        map.put("mch_id",partner);
        map.put("nonce_str", WXPayUtil.generateNonceStr());
        map.put("body","品优购微信支付");
        map.put("out_trade_no",out_trade_no+"");
        map.put("total_fee","1");
        map.put("spbill_create_ip","127.0.0.1");
        map.put("notify_url",notifyurl);
        map.put("trade_type","NATIVE");

        try {
            // 带签名转乘XML
            String signedXml = WXPayUtil.generateSignedXml(map, partnerkey);
            httpClient.setXmlParam(signedXml);
            httpClient.post();
            String content = httpClient.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);

            resultMap.put("out_trade_no",out_trade_no+"");
            resultMap.put("total_fee","1");
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map findPayInfos(String out_trade_no) {
        // 1.首先通过远程调用去调微信接口
        HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
        httpClient.setHttps(true);

        Map<String,String> map = new HashMap();
        map.put("appid",appid);
        map.put("mch_id",partner);
        map.put("out_trade_no",out_trade_no);
        map.put("nonce_str", WXPayUtil.generateNonceStr());
        try {
            // 带签名转乘XML
            String signedXml = WXPayUtil.generateSignedXml(map, partnerkey);
            httpClient.setXmlParam(signedXml);
            httpClient.post();
            String content = httpClient.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
