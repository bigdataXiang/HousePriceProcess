package com.svail.houseprice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import net.sf.json.JSONException;
//import org.json.JSONException;
//import org.json.JSONObject;
import net.sf.json.JSONObject;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.svail.gridprocess.GridLayoutLines;
import com.svail.util.FileTool;
import com.svail.util.HTMLTool;
import com.svail.util.Tool;

public class Houseprice {
	public static String longitude;
	public static String latitude;
	public static String region;
	public static String title;
	public static String time;
	public static String price;
	public static String area;
	public static double unit_price;
	public static String location;
	public static String community ;
	public static String address ;
	public static String property;
	public static String pay_way;
	public static String house_type;
	public static String rent_type;
	public static String fitment ;
	public static String direction ;
	public static String floor ;
	public static String totalarea;
	public static String developer;
	public static String property_fee;
	public static String households;
	public static String built_year;
	public static String volume_rate;
	public static String green_rate;
	public static String park;
	public static String heat_supply;
	public static String traffic ;
	public static String url ;
	public static String property_company;
	public static String checkin;
	public static String facility;
	public static String contact;
	public static String notice;
	public static String structure;
	public static String character;
	public static String biult_type;
	
	public static String  loop;
	public static String  sales_address;
	public static String  open_time;
	public static String  completed_time;
	public static String serve_address;
	public static String licence;
	public static String down_payment;


	public static void main(String[] args){
		
		//getTimeSeriesPrice();		
		//addData("E:/房地产可视化/近一年数据分类汇总/anjuke/resold/anjuke_resold0726_0801 (2).txt",                               //"D:/Crawldata_BeiJing/忘却的那两个月/fang_resold/"+"fang_resold1117_Null_result.txt",
			//	"E:/房地产可视化/近一年数据分类汇总/anjuke/resold/anjuke_resold0726_0801.txt");
		
		
		
		//yearMonthDay("H:/房地产可视化/近一年数据分类汇总/fang/resold/json/fang_resold0606-Json.txt");
		
		finalCheck("E:/房地产可视化/近一年数据分类汇总/5i5j/resold/woaiwojia_resold0314_result.txt-Json.txt");
		
	}
	   public static void finalCheck(String folder){
	    	Vector<String> pois=FileTool.Load(folder, "utf-8");
	    	//Vector<String> sources=FileTool.Load(source, "utf-8");
	    	System.out.println("数据总计："+pois.size());
	    	//System.out.println("source总计："+sources.size());
			int count=0;
			
			for(int i=0;i<pois.size();i++){
				String poi=pois.elementAt(i);
				//System.out.println(i);
				JSONObject obj_poi=JSONObject.fromObject(poi);
				JSONObject obj= new JSONObject();
				//String sourcepoi=sources.elementAt(i).replace(" ", "");
				JSONObject date= new JSONObject();
				
				//检查哪些元素的内容为null，为null的移除,再将整个obj_poi复制到obj上
				Iterator<String> joKeys = obj_poi.keys();  
		        while(joKeys.hasNext()){  
		            String key = joKeys.next();
		           // System.out.println(key);
		            
		            String value=obj_poi.get(key).toString();
		            
		           if(value.equals("null")){ //\"null\".equals("null")
		        	   //System.out.println("value为空");
		            }else{
		            	Tool.delect_content_inBrackets(value, "(", ")");
		            	obj.put(key, value);
		            }
		        } 
				
				//将cmmunity改成community
				if(!obj.containsKey("community")){
					if(obj.containsKey("cmmunity")){
						String community=obj.getString("cmmunity");
						obj.put("community", community);
						obj.remove("cmmunity");
					}					
				}
/*				
				//将错位信息更正
				String str=obj.getString("direction");
				if(str.indexOf("层")!=-1){
					obj.put("floor", str);
				}
				
				str=obj.getString("fitment");
				if(str.indexOf("东")!=-1||str.indexOf("南")!=-1||str.indexOf("西")!=-1||str.indexOf("北")!=-1){
					obj.put("direction", str);
					obj.remove("fitment");
				}
*/				
				//将实际发布时间的年月日分别进行获取
				String time="";
				try{
					if(obj.containsKey("time")){
						if(!obj.get("time").equals("null")){
							String[] dates= new String[3];
							time=obj.getString("time");
							
							if(time.indexOf("/")!=-1){
								
								dates=time.split("/");
								
							}else if(time.indexOf("-")!=-1){						
								
								dates=time.split("-");
							}
							
			               String year=dates[0];
			               date.put("year", year);
			               String month=dates[1];
			               date.put("month", month);
			               String day=dates[2].substring(0, 2);
			               date.put("day", day);  
			            
			              obj.put("date", date);
						}
						
					}
				}catch(NullPointerException e){
					e.getStackTrace();
					System.out.println(i);
				}
				
				
				//检查是否含有“unit_price”字段
				try{
					//if(!obj.containsKey("unit_price")){
					if(obj.containsKey("price")&&obj.containsKey("area")){
						if(!obj.getString("price").equals("null")&&!obj.getString("area").equals("null")){
							String price=obj.getString("price").replace("万元", "");//.substring(0, obj.getString("price").indexOf("元/月"))
							obj.put("price", price);
							String area=obj.getString("area").replace("�", "");
							obj.put("area", area);
							if(area.indexOf("层")==-1){
								double unit_price=Double.parseDouble(price)/Double.parseDouble(area);
								obj.put("unit_price", unit_price);
							}else{
								obj.put("floor", unit_price);
							}
							
						}
						
					}
				}catch(NumberFormatException e){
					e.getStackTrace();
					System.out.println(i);
				}
				
/*				
				//检查是否含有“region”字段
				if(obj.containsKey("region")){
					if(obj.getString("region").equals("null")){
						String region=Tool.getStrByKey(sources.elementAt(i), "<PostReg>", "</PostReg>", "</PostReg>");
						obj.put("region", region);
					}
				}
				
				//检查是否含有“property”字段
				if(obj.containsKey("property")){
					if(obj.getString("property").equals("null")){
						String property="";
						if(sourcepoi.indexOf("<BUILDING_TYPE>")!=-1){
							property=Tool.getStrByKey(sourcepoi, "<BUILDING_TYPE>", "</BUILDING_TYPE>", "</BUILDING_TYPE>");
						}
						obj.put("property", property);
					}
				}
*/
				
				
				//提取户型的具体数据
				String house_type="";
				String rooms="";
				String halls="";
				String kitchen="";
				String bathrooms="";

				JSONObject layout=new JSONObject();
				if(obj.containsKey("house_type")){
					house_type=obj.getString("house_type");//1室1厅1厨1卫  2室2厅1卫
					
					if(house_type.indexOf("室")!=-1){
						rooms=house_type.substring(0, house_type.indexOf("室"));
						layout.put("rooms", rooms);
					}else{
						layout.put("rooms", rooms);
					}
					if(house_type.indexOf("厅")!=-1){
						halls=house_type.substring(house_type.indexOf("室")+"室".length(), house_type.indexOf("厅"));
						layout.put("halls", halls);
					}else{
						layout.put("halls", halls);
					}
					if(house_type.indexOf("厨")!=-1){
						kitchen=house_type.substring(house_type.indexOf("厅")+"厅".length(), house_type.indexOf("厨"));
						layout.put("kitchen", kitchen);
					}else{
						layout.put("kitchen", kitchen);
					}
					if(house_type.indexOf("卫")!=-1){
						if(house_type.indexOf("厨")!=-1){
							bathrooms=house_type.substring(house_type.indexOf("厨")+"厨".length(), house_type.indexOf("卫"));
							layout.put("bathrooms", bathrooms);
						}else{
							bathrooms=house_type.substring(house_type.indexOf("厅")+"厅".length(), house_type.indexOf("卫"));
							layout.put("bathrooms", bathrooms);
						}
						
					}else{
						layout.put("bathrooms", bathrooms);
					}
					
				}else{
					//System.out.println(i+":"+poi);
				}
				obj.put("layout", layout);
				
				//获取具体层高数据
				//第59层(共60层)  10/28层
				if(obj.containsKey("floor")){
					JSONObject storeys= new JSONObject();
					String floor=obj.getString("floor");
					if(floor.indexOf("第")!=-1&&floor.indexOf("(")!=-1&&floor.indexOf(")")!=-1){
						String flooron=floor.substring(0, floor.indexOf("(")).replace("第", "").replace("层", "");
						String floors=floor.substring(floor.indexOf("(")+"(".length(),floor.indexOf(")")).replace("共", "").replace("层", "");
						storeys.put("flooron", flooron);
						storeys.put("floors", floors);
					}else if(floor.indexOf("/")!=-1){
						String flooron=floor.substring(0, floor.indexOf("/")).replace("层", "");
						String floors=floor.substring(floor.indexOf("/")+"/".length()).replace("层", "").replace("共", "");
						storeys.put("flooron", flooron);
						storeys.put("floors", floors);
					}
					obj.put("storeys", storeys);
				}
				
				
/*	
				//获取交通信息
				String traffic="";
				if(sources.elementAt(i).indexOf("<TRAFFIC>")!=-1){
					traffic=Tool.getStrByKey(sources.elementAt(i), "<TRAFFIC>", "</TRAFFIC>", "</TRAFFIC>");
					obj.put("traffic", traffic);
				}
*/

				FileTool.Dump(obj.toString(), folder.replace(".txt", "")+"-tidy.txt", "utf-8");
				count++;
			}
			System.out.println("数据完成："+count);
			
		}
	public static void getUnitPrice(String folder){
		Vector<String> pois=FileTool.Load(folder, "utf-8");
		for(int i=0;i<pois.size();i++){
			String poi=pois.elementAt(i);
			JSONObject obj=JSONObject.fromObject(poi);
			String price=obj.getString("price");
			String area=obj.getString("area");
			double unit_price=Double.parseDouble(price)/Double.parseDouble(area);
			obj.put("unit_price", unit_price);
		}
	}
	
