package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeller;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/13 19:53
 */
@RestController
@RequestMapping("/userIndex")
public class TbUserController {

    @RequestMapping("/findAll")
    public String getUserAll() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
