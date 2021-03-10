package com.mr.rabbitmq.test;

import com.mr.rabbitmq.utils.MD5Util;

/**
 * @ClassName TestPassword
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2021/3/10
 * @Version V1.0
 **/
public class TestPassword {

    public static void main(String[] args) {

        String password = "ef4a468c9ea7834a8f8b5f02903e863d";
        String salt = "明瑞教育,世界第一";
        String password2 = MD5Util.encode(MD5Util.encode("ZHENGHANG") + salt);

        //暴力破解 查询方式
        //for 死循环
        //[A-Z][a-z][0-9]
        //大写 小写 数字 长度 符号
        System.out.println(password2.equals(password));
    }
}
