package com.svail.test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.svail.util.FileTool;
import com.svail.util.Tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class Test2 {
	public static String folder1 = "D:/zhouxiang/人口数据/区划数据/test/solveproblem/1_hotel/result/";
	public static String folder2 = "D:/zhouxiang/人口数据/区划数据/test/solveproblem/2_hotel/result/";
	public static String folder3 = "D:/zhouxiang/人口数据/区划数据/test/solveproblem/3_hotel/result/";
	public static String folder4 = "D:/zhouxiang/人口数据/区划数据/test/solveproblem/4_hotel/result/";
	public static String folder5 = "D:/zhouxiang/人口数据/区划数据/test/solveproblem/5_hotel/result/";
	public static String folder6 = "D:/zhouxiang/人口数据/区划数据/test/solveproblem/6_hotel/result/";
	public static String folder7 = "D:/zhouxiang/人口数据/区划数据/test/solveproblem/7_hotel/result/";
	public static String folder8 = "D:/zhouxiang/人口数据/区划数据/test/solveproblem/8_hotel/result/";
	public static void main(String[] args) throws Exception {
		DataLean(folder1);
		DataLean(folder2);
		DataLean(folder3);
		DataLean(folder4);
		DataLean(folder5);
		DataLean(folder6);
		DataLean(folder7);
		DataLean(folder8);
	}
	public static void DataLean(String folder){

        try{
            File file = new File(folder+"Result.txt");
    		FileInputStream fis = new FileInputStream(file);
    		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
    		BufferedReader reader = null;
    		String rs = null;
            reader = new BufferedReader(isr);
    		try {
				while ((rs = reader.readLine()) != null) {
					String str1="";
					String str2="";
					String str3="";
					String str4="";
					String str5="";
					String str6="";
					String str7="";
					String str8="";
					String str9="";
					String str10="";
					String str11="";
					String str12="";
					String str13="";
					String str14="";
					String str15="";
					String str16="";
					String str17="";
					String total="";
					rs=rs.replaceAll("\\s*", "").replaceAll(" ", "");
					if(rs.indexOf("</Name>")!=-1){
						str1=Tool.getStrByKey(rs,"<Name>","</Name>","</Name>").replace(" ", "");
						total+="<Name>"+str1+"</Name>";
					}
					if(rs.indexOf("</AdminCode>")!=-1){
						str2=Tool.getStrByKey(rs,"<AdminCode>","</AdminCode>","</AdminCode>").replace(" ", "");
						total+="<Code>"+str2+"</Code>";
					}
					if(rs.indexOf("</AdminAddr>")!=-1){
						str3=Tool.getStrByKey(rs,"<AdminAddr>","</AdminAddr>","</AdminAddr>").replace(" ", "");
						total+="<CodeAddr>"+str3+"</CodeAddr>";
					}
					if(rs.indexOf("<Admin_Coor>")!=-1){
						str4=Tool.getStrByKey(rs,"<Admin_Coor>","</Admin_Coor>","</Admin_Coor>").replace(" ", "");
						total+="<CodeCoor>"+str4+"</CodeCoor>";
					}
					if(rs.indexOf("<Hukou_Admin>")!=-1){
						str5=Tool.getStrByKey(rs,"<Hukou_Admin>","</Hukou_Admin>","</Hukou_Admin>").replace(" ", "");
						total+="<CodeReg>"+str5+"</CodeReg>";
					}
					if(rs.indexOf("</CtfTp>")!=-1){
						str6=Tool.getStrByKey(rs,"<CtfTp>","</CtfTp>","</CtfTp>").replace(" ", "");
						total+="<CtfTp>"+str6+"</CtfTp>";
					}
					if(rs.indexOf("</CtfId>")!=-1){
						str7=Tool.getStrByKey(rs,"<CtfId>","</CtfId>","</CtfId>").replace(" ", "");
						total+="<CtfId>"+str7+"</CtfId>";
					}
					if(rs.indexOf("<Hometown>")!=-1){
						str8=Tool.getStrByKey(rs,"<Hometown>","</Hometown>","</Hometown>").replace(" ", "");
						if(str8!=null)
							total+="<Home>"+str8+"</Home>";
						else
							FileTool.Dump(rs, folder+"nohometown.txt", "utf-8");
					}
				   if(rs.indexOf("</Gender>")!=-1){
					   str9=Tool.getStrByKey(rs,"<Gender>","</Gender>","</Gender>").replace(" ", "");
					   total+="<Gender>"+str9+"</Gender>";
					}
					if(rs.indexOf("</Birthday>")!=-1){
					   str10=Tool.getStrByKey(rs,"<Birthday>","</Birthday>","</Birthday>").replace(" ", "");
					   total+="<Birth>"+str10+"</<Birth>";
					}
					if(rs.indexOf("</Postal_Address>")!=-1){
					   str11=Tool.getStrByKey(rs,"<Postal_Address>","</Postal_Address>","</Postal_Address>").replace(" ", "");
					   if(str11.equals("no")){
						   FileTool.Dump(rs, folder+"nopostaddr.txt", "utf-8");
						   continue;
					   }
					   else
						  total+="<PostAddr>"+str11+"</PostAddr>";
					}
					if(rs.indexOf("</Mobile>")!=-1){
					   str12=Tool.getStrByKey(rs,"<Mobile>","</Mobile>","</Mobile>").replace(" ", "");
					   total+="<Mobile>"+str12+"</Mobile>";
					}
					if(rs.indexOf("</Nation>")!=-1){
					   str13=Tool.getStrByKey(rs,"<Nation>","</Nation>","</Nation>").replace(" ", "");
					   total+="<Nation>"+str13+"</Nation>";
					}
					if(rs.indexOf("</Version>")!=-1){
					   str14=Tool.getStrByKey(rs,"<Version>","</Version>","</Version>").replace(" ", "");
					   total+="<Version>"+str14+"</Version>";
					}
					FileTool.Dump(total.replace(" ", ""),folder+ "ResultLean.txt", "UTF-8");
					System.out.println("ok");		
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       }catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}catch(NullPointerException e){
		e.printStackTrace();
		
	}
 }
	
	
	
	
	
	
	
	
	public static void test() throws FailingHttpStatusCodeException, MalformedURLException, IOException, ParserException{
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		final HtmlPage page = webClient.getPage("http://life.nju.edu.cn/?topi=1&fun=%bd%cc%ca%a6%c3%fb%c2%bc");
		String content=page.asXml();
		System.out.println(content);
		Parser parser = new Parser();
		parser.setInputHTML(content);
		parser.setEncoding("utf-8");//utf-8
		HasParentFilter parentFilter1 = new HasParentFilter(new AndFilter(new TagNameFilter("ol"), new HasAttributeFilter("class", "techer-list")));
		HasParentFilter parentFilter2 = new HasParentFilter(new TagNameFilter("td"));
		HasParentFilter parentFilter3 = new HasParentFilter( new AndFilter(new TagNameFilter("li"),parentFilter2));
		//NodeFilter filter =new AndFilter(new TagNameFilter("a"),new HasAttributeFilter("class", "apic"));//new HasAttributeFilter("target", "_blank")
		NodeFilter filter =new TagNameFilter("a");//,parentFilter2);
		NodeList nodes = parser.extractAllNodesThatMatch(filter);
		System.out.println(nodes);
		int count = 0;
		if (nodes.size()!=0) {
			for (int n = 0; n < nodes.size(); n++) {
				TagNode tn = (TagNode) nodes.elementAt(n);
				String name = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "").replace("&nbsp;", "").trim();
				String purl = tn.getAttribute("href");
				//if (purl.indexOf("TeacherDetail") != -1) {//purl.indexOf("zh") != -1&&
					FileTool.Dump(name + "," + purl, folder1 + "-南大-Link.txt", "utf-8");
					System.out.println(name + "," + purl);
					count++;
				//}

			}
			System.out.println(count);
		}

		/*
		HtmlSelect hs = (HtmlSelect) page.getElementById("AspNetPager1_input");  
		hs.getOption(2).setSelected(true); 
		System.out.println("正在跳转…"); 
		ScriptResult sr2 = page.executeJavaScript("__doPostBack('AspNetPager1','')");
		HtmlPage countrySelect = (HtmlPage) sr2.getNewPage(); 
		System.out.println(countrySelect.asText());
		ScriptResult sr = page.executeJavaScript("javascript:__doPostBack('AspNetPager1','2')");  
		HtmlAnchor sr1 = page.getAnchorByHref("javascript:__doPostBack('AspNetPager1','2')");
		HtmlPage next = (HtmlPage) sr.getNewPage(); 
		sr1.click();
		System.out.println(sr1.click().toString());
		String next1 = sr1.getTextContent(); 
		System.out.println(next .asText());
		System.out.println(next1);
		*/
	
	}

	
}
