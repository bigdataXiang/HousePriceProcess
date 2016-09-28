package com.svail.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.svail.util.FileTool;
import com.svail.util.HTMLTool;

public class HttpRequest {
	 final static String url = "http://www.95598.cn/95598/outageNotice/queryOutageNoticeList?";  
     //final static String params = "orgNo=50404&outageStartTime=2016-06-10&outageEndTime=2016-06-17&scope=&provinceNo=50101&typeCode=01&lineName=&pageNow=5&pageCount=10&totalCount=42";//
	   final static String params = "orgNo=50404&outageStartTime=2016-06-10&outageEndTime=2016-06-17&scope=&provinceNo=50101&typeCode=&lineName=&pageNow=1&pageCount=10&totalCount=42";

	// final static String params = "orgNo=50404&outageStartTime=2016-06-10&outageEndTime=2016-06-17&scope=&provinceNo=50101&typeCode=&lineName=";
	

	public static void main(String[] args) throws ParserException {

		Vector<String> pois=FileTool.Load("D:\\post.txt","utf-8");
		String params="";
		for(int i=0;i<pois.size();i++){
			String poi=pois.elementAt(i);
			params+=poi+"&";
		}
		if(params.endsWith("&")){
			int index = params.lastIndexOf('&');
			params=params.substring(0,index);
		}
		String content=sendPost("http://www.njghj.gov.cn/NGWeb/Project/ProjectSearch.aspx?ProjectType=%u9879%u76ee%u9009%u5740%u610f%u89c1%u4e66",params);
		System.out.println(content);

	}

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
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
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
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				//System.out.println(line);
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	// 具体查询函数
	public static String nextPage(int getpage) { // if you want page 2 ,then
													// pass 2
		String form_data = "__VIEWSTATE=&__VIEWSTATEGENERATOR=D0B94C9B&__EVENTTARGET=AspNetPager1&lhdshow=0&";// __EVENTARGUMENT=100&AspNetPager1_input=99";
		String param = form_data + "AspNetPager1_input=" + Integer.toString(getpage - 1) + "&__EVENTARGUMENT="
				+ Integer.toString(getpage);
		String url = "http://www.newjobs.com.cn/dwzp/searchlist.aspx?0";
		String sr = HttpRequest.sendPost(url, param);

		return sr;

	}

	public static String readToString(String fileName) {
		String encoding = "utf-8";
		File file = new File(fileName);
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new String(filecontent, encoding);
		} catch (UnsupportedEncodingException e) {
			System.err.println("The OS does not support " + encoding);
			e.printStackTrace();
			return null;
		}
	}
	/*
		String content = readToString("D:/zhouxiang/人口数据/简历数据/r1400-2411.txt");
		System.out.println(content);
		Parser parser = new Parser();
		if (content == null) {
			return;

		} else {
			try {
				parser.setInputHTML(content);
				parser.setEncoding("utf-8");
				NodeFilter filter = new AndFilter(new TagNameFilter("a"), new HasAttributeFilter("class", "btn"));
				NodeList nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String purl = tn.getAttribute("href");
						if (purl.indexOf("/person/") != -1) {
							FileTool.Dump(purl,"D:/zhouxiang/人口数据/简历数据/resume-link.txt","utf-8");
							System.out.println(purl);

						}
					}
				}

			} catch (NullPointerException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

		}

		*/

	
}
