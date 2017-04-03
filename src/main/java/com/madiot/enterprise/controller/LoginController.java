package com.madiot.enterprise.controller;

import com.madiot.enterprise.common.CommonConstant;
import com.madiot.enterprise.common.http.ConnectInfo;
import com.madiot.enterprise.common.util.HttpUtil;
import com.madiot.enterprise.common.util.StringUtil;
import com.madiot.enterprise.model.ResponseVo;
import com.madiot.enterprise.model.User;
import com.madiot.enterprise.service.IUserService;
import org.omg.PortableServer.ServantLocatorPackage.CookieHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.*;
import java.util.List;

/**
 * Created by Administrator on 2015/10/7 0007.
 */
@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ConnectInfo connectInfo;

    /**
     * 登陆页面
     *
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "common/login";
    }

    /**
     * 登陆函数
     */
    @RequestMapping("/logon")
    @ResponseBody
    public ResponseVo logon(HttpSession session, String username, String password) {
        ResponseVo responseVo = new ResponseVo();
        User user = userService.doLogin(username, password);
        if (user != null) {
            responseVo.setSuccess(true);
            session.setAttribute(CommonConstant.LOGIN_USER, user);
        } else {
            responseVo.setSuccess(false);
            responseVo.setMessage("登陆失败，用户名或密码不正确！");
        }
        return responseVo;
    }

    /**
     * 登出
     *
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(CommonConstant.LOGIN_USER);
        return "common/login";
    }

    /**
     * 监管平台本地控制登录接口
     *
     * @return
     */
    @RequestMapping("/otherLogin")
    @ResponseBody
    public ResponseVo otherLogin() {
        ResponseVo responseVo = new ResponseVo();
        try {
            URL url = new URL(connectInfo.getLoginUrl());
            CookieManager manager = new CookieManager();
            CookieHandler.setDefault(manager);
            url.openConnection();
        } catch (Exception e) {
            responseVo.setSuccess(false);
            responseVo.setMessage("远程登陆出现异常," + e.getMessage());
            return responseVo;
        }
        responseVo.setSuccess(true);
        responseVo.setMessage("远程登陆服务打开成功");
        return responseVo;
    }

    /**
     * 监管平台登录
     *
     * @param code      用户名
     * @param password  密码
     * @param checkCode 图形验证码
     * @return
     */
    @RequestMapping("/otherDoLogin")
    @ResponseBody
    public ResponseVo otherDoLogin(String code, String password, String checkCode) {
        ResponseVo responseVo = new ResponseVo();
        String params = "code=" + code + "&password=" + password + "&checkCode=" + checkCode;
        String result = HttpUtil.sendPost(connectInfo.getLoginUrl(), params);
        if (result.contains("<title>登录页面</title>")) {
            responseVo.setMessage("登录失败，请检查用户名、密码或验证码是否正确");
            responseVo.setSuccess(false);
        }else {
            responseVo.setMessage("登录成功");
            responseVo.setSuccess(true);
        }
        return responseVo;
    }

    /**
     * 从监管平台获取图形验证码
     *
     * @param response
     */
    @RequestMapping("/getImage")
    public void getImage(HttpServletResponse response) {
        response.setContentType("image/gif");
        HttpURLConnection conn = null;
        try {
            OutputStream out = response.getOutputStream();
            URL url = new URL(connectInfo.getImageUrl());
            conn = (HttpURLConnection) url.openConnection();
            /*if (CookieHolder.getCookie() != null) {
                conn.setRequestProperty("Cookie", "JSESSIONID=" + CookieHolder.getCookie());
            }
            CookieHolder.setCookie(conn.getHeaderField("Set-Cookie"));*/
            InputStream input = conn.getInputStream();
            byte[] b = new byte[input.available()];
            input.read(b);
            out.write(b);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
