package com.svail.population_mobility;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.svail.util.HTMLTool;

import java.io.*;  
import java.net.*; 
	public class SiteCall {
		static String Folder="D:/Crawl_Test/null.txt";
		 /**
	     * 向指定 URL 发送POST方法的请求
	     * 
	     * @param url
	     *            发送请求的 URL
	     * @param param
	     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	     * @return 所代表远程资源的响应结果
	     */
	    public static String sendPost(String url, String param) {
	        PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	            URL realUrl = new URL(url);
	            // 打开和URL之间的连接
	            URLConnection conn = realUrl.openConnection();
	            // 设置通用的请求属性
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // 发送POST请求必须设置如下两行
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            // 获取URLConnection对象对应的输出流
	            out = new PrintWriter(conn.getOutputStream());
	            // 发送请求参数
	            out.print(param);
	            // flush输出流的缓冲
	            out.flush();
	            // 定义BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line+"\r\n";
	            }
	        } catch (Exception e) {
	            System.out.println("发送 POST 请求出现异常！"+e);
	            e.printStackTrace();
	        }
	        //使用finally块来关闭输出流、输入流
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
	        return result;
	    } 
	    //查询手机号码所在地函数
	    public static String PhoneRequest(String Tel) throws IOException{ // IDnumber 身份证账号
            String address="";   
            String url ="http://www.ip138.com:8080/search.asp?action=mobile&mobile="+Tel;  
            String content=HTMLTool.fetchURL(url, "gb2312","get"); ;
           // System.out.println(content);
            Parser parser = new Parser();//获取解析器                
            if (content == null)
    		{
            	write_append(Tel,Folder);
    		}
            try {
    			
    			parser.setInputHTML(content);
    			parser.setEncoding("gb2312");
    			NodeFilter filter=new AndFilter(new TagNameFilter("tr"),new HasAttributeFilter("class","tdc"));
    			NodeList nodes= parser.extractAllNodesThatMatch(filter); 	
    			if (nodes != null)
    			{
    				for(int i=0;i<nodes.size();i++)
    				{
    					String str = nodes.elementAt(i).toPlainTextString().replace("\r\n", "").replace("\t", "").trim();
    					if(str.indexOf("卡号归属地")==-1)
    						continue;
        				
        				int loc1=str.indexOf("卡号归属地");//归属地 湖北武汉市
        				address=str.substring(loc1+"卡号归属地".length()).replace("&nbsp;", "");
        				//System.out.println(address); 
    				}
    				
    			}
            }catch (ParserException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    			write_append(Tel,Folder);
    		} 

            return address;

}

	 //查询身份证所在地函数
	  public static String IDCardRequest(String IDnumber) throws IOException{ // IDnumber 身份证账号
		            String address="";
	                String param="word="+IDnumber;
	                String url ="http://www.21ccvn.com/tool-search-idcard";      
	                String content=sendPost(url, param);
	               // System.out.println(content);
	                Parser parser = new Parser();//获取解析器                
	                if (content == null)
	        		{
	                	return null;
	        		}
	                try {
	        			
	        			parser.setInputHTML(content);
	        			parser.setEncoding("utf-8");
	        			NodeFilter filter=new AndFilter(new TagNameFilter("table"),new HasAttributeFilter("class","cities"));
	        			NodeList nodes= parser.extractAllNodesThatMatch(filter); 	
	        			if (nodes != null)
	        			{
	        				for(int i=0;i<nodes.size();i++)
	        				{
	        					String str = nodes.elementAt(i).toPlainTextString().replace("\r\n", "").replace("\t", "").trim();
	        					if(str.indexOf("地址")==-1)
	        						continue;
		        				
		        				int loc1=str.indexOf("地址");
		        				int loc2=str.indexOf("身份证号码前6位");
		        				address=str.substring(loc1+"地址".length(), loc2);
		        				//System.out.println(address);
	        				}
	        				
	        			}
	                }catch (ParserException e1) {
	        			// TODO Auto-generated catch block
	        			e1.printStackTrace();
	        			write_append(IDnumber,Folder);
	        		} 

	                return address;

	    }
	  public static void write_append(String line,String pathname)  throws IOException
		{
			try
			{
			
	          File writefile=new File(pathname);
	          if(!writefile.exists())
	          {
	        	  writefile.createNewFile();
	          }
	          OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(writefile,true),"UTF-8");
	          BufferedWriter writer = new BufferedWriter(write);
	          writer.write(line);
	          writer.write("\r\n"); 
	          writer.close();
	          }catch(Exception e) {
				e.printStackTrace();
			
			}	
		}
	  public static void main(String[] args) throws IOException {
		  
		  
	      
		  }
				
}

	   