	/**
	 * 将sourcefile文件中的数据添加到targetfile中
	 * @param sourcefile ： 需要被添加的数据
	 * @param targetfile ： 数据被添加的目标文件
	 */
	public static void addData(String sourcefile,String targetfile){
		Vector<String> pois=FileTool.Load(sourcefile, "utf-8");
		for(int i=0;i<pois.size();i++){
			String poi=pois.elementAt(i);
			FileTool.Dump(poi, targetfile, "utf-8");
		}
		System.out.println("新增了"+pois.size()+"条数据");
	}
 
	public static void yearMonthDay(String folder){
		
		Vector<String> pois=FileTool.Load(folder, "utf-8");
		
		System.out.println("数据总计："+pois.size());
		int count=0;
		for(int i=0;i<pois.size();i++){
			
			String poi=pois.elementAt(i);
			JSONObject obj=JSONObject.fromObject(poi);
			JSONObject date= new JSONObject();
			
			String time="";
			if(obj.containsKey("time")){
				time=obj.getString("time");
				String[] dates= new String[3];				
				
				if(time.indexOf("售价：")!=-1&&time.indexOf("参考首付")!=-1){
					String str=time.substring(time.indexOf("售价：")+"售价：".length(),time.indexOf("参考首付："));
					if(str.indexOf("万")!=-1){
						String str1=str.substring(0, str.indexOf("万"));
						obj.put("price", str1);
					}
					if(str.indexOf("）")!=-1&&str.indexOf("（")!=-1){
						String str2=str.substring(str.indexOf("（")+"（".length(), str.indexOf("）")).replace("元/�", "元/平米");
						obj.put("unit_price", str2);
					}
					
				}
				
				if(time.indexOf("/")!=-1){
					if(time.indexOf("售价")!=-1){
						time=time.substring(0, time.indexOf("售价"));
						obj.put("time", time);
					}
					dates=time.split("/");
					
				}else if(time.indexOf("-")!=-1){
					if(time.indexOf("售价")!=-1){
						time=time.substring(0, time.indexOf("售价"));
						obj.put("time", time);
					}
					dates=time.split("-");
				}
				
            String year=dates[0];
            date.put(" year", year);
            String month=dates[1];
            date.put(" month", month);
            String day=dates[2];
            date.put(" day", day);                           
                               
			}else{
				System.out.println(i+":"+poi);
			}
			obj.put("date", date);
			
			String house_type="";
			String rooms="";
			String halls="";
			String kitchen="";
			String bathrooms="";

			JSONObject layout=new JSONObject();
			if(obj.containsKey("house_type")){
				house_type=obj.getString("house_type");//1室1厅1厨1卫
				
				if(house_type.indexOf("室")!=-1){
					rooms=house_type.substring(0, house_type.indexOf("室"));
					layout.put("rooms", rooms);
				}else{
					layout.put("rooms", rooms);
				}
				if(house_type.indexOf("厅")!=-1){
					halls=house_type.substring(house_type.indexOf("室")+"室".length(), house_type.indexOf("厅"));
					layout.put("halls", halls);
				}else{
					layout.put("halls", halls);
				}
				if(house_type.indexOf("厨")!=-1){
					kitchen=house_type.substring(house_type.indexOf("厅")+"厅".length(), house_type.indexOf("厨"));
					layout.put("kitchen", kitchen);
				}else{
					layout.put("kitchen", kitchen);
				}
				if(house_type.indexOf("卫")!=-1){
					if(house_type.indexOf("厨")!=-1){
						bathrooms=house_type.substring(house_type.indexOf("厨")+"厨".length(), house_type.indexOf("卫"));
						layout.put("bathrooms", bathrooms);
					}else{
						bathrooms=house_type.substring(house_type.indexOf("厅")+"厅".length(), house_type.indexOf("卫"));
						layout.put("bathrooms", bathrooms);
					}
					
				}else{
					layout.put("bathrooms", bathrooms);
				}
				
			}else{
				System.out.println(i+":"+poi);
			}
			obj.put("layout", layout);
			
			
/*			
			String price="";
			String area="";
			if(obj.containsKey("price")){
				price=obj.getString("price").replace("万", "");
				obj.put("price", price);
			}
			if(obj.containsKey("area")){
				area=obj.getString("area").replace("建筑面积：", "").replace("�", "");
				obj.put("area", area);
			}
			
			double unit_price=Double.parseDouble(price)/Double.parseDouble(area);
			obj.put("unit_price", unit_price);
*/
			
			//System.out.println(i);
			
			FileTool.Dump(obj.toString().replace(" ", ""), folder.replace(".txt", "")+"-tidy.txt", "utf-8");
			count++;
		}
		System.out.println("数据处理："+count);
	}
	
