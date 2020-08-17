package com.bonc.ljf.sso.demo.tmall.controller;

import com.bonc.ljf.sso.demo.tmall.util.SsoClientUtil;
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
    @RequestMapping("/tmall")
    public String index(Model model){
        System.out.println("我是天猫client...");
        model.addAttribute("servetLogouUrl", SsoClientUtil.getServerLogOutUrl());
        return "tmall_login";
    }

    //logOut
    @RequestMapping("/logOut")
    public void logOut(HttpSession session){
        System.out.println("客户端销毁session....");
        session.invalidate();
    }
}
