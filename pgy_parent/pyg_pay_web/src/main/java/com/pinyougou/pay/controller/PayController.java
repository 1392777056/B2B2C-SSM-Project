package com.pinyougou.pay.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pay.service.PayService;
import domaincommon.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/8/4 15:12
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Reference
    private PayService payService;

    @RequestMapping("/unifiedorder")
    public Map getUnifiedorder() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return payService.unifiedorder(name);
    }

    @RequestMapping("/findPayInfos/{out_trade_no}")
    public Result findPayInfos(@PathVariable("out_trade_no") String out_trade_no) {

        int time = 0;
        try {
            while (time<=10) {

                Map<String,String> map = payService.findPayInfos(out_trade_no);
                if (map.get("trade_state").equals("NOTPAY")) {
                }
                if (map.get("trade_state").equals("SUCCESS")) {
                    // 支付成功后 还需要去修改它的订单的状态和支付的时间。
                    String name = SecurityContextHolder.getContext().getAuthentication().getName();
                    String transaction_id = map.get("transaction_id");
                    payService.updateOrderPayStatus(out_trade_no,name,transaction_id);


                    return new Result(true,"支付成功");
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                time++;
                System.out.println("times:" + time);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"支付失败");
        }
        return new Result(false,"支付超时");


    }
}
