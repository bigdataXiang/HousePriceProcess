package com.svail.tomongo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;   
import java.util.Calendar;
import java.util.Date; 
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;

public class DateTransfer {
	public static void main(String[] args) throws ParseException, UnknownHostException, MongoException {
		String time="2015/07/03 15:55:43";
		//2015-06-23 14:43:04
		//2015-7-20 13:01:19
	    int[] date=getDate(time);
	    System.out.println(date[0]);
	    System.out.println(date[1]);
		}
		
	@SuppressWarnings("null")
	public static int[] getDate(String date)
	{
		String str3="";
		int[] Date = new int[2];
		String time_str="";
		DateFormat format = null;
		if (date.indexOf('-') != -1)
			format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //小写的mm表示的是分钟   
		else
			format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"); //小写的mm表示的是分钟   
	     Date d= null;  
	     try {  
	             d= format.parse(date);  
	         } catch (Exception e) {  
	             // TODO Auto-generated catch block  
	             e.printStackTrace();  
	       }
	     try
	     {
	       SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy");
		   String str0 = sdf0.format(d);
		   SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
		   String str1 = sdf1.format(d);	   
		   SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
		   String str2 = sdf2.format(d);
		   
		   int i=0;
		   int n;
		   String temp1="";
		   String temp2="";
		   //System.out.println("取得的时为："+str3);
		   if(str1.startsWith("0"))
		   {
			   temp1=str1.replace("0","");
			   Date[0]=Integer.parseInt(temp1);
		   } else{
			   Date[0]=Integer.parseInt(str1);
		   }
		   if(str2.startsWith("0"))
		   {
			   temp2=str2.replace("0","");
			   Date[1]=Integer.parseInt(temp2);
		   } else{
			   Date[1]=Integer.parseInt(str2);
		   }
	     }catch (NumberFormatException e) {  
             // TODO Auto-generated catch block  
             e.printStackTrace();  
       }
	     return Date;
	}
	public static String date_transfer(String date)
	{
		String str3="";
		String[] Date = new String[2];
		String time_str="";
		DateFormat format = null;
		if (date.indexOf('-') != -1)
			format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //小写的mm表示的是分钟   
		else
			format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"); //小写的mm表示的是分钟   
	     Date d= null;  
	     try {  
	             d= format.parse(date);  
	         } catch (Exception e) {  
	             // TODO Auto-generated catch block  
	             e.printStackTrace();  
	       }
	     try
	     {
	       SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy");
		   String str0 = sdf0.format(d);
		   SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
		   String str1 = sdf1.format(d);
		   Date[0]=str1;
		   
		   SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
		   String str2 = sdf2.format(d);
		   Date[1]=str2;
		   
		   SimpleDateFormat sdf4 = new SimpleDateFormat("mm");
		   String str4 = sdf4.format(d);
		   SimpleDateFormat sdf5 = new SimpleDateFormat("ss");
		   String str5 = sdf5.format(d);
		   int i=0;
		   int n;
		   String temp="";
		   i=date.indexOf(" ");
		   str3=date.substring(i+" ".length(),i+" ".length()+2);
		   //System.out.println("取得的时为："+str3);
		   if(str3.startsWith("0"))
		   {
			   temp=str3.substring(1, 2);
			   n=Integer.parseInt(temp);
		   }
		   else if(str3.endsWith(":"))
		   {
			   temp=str3.substring(0, 1);
			   n=Integer.parseInt(temp);
		   }
		   else
		   {
		       n=Integer.parseInt(str3);
		   }
	       n=n+8;
		   String re_str3=Integer.toString(n); 
		   time_str=str0+"/"+str1+"/"+str2+" "+re_str3+":"+str4+":"+str5;
	     }catch (NumberFormatException e) {  
             // TODO Auto-generated catch block  
             e.printStackTrace();  
       }
	     return time_str;
	}
	

	
		  

}
