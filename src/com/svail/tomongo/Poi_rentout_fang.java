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

public class Poi_rentout_fang {
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
	public String DOWN_PAYMENT;
	public String UNIT_PRICE;
	public String ORIENTATION;
	public String FITMENT;
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

	Poi_rentout_fang(String line) throws IOException
	{
		try{
			if(line.indexOf("<coordinate>")!=-1)
			{
			     COORDINATE=getStrByKey(line,"<coordinate>","</coordinate>");
			    if(COORDINATE!=null)
				{
					
					 write_append(line,"E:/crawldata_beijing/fang_rentout_all925.txt");
				}
			}
			else
			{
				System.out.println("坐标值为空,记录重新写入fang_rentout_problem_poi925.txt中!");
			    write_append(line,"E:/crawldata_beijing/problempoi/fang_rentout_problem_poi925.txt");
			}
		     TITLE=getStrByKey(line,"<TITLE>","</TITLE>").replace("&nbsp;", "");
			 date = getStrByKey(line,"<TIME>","</TIME>");
			 TIME=DateTransfer.date_transfer(date);
			 PRICE= getStrByKey(line,"<PRICE>","</PRICE>").replace("元/月","");
			 COMMUNITY=getStrByKey(line,"<COMMUNITY>","</COMMUNITY>");
			 LOCATION=getStrByKey(line,"<LOCATION>","</LOCATION>");
			 ADDRESS=getStrByKey(line,"<ADDRESS>","</ADDRESS>");
			 HOUSE_TYPE=getStrByKey(line,"<HOUSE_TYPE>","</HOUSE_TYPE>");
			 if(HOUSE_TYPE==null)
				 HOUSE_TYPE="暂无"; 
			 RENT_TYPE=getStrByKey(line,"<PARTMENT>","</PARTMENT>");
			 AREA=getStrByKey(line,"<AREA>","</AREA>").replace("平方米","").replace("平米","");
			 ORIENTATION=getStrByKey(line,"<DIRECTION>","</DIRECTION>");
			 FLOOR=getStrByKey(line,"<FLOOR>","</FLOOR>");
			 FITMENT=getStrByKey(line,"<DECORATION>","</DECORATION>");
			 PROPERTY_TYPE=getStrByKey(line,"<PROPERTY_TYPE>","</PROPERTY_TYPE>");			
			 URL=getStrByKey(line,"<URL>","</URL>");
			 SOURCE="房天下出租";
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
