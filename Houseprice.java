package com.svail.houseprice;

import java.util.ArrayList;
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
	public static String cmmunity ;
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


	public static void main(String[] args){
		
		getTimeSeriesPrice();		
	}
	public static void getTimeSeriesPrice(){
		String folder="E:/房地产可视化/近一年数据分类汇总/fang/rentout/";
		String[] date={
                 "1125","1201","1207","1214","1222","1231","0108","0227","0309",
                 "0314","0324","0401","0414","0428","0509","0520","0527","0603"             
				 };
		// "1125","1201","1207","1214","1222","1231","0108","0119","0127","0219","0227","0309","0314",
		//"0324","0401","0414","0428","0509","0510","0523","0527","0603" 
		String type="fang_rentout";
		String path="";//folder+type+date[0]+"_result.txt";
		//toJson(path,false,"2015-"+date[0].substring(0, 2)+"-"+date[0].substring(2));
		
		
			for(int i=0;i<date.length;i++){
				path=folder+type+date[i]+"_result.txt";
				if(i<13){
					if(i<6){
						toJson(path,false,"2015-"+date[i].substring(0, 2)+"-"+date[i].substring(2));
					}else{
						toJson(path,false,"2016-"+date[i].substring(0, 2)+"-"+date[i].substring(2));
					}
					
				}else{
					toJson(path,true,"2016-"+date[i].substring(0, 2)+"-"+date[i].substring(2));
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
			for(int i=1;i<pois.size();i++){
				poi=pois.elementAt(i);
				JSONObject jsonObjArr = new JSONObject();
				
				if(json){
					jsonObjArr=JSONObject.fromObject(poi);
					jsonObjArr.put("crawldate",date);	
					System.out.println(i+":"+jsonObjArr.toString());	
					FileTool.Dump(jsonObjArr.toString(), folder.replace(".txt", "") + "-Json.txt", "utf-8");
					
				}else{
					/*
					String[] array=poi.split(",");
					//[116.303968, 39.923392, 海淀双榆树1500一居, 2015/9/5 23:51, 1500, 暂无信息, 整租, 海淀，双榆树, 一居, 暂无资料, 18910954221搜房网友, 真实个人发布，有意向的朋友请尽快联系我, http://zu.fang.com/qiuzu/1_96191016_-1.htm]
					int lenth=array.length;
					if(array[0].length()>0&&array.length==10){
						jsonObjArr.put("longitude",array[0]);
						jsonObjArr.put("latitude",array[1]);
						jsonObjArr.put("title",array[2]);
						jsonObjArr.put("time",array[3]);
						jsonObjArr.put("price",array[4]);
						jsonObjArr.put("house_type",array[5]);
						//jsonObjArr.put("unit_price",array[5]);//含有#VALUE!值
						//jsonObjArr.put("property",array[6]);
						jsonObjArr.put("community",array[6]);
						jsonObjArr.put("address",array[7]);
						//jsonObjArr.put("house_type",array[9]);
						//jsonObjArr.put("rent_type",array[10]);
						//jsonObjArr.put("area",array[11]);
						//jsonObjArr.put("direction",array[12]);
						//jsonObjArr.put("floor",array[13]);
						//jsonObjArr.put("fitment",array[14]);
						jsonObjArr.put("url",array[9]);
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
					
					
					//title ：标题
					if(poi.indexOf("TITLE")!=-1){
						title=Tool.getStrByKey(poi, "<TITLE>", "</TITLE>", "</TITLE>");
						jsonObjArr.put("title",title);
					}else{
						title="null";
						jsonObjArr.put("title",title);
					}
					
					//longitude :经度 latitude ：纬度
					if(poi.indexOf("Coordinate")!=-1){
						String[] coor=Tool.getStrByKey(poi, "<Coordinate>", "</Coordinate>", "</Coordinate>").split(";");
						longitude=coor[0];
						latitude=coor[1];
						jsonObjArr.put("longitude",longitude);
						jsonObjArr.put("latitude",latitude);
					}else if(poi.indexOf("Coor")!=-1){
						String[] coor=Tool.getStrByKey(poi, "<Coor>", "</Coor>", "</Coor>").split(";");
						longitude=coor[0];
						latitude=coor[1];
						jsonObjArr.put("longitude",longitude);
						jsonObjArr.put("latitude",latitude);
					}else{
						longitude="null";
						latitude="null";
						jsonObjArr.put("longitude",longitude);
						jsonObjArr.put("latitude",latitude);
					}
					
					//region：坐标所定位的行政区划层级
					if(poi.indexOf("Reg")!=-1){
						region=Tool.getStrByKey(poi, "<Reg>", "</Reg>", "</Reg>");
						jsonObjArr.put("region",region);
					}else{
						region="regionnull";
						jsonObjArr.put("region",region);
					}
					
					//time ：时间
					if(poi.indexOf("TIME")!=-1){
						time=Tool.getStrByKey(poi, "<TIME>", "</TIME>", "</TIME>");
						jsonObjArr.put("time",time);
					}else{
						time="null";
						jsonObjArr.put("time",time);
					}
					
					//price ：总价
					if(poi.indexOf("PRICE")!=-1){
						price=Tool.getStrByKey(poi, "<PRICE>", "</PRICE>", "</PRICE>").replace("元/月","").replace("未知", "");
						jsonObjArr.put("price",price);
					}else{
						price="null";
						jsonObjArr.put("price",price);
					}
					/*
					//checkin：入住时间
					if(poi.indexOf("CHECKIN")!=-1){
						checkin=Tool.getStrByKey(poi, "<CHECKIN>", "</CHECKIN>", "</CHECKIN>");
						jsonObjArr.put("price",price);
					}else{
						checkin="null";
						jsonObjArr.put("price",price);
					}
					
					//facility：配套设施
					if(poi.indexOf("EQUITMENT")!=-1){
						facility=Tool.getStrByKey(poi, "<EQUITMENT>", "</EQUITMENT>", "</EQUITMENT>");
						jsonObjArr.put("facility",facility);
					}else{
						facility="null";
						jsonObjArr.put("facility",facility);
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
					
					//area ：面积
					if(fang){
						url=Tool.getStrByKey(poi, "<URL>", "</URL>", "</URL>");
						area=getArea(url,folder).replace("平米", "").replace("m²", "").replace("平方米", "");
						jsonObjArr.put("area",area);
						
					}else{
						if(poi.indexOf("AREA")!=-1&&poi.indexOf("HOUSE_AREA")==-1){
							area=Tool.getStrByKey(poi, "<AREA>", "</AREA>", "</AREA>").replace("平米", "").replace("m²", "").replace("平方米", "");
							if(area.equals("0")){
								area="null";
							}
							jsonObjArr.put("area",area);
						}else if(poi.indexOf("HOUSE_AREA")!=-1){
							area=Tool.getStrByKey(poi, "<HOUSE_AREA>", "</HOUSE_AREA>", "</HOUSE_AREA>").replace("平米", "").replace("m²", "").replace("平方米", "");
							jsonObjArr.put("area",area);
						}else{
							area="null";
							jsonObjArr.put("area",area);
						}
					}	
				
					
				
					
					//unit_price ：每平方米单价
					if(!(price.equals("null"))&&!(area.equals("null"))){
						unit_price=Double.parseDouble(price)/Double.parseDouble(area);
						jsonObjArr.put("unit_price",unit_price);
					}else{
						unit_price=0;
						jsonObjArr.put("unit_price",unit_price);
					}
					
					
					
				
					//location：所在区域
					if(poi.indexOf("LOCATION")!=-1){
						location=Tool.getStrByKey(poi, "<LOCATION>", "</LOCATION>", "</LOCATION>");
						jsonObjArr.put("location",location);
					}else if(poi.indexOf("REGION")!=-1){
						location=Tool.getStrByKey(poi, "<REGION>", "</REGION>", "</REGION>");
						jsonObjArr.put("location",location);
					}else if(poi.indexOf("DISTRICT")!=-1){
						location=Tool.getStrByKey(poi, "<DISTRICT>", "</DISTRICT>", "</DISTRICT>");
						jsonObjArr.put("location",location);
					}
					/*else if(poi.indexOf("ADDRESS")!=-1){
						location=Tool.getStrByKey(poi, "<ADDRESS>", "</ADDRESS>", "</ADDRESS>");
						jsonObjArr.put("location",location);
					}
					*/
					else{
						location="null";
						jsonObjArr.put("location",location);
					}
					
					
					
					
					//cmmunity ：所在小区
					if(poi.indexOf("COMMUNITY")!=-1){
						cmmunity=Tool.getStrByKey(poi, "<COMMUNITY>", "</COMMUNITY>", "<COMMUNITY>");
						jsonObjArr.put("cmmunity",cmmunity);
					}else{
						cmmunity="null";
						jsonObjArr.put("cmmunity",cmmunity);
					}
					
					
					//address ：地址
					if(poi.indexOf("ADDRESS")!=-1){
						address=Tool.getStrByKey(poi, "<ADDRESS>", "</ADDRESS>", "</ADDRESS>").replace("(地图)", "");
						jsonObjArr.put("address",address);
					}else{
						address="null";
						jsonObjArr.put("address",address);
					}
					
					
					//property：房屋性质（住宅之类的）
					if(poi.indexOf("PROPERTY_TYPE")!=-1){
						property=Tool.getStrByKey(poi, "<PROPERTY_TYPE>", "</PROPERTY_TYPE>", "</PROPERTY_TYPE>");
						jsonObjArr.put("property",property);
					}else if(poi.indexOf("TYPE")!=-1){
						property=Tool.getStrByKey(poi, "<TYPE>", "</TYPE>", "</TYPE>");
						jsonObjArr.put("property",property);
					}else{
						property="null";
						jsonObjArr.put("property",property);
					}
					/*
					//pay_way ： 付款方式
					if(poi.indexOf("DEPOSIT")!=-1){
						pay_way=Tool.getStrByKey(poi, "<DEPOSIT>", "</DEPOSIT>", "</DEPOSIT>");
						jsonObjArr.put("pay_way",pay_way);
					}else{
						pay_way="null";
						jsonObjArr.put("pay_way",pay_way);
					}
					*/
	
					//house_type：户型
					if(poi.indexOf("HOUSE_TYPE")!=-1){
						house_type=Tool.getStrByKey(poi, "<HOUSE_TYPE>", "</HOUSE_TYPE>", "</HOUSE_TYPE>");
						jsonObjArr.put("house_type",house_type);
					}else if(poi.indexOf("TYPE")!=-1){
						house_type=Tool.getStrByKey(poi, "<TYPE>", "</TYPE>", "</TYPE>");
						jsonObjArr.put("house_type",house_type);
					}else if(poi.indexOf("BUILDTYPE")!=-1){
						house_type=Tool.getStrByKey(poi, "<BUILDTYPE>", "</BUILDTYPE>", "</BUILDTYPE>");
						jsonObjArr.put("house_type",house_type);
					}else{
						house_type="null";
						jsonObjArr.put("house_type",house_type);
					}
					
					
					
					//rent_type： 求租方式
					if(poi.indexOf("PARTMENT")!=-1){
						rent_type=Tool.getStrByKey(poi, "<PARTMENT>", "</PARTMENT>", "</PARTMENT>");
						jsonObjArr.put("rent_type",rent_type);
					}else if(poi.indexOf("RENT_TYPE")!=-1){
						rent_type=Tool.getStrByKey(poi, "<RENT_TYPE>", "</RENT_TYPE>", "</RENT_TYPE>");
						jsonObjArr.put("rent_type",rent_type);
					}else if(poi.indexOf("SCHEMA")!=-1){
						rent_type=Tool.getStrByKey(poi, "<SCHEMA>", "</SCHEMA>", "</SCHEMA>");
						jsonObjArr.put("rent_type",rent_type);
					}else{
						rent_type="null";
						jsonObjArr.put("rent_type",rent_type);
					}
					
					//fitment ：装修	
					if(poi.indexOf("DECORATION")!=-1){
						fitment=Tool.getStrByKey(poi, "<DECORATION>", "</DECORATION>", "</DECORATION>");
						jsonObjArr.put("fitment",fitment);
					}else if(poi.indexOf("FITMENT")!=-1){
						fitment=Tool.getStrByKey(poi, "<FITMENT>", "</FITMENT>", "</FITMENT>");
						jsonObjArr.put("fitment",fitment);
					}else{
						fitment="null";
						jsonObjArr.put("fitment",fitment);
					}
					
					
					//direction ：朝向
					if(poi.indexOf("DIRECTION")!=-1){
						direction=Tool.getStrByKey(poi, "<DIRECTION>", "</DIRECTION>", "</DIRECTION>");
						jsonObjArr.put("direction",direction);
					}else if(poi.indexOf("ORIENTATION")!=-1){
						direction=Tool.getStrByKey(poi, "<ORIENTATION>", "</ORIENTATION>", "</ORIENTATION>");
						jsonObjArr.put("direction",direction);
					}else{
						direction="null";
						jsonObjArr.put("direction",direction);
					}

				
					
					//floor ：楼层
					if(poi.indexOf("FLOOR")!=-1){
						floor=Tool.getStrByKey(poi, "<FLOOR>", "</FLOOR>", "</FLOOR>");
						jsonObjArr.put("floor",floor);
					}else{
						floor="null";
						jsonObjArr.put("floor",floor);
					}
					/*
					//totalarea:总面积
					if(poi.indexOf("TOTAL_AREA")!=-1){
						totalarea=Tool.getStrByKey(poi, "<TOTAL_AREA>", "</TOTAL_AREA>", "</TOTAL_AREA>");
						jsonObjArr.put("totalarea",totalarea);
					}else{
						totalarea="null";
						jsonObjArr.put("totalarea",totalarea);
					}
					
					//developer：开发商
					if(poi.indexOf("DEVELOPER")!=-1){
						developer=Tool.getStrByKey(poi, "<DEVELOPER>", "</DEVELOPER>", "</DEVELOPER>");
						jsonObjArr.put("developer",developer);
					}else{
						developer="null";
						jsonObjArr.put("developer",developer);
					}
					
					//property_company:物业公司
					if(poi.indexOf("PROPERTY")!=-1){
						property_company=Tool.getStrByKey(poi, "<PROPERTY>", "</PROPERTY>", "</PROPERTY>");
						jsonObjArr.put("property_company",property_company);
					}else{
						property_company="null";
						jsonObjArr.put("property_company",property_company);
					}
					
					//property_fee:物业费
					if(poi.indexOf("PROPERTY_FEE")!=-1){
						property_fee=Tool.getStrByKey(poi, "<PROPERTY_FEE>", "</PROPERTY_FEE>", "</PROPERTY_FEE>");
						jsonObjArr.put("property_fee",property_fee);
					}else{
						title="null";
						jsonObjArr.put("property_fee",property_fee);
					}
					
					//households：总户数
					if(poi.indexOf("HOUSEHOLDS")!=-1){
						households=Tool.getStrByKey(poi, "<HOUSEHOLDS>", "</HOUSEHOLDS>", "</HOUSEHOLDS>");
						jsonObjArr.put("households",households);
					}else{
						households="null";
						jsonObjArr.put("households",households);
					}
					
					//built_year:建筑年代
					if(poi.indexOf("BUILT_YEAR")!=-1){
						built_year=Tool.getStrByKey(poi, "<BUILT_YEAR>", "</BUILT_YEAR>", "</BUILT_YEAR>");
						jsonObjArr.put("built_year",built_year);
					}else if(poi.indexOf("BUILT_Date")!=-1){
						built_year=Tool.getStrByKey(poi, "<BUILT_Date>", "</BUILT_Date>", "</BUILT_Date>");
						jsonObjArr.put("built_year",built_year);
					}else{
						built_year="null";
						jsonObjArr.put("built_year",built_year);
					}
						
					//volume_rate:容积率
					if(poi.indexOf("VOLUME_RATE")!=-1){
						volume_rate=Tool.getStrByKey(poi, "<VOLUME_RATE>", "</VOLUME_RATE>", "</VOLUME_RATE>");
						jsonObjArr.put("volume_rate",volume_rate);
					}else{
						volume_rate="null";
						jsonObjArr.put("volume_rate",volume_rate);
					}
					
					//green_rate:绿化率
					if(poi.indexOf("GREEN_RATE")!=-1){
						green_rate=Tool.getStrByKey(poi, "<GREEN_RATE>", "</GREEN_RATE>", "</GREEN_RATE>");
						jsonObjArr.put("green_rate",green_rate);
					}else{
						green_rate="null";
						jsonObjArr.put("green_rate",green_rate);
					}
					
					//park：停车位
					if(poi.indexOf("PARK")!=-1){
						park=Tool.getStrByKey(poi, "<PARK>", "</PARK>", "</PARK>");
						jsonObjArr.put("park",park);
					}else{
						park="null";
						jsonObjArr.put("park",park);
					}
					
					//heat_supply:供暖方式
					if(poi.indexOf("HEAT_SUPPLY")!=-1){
						heat_supply=Tool.getStrByKey(poi, "<HEAT_SUPPLY>", "</HEAT_SUPPLY>", "</HEAT_SUPPLY>");
						jsonObjArr.put("heat_supply",heat_supply);
					}else{
						heat_supply="null";
						jsonObjArr.put("heat_supply",heat_supply);
					}
					
					
					*/
					
					//url ：链接		
					if(poi.indexOf("URL")!=-1){
						url=Tool.getStrByKey(poi, "<URL>", "</URL>", "</URL>");
						jsonObjArr.put("url",url);
					}else{
						url="null";
						jsonObjArr.put("url",url);
					}
					
					
					
					
				jsonObjArr.put("crawldate",date);	
					if(jsonObjArr.get("longitude")!=null){
						System.out.println(i+":"+jsonObjArr.toString());	
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
		 ArrayList UnitPrice=new ArrayList();
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
