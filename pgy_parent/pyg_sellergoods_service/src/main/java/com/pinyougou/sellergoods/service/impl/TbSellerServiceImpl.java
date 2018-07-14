package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbSellerMapper;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.TbSellerService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/13 22:21
 */
@Service
public class TbSellerServiceImpl implements TbSellerService {

    @Autowired
    private TbSellerMapper tbSellerMapper;

    public void getRegiterSellerUser(TbSeller tbSeller) {
        tbSeller.setStatus("0");
        tbSellerMapper.insert(tbSeller);
    }
}
