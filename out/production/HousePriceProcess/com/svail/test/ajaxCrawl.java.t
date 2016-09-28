package com.svail.test;
import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;

import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.cookie.Cookie;
import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.parser.DocumentBuilderImpl;
import org.lobobrowser.html.parser.InputSourceImpl;
import org.lobobrowser.html.test.SimpleUserAgentContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.svail.util.FileTool;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;






public class ajaxCrawl {
	private static final String TEST_URI = "http://cq.meituan.com/deal/27678059.html?mtt=1.s%2Fdefault.0.0.iopl3uo2";



	public static void main(tString[] args) throws Exception {

		htmlUnitCrwal();

	} 
	public static void htmlUnitCrwal() throws FailingHttpStatusCodeException, IOException{
		 //创建webclient
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //htmlunit 对css和javascript的支持不好，所以请关闭之
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(true);
        HtmlPage page = (HtmlPage)webClient.getPage(TEST_URI);
        //查找所有div
        
        System.out.println(page.asXml());
        FileTool.Dump(page.asXml(), "D:/重庆基础数据抓取/基础数据/糯米网/restrantDetails/test.txt", "utf-8");
        
        List<?> hbList = page.getByXPath("//div");
        for(int i=0;i<hbList.size();i++){
        	HtmlDivision hb = (HtmlDivision)hbList.get(i);
            System.out.println(hb.toString());
        }
        
      //获取搜索输入框并提交搜索内容
        HtmlInput input = (HtmlInput)page.getHtmlElementById("j-branch-list-content");
        System.out.println(input.toString());
        //关闭webclient
        webClient.closeAllWindows();

     
	}
	/**
	 * 
	 */
	public static void  lobobrowserMethod(){
	
		try{
			UserAgentContext uacontext = new SimpleUserAgentContext();

			DocumentBuilderImpl builder = new DocumentBuilderImpl(uacontext);

			URL url = new URL(TEST_URI);

			InputStream in = url.openConnection().getInputStream();
			System.out.println(in.toString());

			try {

			Reader reader = new InputStreamReader(in, "utf-8");

			InputSourceImpl inputSource = new InputSourceImpl(reader, TEST_URI);

			Document d = builder.parse(inputSource);

			HTMLDocumentImpl document = (HTMLDocumentImpl) d;

			Element ele = document.getElementById("j-branch-list-content");

			System.out.println(ele.getTextContent());

			} finally {

			in.close();

			}
		}catch(IOException e){
			System.out.println(e.getMessage());
			
		}catch(SAXException e){
			System.out.println(e.getMessage());
		}		
		
	}
}
