package com.svail.chongqing;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasChildFilter;
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

public class WaterShutoffNotice {
	
	public static String FOLDER="D:/重庆基础数据抓取/基础数据/停水通知/";
	
	public static void main(String[] args) throws IOException {
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
            	run();
            }  
        };  
    	service.scheduleAtFixedRate(trafficTask, initDelay, 24* 60 * 60 * 1000, TimeUnit.MILLISECONDS);//scheduleAtFixedRate(TimerTask task,long delay,long period) 方法用于安排指定的任务进行重复的固定速率执行，在指定的延迟后开始。
      }
	public static void run(){
		String path="";
		for(int i=0;i<=10;i++){
			path="http://www.cq966886.com/wsfw.php?cid=49&page="+i;
			importMongoDB(path,FOLDER);	
			
			System.out.println("完成了第"+i+"页数据抓取");
		}
	}
	
	public static void importMongoDB(String link,String folder){

		try {
			Mongo mongo = new Mongo("192.168.6.9", 27017);
			DB db = mongo.getDB("chongqing");  // 数据库名称
			
			
			DBCollection coll = db.getCollection("WaterShutoffNotice");
			//coll.drop();//清空表
			
			try {
				   List<BasicDBObject> objs = getWaterShutoffNotice(link,folder);
				   if(objs.size()!=0){
					   int count=0;
					   for(int i=0;i<objs.size();i++){
						   BasicDBObject obj=objs.get(i);
						   
						   BasicDBObject index=new BasicDBObject();
						   Object news=obj.get("href");
						   index.put("href", news);
						   
						   DBCursor rls =coll.find(index);
						   int size=rls.size();
						  // for(int m=0;m<size;i++){
							//   System.out.println( rls.hasNext());
						 //  }
						   
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
	

	public static void waterNotice(JSONObject obj,BasicDBObject document,String url,String folder){
			try {
				String content = Tool.fetchURL(url);
				//content = HTMLTool.fetchURL(url, "utf-8", "get");
				//System.out.println(content);
				Parser parser= new Parser();
				
				if (content == null) {
					FileTool.Dump(url, folder + "-Null.txt", "utf-8");
				} else {
					parser.setInputHTML(content);
					parser.setEncoding("utf-8");

					NodeFilter filter = new AndFilter(new TagNameFilter("td"),new HasAttributeFilter("class", "content_line"));
					NodeList nodes = parser.extractAllNodesThatMatch(filter);
					if (nodes.size() != 0) {
						for (int n = 0; n < nodes.size(); n++) {
							TagNode no = (TagNode) nodes.elementAt(n);					
							String str=no.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "");
							obj.put("news_content",str);
							document.put("news_content",str);
							
						}
					}
				}

			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {
				System.out.println(e.getMessage());
				FileTool.Dump(url, folder + "NullLink.txt", "utf-8");
			}
	}
	public static List<BasicDBObject> getWaterShutoffNotice(String url,String folder){
		
		List<BasicDBObject> objs = new ArrayList<BasicDBObject>();
		
			try {
				String content = Tool.fetchURL(url);
				//content = HTMLTool.fetchURL(url, "utf-8", "get");
				//System.out.println(content);
				Parser parser= new Parser();
				if (content == null) {
					FileTool.Dump(url, folder + "-Null.txt", "utf-8");
				} else {
					parser.setInputHTML(content);
					parser.setEncoding("utf-8");

				    HasParentFilter parentFilter4 = new HasParentFilter(new AndFilter(new TagNameFilter("td"),new HasAttributeFilter("class", "list2")));	   
				    NodeFilter filter = new AndFilter(new TagNameFilter("a"),parentFilter4);
                    NodeList nodes = parser.extractAllNodesThatMatch(filter);
					if (nodes.size() != 0) {
						
						
						for (int n = 0; n < nodes.size(); n++) {
							
							JSONObject obj=new JSONObject();
							BasicDBObject document=new BasicDBObject();
							
							TagNode no = (TagNode) nodes.elementAt(n);
							String str=no.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "").replace("・", "");
							obj.put("title", str);
							document.put("title", str);
							String tur = "http://www.cq966886.com/"+no.getAttribute("href").replace("&amp;", "&");
							obj.put("href", tur);
							document.put("href", tur);
							
							waterNotice(obj,document,tur,folder);
							
							
							Parser parser_html= new Parser();
							parser_html.setInputHTML(content);
							parser_html.setEncoding("utf-8");
							
							NodeFilter filter_html = new AndFilter(new TagNameFilter("td"),new HasAttributeFilter("class", "text3"));
							NodeList nodes_html = parser_html.extractAllNodesThatMatch(filter_html);
							if(nodes_html.size()!=0){
								if(nodes_html.elementAt(n)!=null){
									TagNode no_html = (TagNode) nodes_html.elementAt(n);
									String str_html=no_html.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "");
									obj.put("notice_time",str_html);
									document.put("notice_time",str_html);
								}	
							}
							
							checkMissed(obj,document);
							
							Date d = new Date();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
							obj.put("crawl_time", sdf.format(d));
							document.put("crawl_time", sdf.format(d));
							
							System.out.println(obj);
							objs.add(document);
							FileTool.Dump(obj.toString(), folder+"noWaterNotice.txt", "utf-8");
							
							try {
								Thread.sleep(500 * ((int) (Math
									.max(1, Math.random() * 3))));
							} catch (final InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						   
						}
					}
				}

			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {
				System.out.println(e.getMessage());
				FileTool.Dump(url, folder + "NullLink.txt", "utf-8");
			}

		return objs;
	}
	
	public static void checkMissed(JSONObject obj,BasicDBObject document){
		if(!obj.containsKey("title")){
			obj.put("title", "");
			document.put("title", "");
		}
		if(!obj.containsKey("href")){
			obj.put("href", "");
			document.put("href", "");
		}
		if(!obj.containsKey("news_content")){
			obj.put("news_content", "");
			document.put("news_content", "");
		}
		if(!obj.containsKey("notice_time")){
			obj.put("notice_time", "");
			document.put("notice_time", "");
		}
	}

}
