package com.svail.crawl.crawldata;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.svail.util.FileTool;
import com.svail.util.HTMLTool;

public class  Xue_woaiwojia {


	
	private static String BJ_SchoolEstate="SchoolEstate";
	/**
	 * java.util.Random类中实现的随机算法是伪随机，也就是有规则的随机，
	 * 所谓有规则的就是在给定种(seed)的区间内随机生成数字；相同种子数的Random对象，
	 * 相同次数生成的随机数字是完全相同的；Random类中各方法生成的随机数字都是均匀分布的，
	 * 也就是说区间内部的数字生成的几率均等；
	 * Random()：创建一个新的随机数生成器。
	 */
	public static String URL="http://bj.5i5j.com/school/p/id/10001897";
	//学区房:SchoolEstate
	/*
	 * Node getParent ()：取得父节点
       NodeList getChildren ()：取得子节点的列表
       Node getFirstChild ()：取得第一个子节点
       Node getLastChild ()：取得最后一个子节点
       Node getPreviousSibling ()：取得前一个兄弟（不好意思，英文是兄弟姐妹，直译太麻烦而且不符合习惯，对不起女同胞了）
       Node getNextSibling ()：取得下一个兄弟节点
	 */
	
	
	//解析每个小学的学区房信息
	private static String parseSchoolEstate(String url)
	{
		String content = HTMLTool.fetchURL(url, "utf-8", "get");//GB2312是汉字书写国家标准。

		Parser parser = new Parser();//获取解析器
		if (content == null)
		{
			return null;
		}
		
		String poi = "";
		try {
			
			parser.setInputHTML(content);
			parser.setEncoding("utf-8");
			NodeFilter filter=new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "school-tit"));
		    NodeList nodes = parser.extractAllNodesThatMatch(filter);
			if (nodes != null)
			{
				String str = nodes.elementAt(0).toPlainTextString().replace("  ", "").replace("\r\n", "").replace("\t", "").trim();
				poi = "<DETAIL>" + str + "</DETAIL>";
			}
			parser.reset();
		   filter=new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "school-info-hj"));
		   nodes = parser.extractAllNodesThatMatch(filter);
			//遍历所有的节点
			if (nodes != null)
			{
				String str = nodes.elementAt(0).toPlainTextString().replace("户口要求:", "").replace("\r\n", "").replace("\t", "").trim();
				poi+= "<RESIDENCE_REGISTRATION>" + str + "</RESIDENCE_REGISTRATION>";
			}
			parser.reset();
		    filter=new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "school-info-lh"));
		    nodes = parser.extractAllNodesThatMatch(filter);
			//遍历所有的节点
			if (nodes != null)
			{
				String str = nodes.elementAt(0).toPlainTextString().replace("落户年限:", "").replace("\r\n", "").replace("\t", "").trim();
				poi += "<AGE_LIMIT>" + str + "</AGE_LIMIT>";
			}
			parser.reset();
		    filter=new AndFilter(new TagNameFilter("li"),new HasAttributeFilter("class", "school-info-dk"));
		    nodes = parser.extractAllNodesThatMatch(filter);
			//遍历所有的节点
			if (nodes != null)
			{
				String str = nodes.elementAt(0).toPlainTextString().replace("对口中学：", "").replace("\r\n", "").replace("&nbsp;","").replace("介绍", "").replace("\t", "").trim();
				poi += "<CORRESPONGDING_MIDDLESCHOOL>" + str + "</CORRESPONGDING_MIDDLESCHOOL>";
			}
			parser.reset();
			filter=new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "school-menu1-lb school-menu1-foat"));
		    nodes = parser.extractAllNodesThatMatch(filter);
		     if(nodes!=null)
		     {
		    	 for (int mm = 0; mm < nodes.size(); mm ++)
		    	 {
		    		 Node ni = nodes.elementAt(mm);
					 String tt = ni.toPlainTextString().replace("\r\n", "").replace("							    							    ","  ").trim();
					 int i=tt.indexOf("元/平米");
					 String str=tt.substring(0, i+"元/平米".length());
					 poi+="<REGION>"+str+"</REGION>";
		    	 }
		    	 
		     }
		} catch (ParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		return poi;
	}
	public static String SchoolEstate_URL="http://bj.5i5j.com/school//n1";
	public static String LOG = "D:\\test";
	public static void getSchoolEstate(String url)
	{
		// 首先加载
				Vector<String> log = null;
				synchronized(BJ_SchoolEstate)
				{
					log = FileTool.Load(LOG + File.separator + "_rentout.log", "UTF-8");
				}
				// 2014/12/8 17:16:42
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");//小写的mm表示的是分钟
				
				Date latestdate = null;
				
				if (log != null)
				{
					try {
						latestdate = sdf.parse(log.elementAt(0));
						latestdate = new Date(latestdate.getTime() - 1);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				Parser parser = new Parser();
                String content = HTMLTool.fetchURL(url, "utf-8", "get");
					
					if (content == null)
					{
						
					}
					try {
						
						parser.setInputHTML(content);
						parser.setEncoding("utf-8");
						HasParentFilter parentFilter = new HasParentFilter(new AndFilter(new TagNameFilter("p"), new HasAttributeFilter("class", "title")));
						NodeFilter filter = new AndFilter(new TagNameFilter("a"), new AndFilter(new AndFilter(parentFilter, new HasAttributeFilter("target")), new HasAttributeFilter("href"))); 
                        NodeList nodes = parser.extractAllNodesThatMatch(filter);
                        String poi="";
                        if(nodes != null )
                        {
                        	for (int n = 0; n < nodes.size(); n ++)
                        	{
                        		TagNode tn = (TagNode)nodes.elementAt(n);
                        		String purl = tn.getAttribute("href");
                        		//href="/school/p/id/10001897"
                        		if (purl.startsWith("/school"))
                        		{
                        			poi =parseSchoolEstate("http://bj.5i5j.com"+purl);
                        			poi="<POI>"+poi+"</POI>";
            	          			System.out.println(poi);
            	          			FileTool.Dump(poi, "D:\\北京学区房_我爱我家.csv", "UTF-8");
                        			
                        		}
                        	}
                        	
                        }
 
					try {
						Thread.sleep(500 * ((int) (Math
							.max(1, Math.random() * 3))));
					} catch (final InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
			}//和try { 组成一个整体
			catch (ParserException e1) {
						// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
	public static void initiate()
	{
		// 我爱我家:http://bj.5i5j.com/school//n23
		for(int turl=1;turl<=23;turl++)
		{
		//将整型转换成字符串型: Integer.toString(i)
			String Purl="http://bj.5i5j.com/school/n" +Integer.toString(turl);
			getSchoolEstate(Purl);
			
		}
	
		
		
	}
			
	public static void main(String[] args) {
		
		//String url="http://bj.5i5j.com/school/p/id/10001897";
		//parseSchoolEstate(url);
		initiate();
	}
}



