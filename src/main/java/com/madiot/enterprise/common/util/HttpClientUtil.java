package com.madiot.enterprise.common.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by DELL on 2016/8/22.
 */
public class HttpClientUtil {

    public void getImage(OutputStream out, String cookie) {
        // 使用get方法连接服务器
        HttpGet httpGet = new HttpGet("http://192.168.1.48:8080/Test/test.jpg");
        HttpClient client = new DefaultHttpClient();
        try {
            // 客户端开始向指定的网址发送请求
            HttpResponse response = client.execute(httpGet);

            setCookieStore(response);
            InputStream inputStream = response.getEntity().getContent();

            byte[] data = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(data)) != -1) {
                out.write(data, 0, len);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void setCookieStore(HttpResponse httpResponse) {
        System.out.println("----setCookieStore");
        CookieStore cookieStore = new BasicCookieStore();
        // JSESSIONID
        String setCookie = httpResponse.getFirstHeader("Set-Cookie")
                .getValue();
        String JSESSIONID = setCookie.substring("JSESSIONID=".length(),
                setCookie.indexOf(";"));
        System.out.println("JSESSIONID:" + JSESSIONID);
        // 新建一个Cookie
        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",
                JSESSIONID);
        cookie.setVersion(0);
        cookie.setDomain("127.0.0.1");
        cookie.setPath("/CwlProClient");
        // cookie.setAttribute(ClientCookie.VERSION_ATTR, "0");
        // cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "127.0.0.1");
        // cookie.setAttribute(ClientCookie.PORT_ATTR, "8080");
        // cookie.setAttribute(ClientCookie.PATH_ATTR, "/CwlProWeb");
        cookieStore.addCookie(cookie);
    }

}
