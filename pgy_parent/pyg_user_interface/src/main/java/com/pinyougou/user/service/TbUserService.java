package com.pinyougou.user.service;

import com.pinyougou.pojo.TbUser;
import domaincommon.Result;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/27 21:13
 */
public interface TbUserService {
    void sendCode(String phone) throws Exception;

    void saveUser(TbUser user);

    void checkCode(String phone,String code);
}
