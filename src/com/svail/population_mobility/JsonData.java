package com.svail.population_mobility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.svail.test.Demo;
import com.svail.util.FileTool;
import com.svail.util.Tool;

public class JsonData {
	public String Code;
	public String CodeAddr;
	public String CodeCoor;
	public String CodeCoorLN;
	public String CodeCoorLA;
	public String CodeReg;
	public String Name;
	public String PostCoor;
	public String PostCoorLN;
	public String PostCoorLA;
	public String PostReg;
	public String CtfId;
	public String Gender;
	public String Birth;
	public String PostAddr;
	public String Mobile;
	public String PostCode;
	public String from;
	public String to;
	public String amounts;
	public String title;
	public String address;
	public String telephone;
	public String consumption;
	public String evaluation;
	public String service;
	public String brief;
	public String scode;
	public String sname;
	public String scoor;
	public String sreg;
	public String subcode;
	public static String Folder = "D:/人口数据/countFlowout.txt";

	public static void main(String[] args) {

		//productJson();
		countTo("D:/zhouxiang/人口数据/宾馆数据/人口统计/CodeResult .txt","D:/人口数据/countFlowin.txt");
		System.out.println("OK!");

	}
	public JsonData(String line) {
		/*
		if (line.indexOf("<title>") != -1){
			title=Tool.getStrByKey(line, "<title>", "</title>", "</title>");
		}
		if (line.indexOf("<address>") != -1){
			address=Tool.getStrByKey(line, "<address>", "</address>", "</address>");
		}
		if (line.indexOf("<telephone>") != -1){
			telephone=Tool.getStrByKey(line, "<telephone>", "</telephone>", "</telephone>");
		}
		if (line.indexOf("<consumption>") != -1){
			consumption=Tool.getStrByKey(line, "<consumption>", "</consumption>", "</consumption>");
		}
		if (line.indexOf("<evaluation>") != -1){
			evaluation=Tool.getStrByKey(line, "<evaluation>", "</evaluation>", "</evaluation>");
		}
		if (line.indexOf("<service>") != -1){
			service=Tool.getStrByKey(line, "<service>", "</service>", "</service>");
		}
		if (line.indexOf("<brief>") != -1){
			brief=Tool.getStrByKey(line, "<brief>", "</brief>", "</brief>");
		}
		*/
		if (line.indexOf("<from>") != -1)
			from = Tool.getStrByKey(line, "<from>", "</from>", "</from>").replace("锘?10000", "");
		if (line.indexOf("<to>") != -1)
			to = Tool.getStrByKey(line, "<to>", "</to>", "</to>").replace("锘?10000", "");
		if (line.indexOf("<amounts>") != -1)
			amounts = Tool.getStrByKey(line, "<amounts>", "</amounts>", "</amounts>");
		
		if (line.indexOf("<scode>") != -1)
			scode = Tool.getStrByKey(line, "<scode>", "</scode>", "</scode>");
		if (line.indexOf("<subcode>") != -1)
			subcode = Tool.getStrByKey(line, "<subcode>", "</subcode>", "</subcode>");
		if (line.indexOf("<sname>") != -1)
			sname = Tool.getStrByKey(line, "<sname>", "</sname>", "</sname>");
		if (line.indexOf("<scoor>") != -1)
			scoor = Tool.getStrByKey(line, "<scoor>", "</scoor>", "</scoor>");
		if (line.indexOf("<sreg>") != -1)
			sreg = Tool.getStrByKey(line, "<sreg>", "</sreg>", "</sreg>");
		

	}
	
	public static void countTo(String codefolder,String countfoder){
		try {
			File file = new File(codefolder);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = null;
			String tempString = null;
			reader = new BufferedReader(isr);
			while ((tempString = reader.readLine()) != null) {
				String code="";
			    Vector<String> countfile=FileTool.Load(countfoder, "utf-8");
			    for(int i=0;i<countfile.size();i++){
			    	String poi=countfile.elementAt(i);
			    	Demo demo = new Demo(poi);
			    	code=Tool.getStrByKey(tempString, "<Code>", "</Code>", "</Code>");
			    	if(code.equals(demo.to)){
                       // System.out.println("");
						FileTool.Dump(poi,countfoder.replace(".txt", "")+"-tidy.txt", "utf-8");	
			    	}
			    	
			    }
			    System.out.println("完成"+code+"的整理！");
			}
           reader.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	

	public static void productJson() {
		JSONObject jsonObj = new JSONObject();// 创建json格式的数据

		JSONArray jsonArr = new JSONArray();// json格式的数组

		try {
			Vector<String> rds = FileTool.Load(Folder, "UTF-8");
			for (int i = 0; i < rds.size(); i++) {
				String element = rds.elementAt(i);
				JsonData jsondata = new JsonData(element);
				JSONObject jsonObjArr = new JSONObject();

				jsonObjArr.put("from", jsondata.from);
				jsonObjArr.put("to", jsondata.to);
				jsonObjArr.put("amounts", jsondata.amounts);
				jsonArr.put(jsonObjArr);

			}

			// 将json格式的数据放到json格式的数组里

			// jsonObj.put("Points", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

			//System.out.println(jsonArr);
			System.out.println("开始写入txt中");
			FileTool.Dump(jsonArr.toString(), "D:/人口数据/FlowoutJson.txt", "utf-8");

		} catch (JSONException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}

}
