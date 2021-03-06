package com.svail.test;

import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStreamWriter;  
import java.net.HttpURLConnection;  
import java.net.URL;

import com.svail.util.FileTool;  
  
public class PostDemo {  
  
    final static String url = "http://www.95598.cn/95598/outageNotice/queryOutageNoticeList";  
    final static String temp="";
    final static String params = "{\"orgNo\" : \"50402\",\"outageStartTime\" : \"2016-06-10\",\"outageEndTime\" : \"2016-06-17\",\"scope\" : ,\"provinceNo\" : \"50101\",\"typeCode\" : \"01\",\"lineName\" : }";
/**
需要注意的两个参数
*@param  __EVENTARGUMENT ：下一页数
*@param AspNetPager1_input” 当前页所在页数
**/      


/** 
     * 发送HttpPost请求 
     *  
     * @param strURL 服务地址 
     * @param params 
     *            json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/> 
     * @return 成功:返回json字符串<br/> 
     */  
    public static String post(String strURL, String params) {  
        //System.out.println(strURL);  
        //System.out.println(params);  
        try {  
            URL url = new URL(strURL);// 创建连接  
            HttpURLConnection connection = (HttpURLConnection) url  
                    .openConnection();  
            connection.setDoOutput(true);  
            connection.setDoInput(true);  
            connection.setUseCaches(false);  
            connection.setInstanceFollowRedirects(true);  
            connection.setRequestMethod("POST"); // 设置请求方式  
            connection.setRequestProperty("Accept", "text/html"); // 设置接收数据的格式  
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式  
            connection.connect();  
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码  
            out.append(params);  
            out.flush();  
            out.close();  
            // 读取响应  
            int length = (int) connection.getContentLength();// 获取长度  
            InputStream is = connection.getInputStream();  
            if (length != -1) {  
                byte[] data = new byte[length];  
                byte[] temp = new byte[1024];  
                int readLen = 0;  
                int destPos = 0;  
                while ((readLen = is.read(temp)) > 0) {  
                    System.arraycopy(temp, 0, data, destPos, readLen);  
                    destPos += readLen;  
                }  
                String result = new String(data, "UTF-8"); // utf-8编码  
                //System.out.println(result);  
                return result;  
            }  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return "error"; // 自定义错误信息  
    }  
  
    public static void main(String[] args) {  
       FileTool.Dump(post(url, params), "D:/Crawl_Test/post2.txt", "utf-8");
       System.out.println("ok");  
    }  
  
}  
