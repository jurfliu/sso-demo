package com.bonc.ljf.sso.demo.taobao.controller;

import com.bonc.ljf.sso.demo.taobao.util.SsoClientUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @ClassName: LoginControoler
 * @Description: TODO
 * @Author: liujianfu
 * @Date: 2020/08/16 14:14:51
 * @Version: V1.0
 **/
@Controller
public class LoginControoler {
    @RequestMapping("/taobao")
    public String index(Model model){
        System.out.println("我是淘宝client...");
        model.addAttribute("servetLogouUrl", SsoClientUtil.getServerLogOutUrl());
        return "taobao_login";
    }

    //logOut
    @RequestMapping("/logOut")
    public void logOut(HttpSession session){
        System.out.println("客户端注销....");
        session.invalidate();
    }
}
