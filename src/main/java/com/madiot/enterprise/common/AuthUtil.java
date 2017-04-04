package com.madiot.enterprise.common;

import com.madiot.enterprise.model.User;

/**
 * Created by Administrator on 2016/1/24 0024.
 */
public class AuthUtil {

    private static String ADMIN_NAME = "admin";

    public static boolean isAdmin(User user) {
        return user != null && user.getUsername().equals(ADMIN_NAME);
    }

    public static boolean checkLogin(String result) {
        if (result.startsWith("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML") && result.contains("<title>登陆页面</title>")) {
            return false;
        } else {
            return true;
        }
    }
}