	//E:\数据备份\Data_Classify\finish_classify\fang\fang_result\fang_rentout   中有7-9月的原文件
	public static void getTimeSeriesPrice(){
		String folder="E:/房地产可视化/近一年数据分类汇总/anjuke/resold/";
		String[] date={
                 "0530","0620","0704","0711","0718","0725","0801","0808","0815","0822","0829","0905","0912","0919",
				 };
		// "1125","1201","1207","1214","1222","1231","0108","0119","0127","0219","0227","0309","0314",
		//"0324","0401","0414","0428","0509","0510","0523","0527","0603" 
		/*	    "2014_10","2014_11","2014_12",
				"2015_01","2015_02","2015_03","2015_04","2015_05","2015_06","2015_07","2015_08","2015_09","2015_10","2015_11","2015_12",
				"2016_01","2016_02","2016_03","2016_04","2016_05","2016_06"
		 * 
		 */
		String type="anjuke_resold";
		String path="";//folder+type+date[0]+"_result.txt";
		//toJson(path,false,"2015-"+date[0].substring(0, 2)+"-"+date[0].substring(2));
		
		
			for(int i=0;i<date.length;i++){//date.length
				path=folder+type+date[i]+".txt";
				if(i>0){
					if(i>0){
						toJson(path,false,"2015-"+date[i].substring(0, 2)+"-"+date[i].substring(2));//"2015-"+date[i].substring(0, 2)+"-"+date[i].substring(2)
					}else{
						//toJson(path,false,"2016-"+date[i].substring(0, 2)+"-"+date[i].substring(2));//"2016-"+date[i].substring(0, 2)+"-"+date[i].substring(2)
					}
					
				}else{
					if(i<6){
						toJson(path,true,"2015-"+date[i].substring(0, 2)+"-"+date[i].substring(2));//"2016-"+date[i].substring(0, 2)+"-"+date[i].substring(2)
					}else{
						toJson(path,true,"2016-"+date[i].substring(0, 2)+"-"+date[i].substring(2));
					}
					
				}
				
				System.out.println("完成第"+(i+1)+"个文件的处理");
			}
			
		
	}
	/**
	 * 将文本格式的数据转成json格式
	 * @param folder
	 */
	public static void toJson(String folder,boolean json,String date){
		Vector<String> pois=FileTool.Load(folder, "utf-8");
		boolean fang=false;
		String poi="";
	try{
			int n=pois.size();
			for(int i=0;i<pois.size();i++){
				poi=pois.elementAt(i);
				JSONObject jsonObjArr = new JSONObject();
				
				if(json){
					jsonObjArr=JSONObject.fromObject(poi);
					jsonObjArr.put("crawldate",date);	
					//System.out.println(i+":"+jsonObjArr.toString());	
					FileTool.Dump(jsonObjArr.toString(), folder.replace(".txt", "") + "-Json.txt", "utf-8");
					
				}else{
					/*
					String[] array=poi.split(",");
					//[116.303968, 39.923392, 海淀双榆树1500一居, 2015/9/5 23:51, 1500, 暂无信息, 整租, 海淀，双榆树, 一居, 暂无资料, 18910954221搜房网友, 真实个人发布，有意向的朋友请尽快联系我, http://zu.fang.com/qiuzu/1_96191016_-1.htm]
					int lenth=array.length;
					if(array[0].length()>0&&array.length==9){
						jsonObjArr.put("longitude",array[0]);
						jsonObjArr.put("latitude",array[1]);
						jsonObjArr.put("title",array[2]);
						jsonObjArr.put("time",array[3]);
						jsonObjArr.put("price",array[4]);
						jsonObjArr.put("down_payment",array[5]);
						jsonObjArr.put("house_type",array[6]);
						//jsonObjArr.put("unit_price",array[5]);//含有#VALUE!值
						//jsonObjArr.put("property",array[6]);
						//jsonObjArr.put("community",array[6]);
						jsonObjArr.put("address",array[7]);
						//jsonObjArr.put("house_type",array[9]);
						//jsonObjArr.put("rent_type",array[10]);
						//jsonObjArr.put("area",array[11]);
						//jsonObjArr.put("direction",array[12]);
						//jsonObjArr.put("floor",array[13]);
						//jsonObjArr.put("fitment",array[14]);
						jsonObjArr.put("url",array[8]);
						jsonObjArr.put("crawldate",date);
					}else if(array[0].length()>0&&array.length==8){
						jsonObjArr.put("longitude",array[0]);
						jsonObjArr.put("latitude",array[1]);
						jsonObjArr.put("title",array[2]);
						jsonObjArr.put("time",array[3]);
						jsonObjArr.put("location",array[4]); 
						jsonObjArr.put("house_type",array[5]);
						jsonObjArr.put("price",array[6]);
						jsonObjArr.put("url",array[7]);
						jsonObjArr.put("crawldate",date);
					}
					*/
					
					
					//longitude :经度 latitude ：纬度
					if(poi.indexOf("<Coordinate>")!=-1){
						String[] coor=Tool.getStrByKey(poi, "<Coordinate>", "</Coordinate>", "</Coordinate>").split(";");
						longitude=coor[0];
						latitude=coor[1];
						jsonObjArr.put("longitude",longitude);
						jsonObjArr.put("latitude",latitude);
					}else if(poi.indexOf("<Coor>")!=-1&&poi.indexOf("<PostCoor>")==-1){
						String[] coor=Tool.getStrByKey(poi, "<Coor>", "</Coor>", "</Coor>").split(";");
						longitude=coor[0];
						latitude=coor[1];
						jsonObjArr.put("longitude",longitude);
						jsonObjArr.put("latitude",latitude);
					}else if(poi.indexOf("<coordinate>")!=-1){
						String[] coor=Tool.getStrByKey(poi, "<coordinate>", "</coordinate>", "</coordinate>").split(";");
						longitude=coor[0];
						latitude=coor[1];
						jsonObjArr.put("longitude",longitude);
						jsonObjArr.put("latitude",latitude);
					}else if(poi.indexOf("<PostCoor>")!=-1){
						String[] coor=Tool.getStrByKey(poi, "<PostCoor>", "</PostCoor>", "</PostCoor>").split(";");
						longitude=coor[0];
						latitude=coor[1];
						jsonObjArr.put("longitude",longitude);
						jsonObjArr.put("latitude",latitude);
					}
					
					//region：坐标所定位的行政区划层级
					if(poi.indexOf("<Reg>")!=-1){
						region=Tool.getStrByKey(poi, "<Reg>", "</Reg>", "</Reg>");
						jsonObjArr.put("region",region);
					}else if(poi.indexOf("<PostReg>")!=-1){
						region=Tool.getStrByKey(poi, "<PostReg>", "</PostReg>", "</PostReg>");
						jsonObjArr.put("region",region);
					}
					else{
						region="regionnull";
						jsonObjArr.put("region",region);
					}
					
					//title ：标题
					if(poi.indexOf("<TITLE>")!=-1&&poi.indexOf("</PostReg>>")==-1&&poi.indexOf("</Reg>LE>")==-1){
						title=Tool.getStrByKey(poi, "<TITLE>", "</TITLE>", "</TITLE>");
						jsonObjArr.put("title",title);
					}else if(poi.indexOf("TITLE")!=-1&&poi.indexOf("</PostReg>>")!=-1){
						title=Tool.getStrByKey(poi, "</PostReg>>", "</TITLE>", "</TITLE>");
						jsonObjArr.put("title",title);
					}else if(poi.indexOf("TITLE")!=-1&&poi.indexOf("</Reg>LE>")!=-1){
						title=Tool.getStrByKey(poi, "</Reg>LE>", "</TITLE>", "</TITLE>");
						jsonObjArr.put("title",title);
					}

					
					//time ：时间
					if(poi.indexOf("<TIME>")!=-1){
						time=Tool.getStrByKey(poi, "<TIME>", "</TIME>", "</TIME>");
						jsonObjArr.put("time",time);
					}
					
					//price ：总价或者新房均价
					if(poi.indexOf("<PRICE>")!=-1){
						price=Tool.getStrByKey(poi, "<PRICE>", "</PRICE>", "</PRICE>").replace("元/月","").replace("未知", "").replace("万", "").replace("万元", "");
						jsonObjArr.put("price",price);
					}
					
					//down_payment:最低首付
					if(poi.indexOf("<DOWN_PAYMENT>")!=-1){
						down_payment=Tool.getStrByKey(poi, "<PRICE>", "</PRICE>", "</PRICE>");
						jsonObjArr.put("down_payment",down_payment);
					}
					
					//area ：面积
					if(fang){
						url=Tool.getStrByKey(poi, "<URL>", "</URL>", "</URL>");
						area=getArea(url,folder).replace("平米", "").replace("m²", "").replace("平方米", "").replace("㎡", "").replace("O", "").replace("㎡", "").replace("建筑面积：", "");
						jsonObjArr.put("area",area);
						
					}else{
						if(poi.indexOf("<AREA>")!=-1&&poi.indexOf("HOUSE_AREA")==-1&&poi.indexOf("BUILDING_AREA")==-1){
							//String temp=poi.substring(0, poi.indexOf("</FLOOR>"));
							area=Tool.getStrByKey(poi, "<AREA>", "</AREA>", "</AREA>").replace("平米", "").replace("m²", "").replace("平方米", "").replace("㎡", "").replace("O", "").replace("建筑面积：", "");
							if(area.equals("0")){
								area="null";
							}
							jsonObjArr.put("area",area);
						}else if(poi.indexOf("<HOUSE_AREA>")!=-1){
							area=Tool.getStrByKey(poi, "<HOUSE_AREA>", "</HOUSE_AREA>", "</HOUSE_AREA>").replace("�", "").replace("平米", "").replace("m²", "").replace("平方米", "").replace("㎡", "").replace("O", "").replace("建筑面积：", "");
							jsonObjArr.put("area",area);
						}else if(poi.indexOf("<BUILDING_AREA>")!=-1){
							area=Tool.getStrByKey(poi, "<BUILDING_AREA>", "</BUILDING_AREA>", "</BUILDING_AREA>").replace(".㎡", "").replace("�", "").replace("平米", "").replace("m²", "").replace("平方米", "").replace("㎡", "").replace("O", "").replace("建筑面积：", "");
							jsonObjArr.put("area",area);
						}
					}
					
					//unit_price ：每平方米单价
					if(poi.indexOf("<UNIT_PRICE>")!=-1){
						String unitprice=Tool.getStrByKey(poi, "<UNIT_PRICE>", "</UNIT_PRICE>", "</UNIT_PRICE>").replace("元/平米", "");
						unit_price=Double.parseDouble(unitprice);
						jsonObjArr.put("unit_price",unit_price);
					}else if(!(price.equals("null"))&&!(area.equals("null"))){
						unit_price=Double.parseDouble(price)/Double.parseDouble(area);
						jsonObjArr.put("unit_price",unit_price);
					}else{
						unit_price=0;
						jsonObjArr.put("unit_price",unit_price);
					}
					
					//community ：所在小区
					if(poi.indexOf("<COMMUNITY>")!=-1){
						community=Tool.getStrByKey(poi, "<COMMUNITY>", "</COMMUNITY>", "<COMMUNITY>");
						jsonObjArr.put("community",community);
					}else if(poi.indexOf("<BUILDING_NAME>")!=-1){
						community=Tool.getStrByKey(poi, "<BUILDING_NAME>", "</BUILDING_NAME>", "<BUILDING_NAME>");
						jsonObjArr.put("community",community);
					}

					//location：所在区域
					if(poi.indexOf("<LOCATION>")!=-1){
						location=Tool.getStrByKey(poi, "<LOCATION>", "</LOCATION>", "</LOCATION>");
						jsonObjArr.put("location",location);
					}else if(poi.indexOf("<REGION>")!=-1){
						location=Tool.getStrByKey(poi, "<REGION>", "</REGION>", "</REGION>");
						jsonObjArr.put("location",location);
					}else if(poi.indexOf("<DISTRICT>")!=-1){
						location=Tool.getStrByKey(poi, "<DISTRICT>", "</DISTRICT>", "</DISTRICT>");
						jsonObjArr.put("location",location);
					}
					
					//address ：地址
					if(poi.indexOf("<ADDRESS>")!=-1){
						address=Tool.getStrByKey(poi, "<ADDRESS>", "</ADDRESS>", "</ADDRESS>").replace("(地图)", "");
						jsonObjArr.put("address",address);
					}
					
					//developer：开发商
					if(poi.indexOf("<DEVELOPER>")!=-1){
						developer=Tool.getStrByKey(poi, "<DEVELOPER>", "</DEVELOPER>", "</DEVELOPER>");
						jsonObjArr.put("developer",developer);
					}
					
					//property_company:物业公司
					if(poi.indexOf("<PROPERTY>")!=-1){
						property_company=Tool.getStrByKey(poi, "<PROPERTY>", "</PROPERTY>", "</PROPERTY>");
						jsonObjArr.put("property_company",property_company);
					}else if(poi.indexOf("<SERVER>")!=-1){
						property_company=Tool.getStrByKey(poi, "<SERVER>", "</SERVER>", "</SERVER>");
						jsonObjArr.put("property_company",property_company);
					}
					
					//property：房屋性质（住宅之类的）
					if(poi.indexOf("<PROPERTY_TYPE>")!=-1){
						property=Tool.getStrByKey(poi, "<PROPERTY_TYPE>", "</PROPERTY_TYPE>", "</PROPERTY_TYPE>");
						jsonObjArr.put("property",property);
					}else if(poi.indexOf("<TYPE>")!=-1){
						property=Tool.getStrByKey(poi, "<TYPE>", "</TYPE>", "</TYPE>");
						jsonObjArr.put("property",property);
					}else if(poi.indexOf("<BUILDING_TYPE>")!=-1){
						property=Tool.getStrByKey(poi, "<BUILDING_TYPE>", "</BUILDING_TYPE>", "</BUILDING_TYPE>");
						jsonObjArr.put("property",property);
					}else if(poi.indexOf("<BUILDING_USAGE>")!=-1){
						property=Tool.getStrByKey(poi, "<BUILDING_USAGE>", "</BUILDING_USAGE>", "</BUILDING_USAGE>");
						jsonObjArr.put("property",property);
					}
					
					//property_fee:物业费
					if(poi.indexOf("<PROPERTY_FEE>")!=-1){
						property_fee=Tool.getStrByKey(poi, "<PROPERTY_FEE>", "</PROPERTY_FEE>", "</PROPERTY_FEE>");
						jsonObjArr.put("property_fee",property_fee);
					}
					
					
					//house_type：户型
					if(poi.indexOf("<HOUSE_TYPE>")!=-1){
						house_type=Tool.getStrByKey(poi, "<HOUSE_TYPE>", "</HOUSE_TYPE>", "</HOUSE_TYPE>");
						jsonObjArr.put("house_type",house_type);
					}else if(poi.indexOf("<TYPE>")!=-1){
						house_type=Tool.getStrByKey(poi, "<TYPE>", "</TYPE>", "</TYPE>");
						jsonObjArr.put("house_type",house_type);
					}else if(poi.indexOf("<BUILDTYPE>")!=-1){
						house_type=Tool.getStrByKey(poi, "<BUILDTYPE>", "</BUILDTYPE>", "</BUILDTYPE>");
						jsonObjArr.put("house_type",house_type);
					}
					
					//direction ：朝向
					if(poi.indexOf("<DIRECTION>")!=-1){
						direction=Tool.getStrByKey(poi, "<DIRECTION>", "</DIRECTION>", "</DIRECTION>");
						jsonObjArr.put("direction",direction);
					}else if(poi.indexOf("<ORIENTATION>")!=-1){
						direction=Tool.getStrByKey(poi, "<ORIENTATION>", "</ORIENTATION>", "</ORIENTATION>");
						jsonObjArr.put("direction",direction);
					}else if(poi.indexOf("<BUILDING_DIR>")!=-1){
						direction=Tool.getStrByKey(poi, "<BUILDING_DIR>", "</BUILDING_DIR>", "</BUILDING_DIR>");
						jsonObjArr.put("direction",direction);
					}
					
					//floor ：楼层
					if(poi.indexOf("<FLOOR>")!=-1&&poi.indexOf("BUILDING_FLOOR")==-1){
						floor=Tool.getStrByKey(poi, "<FLOOR>", "</FLOOR>", "</FLOOR>");
						jsonObjArr.put("floor",floor);
					}else if(poi.indexOf("<BUILDING_FLOOR>")!=-1){
						floor=Tool.getStrByKey(poi, "<BUILDING_FLOOR>", "</BUILDING_FLOOR>", "</BUILDING_FLOOR>");
						jsonObjArr.put("floor",floor);
					}
					
					//fitment ：装修	
					if(poi.indexOf("<DECORATION>")!=-1){
						fitment=Tool.getStrByKey(poi, "<DECORATION>", "</DECORATION>", "</DECORATION>");
						jsonObjArr.put("fitment",fitment);
					}else if(poi.indexOf("<FITMENT>")!=-1){
						fitment=Tool.getStrByKey(poi, "<FITMENT>", "</FITMENT>", "</FITMENT>");
						jsonObjArr.put("fitment",fitment);
					}else if(poi.indexOf("<BUILDING_CONDITION>")!=-1){
						fitment=Tool.getStrByKey(poi, "<BUILDING_CONDITION>", "</BUILDING_CONDITION>", "</BUILDING_CONDITION>");
						jsonObjArr.put("fitment",fitment);
					}

					
					//totalarea:总面积
					if(poi.indexOf("<TOTAL_AREA>")!=-1){
						totalarea=Tool.getStrByKey(poi, "<TOTAL_AREA>", "</TOTAL_AREA>", "</TOTAL_AREA>").replace("(大型小区)", "").replace("(中型小区)", "");
						jsonObjArr.put("totalarea",totalarea);
					}else if(poi.indexOf("<AREA>")!=-1){
						String temp=poi.substring(poi.indexOf("</FLOOR>"));
						totalarea=Tool.getStrByKey(temp, "<AREA>", "</AREA>", "</AREA>").replace("(大型小区)", "").replace("(中型小区)", "");
						if(totalarea.indexOf("平米")==-1){
							jsonObjArr.put("totalarea",totalarea);
						}
						
					}
					
					
					//households：总户数
					if(poi.indexOf("HOUSEHOLDS")!=-1){
						households=Tool.getStrByKey(poi, "<HOUSEHOLDS>", "</HOUSEHOLDS>", "</HOUSEHOLDS>");
						jsonObjArr.put("households",households);
					}
					
					//built_year:建筑年代
					if(poi.indexOf("BUILT_YEAR")!=-1){
						built_year=Tool.getStrByKey(poi, "<BUILT_YEAR>", "</BUILT_YEAR>", "</BUILT_YEAR>");
						jsonObjArr.put("built_year",built_year);
					}else if(poi.indexOf("BUILT_Date")!=-1){
						built_year=Tool.getStrByKey(poi, "<BUILT_Date>", "</BUILT_Date>", "</BUILT_Date>");
						jsonObjArr.put("built_year",built_year);
					}else if(poi.indexOf("BUILDING_TIME")!=-1){
						built_year=Tool.getStrByKey(poi, "<BUILDING_TIME>", "</BUILDING_TIME>", "</BUILDING_TIME>");
						jsonObjArr.put("built_year",built_year);
					}
					
					

					//volume_rate:容积率
					if(poi.indexOf("VOLUME_RATE")!=-1){
						volume_rate=Tool.getStrByKey(poi, "<VOLUME_RATE>", "</VOLUME_RATE>", "</VOLUME_RATE>");
						jsonObjArr.put("volume_rate",volume_rate);
					}else if(poi.indexOf("FAR")!=-1){
						volume_rate=Tool.getStrByKey(poi, "<FAR>", "</FAR>", "</FAR>");
						jsonObjArr.put("volume_rate",volume_rate);
					}
					
					//park：停车位
					if(poi.indexOf("PARK")!=-1){
						park=Tool.getStrByKey(poi, "<PARK>", "</PARK>", "</PARK>");
						jsonObjArr.put("park",park);
					}
					
					
					//green_rate:绿化率
					if(poi.indexOf("GREEN_RATE")!=-1){
						green_rate=Tool.getStrByKey(poi, "<GREEN_RATE>", "</GREEN_RATE>", "</GREEN_RATE>");
						jsonObjArr.put("green_rate",green_rate);
					}else if(poi.indexOf("GREEN")!=-1){
						green_rate=Tool.getStrByKey(poi, "<GREEN>", "</GREEN>", "</GREEN>");
						jsonObjArr.put("green_rate",green_rate);
					}
					
					//traffic:交通
					if(poi.indexOf("TRAFFIC")!=-1){
						traffic=Tool.getStrByKey(poi, "<TRAFFIC>", "</TRAFFIC>", "</TRAFFIC>");
						jsonObjArr.put("traffic",traffic);
					}
					
					//房屋结构：structure (平层或者高层)   resold中
					if(poi.indexOf("BUILDING_STRUCT")!=-1){
						structure=Tool.getStrByKey(poi, "<BUILDING_STRUCT>", "</BUILDING_STRUCT>", "</BUILDING_STRUCT>");
						jsonObjArr.put("structure",structure);
					}
					
					
					//facility：配套设施
					if(poi.indexOf("EQUITMENT")!=-1){
						facility=Tool.getStrByKey(poi, "<EQUITMENT>", "</EQUITMENT>", "</EQUITMENT>");
						jsonObjArr.put("facility",facility);
					}else if(poi.indexOf("BUILDING_SERVICE")!=-1){
						facility=Tool.getStrByKey(poi, "<BUILDING_SERVICE>", "</BUILDING_SERVICE>", "</BUILDING_SERVICE>");
						jsonObjArr.put("facility",facility);
					}
					
					
					//url ：链接		
					if(poi.indexOf("URL")!=-1){
						url=Tool.getStrByKey(poi, "<URL>", "</URL>", "</URL>");
						jsonObjArr.put("url",url);
					}
					
				    jsonObjArr.put("crawldate",date);
					
					
					
					

					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
				    
				    
				    
				    
					/*
					 * 
					 * //rent_type： 求租方式
					if(poi.indexOf("<PARTMENT>")!=-1){
						rent_type=Tool.getStrByKey(poi, "<PARTMENT>", "</PARTMENT>", "</PARTMENT>");
						jsonObjArr.put("rent_type",rent_type);
					}else if(poi.indexOf("<RENT_TYPE>")!=-1){
						rent_type=Tool.getStrByKey(poi, "<RENT_TYPE>", "</RENT_TYPE>", "</RENT_TYPE>");
						jsonObjArr.put("rent_type",rent_type);
					}else if(poi.indexOf("<SCHEMA>")!=-1){
						rent_type=Tool.getStrByKey(poi, "<SCHEMA>", "</SCHEMA>", "</SCHEMA>");
						jsonObjArr.put("rent_type",rent_type);
					}
					 * 
					 * //pay_way ： 付款方式
					if(poi.indexOf("<DEPOSIT>")!=-1){
						pay_way=Tool.getStrByKey(poi, "<DEPOSIT>", "</DEPOSIT>", "</DEPOSIT>");
						jsonObjArr.put("pay_way",pay_way);
					}
					
					//character:别墅  用于新房中
					if(poi.indexOf("CHARACTER")!=-1){
						character=Tool.getStrByKey(poi, "<CHARACTER>", "</CHARACTER>", "</CHARACTER>");
						jsonObjArr.put("character",character);
					}
					//biult_type:建筑类型（高层之类的）
					if(poi.indexOf("BUILDING_TYPE")!=-1){
						 biult_type=Tool.getStrByKey(poi, "<BUILDING_TYPE>", "</BUILDING_TYPE>", "</BUILDING_TYPE>");
						 jsonObjArr.put("biult_type",biult_type);
					}
					
					
					
					//loop:所在环线
					if(poi.indexOf("LOOP_LINE")!=-1){
						loop=Tool.getStrByKey(poi, "<LOOP_LINE>", "</LOOP_LINE>", "</LOOP_LINE>");
						jsonObjArr.put("loop",loop);
					}
					

					
					

					//open_time:开盘时间
					if(poi.indexOf("SALE_TIME")!=-1){
						open_time=Tool.getStrByKey(poi, "<SALE_TIME>", "</SALE_TIME>", "</SALE_TIME>");
						jsonObjArr.put("open_time",open_time);
					}
					//completed_time：收房时间
					if(poi.indexOf("SUBMIT_TIME")!=-1){
						completed_time=Tool.getStrByKey(poi, "<SUBMIT_TIME>", "</SUBMIT_TIME>", "</SUBMIT_TIME>");						
						jsonObjArr.put("completed_time",completed_time);
					}
					
					
					
					//licence:销售证明
                    if(poi.indexOf("LICENCE")!=-1){
                    	licence=Tool.getStrByKey(poi, "<LICENCE>", "</LICENCE>", "</LICENCE>");
						jsonObjArr.put("licence",licence);
					}
				
					//sales_address:销售地址
					if(poi.indexOf("SALE_ADDRESS")!=-1){
						sales_address=Tool.getStrByKey(poi, "<SALE_ADDRESS>", "</SALE_ADDRESS>", "</SALE_ADDRESS>");
						jsonObjArr.put("sales_address",sales_address);
					}
					
					//serve_address:服务地址，物业地址
					if(poi.indexOf("SEVER_ADDRESS")!=-1){
						serve_address=Tool.getStrByKey(poi, "<SEVER_ADDRESS>", "</SEVER_ADDRESS>", "</SEVER_ADDRESS>");
						jsonObjArr.put("serve_address",serve_address);
					}
					
					//traffic:交通
					if(poi.indexOf("TRAFFIC")!=-1){
						traffic=Tool.getStrByKey(poi, "<TRAFFIC>", "</TRAFFIC>", "</TRAFFIC>");
						jsonObjArr.put("traffic",traffic);
					}
					
					
					*/
						
					
					/*
					
					
					
					*/
					
					
					
					/*
					
					
					
						
				

					
					
					*/
					/*
					
					
					
				
					
					*/
					
					
					/*
					 * 
					 * //heat_supply:供暖方式
					if(poi.indexOf("HEAT_SUPPLY")!=-1){
						heat_supply=Tool.getStrByKey(poi, "<HEAT_SUPPLY>", "</HEAT_SUPPLY>", "</HEAT_SUPPLY>");
						jsonObjArr.put("heat_supply",heat_supply);
					}else{
						heat_supply="null";
						jsonObjArr.put("heat_supply",heat_supply);
					}
					 
					 
					//checkin：入住时间
					if(poi.indexOf("CHECKIN")!=-1){
						checkin=Tool.getStrByKey(poi, "<CHECKIN>", "</CHECKIN>", "</CHECKIN>");
						jsonObjArr.put("price",price);
					}else{
						checkin="null";
						jsonObjArr.put("price",price);
					}
					
				
					
					//contact：联系方式
					if(poi.indexOf("CONTACT")!=-1){
						contact=Tool.getStrByKey(poi, "<CONTACT>", "</CONTACT>", "</CONTACT>");
						jsonObjArr.put("contact",contact);
					}else{
						contact="null";
						jsonObjArr.put("contact",contact);
					}
					
					//notice：注意事项，留言
					if(poi.indexOf("NOTATION")!=-1){
						notice=Tool.getStrByKey(poi, "<NOTATION>", "</NOTATION>", "</NOTATION>");
						jsonObjArr.put("notice",notice);
					}else{
						notice="null";
						jsonObjArr.put("notice",notice);
					}
					*/
			
			
					/*
				
					
					*/
					/*
					
					*/
	
					
					
				
					/*
					
					
						
					
					
					
					
					
					
					
					*/
				
					if(jsonObjArr.get("longitude")!=null){
						//System.out.println(i+":"+jsonObjArr.toString());	
						FileTool.Dump(jsonObjArr.toString(), folder.replace(".txt", "") + "-Json.txt", "utf-8");
					}
				}
			}
	}catch(JSONException e){
		e.getStackTrace();
	}catch(NumberFormatException e){
		System.out.println(e.getMessage());
		System.out.println(poi);
	}
}

