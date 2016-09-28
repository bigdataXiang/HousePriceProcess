package com.svail.gridprocess;


import java.io.IOException;  
import java.io.PrintWriter;  
import java.util.ArrayList;  
import java.util.List;  
  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
import net.sf.json.JSONArray;  
  
public class JSONServlet extends HttpServlet {  
  
    private static final long serialVersionUID = 1L;  
  
    public JSONServlet() {  
        super();  
    }  
  
    public void destroy() {  
        super.destroy();   
    }  
  
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        doPost(request, response);  
    }  
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        //使用JSONArray测试  
        JSONArray jsonArray = new JSONArray();  
        jsonArray.add("MCA");  
        jsonArray.add("kevin");  
        jsonArray.add("15-12-1998");  
        jsonArray.add(new Double(12.3));  
        List<String> list = new ArrayList<String>();   
        list.add("a collection added");  
        list.add("kevin collection test");  
        jsonArray.addAll(list);  
          
        //页面输出JSONArray的内容  
        PrintWriter out = response.getWriter();  
        out.print(jsonArray);  
        out.println("======================================");  
        for(int i=0;i<jsonArray.size();i++){  
            out.print(jsonArray.getString(i));  
        }  
    }  
  
    public void init() throws ServletException {  
    }  
} 
/*
 * <?xml version="1.0" encoding="UTF-8"?>  
<web-app version="2.5"   
    xmlns="http://java.sun.com/xml/ns/javaee"   
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"   
    xsi:schemaLocation="http://java.sun.com/xml/ns/javaee   
    http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd">  
  <servlet>  
    <servlet-name>JSONServlet</servlet-name>  
    <servlet-class>com.justinmobile.JSONServlet</servlet-class>  
  </servlet>  
  
  <servlet-mapping>  
    <servlet-name>JSONServlet</servlet-name>  
    <url-pattern>*.do</url-pattern>  
  </servlet-mapping>  
  <welcome-file-list>  
    <welcome-file>index.jsp</welcome-file>  
  </welcome-file-list>  
</web-app>  
 */
