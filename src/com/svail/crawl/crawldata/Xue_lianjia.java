package com.svail.crawl.crawldata;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasChildFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.HasSiblingFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.svail.util.FileTool;
import com.svail.util.HTMLTool;

public class Xue_lianjia {
	private static Random random = new Random();
	private static String BJ_SchoolEstate="SchoolEstate";
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
				NodeFilter filter=new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class","info-list"));
			    NodeList nodes = parser.extractAllNodesThatMatch(filter);
			    if(nodes!=null)
			    {
			    	Node no = nodes.elementAt(0);
					if (no instanceof TagNode)
						{
							TagNode tno = (TagNode) no;
							String str = tno.toPlainTextString().replace(" ", "").replace("\t", "").trim();
							poi+="<DETAIL>"+str+"</DETAIL>";
							}
				}
					
			
                parser.reset();
				NodeFilter innerFilter=new AndFilter(new TagNameFilter("p"),(new HasAttributeFilter("class","name")));
				 filter=new AndFilter(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class","fl")),new HasChildFilter(innerFilter));
			     nodes = parser.extractAllNodesThatMatch(filter);
			    if (nodes != null)
			    {
					for (int mm = 0; mm < nodes.size(); mm ++)
					{
						Node ni = nodes.elementAt(mm);
						String tt = ni.toPlainTextString().trim();
						poi+="<REGION>"+tt+"</REGION>";
					}
			    }
			} catch (ParserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			return poi;
		}
		public static String SchoolEstate_URL="http://bj.lianjia.com/xuequfang/pg1/";
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
					Date newest = null;
					
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
					boolean quit = false;
	                String content = HTMLTool.fetchURL(url, "utf-8", "get");
						
						if (content == null)
						{
							
						}
						try {
							
							parser.setInputHTML(content);
							parser.setEncoding("utf-8");
							HasParentFilter parentFilter =new HasParentFilter(new TagNameFilter("h2"));
						  //NodeFilter filter = new AndFilter(new TagNameFilter("a"), new AndFilter(new AndFilter(parentFilter, new HasAttributeFilter("title")), new HasAttributeFilter("href"))); 
							NodeFilter filter = new AndFilter(new TagNameFilter("a"), new AndFilter(new AndFilter(parentFilter, new HasAttributeFilter("target")), new HasAttributeFilter("href"))); 
	                        NodeList nodes = parser.extractAllNodesThatMatch(filter);
	                        String poi="";
	                        if(nodes != null )
	                        {
	                        	for (int n = 0; n < nodes.size(); n ++)
	                        	{
	                        		Node ni = nodes.elementAt(n);
	        						String tt = ni.toPlainTextString().trim();
	        						poi="<TITLE>"+tt+"</TITLE>";
	                        		TagNode tn = (TagNode)nodes.elementAt(n);
	                        		String purl = tn.getAttribute("href");
	                        		//  http://bj.lianjia.com/xuequfang/4000000966.html
	                        		if (purl.startsWith("/xuequfang"))
	                        		{
	                        			poi+=parseSchoolEstate("http://bj.lianjia.com"+purl);
	                        			poi="<POI>"+poi+"</POI>";
	            	          			System.out.println(poi);
	            	          			FileTool.Dump(poi, "D:\\北京学区房_链家.csv", "UTF-8");
	            	 					
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
			// 链家:http://bj.lianjia.com/xuequfang/pg8/
			for(int turl=1;turl<=8;turl++)
			{
			//将整型转换成字符串型: Integer.toString(i)
				String Purl="http://bj.lianjia.com/xuequfang/pg" +Integer.toString(turl)+"/";
				getSchoolEstate(Purl);
				
			}
		
			
			
		}
				
		public static void main(String[] args) {
			
			//getSchoolEstate(SchoolEstate_URL);
			//String url="http://bj.lianjia.com/xuequfang/4000000966.html";
			//parseSchoolEstate(url);
			initiate();
		}
	}


