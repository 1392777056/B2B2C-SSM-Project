package com.pinyougou.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbUser;
import com.pinyougou.user.service.TbUserService;
import domaincommon.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/27 21:28
 */
@RestController
@RequestMapping("/user")
public class TbUserController {

    @Reference
    private TbUserService tbUserService;

    @RequestMapping("/getCode/{phone}")
    public Result getCode(@PathVariable("phone") String phone) {
        try {
            tbUserService.sendCode(phone);
            return new Result(true,"发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"发送失败");
        }
    }

    @RequestMapping("/saveUser/{code}")
    public Result saveUser(@RequestBody TbUser user,@PathVariable("code") String code) {
        try {
            try {
                tbUserService.checkCode(user.getPhone(),code);
            } catch (RuntimeException e) {
                e.printStackTrace();
                new Result(false,e.getMessage());
            }
            tbUserService.saveUser(user);
            return new Result(true,"发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"发送失败");
        }
    }

}
