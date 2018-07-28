package com.pinyougou.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbUserMapper;
import com.pinyougou.pojo.TbUser;
import com.pinyougou.user.service.TbUserService;
import com.pinyougou.utils.HttpClient;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/27 21:12
 */
@Service
public class TbUserServiceImpl implements TbUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public void sendCode(String phone) throws Exception {
        HttpClient httpClient = new HttpClient("http://localhost:7788/smsInterface/smsSend");
        httpClient.addParameter("phoneNumbers",phone);
        httpClient.addParameter("signName","XXXX");
        httpClient.addParameter("templateCode","XXXXX");
        String numeric = RandomStringUtils.randomNumeric(4);
        redisTemplate.boundValueOps(phone).set(numeric,5l, TimeUnit.MINUTES);
        httpClient.addParameter("templateParam",numeric);
        httpClient.post();
    }

    @Override
    public void saveUser(TbUser user) {

        user.setUpdated(new Date());
        user.setCreated(new Date());
        String password = user.getPassword();
        String s = DigestUtils.md5Hex(password);
        user.setPassword(s);
        tbUserMapper.insert(user);
    }

    @Override
    public void checkCode(String phone, String code) {
        String sendCode = (String) redisTemplate.boundValueOps(phone).get();
        if (sendCode ==null){
            throw new RuntimeException("验证码过期");
        } else {
            if (!code.equals(sendCode)) {
                throw new RuntimeException("验证码错误");
            }
        }
    }
}
