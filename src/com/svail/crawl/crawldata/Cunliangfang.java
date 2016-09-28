package com.svail.crawl.crawldata;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.htmlparser.filters.StringFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.svail.util.FileTool;
import com.svail.util.HTMLTool;

public class Cunliangfang {
	public static String URL_TEMPLATE = "http://210.75.213.188/shh/portal/bjjs/audit_house_list.aspx?pagenumber=";// 1&pagesize=10";
	//该网址为北京市存量房交易服务平台
	private static String BJ_RESOLDS = "RESOLD";
	public static String LOG = "D:/Test";
	
    //解析POI函数
	public static String parsePois(String url)
	{
		String content = HTMLTool.fetchURL(url, "utf-8", "get");//GB2312是汉字书写国家标准。
		
		if (content == null)
		{
			return null;
		}
		String poi="";
		Parser parser = new Parser();
		try {
			
			parser.setInputHTML(content);
			parser.setEncoding("utf-8");
			//NodeFilter filter = new AndFilter(new TagNameFilter("tr"),filter1);
			NodeFilter filter = new AndFilter(new TagNameFilter("td"), new HasParentFilter(new TagNameFilter("tr")));
			//new HasParentFilter(new TagNameFilter("tbody"))
			//NodeFilter filter = new AndFilter(new TagNameFilter("table"), new HasAttributeFilter("class", "houseInfo"));
			NodeList nodes = parser.extractAllNodesThatMatch(filter);
			if (nodes != null)
			{
				for (int mm = 0; mm < nodes.size(); mm ++)
				{
					Node ni = nodes.elementAt(mm);
                    String tt1 = ni.toPlainTextString().trim();
                    String sub = tt1.trim();
                    if (mm == 0)
    					poi += "<inspecttion_number>" +"检验编号:"+ sub + "</inspecttion_number>";
                    if (mm == 1)
                    	 poi += "<county>" +"区县:"+ sub + "</county>";
                    if (mm == 2)
                   	 poi += "<housing_estate>" +"小区:"+ sub + "</housing_estate>";
                    if (mm == 3)
                   	 poi += "<orientation>" +"朝向:"+ sub + "</orientation>";
                    if (mm == 4)
                   	 poi += "<house_type>" +"户型:"+ sub + "</house_type>";
                    if (mm == 5)
                   	 poi += "<strucure_area>" +"建筑面积:"+ sub + "</strucure_area>";
                    if (mm == 6)
                   	 poi += "<floor_in>" +"所在楼层:"+ sub + "</floor_in>";
                    if (mm == 7)
                      	 poi += "<total_floor>" +"总层数:"+ sub + "</total_floor>";
                    if (mm == 8)
                      	 poi += "<building_age>" +"建筑年代:"+ sub + "</building_age>";
                    if (mm == 9)
                      	 poi += "<planning_use>" +"规划用途:"+ sub + "</planning_use>";
                    if (mm == 10)
                      	 poi += "<decoration>" +"装修情况:"+ sub + "</decoration>";
                    if (mm == 11)
                     	 poi += "<sale_price>" +"拟售价格:"+ sub + "</sale_price>";
                    if (mm == 12)
                     	 poi += "<institution>" +"经纪机构:"+ sub + "</institution>";
                    if (mm == 13)
                    	 poi += "<telephone>" +"联系电话:"+ sub + "</telephone>";
                    
				 }
				
				}
			
			parser.reset();
		
		} catch (ParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		return poi;

}
	public static void getHouseDate(int pageCount){

		String url = URL_TEMPLATE + pageCount + "&pagesize=10";
		
		Parser parser = new Parser();//解析url目标内容
		Vector<String> log = null;
		synchronized(BJ_RESOLDS)
		{
			log = FileTool.Load(LOG + File.separator + url + "_resold.log", "UTF-8");
		}
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");//小写的mm表示的是分钟
		
		Date latestdate = null;
		//Date newest = null;
		
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
        Vector<String> urls = new Vector<String>();
		Set<String> visited = new TreeSet<String>();
		urls.add(url);
		while (urls.size() > 0)
		{
			// 解析页面
			url = urls.get(0);
			
			urls.remove(0);
			visited.add(url);
			
			String content = HTMLTool.fetchURL(url, "utf-8", "get");
			if (content == null)
			{
				continue;
				
			}
			
		try {
					
			parser.setInputHTML(content);
			parser.setEncoding("utf-8");
			//获取每个房源的超链接
			/*
			 * <a href="audit_house_detail.aspx?House_Id=1312491">
			 * <img src="images/icon_show.gif" width="16" height="16" border="0">
			 * </a>
			 */
			String str="";
			NodeFilter filter =new TagNameFilter("tr");	
			NodeList nodes =parser.extractAllNodesThatMatch(filter); 
			if(nodes!=null){
				for(int i=0;i<nodes.size();i++){
					String str1 = (String)nodes.elementAt(i).toPlainTextString().replace("&nbsp;", "").replace("\r\n", "").replace("\t", "").replace("", "");
					if(str1.indexOf("区县")!=-1){
						
					}else if(str1.indexOf("发布方式")!=-1){
						
					}else if(str1.indexOf("核验编号")!=-1){
						
					}else if(str1.indexOf("核验编号区县小区")!=-1){
						
					}else if(str1.indexOf("共有")!=-1){
						
					}else if(str1.indexOf("用户名")!=-1){
						
					}else if(str1.indexOf("密码")!=-1){
						
					}else if(str1.indexOf("合同号")!=-1){
						
					}else if(str1.indexOf("签约方")!=-1){
						
					}else{
						FileTool.Dump(str1,LOG+"/北京存量房_简洁.txt", "UTF-8");
						System.out.println(str1);
					}
					
				}
				
			}
		} catch (ParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	  }
	
		
	}
    //抓取存量房函数
	public static void getStoragehouse(int pageCount) 
	{
		String url = URL_TEMPLATE + pageCount + "&pagesize=10";
		
		Parser parser = new Parser();//解析url目标内容
		Vector<String> log = null;
		synchronized(BJ_RESOLDS)
		{
			log = FileTool.Load(LOG + File.separator + url + "_resold.log", "UTF-8");
		}
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");//小写的mm表示的是分钟
		
		Date latestdate = null;
		//Date newest = null;
		
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
        Vector<String> urls = new Vector<String>();
		Set<String> visited = new TreeSet<String>();
		urls.add(url);
		while (urls.size() > 0)
		{
			// 解析页面
			url = urls.get(0);
			
			urls.remove(0);
			visited.add(url);
			
			String content = HTMLTool.fetchURL(url, "utf-8", "get");
			if (content == null)
			{
				continue;
				
			}
			
		try {
					
			parser.setInputHTML(content);
			parser.setEncoding("utf-8");
			//获取每个房源的超链接
			/*
			 * <a href="audit_house_detail.aspx?House_Id=1312491">
			 * <img src="images/icon_show.gif" width="16" height="16" border="0">
			 * </a>
			 */
			String str="";
			NodeFilter filter =new TagNameFilter("tr");	
			NodeList nodes =parser.extractAllNodesThatMatch(filter); 
			if(nodes!=null){
				for(int i=0;i<nodes.size();i++){
					String str1 = (String)nodes.elementAt(i).toPlainTextString().replace("&nbsp;", "").replace("\r\n", "").replace("\t", "").replace("", "");
					System.out.println(str1);
				}
				
			}
			parser.reset();
			filter = new AndFilter(new TagNameFilter("a"), new AndFilter(new HasParentFilter(new AndFilter(new TagNameFilter("td"), new HasParentFilter(new TagNameFilter("tr")))), new HasChildFilter(new TagNameFilter("img"))));
			nodes = parser.extractAllNodesThatMatch(filter);
			for (int n = 0; n < 10; n ++)
			{
				TagNode no = (TagNode)nodes.elementAt(n);//elementAt(int index) 方法用于获取组件的向量的指定索引/位置。
				String tur= no.getAttribute("href");   
				if (tur.startsWith("audit_house_detail"))
				{
					
					String url1="http://210.75.213.188/shh/portal/bjjs/" + tur;
					String pois = parsePois(url1);	
					//Vector<String> 就是在Vector中放String的意思.这是java的泛型	
					pois="<POI>"+pois+"</POI>";
                    FileTool.Dump(pois,LOG+"/北京存量房_详细.txt", "UTF-8");
                    //"D:\\存量房.csv"
					System.out.println(pois);
	
				}
				try {
					Thread.sleep(500 * ((int) (Math
						.max(1, Math.random() * 3))));
				} catch (final InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		} catch (ParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	  }
	}
	
	public static void initiate() throws MalformedURLException, IOException, ParserException 
	{
		String url = URL_TEMPLATE + 1 + "&pagesize=10";
		String content = HTMLTool.fetchURL(url, "utf-8", "get");//GB2312是汉字书写国家标准。

		//  分析最大页数
		Parser parser = new Parser();//获取解析器
		if (content == null)
		{
			//continue;
		}
		try
		{
			parser.setInputHTML(content);
			parser.setEncoding("utf-8");
			NodeFilter filter = new StringFilter("页次：");
			NodeList nodes = parser.extractAllNodesThatMatch(filter);
			if (nodes != null)
			{
				String str = nodes.elementAt(0).toPlainTextString().trim();
				if(str.startsWith("页次："))
				{
					
					int n1=str.indexOf("/");
					int m=str.indexOf("页", n1 + 1);
					String str1 =str.substring(n1 + "/".length(), m).trim();
					int pages = Integer.parseInt(str1);
					try {
						pages = Integer.parseInt(str1);//parseInt(String s)将字符串参数作为有符号的十进制整数进行解析。
						for(int i=1;i<=pages;i++)
						{
							//getStoragehouse(i);
							getHouseDate(i);
						}
					}
					catch( NumberFormatException e)
					{	    		
					}	

				}

			}
		}
		catch (FailingHttpStatusCodeException e1)
		{}

}

		
public static void main(String[] args) throws MalformedURLException, IOException, ParserException {
	
	initiate();
}
}





