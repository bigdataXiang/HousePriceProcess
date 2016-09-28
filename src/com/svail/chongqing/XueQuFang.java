package com.svail.chongqing;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

import com.google.gson.JsonSyntaxException;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.svail.util.FileTool;
import com.svail.util.HTMLTool;
import com.svail.util.Tool;

import net.sf.json.JSONObject;

public class XueQuFang {
	
	public static String  SchoolDistrictHousing="http://m.fang.com/xf/cq/";
	public static String HOTREALESTATE_URL ="http://newhouse.cq.fang.com/house/asp/trans/buynewhouse/default.htm";
	
	public static String FOLDER="D:/重庆基础数据抓取/基础数据/学区房/";
	
	public static void main(String[] args){
		
		timingCrawl("21:00:00");

	}
	public static void timingCrawl(String time){		  
    	ScheduledExecutorService service = Executors.newScheduledThreadPool(4);          
    	long oneDay = 24 * 60 * 60 * 1000;
    	long timemillis=Tool.getTimeMillis(time);
    	long timenow=System.currentTimeMillis();
    	long initDelay = timemillis - timenow;  
    	initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;  

        Runnable trafficTask = new Runnable(){  
            public void run() { 
            	System.out.println("开始定期抓取");
            	importMongoDB();
            }  
        };  
    	service.scheduleAtFixedRate(trafficTask, initDelay, 24* 60 * 60 * 1000, TimeUnit.MILLISECONDS);//scheduleAtFixedRate(TimerTask task,long delay,long period) 方法用于安排指定的任务进行重复的固定速率执行，在指定的延迟后开始。    
	}
	public static void run(){
		importMongoDB();
	}
	 public static void importMongoDB(){

			try {
				Mongo mongo = new Mongo("192.168.6.9", 27017);
				DB db = mongo.getDB("chongqing");  // 数据库名称
				
				
				DBCollection coll = db.getCollection("HotRealEstate");
				//coll.drop();//清空表
				
				try {
					   List<BasicDBObject> objs =getHotRealEstate(FOLDER);
					   if(objs.size()!=0){
						   int count=0;
						   for(int i=0;i<objs.size();i++){
							   BasicDBObject obj=objs.get(i);						
							   
							   BasicDBObject index=new BasicDBObject();
							   Object news=obj.get("community");
							   index.put("community", news);
							   DBCursor rls =coll.find(index);
							   
							   if(rls == null || rls.size() == 0){
								   coll.insert(obj);
								   count++;
							   }else{
								   System.out.println("该数据已经存在!");
							   }
						   }
						   System.out.println("共导入"+count+"条数据");
					   }
					  
					   				
				}catch (JsonSyntaxException e) {
		    		// TODO Auto-generated catch block
		    		e.printStackTrace();
		    	}catch (NullPointerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					//FileTool.Dump(photo.toString(), poiError, "utf-8");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

						
				
			}catch (UnknownHostException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (MongoException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		
	 }

	public static void parseHotRealEstate(String link,JSONObject obj,BasicDBObject document,String folder) throws UnsupportedEncodingException{

		//String content = HTMLTool.fetchURL(link, "gb2312", "get");
		String content = Tool.fetchURL(link);

		Parser parser = new Parser();
		
		String poi = "";
		try {
			
			parser.setInputHTML(content);
			parser.setEncoding("gb2312");
			// <a id="xfxq_C03_14" target="_blank" href="http://haitangwanld023.fang.com/house/3111064820/housedetail.htm">更多详细信息&gt;&gt;</a>
			NodeFilter filter = new AndFilter(new TagNameFilter("a"), new AndFilter(new HasAttributeFilter("id", "xfdsxq_B04_14"), new HasAttributeFilter("href")));//xfxq_C03_14
			//AndFilter可以把两种Filter进行组合，只有同时满足条件的Node才会被过滤。
			//NodeFilter filter = new HasAttributeFilter( "id", "logoindex" );getText:div id="logoindex"
			NodeList nodes = parser.extractAllNodesThatMatch(filter);
			
			if (nodes.size()!=0)
			{
				int size=nodes.size();
				for (int n = 0; n < 1; n ++)
				{
					if (nodes.elementAt(n) instanceof TagNode)
					//elementAt(int index) 方法用于获取组件的向量的指定索引/位置。index--这是一个索引该向量,它返回指定索引处的组件。
					{
						TagNode tn = (TagNode) nodes.elementAt(n);
						
						if (tn.getAttribute("href").endsWith("housedetail.htm")){
							content = Tool.fetchURL(tn.getAttribute("href"));
							
							try {
								
								parser.setInputHTML(content);
								parser.setEncoding("gb2312");
								
								// filter = new AndFilter(new TagNameFilter("table"), new HasParentFilter(new HasAttributeFilter("class", "besic_inform")));
								
								nodes = parser.extractAllNodesThatMatch(new HasAttributeFilter("class", "besic_inform"));
								
								String tt = nodes.elementAt(0).toPlainTextString().replace("提示：页中所涉面积，如无特殊说明，均为建筑面积；所涉及装修状况、标准以最终合同为准。", "").replace("\r\n", "").replace("\n", "").replace("\t", "").replace(" ", "").replace("&nbsp;", "").trim();
			
									if (tt.indexOf("[更多]") != -1&&tt.indexOf("房价")!=-1)
									{
										obj.put("community",tt.substring(tt.indexOf("[更多]")+"[更多]".length(),tt.indexOf("房价")));
										document.put("community",tt.substring(tt.indexOf("[更多]")+"[更多]".length(),tt.indexOf("房价")));
											
										
									}
									if (tt.indexOf("物业类别") != -1&&tt.indexOf("项目特色")!=-1)
									{
										
											obj.put("property",tt.substring(tt.indexOf("物业类别")+"物业类别".length(),tt.indexOf("项目特色")));
											document.put("property",tt.substring(tt.indexOf("物业类别")+"物业类别".length(),tt.indexOf("项目特色")));
									}
									 if (tt.indexOf("项目特色") != -1&&tt.indexOf("建筑类别")!=-1)
									{
										
										 obj.put("character",tt.substring(tt.indexOf("物业类别")+"物业类别".length(),tt.indexOf("项目特色")));
										 document.put("character",tt.substring(tt.indexOf("物业类别")+"物业类别".length(),tt.indexOf("项目特色")));
									}
									 if (tt.indexOf("建筑类别") != -1&&tt.indexOf("装修状况")!=-1)
									{
										    int xx=tt.indexOf("建筑类别")+"建筑类别".length();
										    int yy=tt.indexOf("装修状况",81);

                                            if(xx!=-1&&yy!=-1){
                                            	 obj.put("biult_type",tt.substring(xx,yy));
                                            	 document.put("biult_type",tt.substring(xx,yy));
                                            }   
									}
									 if (tt.indexOf("装修状况",tt.indexOf("建筑类别")+"建筑类别".length()) != -1&&tt.indexOf("环线位置")!=-1)
									{
										
										 obj.put("fitment",tt.substring(tt.indexOf("装修状况",tt.indexOf("建筑类别")+"建筑类别".length())+"装修状况".length(),tt.indexOf("环线位置")).replace("[装修相册]", "").replace("[建材卖场]", ""));
										 document.put("fitment",tt.substring(tt.indexOf("装修状况",tt.indexOf("建筑类别")+"建筑类别".length())+"装修状况".length(),tt.indexOf("环线位置")).replace("[装修相册]", "").replace("[建材卖场]", ""));
									}
									 if (tt.indexOf("容积率") != -1&&tt.indexOf("绿化率")!=-1)
									{
										 obj.put("volume_rate",tt.substring(tt.indexOf("容积率")+"容积率".length(),tt.indexOf("绿化率")));
										 document.put("volume_rate",tt.substring(tt.indexOf("容积率")+"容积率".length(),tt.indexOf("绿化率")));
									}
									if (tt.indexOf("绿化率") != -1&&tt.indexOf("开盘时间")!=-1)
									{
										 obj.put("green_rate",tt.substring(tt.indexOf("绿化率")+"绿化率".length(),tt.indexOf("开盘时间")));
										 document.put("green_rate",tt.substring(tt.indexOf("绿化率")+"绿化率".length(),tt.indexOf("开盘时间")));
									}
									 if (tt.indexOf("开盘时间") != -1&&tt.indexOf("交房时间")!=-1)
									{
										
										obj.put("open_time",tt.substring(tt.indexOf("开盘时间")+"开盘时间".length(),tt.indexOf("交房时间")));
										document.put("open_time",tt.substring(tt.indexOf("开盘时间")+"开盘时间".length(),tt.indexOf("交房时间")));
									}
									 if (tt.indexOf("交房时间") != -1&&tt.indexOf("物业费")!=-1)
									{
										
										obj.put("completed_time",tt.substring(tt.indexOf("交房时间")+"交房时间".length(),tt.indexOf("物业费")));
										document.put("completed_time",tt.substring(tt.indexOf("交房时间")+"交房时间".length(),tt.indexOf("物业费")));
									}
									 if (tt.indexOf("物业费") != -1&&tt.indexOf("物业公司")!=-1)
									{
										
										 obj.put("property_fee",tt.substring(tt.indexOf("物业费")+"物业费".length(),tt.indexOf("物业公司")));
										 document.put("property_fee",tt.substring(tt.indexOf("物业费")+"物业费".length(),tt.indexOf("物业公司")));
									}
									 if (tt.indexOf("物业公司") != -1&&tt.indexOf("开发商")!=-1)
									{
										
										 obj.put("property_company",tt.substring(tt.indexOf("物业公司")+"物业公司".length(),tt.indexOf("开发商")));
										 document.put("property_company",tt.substring(tt.indexOf("物业公司")+"物业公司".length(),tt.indexOf("开发商")));
									}
									 if (tt.indexOf("开发商") != -1&&tt.indexOf("预售许可证")!=-1)
									{
										
										 obj.put("developer",tt.substring(tt.indexOf("开发商")+"开发商".length(),tt.indexOf("预售许可证")));
										 document.put("developer",tt.substring(tt.indexOf("开发商")+"开发商".length(),tt.indexOf("预售许可证")));
									}

									 if (tt.indexOf("售楼地址") != -1&&tt.indexOf("物业地址")!=-1)
									{
										
										obj.put("sales_address",tt.substring(tt.indexOf("售楼地址")+"售楼地址".length(),tt.indexOf("物业地址")));
										document.put("sales_address",tt.substring(tt.indexOf("售楼地址")+"售楼地址".length(),tt.indexOf("物业地址")));
									}
									 if (tt.indexOf("价") != -1&&tt.indexOf("走势")!=-1)
									{
										
										 obj.put("price",tt.substring(tt.indexOf("价")+"价".length(),tt.indexOf("走势")).replace("[房价", ""));
										 document.put("price",tt.substring(tt.indexOf("价")+"价".length(),tt.indexOf("走势")).replace("[房价", ""));
									}
							     
								 parser.reset();
								 HasParentFilter parentFilter1 = new HasParentFilter(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "lineheight")));
								 HasParentFilter parentFilter2 = new HasParentFilter(new AndFilter(new TagNameFilter("div"),new AndFilter(parentFilter1,new HasAttributeFilter("id", "priceListClose"))));
								 HasParentFilter parentFilter3 =new HasParentFilter(new AndFilter(new TagNameFilter("table"),parentFilter2));
								 //HasParentFilter parentFilter4=new HasParentFilter(new AndFilter(new TagNameFilter("tr"),parentFilter3));
								 filter = new AndFilter(new TagNameFilter("tr"),parentFilter3);
								 nodes = parser.extractAllNodesThatMatch(filter);
								 int count=nodes.size();
								 JSONObject record=new JSONObject();
								 if(nodes.size()!=0){
									 for(int i=0;i<nodes.size();i++){
										 TagNode no=(TagNode) nodes.elementAt(i);
										 String str=no.toPlainTextString().replace("  ", "").replace("\r\n", "").replace("\t", "").replace("\n", "");
										 if(i>0){
											 String key="record"+(i);
											 record.put(key,str);
										 }		 
									 }	 
								 }
								 obj.put("record",record);
								 document.put("record",record);
								 
								 
								 parser.reset();
								 //parentFilter1 = new HasParentFilter(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "lineheight")));
								 filter = new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "lineheight"));
								 nodes = parser.extractAllNodesThatMatch(filter);
								 if(nodes.size()!=0){
									 for(int i=1;i<nodes.size();i++){
										 TagNode no=(TagNode)nodes.elementAt(i);
										 String str=no.toPlainTextString().replace("  ", "").replace("\r\n", "").replace("\t", "").replace("\n", "");
										 //System.out.println(str);
										 if(i==1){
											 obj.put("supporting_project",str); 
											 document.put("supporting_project",str); 
										 }else if(i==2){
											 obj.put("traffic",str);
											 document.put("traffic",str);
										 }else if(i==3){
											 obj.put("building_decoration",str); 
											 document.put("building_decoration",str);
										 }else if(i==4){											 
											 obj.put("floor_condition",str); 
											 document.put("floor_condition",str);
										 }else if(i==5){
											 obj.put("park",str); 
											 document.put("park",str); 
										 }else if(i==6){
											 obj.put("project_profile",str); 
											 document.put("project_profile",str);
										 }else if(i==7){
											 obj.put("related_information",str);
											 document.put("related_information",str);
										 }
									 }
									 
								 }
								 
								 obj.put("url",link);
								 document.put("url",link);
							
							} catch (ParserException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} 
						}
					}
				}
			}else{
				 parser.reset();
                 filter = new AndFilter(new TagNameFilter("a"), new AndFilter(new HasAttributeFilter("id", "xfptxq_B04_14"), new HasAttributeFilter("href")));
                 nodes = parser.extractAllNodesThatMatch(filter);
                 if (nodes != null)
     			{
     				int size=nodes.size();
    				for (int n = 0; n < 1; n ++)
    				{
    					if (nodes.elementAt(n) instanceof TagNode)
    					//elementAt(int index) 方法用于获取组件的向量的指定索引/位置。index--这是一个索引该向量,它返回指定索引处的组件。
    					{
    						TagNode tn = (TagNode) nodes.elementAt(n);
    						
    						if (tn.getAttribute("href").endsWith("housedetail.htm")){
    							content = Tool.fetchURL(tn.getAttribute("href"));
    							
    							try {
    								
    								parser.setInputHTML(content);
    								parser.setEncoding("gb2312");
    								
    								// filter = new AndFilter(new TagNameFilter("table"), new HasParentFilter(new HasAttributeFilter("class", "besic_inform")));
    								
    								nodes = parser.extractAllNodesThatMatch(new HasAttributeFilter("class", "besic_inform"));
    								
    								String tt = nodes.elementAt(0).toPlainTextString().replace("提示：页中所涉面积，如无特殊说明，均为建筑面积；所涉及装修状况、标准以最终合同为准。", "").replace("\r\n", "").replace("\n", "").replace("\t", "").replace(" ", "").replace("&nbsp;", "").trim();
    			
    									if (tt.indexOf("[更多]") != -1&&tt.indexOf("房价")!=-1)
    									{
    										obj.put("community",tt.substring(tt.indexOf("[更多]")+"[更多]".length(),tt.indexOf("房价")));
    										document.put("community",tt.substring(tt.indexOf("[更多]")+"[更多]".length(),tt.indexOf("房价")));
    											
    										
    									}
    									if (tt.indexOf("物业类别") != -1&&tt.indexOf("项目特色")!=-1)
    									{
    										
    											obj.put("property",tt.substring(tt.indexOf("物业类别")+"物业类别".length(),tt.indexOf("项目特色")));
    											document.put("property",tt.substring(tt.indexOf("物业类别")+"物业类别".length(),tt.indexOf("项目特色")));
    									}
    									 if (tt.indexOf("项目特色") != -1&&tt.indexOf("建筑类别")!=-1)
    									{
    										
    										 obj.put("character",tt.substring(tt.indexOf("物业类别")+"物业类别".length(),tt.indexOf("项目特色")));
    										 document.put("character",tt.substring(tt.indexOf("物业类别")+"物业类别".length(),tt.indexOf("项目特色")));
    									}
    									 if (tt.indexOf("建筑类别") != -1&&tt.indexOf("装修状况")!=-1)
    									{
    										    int xx=tt.indexOf("建筑类别")+"建筑类别".length();
    										    int yy=tt.indexOf("装修状况",81);

                                                if(xx!=-1&&yy!=-1){
                                                	 obj.put("biult_type",tt.substring(xx,yy));
                                                	 document.put("biult_type",tt.substring(xx,yy));
                                                }   
    									}
    									 if (tt.indexOf("装修状况",tt.indexOf("建筑类别")+"建筑类别".length()) != -1&&tt.indexOf("环线位置")!=-1)
    									{
    										
    										 obj.put("fitment",tt.substring(tt.indexOf("装修状况",tt.indexOf("建筑类别")+"建筑类别".length())+"装修状况".length(),tt.indexOf("环线位置")).replace("[装修相册]", "").replace("[建材卖场]", ""));
    										 document.put("fitment",tt.substring(tt.indexOf("装修状况",tt.indexOf("建筑类别")+"建筑类别".length())+"装修状况".length(),tt.indexOf("环线位置")).replace("[装修相册]", "").replace("[建材卖场]", ""));
    									}
    									 if (tt.indexOf("容积率") != -1&&tt.indexOf("绿化率")!=-1)
    									{
    										 obj.put("volume_rate",tt.substring(tt.indexOf("容积率")+"容积率".length(),tt.indexOf("绿化率")));
    										 document.put("volume_rate",tt.substring(tt.indexOf("容积率")+"容积率".length(),tt.indexOf("绿化率")));
    									}
    									if (tt.indexOf("绿化率") != -1&&tt.indexOf("开盘时间")!=-1)
    									{
    										 obj.put("green_rate",tt.substring(tt.indexOf("绿化率")+"绿化率".length(),tt.indexOf("开盘时间")));
    										 document.put("green_rate",tt.substring(tt.indexOf("绿化率")+"绿化率".length(),tt.indexOf("开盘时间")));
    									}
    									 if (tt.indexOf("开盘时间") != -1&&tt.indexOf("交房时间")!=-1)
    									{
    										
    										obj.put("open_time",tt.substring(tt.indexOf("开盘时间")+"开盘时间".length(),tt.indexOf("交房时间")));
    										document.put("open_time",tt.substring(tt.indexOf("开盘时间")+"开盘时间".length(),tt.indexOf("交房时间")));
    									}
    									 if (tt.indexOf("交房时间") != -1&&tt.indexOf("物业费")!=-1)
    									{
    										
    										obj.put("completed_time",tt.substring(tt.indexOf("交房时间")+"交房时间".length(),tt.indexOf("物业费")));
    										document.put("completed_time",tt.substring(tt.indexOf("交房时间")+"交房时间".length(),tt.indexOf("物业费")));
    									}
    									 if (tt.indexOf("物业费") != -1&&tt.indexOf("物业公司")!=-1)
    									{
    										
    										 obj.put("property_fee",tt.substring(tt.indexOf("物业费")+"物业费".length(),tt.indexOf("物业公司")));
    										 document.put("property_fee",tt.substring(tt.indexOf("物业费")+"物业费".length(),tt.indexOf("物业公司")));
    									}
    									 if (tt.indexOf("物业公司") != -1&&tt.indexOf("开发商")!=-1)
    									{
    										
    										 obj.put("property_company",tt.substring(tt.indexOf("物业公司")+"物业公司".length(),tt.indexOf("开发商")));
    										 document.put("property_company",tt.substring(tt.indexOf("物业公司")+"物业公司".length(),tt.indexOf("开发商")));
    									}
    									 if (tt.indexOf("开发商") != -1&&tt.indexOf("预售许可证")!=-1)
    									{
    										
    										 obj.put("developer",tt.substring(tt.indexOf("开发商")+"开发商".length(),tt.indexOf("预售许可证")));
    										 document.put("developer",tt.substring(tt.indexOf("开发商")+"开发商".length(),tt.indexOf("预售许可证")));
    									}

    									 if (tt.indexOf("售楼地址") != -1&&tt.indexOf("物业地址")!=-1)
    									{
    										
    										obj.put("sales_address",tt.substring(tt.indexOf("售楼地址")+"售楼地址".length(),tt.indexOf("物业地址")));
    										document.put("sales_address",tt.substring(tt.indexOf("售楼地址")+"售楼地址".length(),tt.indexOf("物业地址")));
    									}
    									 if (tt.indexOf("价") != -1&&tt.indexOf("走势")!=-1)
    									{
    										
    										 obj.put("price",tt.substring(tt.indexOf("价")+"价".length(),tt.indexOf("走势")).replace("[房价", ""));
    										 document.put("price",tt.substring(tt.indexOf("价")+"价".length(),tt.indexOf("走势")).replace("[房价", ""));
    									}
    							     
    								 parser.reset();
    								 HasParentFilter parentFilter1 = new HasParentFilter(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "lineheight")));
    								 HasParentFilter parentFilter2 = new HasParentFilter(new AndFilter(new TagNameFilter("div"),new AndFilter(parentFilter1,new HasAttributeFilter("id", "priceListClose"))));
    								 HasParentFilter parentFilter3 =new HasParentFilter(new AndFilter(new TagNameFilter("table"),parentFilter2));
    								 //HasParentFilter parentFilter4=new HasParentFilter(new AndFilter(new TagNameFilter("tr"),parentFilter3));
    								 filter = new AndFilter(new TagNameFilter("tr"),parentFilter3);
    								 nodes = parser.extractAllNodesThatMatch(filter);
    								 int count=nodes.size();
    								 JSONObject record=new JSONObject();
    								 if(nodes.size()!=0){
    									 for(int i=0;i<nodes.size();i++){
    										 TagNode no=(TagNode) nodes.elementAt(i);
    										 String str=no.toPlainTextString().replace("  ", "").replace("\r\n", "").replace("\t", "").replace("\n", "");
    										 if(i>0){
    											 String key="record"+(i);
    											 record.put(key,str);
    										 }		 
    									 }	 
    								 }
    								 obj.put("record",record);
    								 document.put("record",record);
    								 
    								 
    								 parser.reset();
    								 //parentFilter1 = new HasParentFilter(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "lineheight")));
    								 filter = new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "lineheight"));
    								 nodes = parser.extractAllNodesThatMatch(filter);
    								 if(nodes.size()!=0){
    									 for(int i=1;i<nodes.size();i++){
    										 TagNode no=(TagNode)nodes.elementAt(i);
    										 String str=no.toPlainTextString().replace("  ", "").replace("\r\n", "").replace("\t", "").replace("\n", "");
    										 //System.out.println(str);
    										 if(i==1){
    											 obj.put("supporting_project",str); 
    											 document.put("supporting_project",str); 
    										 }else if(i==2){
    											 obj.put("traffic",str);
    											 document.put("traffic",str);
    										 }else if(i==3){
    											 obj.put("building_decoration",str); 
    											 document.put("building_decoration",str);
    										 }else if(i==4){											 
    											 obj.put("floor_condition",str); 
    											 document.put("floor_condition",str);
    										 }else if(i==5){
    											 obj.put("park",str); 
    											 document.put("park",str); 
    										 }else if(i==6){
    											 obj.put("project_profile",str); 
    											 document.put("project_profile",str);
    										 }else if(i==7){
    											 obj.put("related_information",str);
    											 document.put("related_information",str);
    										 }
    									 }
    									 
    								 }
    								 
    								 obj.put("url",link);
    								 document.put("url",link);
    							
    							} catch (ParserException e1) {
    								// TODO Auto-generated catch block
    								e1.printStackTrace();
    							} 
    						}
    					}
    				}
    			}
				
			}

		} catch (ParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		if (poi != null)
		{
			poi = poi.replace("&nbsp;", "");
			int ss = poi.indexOf("[");
			while (ss != -1)
			{
				int ee = poi.indexOf("]", ss + 1);
				if (ee != -1)
				{
					String sub = poi.substring(ss, ee + 1);
					poi = poi.replace(sub, "");
				}
				else
					break;
				ss = poi.indexOf("[", ss);
			}
		}
		
		//进行实时的地理编码
		if(obj.containsKey("sales_address")){
			String addr=obj.getString("sales_address");
			JSONObject geo=GeoCode.AddressMatch(addr, folder, obj);
			obj.put("coordinate", geo.get("coordinate"));
			document.put("coordinate", geo.get("coordinate"));
			obj.put("region", geo.get("region"));
			document.put("region", geo.get("region"));
		}
		checkMissed(obj,document);
		
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		obj.put("crawl_time", sdf.format(d));
		document.put("crawl_time", sdf.format(d));

	}
	public static void checkMissed(JSONObject obj,BasicDBObject document){
		if(!obj.containsKey("community")){
			obj.put("community", "");
			document.put("community", "");
		}
		if(!obj.containsKey("property")){
			obj.put("property", "");
			document.put("property", "");
		}
		if(!obj.containsKey("character")){
			obj.put("character", "");
			document.put("character", "");
		}
		if(!obj.containsKey("biult_type")){
			obj.put("biult_type", "");
			document.put("biult_type", "");
		}
		if(!obj.containsKey("fitment")){
			obj.put("fitment", "");
			document.put("fitment", "");
		}
		if(!obj.containsKey("volume_rate")){
			obj.put("volume_rate", "");
			document.put("volume_rate", "");
		}
		if(!obj.containsKey("green_rate")){
			obj.put("green_rate", "");
			document.put("green_rate", "");
		}
		if(!obj.containsKey("open_time")){
			obj.put("open_time", "");
			document.put("open_time", "");
		}
		if(!obj.containsKey("completed_time")){
			obj.put("completed_time", "");
			document.put("completed_time", "");
		}
		if(!obj.containsKey("property_fee")){
			obj.put("property_fee", "");
			document.put("property_fee", "");
		}
		if(!obj.containsKey("property_company")){
			obj.put("property_company", "");
			document.put("property_company", "");
		}
		if(!obj.containsKey("developer")){
			obj.put("developer", "");
			document.put("developer", "");
		}
		if(!obj.containsKey("sales_address")){
			obj.put("sales_address", "");
			document.put("sales_address", "");
		}
		if(!obj.containsKey("price")){
			obj.put("price", "");
			document.put("price", "");
		}
		if(!obj.containsKey("record")){
			obj.put("record", "");
			document.put("record", "");
		}
		if(!obj.containsKey("supporting_project")){
			obj.put("supporting_project", "");
			document.put("supporting_project", "");
		}
		if(!obj.containsKey("traffic")){
			obj.put("traffic", "");
			document.put("traffic", "");
		}
		if(!obj.containsKey("building_decoration")){
			obj.put("building_decoration", "");
			document.put("building_decoration", "");
		}
		if(!obj.containsKey("floor_condition")){
			obj.put("floor_condition", "");
			document.put("floor_condition", "");
		}
		
		
		if(!obj.containsKey("park")){
			obj.put("park", "");
			document.put("park", "");
		}
		if(!obj.containsKey("project_profile")){
			obj.put("project_profile", "");
			document.put("project_profile", "");
		}
		if(!obj.containsKey("related_information")){
			obj.put("related_information", "");
			document.put("related_information", "");
		}
		if(!obj.containsKey("url")){
			obj.put("url", "");
			document.put("url", "");
		}
	}

	public static List<BasicDBObject>  getHotRealEstate(String Folder) throws UnsupportedEncodingException{
		
		List<BasicDBObject> objs=new ArrayList<BasicDBObject>();
		
		String url = HOTREALESTATE_URL;
        Vector<String> urls = new Vector<String>();
		
		Set<String> visited = new TreeSet<String>();
		urls.add(url);
		
		Parser parser = new Parser();
		boolean quit = false;
		
		while (urls.size() > 0)
		{
			// 解析页面
			url = urls.get(0);
			
			urls.remove(0);
			visited.add(url);
			
			//String content = HTMLTool.fetchURL(url, "gb2312", "post");
			String content = Tool.fetchURL(url);
			if (content == null)
			{
				continue;
			}
			try {
				parser.setInputHTML(content);
				parser.setEncoding("gb2312");
				//System.out.println(content);
				int ss = content.indexOf("<strong class=\"f14 fb_blue\">");
				
				while (ss != -1)
				{

					int en = content.indexOf("</strong>", ss + "<strong class=\"f14 fb_blue\">".length());
					if (en != -1)
					{
						String sub = content.substring(ss, en);
						
						int rfs = sub.indexOf("href=\"");
						if (rfs != -1)
						{
							int rfe = sub.indexOf("\"", rfs + "href=\"".length());
							if (rfe != -1)
							{
								String purl = sub.substring(rfs + "href=\"".length(), rfe);
								//System.out.println(purl);
								
								JSONObject obj = new JSONObject();
								BasicDBObject document=new BasicDBObject();
								
								parseHotRealEstate(purl,obj,document,FOLDER);
								

								objs.add(document);
								FileTool.Dump(obj.toString(), FOLDER+"hotRealEstate-0612.txt", "UTF-8");
								System.out.println(obj);
							}
							else
								break;
						}
						else
							break;
					}
					else
						break;
					
					ss = content.indexOf("<strong class=\"f14 fb_blue\">", en + "</strong>".length());
					
					try {
						Thread.sleep(500 * ((int) (Math
							.max(1, Math.random() * 3))));
					} catch (final InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
		
				
                NodeFilter filter = new AndFilter(new TagNameFilter("div"), new HasParentFilter(new HasAttributeFilter("class", "searchListPage"))); 
				NodeList nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null)
				{

					for (int nn = 0; nn < nodes.size(); nn ++)
					{
						Node ni = nodes.elementAt(nn);
						NodeList cld = ni.getChildren();
						if (cld != null)
						{
							for (int kkk = 0; kkk < cld.size(); kkk ++)
							{
								if (cld.elementAt(kkk) instanceof TagNode)
								{
									String href = ((TagNode)cld.elementAt(kkk)).getAttribute("href");
									if (href != null)
									{
										if (!href.startsWith("http://"))
										{
											if (href.startsWith("/house"))
												href = "http://newhouse.cq.fang.com/" + href;
											else
												continue;
										}
										
										if (!visited.contains(href))
										{
											int kk = 0;
											for (; kk < urls.size(); kk ++)
											{
												if (urls.elementAt(kk).equalsIgnoreCase(href))
												{
													break;
												}
											}
											
											if (kk == urls.size())
												urls.add(href);
										}
									}
								}
							}
						}						
					}
				}
				
			}catch (ParserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}
		return objs;
	}
	
	 /**
	  * 知名学校对应的楼盘获取有问题，ajax动态加载的问题
	  * @param Folder
	  */
	 public static void getschoolDistrictHousing(String Folder){

			String url = SchoolDistrictHousing;
	        Vector<String> urls = new Vector<String>();
			
			Set<String> visited = new TreeSet<String>();
			urls.add(url);
			
			Parser parser = new Parser();
			boolean quit = false;
			
			while (urls.size() > 0)
			{
				// 解析页面
				url = urls.get(0);
				
				urls.remove(0);
				visited.add(url);
				
				//String content = HTMLTool.fetchURL(url, "gb2312", "post");
				String content = Tool.fetchURL(url);
				if (content == null)
				{
					continue;
				}
				try {
					parser.setInputHTML(content);
					parser.setEncoding("gb2312");
					System.out.println(content);
					HasParentFilter parentFilter1 = new HasParentFilter(new AndFilter(new TagNameFilter("div"), new HasParentFilter(new HasAttributeFilter("class", "nlcd_name"))));
					NodeFilter filter = new AndFilter(new TagNameFilter("a"),parentFilter1); 
					NodeList nodes = parser.extractAllNodesThatMatch(filter);
					if(nodes.size()!=0){
						for(int i=0;i<nodes.size();i++){
							TagNode no=(TagNode) nodes.elementAt(i);
							String href=no.getAttribute("href");
							String title=no.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "");
							System.out.println(title+":"+href);
						}
					}
					
					int ss = content.indexOf("<strong class=\"f14 fb_blue\">");
					
					while (ss != -1)
					{

						int en = content.indexOf("</strong>", ss + "<strong class=\"f14 fb_blue\">".length());
						if (en != -1)
						{
							String sub = content.substring(ss, en);
							
							int rfs = sub.indexOf("href=\"");
							if (rfs != -1)
							{
								int rfe = sub.indexOf("\"", rfs + "href=\"".length());
								if (rfe != -1)
								{
									String purl = sub.substring(rfs + "href=\"".length(), rfe);
									System.out.println(purl);
									String poi = "";//parseHotRealEstate(purl);
									
									if (poi != null)
									{
										FileTool.Dump(poi, FOLDER+"hotRealEstate.txt", "UTF-8");
										System.out.println(poi);
									}
								else
									break;
							}
							else
								break;
						}
						else
							break;
						
						ss = content.indexOf("<strong class=\"f14 fb_blue\">", en + "</strong>".length());
						
						try {
							Thread.sleep(500 * ((int) (Math
								.max(1, Math.random() * 3))));
						} catch (final InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
			
					}
	                filter = new AndFilter(new TagNameFilter("div"), new HasParentFilter(new HasAttributeFilter("class", "searchListPage"))); 
					nodes = parser.extractAllNodesThatMatch(filter);
					if (nodes != null)
					{

						for (int nn = 0; nn < nodes.size(); nn ++)
						{
							Node ni = nodes.elementAt(nn);
							NodeList cld = ni.getChildren();
							if (cld != null)
							{
								for (int kkk = 0; kkk < cld.size(); kkk ++)
								{
									if (cld.elementAt(kkk) instanceof TagNode)
									{
										String href = ((TagNode)cld.elementAt(kkk)).getAttribute("href");
										if (href != null)
										{
											if (!href.startsWith("http://"))
											{
												if (href.startsWith("/house"))
													href = "http://newhouse.cq.fang.com/" + href;
												else
													continue;
											}
											
											if (!visited.contains(href))
											{
												int kk = 0;
												for (; kk < urls.size(); kk ++)
												{
													if (urls.elementAt(kk).equalsIgnoreCase(href))
													{
														break;
													}
												}
												
												if (kk == urls.size())
													urls.add(href);
											}
										}
									}
								}
							}						
						}
					}
					
				}catch (ParserException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
			}
		
	 }
}
