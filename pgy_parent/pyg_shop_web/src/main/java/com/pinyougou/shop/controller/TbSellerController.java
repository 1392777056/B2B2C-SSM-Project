package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.TbSellerService;
import domaincommon.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/13 21:47
 */
@RestController
@RequestMapping("/userIndex")
public class TbSellerController {

    @Reference
    private TbSellerService tbSellerService;
    @Autowired
    private   BCryptPasswordEncoder bCryptPasswordEncoder ;

    @RequestMapping("/registerUser")
    public Result getRegiterSellerUser(@RequestBody TbSeller tbSeller) {
        try {

            String encode = bCryptPasswordEncoder.encode(tbSeller.getPassword());
            tbSeller.setPassword(encode);
            tbSellerService.getRegiterSellerUser(tbSeller);
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }

}
