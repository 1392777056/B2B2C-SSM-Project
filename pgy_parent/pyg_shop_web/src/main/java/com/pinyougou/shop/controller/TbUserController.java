package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.TbSellerService;
import domaincommon.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/13 19:53
 */
@RestController
@RequestMapping("/tbUser")
public class TbUserController {

    @Reference
    private TbSellerService tbSellerService;

    @RequestMapping("/findAll")
    public String getUserAll() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @RequestMapping("/updateSellerInfo/{username}")
    public Result updateSellerInfo(@PathVariable("username") String username, @RequestBody TbSeller tbSeller) {
        try {
            if (username.equals(SecurityContextHolder.getContext().getAuthentication().getName())){
                tbSellerService.updateSellerInfo(tbSeller);
            }
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }

}
