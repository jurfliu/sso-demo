package com.bonc.ljf.sso.demo.analysis;

import java.util.HashMap;

/**
 * @ClassName: HttpTest
 * @Description: TODO
 * @Author: liujianfu
 * @Date: 2020/08/16 10:50:24
 * @Version: V1.0
 **/
// 原生Java代码实现请求发送及接受响应信息
// Url:https://way.jd.com/jisuapi/query4
// ?shouji=13456755448&appkey=06642046425c68a351817b5b020b591f
public class HttpTest {
    public static void main(String[] args) throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("shouji","13456755448");
        map.put("appkey","06642046425c68a351817b5b020b591f");
        System.out.println(HttpUtils.sendHttpRequest("http://way.jd.com/jisuapi/query4", map));

    }
}
