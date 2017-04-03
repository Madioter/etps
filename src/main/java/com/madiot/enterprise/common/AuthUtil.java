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
}
