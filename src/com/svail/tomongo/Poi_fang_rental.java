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

public class Poi_fang_rental{
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
	public String GENERAL_SITUATION;
	public String BUILDING_USAGE;
	public String CHARACTER;
	public String BUILDING_TYPE;
	public String BUILDING_CONDITION;
	public String LOOP_LINE;
	public String FAR;
	public String GREEN;
	public String SALE_TIME;
	public String SUBMIT_TIME;
	public String PROPERTY_FEE;
	public String SERVER;
	public String DEVELOPER;
	public String LICENCE;
	public String SALE_ADDRESS;
	public String SEVER_ADDRESS;
	public String EQUITMENT;
	public String CONTACT;
	public String NOTATION;
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

	Poi_fang_rental(String line) throws IOException
	{
		try{
			if(line.indexOf("<coordinate>")!=-1)
			{
			     COORDINATE=getStrByKey(line,"<coordinate>","</coordinate>");
			    if(COORDINATE!=null)
				{
				write_append(line,"E:/crawldata_beijing/fang_rental_all925.txt");
				}
			}
			else
			{
				System.out.println("坐标值为空,记录重新写入fang_rental_problem_poi925.txt中!");
			    write_append(line,"E:/crawldata_beijing/problempoi/fang_rental_problem_poi925.txt");
			}
			TITLE=getStrByKey(line,"<TITLE>","</TITLE>");
			date = getStrByKey(line,"<TIME>","</TIME>");
			TIME=DateTransfer.date_transfer(date);
			PRICE=getStrByKey(line,"<PRICE>","</PRICE>");
			AREA=getStrByKey(line,"<AREA>","</AREA>");
			RENT_TYPE=getStrByKey(line,"<RENT_TYPE>","</RENT_TYPE>");
			ADDRESS=getStrByKey(line,"<ADDRESS>","</ADDRESS>");
			HOUSE_TYPE=getStrByKey(line,"<BUILDTYPE>","</BUILDTYPE>");
			EQUITMENT=getStrByKey(line,"<EQUITMENT>","</EQUITMENT>");
			CONTACT=getStrByKey(line,"<CONTACT>","</CONTACT>");
			NOTATION=getStrByKey(line,"<NOTATION>","</NOTATION>");
			URL=getStrByKey(line,"<URL>","</URL>");
			SOURCE="房天下求租";
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
