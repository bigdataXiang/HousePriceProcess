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
import com.svail.util.Tool;

import net.sf.json.JSONObject;

public class HighSpeedTraffic {
	

	public static void main(String[] args) throws IOException {
		timingCrawl("18:15:00");
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
            	HighSpeedTraffic.run();
            }  
        };  
    	service.scheduleAtFixedRate(trafficTask, initDelay, 60 * 60 * 1000, TimeUnit.MILLISECONDS);//scheduleAtFixedRate(TimerTask task,long delay,long period) 方法用于安排指定的任务进行重复的固定速率执行，在指定的延迟后开始。    
	}
	
	public static void run(){
		importMongoDB("http://e.t.qq.com/jtzfzd");
	}
	public static void importMongoDB(String link){

		try {
			Mongo mongo = new Mongo("192.168.6.9", 27017);
			DB db = mongo.getDB("chongqing");  // 数据库名称
			
			
			DBCollection coll = db.getCollection("HighSpeedTraffic");
			//coll.drop();//清空表
			
			try {
				   List<BasicDBObject> objs = getTrafficInfo(link);
				   if(objs.size()!=0){
					   int count=0;
					   for(int i=0;i<objs.size();i++){
						   BasicDBObject obj=objs.get(i);
						   
						   BasicDBObject index=new BasicDBObject();
						   Object news=obj.get("news");
						   index.put("news", news);
						   
						   DBCursor rls =coll.find(index);
						   if(rls == null || rls.size() == 0){
							   coll.insert(obj);
							   count++;
						   }else{
							   System.out.println("该数据已经存在!");
						   }
					   }
					   System.out.println("导入"+count+"条数据！");
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
	 
	public static List<BasicDBObject> getTrafficInfo(String url){

		List<BasicDBObject> objs = new ArrayList<BasicDBObject>();
			try {
				String content = Tool.fetchURL(url);
				//content = HTMLTool.fetchURL(url, "utf-8", "get");
				//System.out.println(content);
				Parser parser= new Parser();
				
				if (content == null) {

				} else {
					parser.setInputHTML(content);
					parser.setEncoding("utf-8");
			    
				    NodeFilter filter = new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "msgBox"));
					NodeList nodes = parser.extractAllNodesThatMatch(filter);
					if (nodes.size() != 0) {											
						for (int n = 0; n < nodes.size(); n++) {
							TagNode no = (TagNode) nodes.elementAt(n);
							String html=no.toHtml();
							//System.out.println();
							Parser parser_html= new Parser();
							parser_html.setInputHTML(html);
							parser_html.setEncoding("utf-8");
							
							JSONObject obj=new JSONObject();
							BasicDBObject document=new BasicDBObject();
							
							HasParentFilter parentFilter_html = new HasParentFilter(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "msgBox")));
							NodeFilter filter_html = new AndFilter(new TagNameFilter("div"),new AndFilter(parentFilter_html,new HasAttributeFilter("class", "userName")));
							NodeList nodes_html = parser_html.extractAllNodesThatMatch(filter_html);
							if(nodes_html.size()!=0){
                               	for(int i=0;i<nodes_html.size();i++){
									TagNode no_html = (TagNode) nodes_html.elementAt(i);
									String str_html=no_html.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "");
									//System.out.println(str_html);
									if(str_html.indexOf("重庆市交通行政执法总队")!=-1){
										
										String id=str_html.replace(":", "");
										obj.put("id", id);
										document.put("id", id);
									}
                               	}
							}
							
							parser_html.reset();
							parentFilter_html = new HasParentFilter(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "msgBox")));
							filter_html = new AndFilter(new TagNameFilter("div"),new AndFilter(parentFilter_html,new HasAttributeFilter("class", "pubInfo c_tx5")));
							nodes_html = parser_html.extractAllNodesThatMatch(filter_html);
							if(nodes_html.size()!=0){
								for(int i=0;i<nodes_html.size();i++){
									TagNode no_html = (TagNode) nodes_html.elementAt(i);
									String str_html=no_html.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "");
									
									if(str_html.indexOf("来自腾讯微博")!=-1){
										String time=str_html.replace("来自腾讯微博", "").replace("转播", "").replace("评论", "").replace("更多", "").replace("|", "").replace("全部", "").replace("和", "");
										
										if (time != null)
										{
											int ss = time.indexOf("(");
											while (ss != -1)
											{
												int ee = time.indexOf(")", ss + 1);
												if (ee != -1)
												{
													String sub = time.substring(ss, ee + 1);
													time = time.replace(sub, "");
												}
												else
													break;
												ss = time.indexOf("(", ss);
											}
										}
										
										obj.put("time", time);
										document.put("time", time);
									}
								}
							}
							
							parser_html.reset();
							parentFilter_html = new HasParentFilter(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "msgBox")));
							filter_html = new AndFilter(new TagNameFilter("div"),new AndFilter(parentFilter_html,new HasAttributeFilter("class", "msgCnt")));
							nodes_html = parser_html.extractAllNodesThatMatch(filter_html);
							if(nodes_html.size()!=0){
								for(int i=0;i<nodes_html.size();i++){
									TagNode no_html = (TagNode) nodes_html.elementAt(i);
									String str_html=no_html.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "");
									String news=str_html;
									obj.put("news", news);
									document.put("news", news);
									
								}
							}
							
							checkMissed(obj,document);
							
							Date d = new Date();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
							obj.put("crawl_time", sdf.format(d));
							document.put("crawl_time", sdf.format(d));							
							
							System.out.println(obj);
							
							objs.add(document);

							
							try {
								Thread.sleep(5000 * ((int) (Math
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
			}
			
			return objs;
	
	}
	/**
	 * 检查json中是否包含了所有字段，没包含的要赋""
	 * @param obj
	 * @param document
	 */
	public static void checkMissed(JSONObject obj,BasicDBObject document){
		if(!obj.containsKey("id")){
			obj.put("id", "");
			document.put("id", "");
		}
		if(!obj.containsKey("time")){
			obj.put("time", "");
			document.put("time", "");
		}
		if(!obj.containsKey("news")){
			obj.put("news", "");
			document.put("news", "");
		}
	}

}
