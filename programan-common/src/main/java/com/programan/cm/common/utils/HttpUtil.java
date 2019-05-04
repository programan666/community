package com.programan.cm.common.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSON;
import org.json.JSONArray;
import org.json.JSONObject;

public class HttpUtil {

    public static String getJsonData(JSONObject jsonParam,String urls) {
        StringBuffer sb=new StringBuffer();
        try {
            ;
            // 创建url资源
            URL url = new URL(urls);
            // 建立http连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置允许输出
            conn.setDoOutput(true);
            // 设置允许输入
            conn.setDoInput(true);
            // 设置不用缓存
            conn.setUseCaches(false);
            // 设置传递方式
            conn.setRequestMethod("POST");
            // 设置维持长连接
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置文件字符集:
            conn.setRequestProperty("Charset", "UTF-8");
            // 转换为字节数组
            byte[] data = (jsonParam.toString()).getBytes();
            // 设置文件长度
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            // 设置文件类型:
            conn.setRequestProperty("contentType", "application/json;");
            // 开始连接请求
            conn.connect();
            OutputStream out = new DataOutputStream(conn.getOutputStream()) ;
            // 写入请求的字符串
            out.write((jsonParam.toString()).getBytes());
            out.flush();
            out.close();

            System.out.println(conn.getResponseCode());

            // 请求返回的状态
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()){
                System.out.println("连接成功");
                // 请求返回的数据
                InputStream in1 = conn.getInputStream();
                try {
                    String readLine=new String();
                    BufferedReader responseReader=new BufferedReader(new InputStreamReader(in1,"UTF-8"));
                    while((readLine=responseReader.readLine())!=null){
                        sb.append(readLine).append("\n");
                    }
                    responseReader.close();
//                    System.out.println(sb.toString());

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else {
                System.out.println("error++");

            }

        } catch (Exception e) {

        }

        return sb.toString();

    }
    public static void main(String[] args) {
        JSONObject jsonParam = new JSONObject();
        JSONObject headerJsonParam = new JSONObject();
        headerJsonParam.put("username", "ranhongsuiyue");
        headerJsonParam.put("password", "IGAIA666666");
        headerJsonParam.put("token", "5e5e02a4e3da0ef3607d8e78da589b9c");
        headerJsonParam.put("account_type", "1");
        jsonParam.put("header", headerJsonParam);
        JSONObject bodyJsonParam = new JSONObject();
        bodyJsonParam.put("site_id", "13260371");
        bodyJsonParam.put("metrics", "area,ip,visit_time,start_time");
        bodyJsonParam.put("method", "trend/latest/a");
        bodyJsonParam.put("max_results", "100");
        bodyJsonParam.put("area", "");
        jsonParam.put("body", bodyJsonParam);
        System.out.println(jsonParam);

        String url="https://api.baidu.com/json/tongji/v1/ReportService/getData";
        String data=HttpUtil.getJsonData(jsonParam,url);
        //返回的是一个[{}]格式的字符串时:

        JSON json = JSON.parseObject(data);
        System.out.println(json);
        //返回的是一个{}格式的字符串时:
        /*JSONObject obj= new JSONObject(data);*/
    }
}