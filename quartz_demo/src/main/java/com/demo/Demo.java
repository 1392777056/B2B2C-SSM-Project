package com.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/29 11:23
 */
public class Demo {

    public static void main(String[] args) {

        new ClassPathXmlApplicationContext("classpath:applicationContext_job.xml");

    }
}
