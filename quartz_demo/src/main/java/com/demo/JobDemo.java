package com.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/29 11:21
 */
public class JobDemo {

    public void showTime()
    {
        System.out.println("当前时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( new Date()));
    }
}
