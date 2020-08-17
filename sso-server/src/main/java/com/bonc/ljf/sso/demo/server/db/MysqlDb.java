package com.bonc.ljf.sso.demo.server.db;

import com.bonc.ljf.sso.demo.server.model.UserInfoBean;

import java.util.*;

/**
 * @ClassName: MysqlDb
 * @Description: TODO
 * @Author: liujianfu
 * @Date: 2020/08/16 15:51:56
 * @Version: V1.0
 **/
public class MysqlDb {
    //相当于一个mysql数据表来存储信息，模拟数据，建议存储到数据库中
    //使用hashset保持保持无须唯一
    public static Set<String> T_TOKEN_Set  = new HashSet<String>(); // token保存表
    // 用户登出地址保存表  token， Alist
    public static Map<String, List<UserInfoBean>> T_CLIENT_INFO
            = new HashMap<String, List<UserInfoBean>>();
}
