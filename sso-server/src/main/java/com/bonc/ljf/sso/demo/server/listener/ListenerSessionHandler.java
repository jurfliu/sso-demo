package com.bonc.ljf.sso.demo.server.listener;

import com.bonc.ljf.sso.demo.server.db.MysqlDb;
import com.bonc.ljf.sso.demo.server.model.UserInfoBean;
import com.bonc.ljf.sso.demo.server.util.HttpUtil;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

/**
 * @ClassName: ListenerSessionHandler
 * @Description: TODO
 * @Author: liujianfu
 * @Date: 2020/08/16 20:01:46
 * @Version: V1.0
 **/
public class ListenerSessionHandler implements HttpSessionListener {
    // Session创建的时候执行的操作
    public void sessionCreated(HttpSessionEvent se) {

    }

    // Session销毁的时候执行的操作
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        String token = (String) session.getAttribute("token");

        for(String key:MysqlDb.T_CLIENT_INFO.keySet()){
            List<UserInfoBean> clientInfoLists2=MysqlDb.T_CLIENT_INFO.get(key);
            for(UserInfoBean userInfoBean:clientInfoLists2){
                System.out.println("userInfoBean:"+userInfoBean.getClientUrl());
            }
        }
        //销毁表中的数据
        MysqlDb.T_TOKEN_Set.remove(token);
        List<UserInfoBean> clientInfoLists = MysqlDb.T_CLIENT_INFO.remove(token);

        for (UserInfoBean vo : clientInfoLists) {
            try {
                // 服务器端通知所有的客户端进行session 的注销！
                HttpUtil.sendHttpRequest(vo.getClientUrl(),vo.getJsessionid());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
