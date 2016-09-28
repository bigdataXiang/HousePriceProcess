package com.svail.crawl.crawldata;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Vector;
import com.svail.util.FileTool;
import java.util.StringTokenizer;

public class Poi_tidy_anjuke{
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
	public String FLOOR;
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
	public String infloor;
	public String totalfloor;
	public int roughcast;
	public int Simple_decoration;
	public int Fine_decoration;
	public int Luxury_decoration;
	public int Moderate_decoration;
	public int south;
	public int north;
	public int east;
	public int west;
	public int south_north;
	public int east_west;
	public int west_south;
	public int east_north;
	public int west_north;
	public int east_south;
	public String DEVELOPER;
	public String PROPERTY;
	public String PROPERTY_FEE;
	public String TOTAL_AREA;
	public String BUILT_YEAR;
	public String HOUSEHOLDS;
	public String VOLUME_RATE;
	public String PARK;
	public String GREEN_RATE;
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

	Poi_tidy_anjuke(String line) throws IOException
	{
		try{
			if(line.indexOf("<coordinate>")!=-1)
			{
			     COORDINATE=getStrByKey(line,"<coordinate>","</coordinate>");
			}
			else
			{
				System.out.println("坐标值为空!");
			    write_append(line,"D:/Crawldata_beijing/rentout_problem_poi.txt");
			}
			 if(line.indexOf("<TITLE>")!=-1)
		     TITLE=getStrByKey(line,"<TITLE>","</TITLE>").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");
			 if(line.indexOf("<TIME>")!=-1)
			 TIME=getStrByKey(line,"<TIME>","</TIME>").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");
			 if(line.indexOf("<PRICE>")!=-1)
			 PRICE= getStrByKey(line,"<PRICE>","</PRICE>").replace("[面议]","").replace("[押一付三]","").replace("元/月","").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");
			 if(line.indexOf("<COMMUNITY>")!=-1)
			 COMMUNITY=getStrByKey(line,"<COMMUNITY>","</COMMUNITY>").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");
			 if(line.indexOf("<LOCATION>")!=-1)
			 LOCATION=getStrByKey(line,"<LOCATION>","</LOCATION>").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");
			 if(line.indexOf("<ADDRESS>")!=-1)
			 ADDRESS=getStrByKey(line,"<ADDRESS>","</ADDRESS>").replace("(地图)","").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");
			 if(line.indexOf("<DEVELOPER>")!=-1)
			 DEVELOPER=getStrByKey(line,"<DEVELOPER>","</DEVELOPER>").replace("(地图)","").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");	
			 if(line.indexOf("<PROPERTY>")!=-1)
			 PROPERTY=getStrByKey(line,"<PROPERTY>","</PROPERTY>").replace("(地图)","").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");	
			 if(line.indexOf("<PROPERTY_FEE>")!=-1)
			 PROPERTY_FEE=getStrByKey(line,"<PROPERTY_FEE>","</PROPERTY_FEE>").replace("(地图)","").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");				 	 
			 if(line.indexOf("<TOTAL_AREA>")!=-1)
			 TOTAL_AREA=getStrByKey(line,"<TOTAL_AREA>","</TOTAL_AREA>").replace("(小型小区)","").replace("(中型小区)","").replace("(大型小区)","").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");				 	 
			 if(line.indexOf("<HOUSEHOLDS>")!=-1)
			 HOUSEHOLDS=getStrByKey(line,"<HOUSEHOLDS>","</HOUSEHOLDS>").replace("户","").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");				 	 
			 if(line.indexOf("<BUILT_YEAR>")!=-1)
			 BUILT_YEAR=getStrByKey(line,"<BUILT_YEAR>","</BUILT_YEAR>").replace("户","").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");				 	 
			 if(line.indexOf("<VOLUME_RATE>")!=-1)
			 VOLUME_RATE=getStrByKey(line,"<VOLUME_RATE>","</VOLUME_RATE>").replace("户","").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");				 	 
			 if(line.indexOf("<PARK>")!=-1)
			 PARK=getStrByKey(line,"<PARK>","</PARK>").replace("户","").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");				 	 
			 if(line.indexOf("<GREEN_RATE>")!=-1)
			 GREEN_RATE=getStrByKey(line,"<GREEN_RATE>","</GREEN_RATE>").replace("户","").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");				 	 
			 if(line.indexOf("<HOUSE_TYPE>")!=-1) 
			 HOUSE_TYPE=getStrByKey(line,"<HOUSE_TYPE>","</HOUSE_TYPE>").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");
			 if(line.indexOf("<RENT_TYPE>")!=-1)
			 RENT_TYPE=getStrByKey(line,"<RENT_TYPE>","</RENT_TYPE>").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");
			 //HOUSE_AREA
			 if(line.indexOf("<HOUSE_AREA>")!=-1&&line.indexOf("小区")==-1)
			 {
			   AREA=getStrByKey(line,"<HOUSE_AREA>","</HOUSE_AREA>").replace("平米","").replace("平方米","").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");
			 }
			 if(line.indexOf("<ORIENTATION>")!=-1)
			 {
				 
				 ORIENTATION=getStrByKey(line,"<ORIENTATION>","</ORIENTATION>").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";",""); 
				 if(ORIENTATION.equals("南北"))
				 {
					 south_north=10; 
				 }
				
				 if(ORIENTATION.equals("南"))
				 {
					 south=9; 
				 }
				
				 if(ORIENTATION.equals("东南"))
				 {
					 east_south=8;
				 }
				
				 if(ORIENTATION.equals("西南"))
				 {
					 west_south=7;
				 }
				
				 if(ORIENTATION.equals("东"))
				 {
					 east=6; 
				 }
				 
				 if(ORIENTATION.equals("东西"))
				 {
					 east_west=5; 
				 }
				 
				 if(ORIENTATION.equals("西"))
				 {
					 west=4; 
				 }
				 ;
				 if(ORIENTATION.equals("东北"))
				 {
					 east_north=3; 
				 }
				 
				 if(ORIENTATION.equals("西北"))
				 {
					 west_north=2;
				 }
				 
				 if(ORIENTATION.equals("北"))
				 {
					 north=1; 
				 }
			 }
			 if(line.indexOf("<FLOOR>")!=-1)
			 {
			    FLOOR=getStrByKey(line,"<FLOOR>","</FLOOR>").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");
			    if(FLOOR.indexOf("/")!=-1)
			   {
			    // FLOOR=FLOOR.replace("/","//");
			     infloor=FLOOR.substring(0, FLOOR.indexOf("/")).replace("/", "").replace("层", "");
			     totalfloor=FLOOR.substring(FLOOR.indexOf("/")).replace("/", "").replace("层", "");
			   }
			 }
			 if(line.indexOf("FITMENT")!=-1)
			 {
				 FITMENT=getStrByKey(line,"<FITMENT>","</FITMENT>").replace("　","").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");
				 if(FITMENT.equals("毛坯"))
				 {
					roughcast=-1;
				 }
				 if(FITMENT.equals("简单装修")||FITMENT.equals("简装修")||FITMENT.equals("普通装修"))//
				 {
					 Simple_decoration=1;
				 }
				 if(FITMENT.equals("中等装修")||FITMENT.equals("中装修"))
				 {
					 Moderate_decoration=2;
				 }
				 if(FITMENT.equals("精装修"))
				 {
					 Fine_decoration=3;
				 }
				 if(FITMENT.equals("豪华装修"))
				 {
					 Luxury_decoration=4;
				 }
				 
			 }
			if(line.indexOf("<PROPERTY_TYPE>")!=-1)
			PROPERTY_TYPE=getStrByKey(line,"<PROPERTY_TYPE>","</PROPERTY_TYPE>").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");	
			if(line.indexOf("<TRAFFIC>")!=-1)
			TRAFFIC=getStrByKey(line,"<TRAFFIC>","</TRAFFIC>").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");	
			URL=getStrByKey(line,"<URL>","</URL>").replace("&nbsp;", "").replace("&amp;","").replace("nb;","").replace("nbsp","").replace("|","").replace(";","");
		}catch (NullPointerException e) {
			
		     e.printStackTrace();
			 System.out.println(e.getMessage());
			 write_append(line,"D:/Crawl_test/exception.txt");	
		}
			 	 
	}

	//提取每个标签里的内容
	public static String getStrByKey(String sContent, String sStart, String sEnd) {
		String sOut ="";
		int fromIndex = 0;
		int iBegin = 0;
		int iEnd = 0;
		int iStart=sContent.indexOf("</POI>");
		String temp=" ";
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
        	  return temp;
		  if (iEnd > 0) 
		  {
		   fromIndex = iEnd + sEnd.length();
		  }
		}
		  return sOut;
	}
	public static void main(String[] args) throws IOException {
		String POI="";
		Vector<String> rds = FileTool.Load("D:/Data_classify/finish_classify/anjuke/anjuke_result/anjuke_rentout/anjuke_rentout0913_0919.txt","UTF-8");
		//D:/Data_classify/finish_classify/58/58_result/58_rentout/58_rentout0712_0718.txt
		//D:/Data_classify/finish_classify/fang/fang_result/fang_rentout/fang_rentout0426_0502.txt
		//D:/Crawl_test/exception.txt
		//E:/Crawl_test/fang_result/fang_rentout/exception.txt
		for (int n = 0; n < rds.size(); n ++)
		{
			String element=rds.elementAt(n);
			Poi_tidy_anjuke poi = new Poi_tidy_anjuke(element);
			if(poi.COORDINATE!=null)
			{
				 POI+=poi.COORDINATE+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.TITLE!=null)
			{
				 POI+=poi.TITLE+"|";
			}
			else
			{
				 POI+="|"; 
			}if(poi.TIME!=null)
			{
				 POI+=poi.TIME+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.PRICE!=null)
			{
				 POI+=poi.PRICE+"|";
			}
			else
			{
				 POI+="|"; 
			}
			
			if(poi.AREA!=null)
			{
				 POI+=poi.AREA+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.TOTAL_AREA!=null)
			{
				 POI+=poi.TOTAL_AREA+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.COMMUNITY!=null)
			{
				 POI+=poi.COMMUNITY+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.LOCATION!=null)
			{
				 POI+=poi.LOCATION+"|";
			}
			else
			{
				 POI+="|"; 
			}if(poi.ADDRESS!=null)
			{
				 POI+=poi.ADDRESS+"|";
			}
			else
			{
				 POI+="|"; 
			}if(poi.HOUSE_TYPE!=null)
			{
				 POI+=poi.HOUSE_TYPE+"|";
			}
			else
			{
				 POI+="|"; 
			}if(poi. RENT_TYPE!=null)
			{
				 POI+=poi. RENT_TYPE+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi. FLOOR!=null)
			{
				 POI+=poi. FLOOR+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi. infloor!=null)
			{
				 POI+=poi. infloor+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.totalfloor!=null)
			{
				 POI+=poi.totalfloor+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi. ORIENTATION!=null)
			{
				 POI+=poi.ORIENTATION+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.south_north==10)
			{
				POI+=poi.south_north+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.south==9)
			{
				POI+=poi.south+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.east_south==8)
			{
				POI+=poi.east_south+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.west_south==7)
			{
				POI+=poi.west_south+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.east==6)
			{
				POI+=poi.east+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.east_west==5)
			{
				POI+=poi.east_west+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.west==4)
			{
				POI+=poi.west+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi. east_north==3)
			{
				POI+=poi. east_north+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi. west_north==2)
			{
				POI+=poi.west_north+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.north==1)
			{
				POI+=poi.north+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.FITMENT!=null)
			{
				 POI+=poi.FITMENT+"|";
			}
			else
			{
				 POI+="|"; 
			}
			
			if(poi.roughcast==-1)
			{
				 POI+=poi.roughcast+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.Simple_decoration==1)
			{
				 POI+=poi.Simple_decoration+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.Moderate_decoration==2)
			{
				 POI+=poi.Moderate_decoration+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.Fine_decoration==3)
			{
				 POI+=poi.Fine_decoration+"|";
			}
			else
			{
				 POI+="|"; 
			}			
			if(poi.Luxury_decoration==4)
			{
				 POI+=poi.Luxury_decoration+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.PROPERTY_TYPE!=null)
			{
				 POI+=poi.PROPERTY_TYPE+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.DEVELOPER!=null)
			{
				 POI+=poi.DEVELOPER+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.PROPERTY!=null)
			{
				 POI+=poi.PROPERTY+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.PROPERTY_FEE!=null)
			{
				 POI+=poi.PROPERTY_FEE+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.BUILT_YEAR!=null)
			{
				 POI+=poi.BUILT_YEAR+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.VOLUME_RATE!=null)
			{
				 POI+=poi.VOLUME_RATE+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.GREEN_RATE!=null)
			{
				 POI+=poi.GREEN_RATE+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.HOUSEHOLDS!=null)
			{
				 POI+=poi.HOUSEHOLDS+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.PARK!=null)
			{
				 POI+=poi.PARK+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.TRAFFIC!=null)
			{
				 POI+=poi.TRAFFIC+"|";
			}
			else
			{
				 POI+="|"; 
			}
			if(poi.URL!=null)
			{
				 POI+=poi.URL+"|";
			}
			else
			{
				 POI+="|"; 
			}
			
			System.out.println(POI);
			write_append(POI,"D:/Crawl_test/anjuke_rentout0913_0919.txt");	
			POI="";
		}
	}

}
