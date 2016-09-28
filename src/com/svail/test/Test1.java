package com.svail.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.JsonElement;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.svail.geotext.GeoQuery;
import com.svail.population_mobility.PeopleAddrMatch;
import com.svail.util.FileTool;
import com.svail.util.HTMLTool;
import com.svail.util.Tool;
public class Test1 {
	public static String folder1="D:/zhouxiang/人口数据/区划数据/test/1_hotel/resultAddr/";
	public static String folder2="D:/zhouxiang/人口数据/区划数据/test/2_hotel/resultAddr/";
	public static String folder3="D:/zhouxiang/人口数据/区划数据/test/3_hotel/resultAddr/";
	public static String folder4="D:/zhouxiang/人口数据/区划数据/test/4_hotel/resultAddr/";
	public static String folder5="D:/zhouxiang/人口数据/区划数据/test/5_hotel/resultAddr/";
	public static String folder6="D:/zhouxiang/人口数据/区划数据/test/6_hotel/resultAddr/";
	public static String folder7="D:/zhouxiang/人口数据/区划数据/test/7_hotel/resultAddr/";
	public static String folder8="D:/zhouxiang/人口数据/区划数据/test/8_hotel/resultAddr/";
	 public static void main(String argv[]) throws Exception{
		 Vector<String> pois=FileTool.Load("D:/Alibaba/alibabatag.txt", "utf-8");
		 for(int i=0;i<pois.size();i++){
			 String poi=pois.elementAt(i);
			 String str="\""+poi+"\""+",";
			 FileTool.Dump(str, "D:/Alibaba/alibabatag1.txt", "utf-8");
		 }
	 }
    public static void DataLean(String folder){
        try{
            File file = new File(folder+"Result.txt");
    		FileInputStream fis = new FileInputStream(file);
    		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
    		BufferedReader reader = null;
    		String rs = null;
            reader = new BufferedReader(isr);
    		try {
				while ((rs = reader.readLine()) != null) {
					String str1="";
					String str2="";
					String str3="";
					String str4="";
					String str5="";
					String str6="";
					String str7="";
					String str8="";
					String str9="";
					String str10="";
					String total="";
					rs=rs.replaceAll("\\s*", "").replaceAll(" ", "");
					if(rs.indexOf("</Name>")!=-1){
						str1=Tool.getStrByKey(rs,"<Name>","</Name>","</Name>").replace(" ", "");
						total+="<Name>"+str1+"</Name>";
					}
					if(rs.indexOf("</CtfTp>")!=-1){
						str2=Tool.getStrByKey(rs,"<CtfTp>","</CtfTp>","</CtfTp>").replace(" ", "");
						total+="<CtfTp>"+str2+"</CtfTp>";
					}
					if(rs.indexOf("</CtfId>")!=-1){
						str3=Tool.getStrByKey(rs,"<CtfId>","</CtfId>","</CtfId>").replace(" ", "");
						total+="<CtfId>"+str3+"</CtfId>";
					}
					if(rs.indexOf("<Hometown>")!=-1){
						str4=Tool.getStrByKey(rs,"<Hometown>","<Hometown>","<Hometown>").replace(" ", "");
						if(str4!=null)
							total+="<Home>"+str4+"</Home>";
						else
							FileTool.Dump(rs, file+"nohometown.txt", "utf-8");
					}
				   if(rs.indexOf("</Gender>")!=-1){
					   str5=Tool.getStrByKey(rs,"<Gender>","</Gender>","</Gender>").replace(" ", "");
					   total+="<Gender>"+str5+"</Gender>";
					}
					if(rs.indexOf("</Birthday>")!=-1){
					   str6=Tool.getStrByKey(rs,"<Birthday>","</Birthday>","</Birthday>").replace(" ", "");
					   total+="<Birth>"+str6+"</<Birth>";
					}
					if(rs.indexOf("</Postal_Address>")!=-1){
					   str7=Tool.getStrByKey(rs,"<Postal_Address>","</Postal_Address>","</Postal_Address>").replace(" ", "");
					   if(str7.equals("no")){
						   FileTool.Dump(rs, folder+"nopostaddr.txt", "utf-8");
						   continue;
					   }
					   else
						  total+="<PostAddr>"+str7+"</PostAddr>";
					}
					if(rs.indexOf("</Mobile>")!=-1){
					   str8=Tool.getStrByKey(rs,"<Mobile>","</Mobile>","</Mobile>").replace(" ", "");
					   total+="<Mobile>"+str8+"</Mobile>";
					}
					if(rs.indexOf("</Nation>")!=-1){
					   str9=Tool.getStrByKey(rs,"<Nation>","</Nation>","</Nation>").replace(" ", "");
					   total+="<Nation>"+str9+"</Nation>";
					}
					if(rs.indexOf("</Version>")!=-1){
					   str10=Tool.getStrByKey(rs,"<Version>","</Version>","</Version>").replace(" ", "");
					   total+="<Version>"+str10+"</Version>";
					}
					FileTool.Dump(total.replace(" ", ""),folder+ "ResultLean.txt", "UTF-8");
					System.out.println("ok");		
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       }catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}catch(NullPointerException e){
		e.printStackTrace();
		
	}
        
    }
}
