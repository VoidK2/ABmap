package com.majie.abmap.utils;


import org.json.*;
import java.io.IOException;
import okhttp3.*;

/**
 * 用户登录注册验证的工具类
 */
/*
* 在添加用户前需要使用登录获取jsessionid来获取注册用户权限
* */
public class WebVerify{
    public static WebVerify wv;
    public String jid;
    public static WebVerify getInstance(){
        if(wv==null){
            wv=new WebVerify();
        }
        return wv;
    }
    public boolean LogIn(String username,String password) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("user_code", username)
                .add("user_pwd", password).build();
        String PcPath = "http://www.mypc.com:8080";
        String url = PcPath + "/permission/user/login";
        Request request = new Request.Builder().url(url).post(body).build();
        final Call call = client.newCall(request);
        Response response = call.execute();
        String cook =response.headers().toString();
        String ji=cook.substring(cook.indexOf("JSESSIONID="), cook.indexOf(";"));
        this.jid=ji;
        String responseData = response.body().string();
        JSONObject jsonObject = new JSONObject(responseData);
        int id = jsonObject.getInt("status");
        if (id == 200) {
            return true;
        }
        return false;
    }

    public boolean SignUp(String username,String password,String realname) throws IOException, JSONException {
//        "localhost:8080/permission/user/useradd?user_id=&user_code=1112&user_pwd= 1112&user_name= 1232&user_birthday= 2020-03-12"
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("user_id","")
                .add("user_code", username)
                .add("user_pwd", password)
                .add("user_name", realname)
                .add("user_birthday","").build();
        String PcPath = "http://www.mypc.com:8080";
        String url=PcPath+"/permission/user/useradd";
        Request request = new Request.Builder().url(url).post(body).addHeader("Cookie",this.jid).build();
        final Call call = client.newCall(request);
        Response response = call.execute();
        String responseData = response.body().string();
        JSONObject jsonObject = new JSONObject(responseData);
        int id = jsonObject.getInt("status");
        if (id == 200) {
            return true;
        }
        return false;
    }
}