	/**
	 * 获取同一网格里的房源的平均价格
	 * @param file
	 */
	public static void getPrice(String file){
		Vector<String> pois = FileTool.Load(file, "utf-8");
		String poi="";
		 ArrayList<Object> UnitPrice=new ArrayList<Object>();
		 double total=0;
		 double average=0;
		try{
			for(int i=0;i<pois.size();i++){
				poi=pois.elementAt(i);
				JSONObject jsonObject = JSONObject.fromObject(poi);
				
				//Object price = jsonObject.get("price");				
				//Object area = jsonObject.get("area");
				//Object house_type=jsonObject.get("house_type");
				Object unit_price=jsonObject.get("unit_price");
				
				UnitPrice.add(unit_price);
				
				/*
				String total=price.toString()+","+area.toString()+","+house_type.toString()+","+unit_price.toString();
                FileTool.Dump(total, file.replace(".txt", "") + "-unitprice.txt", "utf-8");
                */
              }
			
			int pricecounts=0;
			for(int k=0;k<UnitPrice.size();k++){
				double unitprice=Double.parseDouble(UnitPrice.get(k).toString());
				if(unitprice!=0){
					total+=unitprice;
					pricecounts++;
				}
				
			}			
			average=total/pricecounts;
			System.out.println(average);

		}catch(net.sf.json.JSONException e){
			FileTool.Dump(poi, file.replace(".txt", "") + "-exception.txt", "utf-8");
		}
		
	}
	/**
	 * 将某一个code的房源全部挑出来进行分析研究
	 * @param file
	 */
	public static void getCode(String file){
		
		Vector<String> pois = FileTool.Load(file, "utf-8");
		String poi="";
		try{
			for(int i=0;i<pois.size();i++){
				poi=pois.elementAt(i);
				JSONObject jsonObject = JSONObject.fromObject(poi);
				Object code = jsonObject.get("code");
				String codestr = code.toString();
				int codeint = Integer.parseInt(codestr);
				if(codeint==15446){
					FileTool.Dump(poi, file.replace(".txt", "") + "-15446.txt", "utf-8");
				}
			}

		}catch(net.sf.json.JSONException e){
			FileTool.Dump(poi, file.replace(".txt", "") + "-exception.txt", "utf-8");
		}
		
		
	}
	/**
	 * 获取房天下的房源数据的面积
	 * @param url
	 * @return
	 */
	public static String getArea(String url,String folder){
		String area="";
		int monitor=0;
		try {
			String content = Tool.fetchURL(url);
			//String  content = HTMLTool.fetchURL(url, "gbk", "get");
			Parser parser = new Parser();
			if (content == null) {
				FileTool.Dump(url, folder + "-Null.txt", "utf-8");
			} else {
                parser.setInputHTML(content);
				parser.setEncoding("utf-8");
				HasParentFilter parentFilter1 = new HasParentFilter(new AndFilter(new TagNameFilter("ul"),new HasAttributeFilter("class", "Huxing floatl")));
			    HasParentFilter parentFilter2 = new HasParentFilter(new AndFilter(new TagNameFilter("li"),parentFilter1));
			   // HasParentFilter parentFilter3 = new HasParentFilter(new AndFilter(new TagNameFilter("li"),parentFilter2));
				NodeFilter filter = new AndFilter(new TagNameFilter("p"),new AndFilter(new HasAttributeFilter("class", "info"),parentFilter2));//new HasAttributeFilter("class", "info")
				NodeList nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes.size() != 0) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode no = (TagNode) nodes.elementAt(n);
						String str=no.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "").trim();
						if(str.indexOf("平方米")!=-1){
							area=str;
							System.out.println(str);
							monitor=1;
						}
						
					}
				}
				parser.reset();
				if(monitor==0){
					parentFilter1 = new HasParentFilter(new AndFilter(new TagNameFilter("ul"),new HasAttributeFilter("class", "house-info")));
					parentFilter2 = new HasParentFilter(new AndFilter(new TagNameFilter("li"),parentFilter1));
					filter = new AndFilter(new TagNameFilter("span"),new AndFilter(new HasAttributeFilter("title", "建筑面积"),parentFilter2));
					nodes = parser.extractAllNodesThatMatch(filter);
					if (nodes.size() != 0) {
						for (int n = 0; n < nodes.size(); n++) {
							TagNode no = (TagNode) nodes.elementAt(n);
							String str=no.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "").trim();
							if(str.indexOf("m²")!=-1){
								area=str;
								System.out.println(str);
								monitor=1;
							}
						}
					}
				}
				
				if(monitor==0){
					area="null";
				}
				
			
			}
			
			
		}catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			FileTool.Dump(url, folder + "NullLink.txt", "utf-8");
		}catch(FailingHttpStatusCodeException e){
			System.out.println(e.getMessage());
		}
		return area;
	}
	
}
