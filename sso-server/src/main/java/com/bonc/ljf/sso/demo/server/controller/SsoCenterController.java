package com.bonc.ljf.sso.demo.server.controller;

import com.bonc.ljf.sso.demo.server.db.MysqlDb;
import com.bonc.ljf.sso.demo.server.model.UserInfoBean;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName: SsoCenterController
 * @Description: TODO
 * @Author: liujianfu
 * @Date: 2020/08/16 12:33:28
 * @Version: V1.0
 **/
@Controller
public class SsoCenterController {
    /**
     访问首页
    */
    @RequestMapping("/index")
    public String index(){
        return "login";
    }
    // redirectUrl 我从哪里来~
    @RequestMapping("/login")
   // @ResponseBody
    public String login(String username, String password,
                        String redirectUrl, HttpServletRequest request, HttpSession session, Model model) {
        System.out.println(username + "," + password);

        String path=request.getRequestURL().toString();
        System.out.println("path:"+path);
        //登录成功
        if ("admin".equals(username) && "123".equals(password)){
            // 1. 服务器端创建令牌
            String token = UUID.randomUUID().toString();
            System.out.println("token创建成功=>"+token);
            // 2. 创建全局会话，将令牌信息存入
            session.setAttribute("token",token);
            // 3. 存在数据库中
            MysqlDb.T_TOKEN_Set.add(token);
            // 4. 返回给用户，来时的地方
            model.addAttribute("token",token);
            //if(path.contains("http://localhost:8091/login")){
             //   model.addAttribute("currentUser","admin");
               // model.addAttribute("logoutUrl","http://localhost:8091/logOut");
             //   return "success";
            //}
            return "redirect:"+redirectUrl; //  ?token = xxxxx
        }
        else{
            System.out.println("用户名或密码错误！");
            model.addAttribute("redirectUrl",redirectUrl);
            // 登录的操作，保存token数据  redis。。。 mock
            //return  "登录ok!!";
            return "login";
        }


    }
    /**
    服务端验证用户的登录
    */
    // checkLogin
    @RequestMapping("/checkLogin")
    public String checkLogin(String redirectUrl, HttpSession session,Model model){
        //1. 是否存在会话。
        String token = (String) session.getAttribute("token");
        if (StringUtils.isEmpty(token)){
            // 没有全局会话，需要登录，跳转到登录页面，需要携带我从哪里来~
            model.addAttribute("redirectUrl",redirectUrl);
            return "login";
        }else { // 存在全局会话
            // 取出令牌 token，返回给客户端
            model.addAttribute("token",token);
            return "redirect:" + redirectUrl; //model  ?token=xxxx
        }

    }
    /**
     服务端验证用户Token是否存在
    */
    @RequestMapping("/verify")
    @ResponseBody
    public String verifyToken(String token,String clientUrl,String jsessionid){
       System.out.println("我是客户端:"+clientUrl+" 正在服务段校验verify所携带的token");
        if (MysqlDb.T_TOKEN_Set.contains(token)){
            System.out.println("服务器端token校验成功！"+token);
            // 存表操作！，存储客户端退出的地址，session，在退出注销，session失效时使用
            List<UserInfoBean> clientInfoVoList =MysqlDb.T_CLIENT_INFO.get(token);
            if (clientInfoVoList==null){
                clientInfoVoList = new ArrayList<UserInfoBean>();
                // 当如当前的用户信息
                MysqlDb.T_CLIENT_INFO.put(token,clientInfoVoList);
            }

            UserInfoBean vo = new UserInfoBean();
            vo.setClientUrl(clientUrl);
            vo.setJsessionid(jsessionid);
            clientInfoVoList.add(vo);
            return "true";
        }
        return "false";
    }
    // Session  手动注销，自动注销！ 监听器！
    //logOut
    @RequestMapping("/logOut")
    public String logOut(HttpSession session){
        session.invalidate();
        //客户端的session 应该在在这里通知销毁吗？
        //存在自动注销，我们需要在监听器中实现
        //此方法执行后，触发监听器
        System.out.println("服务端session 销毁.....");
        return "login";
    }
}
