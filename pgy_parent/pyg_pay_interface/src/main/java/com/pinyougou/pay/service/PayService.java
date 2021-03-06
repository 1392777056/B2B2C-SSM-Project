package com.pinyougou.pay.service;

import java.util.Map;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/8/4 15:16
 */
public interface PayService {

    Map unifiedorder(String name);

    Map findPayInfos(String out_trade_no);

    void updateOrderPayStatus(String out_trade_no, String name, String transaction_id);
}
