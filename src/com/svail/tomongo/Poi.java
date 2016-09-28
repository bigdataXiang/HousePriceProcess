package com.svail.tomongo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.svail.util.FileTool;


public class Poi {
	// <POI><coordinate>longitude=116.6157531637;latitude=39.9244665708</coordinate><TITLE>中弘北京像素整租1室1厅1卫54平米(个人)</TITLE><TIME>2015-7-20 23:44:36</TIME><PRICE>3800元/月[押一付三]</PRICE><PROPERTY_TYPE>住宅</PROPERTY_TYPE><COMMUNITY>中弘北京像素</COMMUNITY><ADDRESS>朝阳区五里桥一街一号院(朝阳北路6号线草房站)</ADDRESS><HOUSE_TYPE>1室1厅1卫</HOUSE_TYPE><PARTMENT>整租</PARTMENT><AREA>54平米</AREA><DIRECTION>北</DIRECTION><FLOOR>12/23层</FLOOR><DECORATION>精装修</DECORATION><TRAFFIC>距朝阳路1.5公里，距京通快速路2公里，直线通到CBD的朝阳北路</TRAFFIC><URL>http://zu.fang.com/chuzu/1_59235529_-1.htm</URL></POI>
	
	public Object TITLE = null;
	public double lng = 0.0;
	public double lat = 0.0;
	public String DIRECTION = null;
	public static Object INVALID_DEGREE;
	public String TIME;
	public String PRICE;
	public String HOUSE_TYPE;
	public Object COMMUNITY;
	public Object ADDRESS;
	public Object PARTMENT;
	public String AREA;
	public Object FLOOR;
	public Object DECORATION;
	public Object TRAFFIC;
	public Object URL;
	public String date;
	public String COORDINATE;
	public String DEPOSIT;
	public String RENT_TYPE;
	public String PROPERTY_TYPE;
	public String LOCATION;
	public String SOURCE;
	public static void write_append(String line,String pathname)  throws IOException
	{
		try
		{
		
          File writefile=new File(pathname);
          if(!writefile.exists())
          {
        	  writefile.createNewFile();
          }
          OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(writefile,true),"UTF-8");
          BufferedWriter writer = new BufferedWriter(write);
          writer.write(line);
          writer.write("\r\n"); 
          writer.close();
          }catch(Exception e) {
			e.printStackTrace();
		
		}	
	}

	Poi(String line) throws IOException
	{
		try{
		     COORDINATE=getStrByKey(line,"<coordinate>","</coordinate>");
			 if(COORDINATE==null)
			 {
				 System.out.println("坐标值为空,记录重新写入fang_rentout_problem_poi.txt中!");
				 write_append(line,"E:/crawldata_beijing/58/rentout/58_rentout_result/58_rentout_problempoi.txt");
				 //E:/crawldata_beijing/fang/rentout/fang_rentout_result/fang_rentout_problem_poi.txt
				 //E:/crawldata_beijing/anjuke/rentout/anjuk_rentout_result/anjuke_rentout_problem_poi.txt
			 }
		     TITLE=getStrByKey(line,"<TITLE>","</TITLE>").replace("&nbsp;", "");
			 date = getStrByKey(line,"<TIME>","</TIME>");
			 TIME=DateTransfer.date_transfer(date);
			 PRICE= getStrByKey(line,"<PRICE>","</PRICE>").replace("元/月","").replace("[押一付三]","").replace("[面议]","");
			 HOUSE_TYPE=getStrByKey(line,"<GENERAL_SITUATION>","</GENERAL_SITUATION>");
			 if(HOUSE_TYPE==null)
				 HOUSE_TYPE="暂无"; 
			 //PARTMENT=getStrByKey(line,"<PARTMENT>","</PARTMENT>");
		     PARTMENT="暂无";
			 COMMUNITY=getStrByKey(line,"<DISTRICT>","</DISTRICT>");
			 //LOCATION=getStrByKey(line,"<LOCATION>","</LOCATION>");
			 ADDRESS=getStrByKey(line,"<ADDRESS>","</ADDRESS>");
			 //DECORATION=getStrByKey(line,"<DECORATION>","</DECORATION>");
			 DECORATION="详见HOUSE_TYPE";
			 int n=HOUSE_TYPE.indexOf("㎡");
			 if(n!=-1)
			 AREA=HOUSE_TYPE.substring(HOUSE_TYPE.indexOf("卫")+1, HOUSE_TYPE.indexOf("㎡"));
			 else
				 AREA=null;
			// if((getStrByKey(line,"<AREA>","</AREA>"))!=null)
			//	 AREA=getStrByKey(line,"<AREA>","</AREA>").replace("平米","").replace("平方米","");
				//else
				// AREA="暂无";
			// DIRECTION =getStrByKey(line,"<DIRECTION>","</DIRECTION>");
			 DECORATION="详见HOUSE_TYPE";
			 FLOOR=getStrByKey(line,"<FLOOR>","</FLOOR>");
			 //PROPERTY_TYPE=getStrByKey(line,"<PROPERTY_TYPE>","</PROPERTY_TYPE>");
			 PROPERTY_TYPE="住宅";
			 URL=getStrByKey(line,"<URL>","</URL>");
			 SOURCE="58同城";
		}catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 	 
	}

	//提取每个标签里的内容
	public static String getStrByKey(String sContent, String sStart, String sEnd) {
		String sOut ="";
		int fromIndex = 0;
		int iBegin = 0;
		int iEnd = 0;
		int iStart=sContent.indexOf("</POI>");
		if (iStart < 0) {
		  return null;
		  }
		for (int i = 0; i < iStart; i++) {
		  // 找出某位置，并找出该位置后的最近的一个匹配
		  iBegin = sContent.indexOf(sStart, fromIndex);
		  if (iBegin >= 0) 
		  {
		    iEnd = sContent.indexOf(sEnd, iBegin + sStart.length());
		    if (iEnd <= iBegin)
		    {
		      return null;
		    }
		  }
		  else 
		  {
				return sOut;
		  }
          if (iEnd > 0&&iEnd!=iBegin + sStart.length())
          {
		   sOut += sContent.substring(iBegin + sStart.length(), iEnd);
		  }
          else
        	  return null;
		  if (iEnd > 0) 
		  {
		   fromIndex = iEnd + sEnd.length();
		  }
		}
		  return sOut;
	}
}
