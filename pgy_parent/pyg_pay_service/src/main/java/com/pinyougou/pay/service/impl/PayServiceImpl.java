package com.pinyougou.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.wxpay.sdk.WXPayUtil;
import com.pinyougou.mapper.TbOrderItemMapper;
import com.pinyougou.mapper.TbOrderMapper;
import com.pinyougou.mapper.TbPayLogMapper;
import com.pinyougou.pay.service.PayService;
import com.pinyougou.pojo.TbOrder;
import com.pinyougou.pojo.TbPayLog;
import com.pinyougou.utils.HttpClient;
import com.pinyougou.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/8/4 18:06
 */
@Service
@Transactional
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

    @Autowired
    private TbPayLogMapper payLogMapper;

    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map unifiedorder(String name) {

        // 1.首先通过远程调用去调微信接口
        HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
        httpClient.setHttps(true);
        long out_trade_no = idWorker.nextId();
        TbPayLog payLog = (TbPayLog) redisTemplate.boundHashOps("payLog").get(name);
        String outTradeNo = payLog.getOutTradeNo();

        Map<String,String> map = new HashMap();
        map.put("appid",appid);
        map.put("mch_id",partner);
        map.put("nonce_str", WXPayUtil.generateNonceStr());
        map.put("body","品优购微信支付");
        map.put("out_trade_no",outTradeNo);
        map.put("total_fee","1");  //  测试用的
        //map.put("total_fee",payLog.getTotalFee()+"");   现实生活中真实的场景，真实的数据。
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
            resultMap.put("total_fee",payLog.getTotalFee()+"");
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

    @Override
    public void updateOrderPayStatus(String out_trade_no, String name, String transaction_id) {
        // 修改paylog
        TbPayLog payLog = (TbPayLog) redisTemplate.boundHashOps("payLog").get(name);
        payLog.setTradeState("1"); // 已支付
        payLog.setPayTime(new Date());
        payLog.setTransactionId(transaction_id);
        payLogMapper.updateByPrimaryKey(payLog);

        // 修改order
        String[] orderIds = payLog.getOrderList().split(",");
        for (String orderId : orderIds) {
            TbOrder tbOrder = orderMapper.selectByPrimaryKey(Long.parseLong(orderId));
            tbOrder.setUpdateTime(new Date());
            tbOrder.setStatus("1");
            tbOrder.setPaymentTime(new Date());
            orderMapper.updateByPrimaryKey(tbOrder);
        }

        // 最后去清空Redis
        redisTemplate.boundHashOps("payLog").delete(name);

    }
}
