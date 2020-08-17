package com.bonc.ljf.sso.demo.server.util;

import org.springframework.util.StreamUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @ClassName: HttpUtil
 * @Description: TODO
 * @Author: liujianfu
 * @Date: 2020/08/16 16:35:04
 * @Version: V1.0
 **/
public class HttpUtil {
    public static String sendHttpRequest(String httpURL, Map<String,String> params) throws Exception {
        //1. 定义需要访问的地址 "https://way.jd.com/he/freeweather"
        URL url = new URL(httpURL);
        //2. 开启连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //3. 设置请求的方式
        connection.setRequestMethod("POST");
        //4. 输出参数
        connection.setDoOutput(true);
        //5. 拼接参数信息 city=beijing&appkey=06642046425c68a351817b5b020b591f
        if (params!=null&&params.size()>0){
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String,String> entry:params.entrySet()){
                sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
            //6. 去除最前面的 &写出参数
            connection.getOutputStream().write(sb.substring(1).toString().getBytes("UTF-8"));
        }
        //7. 发起请求
        connection.connect();
        //8. 接收对方响应的信息,可以使用Spring的 StreamUtils 工具类
        String response = StreamUtils.copyToString(connection.getInputStream(), Charset.forName("UTF-8"));
        return response;
    }
    /**
     * 模拟浏览器的请求
     */
    public static void sendHttpRequest(String httpURL,String jesssionId) throws Exception{
       System.out.println("销毁httpURL："+httpURL);
        //建立URL连接对象
        URL url = new URL(httpURL);
        //创建连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置请求的方式(需要是大写的)
        conn.setRequestMethod("POST");
        //设置需要输出
        conn.setDoOutput(true);
        conn.addRequestProperty("Cookie","JSESSIONID="+jesssionId);
        //发送请求到服务器
        conn.connect();

        int code = conn.getResponseCode();
        if (code == 200) {
            conn.getInputStream(); // 得到网络返回的正确输入流
        } else {
            conn.getErrorStream(); // 得到网络返回的错误输入流
        }

        conn.disconnect();
    }
}
