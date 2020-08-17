package com.bonc.ljf.sso.demo.server.model;

import lombok.Data;

/**
 * @ClassName: UserInfoBean
 * @Description: TODO
 * @Author: liujianfu
 * @Date: 2020/08/16 15:54:59
 * @Version: V1.0
 **/
@Data
public class UserInfoBean {
    private String clientUrl; //客户单的注销地址
    private String jsessionid; // 当前的用的session 信息
}
