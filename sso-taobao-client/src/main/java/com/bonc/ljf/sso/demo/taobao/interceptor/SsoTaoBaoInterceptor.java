package com.bonc.ljf.sso.demo.taobao.interceptor;

import com.bonc.ljf.sso.demo.taobao.util.HttpUtil;
import com.bonc.ljf.sso.demo.taobao.util.SsoClientUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @ClassName: SsoTaoBaoInterceptor
 * @Description: TODO
 * @Author: liujianfu
 * @Date: 2020/08/16 14:29:30
 * @Version: V1.0
 **/
public class SsoTaoBaoInterceptor implements HandlerInterceptor {
    // false 拦截    true 通行
     /**
     * @author liujianfu
     * @description  做预处理
     * @date 2020/8/16 14:31
     * @param [request, response, handler]
     * @return boolean
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1. 判断是否存在会话 isLogin = true
        HttpSession session = request.getSession();
        Boolean isLogin = (Boolean) session.getAttribute("isLogin");
        if (isLogin != null && isLogin) { //存在会话
            return true;
        }
        // 2. 判断token,进行陶肯校验
        String token = request.getParameter("token");
        if (!StringUtils.isEmpty(token)){ //判断token 不为空
            System.out.println("检测到token信息，需要拿到服务器去校验token"+token);
            // 发起一个请求，携带一个参数！获得一个结果,SERVER_URL_PREFIX
            String httpUrl = SsoClientUtil.SERVER_URL_PREFIX + "/verify";
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("token",token);
            params.put("clientUrl",SsoClientUtil.getClientLogOutUrl());
            params.put("jsessionid",session.getId());
            try {
                String isVerify = HttpUtil.sendHttpRequest(httpUrl, params);
                if ("true".equals(isVerify)){
                    System.out.println("服务器端校验token信息通过");
                    session.setAttribute("isLogin",true);
                    return true;
                }
            }catch (Exception e){
                System.out.println("校验HTTP通信异常");
                e.printStackTrace();
            }

        }
        //2.没有登录信息，就需要跳转到登录的服务器！ www.sso.com:8091
        // http://www.sso.com:8091 /checkLogin?redirectUrl=http://www.tb.com:8092
        SsoClientUtil.redirectToSSOURL(request,response);
        return false;
    }

}
