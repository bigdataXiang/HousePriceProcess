package com.svail.population_mobility;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.svail.util.FileTool;

public class CodeQuery{
	 public static void main(String[] args) throws IOException {
		 ID_Detect("D:/zhouxiang/人口数据/区划数据/test/4_hotel/problem/Hometown_Null.txt");
	 }
	 public static void ID_Detect(String folder){
		 try{
			    File file=new File(folder);
	 	        FileInputStream fis=new FileInputStream(file);
	 	        InputStreamReader isr=new InputStreamReader(fis,"UTF-8");
	 	        BufferedReader reader=null;      
	 	        String tempString=null;
                String Addr="";
                String code="";
			    reader = new BufferedReader(isr);  
			    while((tempString=reader.readLine())!=null){
			 
			    	 String [] result = tempString.split(",");
			    	 if(result[4].length()>=6){
			    		 code=result[4].substring(0, 6);
			    		 Addr=SiteCall.IDCardRequest(code);
			    		 if(Addr.length()!=0){
			    			 FileTool.Dump(code+","+Addr,folder.replace(".txt", "") + "_result.txt","UTF-8");
					    	 System.out.print(code+":"+Addr+"\r\n"); 
			    		 }else{
			    			 FileTool.Dump(code+","+Addr,folder.replace(".txt", "") + "_problemID.txt","UTF-8");
			    		 }
				    	 
			    	 }else{
			    		 FileTool.Dump(code+","+Addr,folder.replace(".txt", "") + "_invalidID.txt","UTF-8");
			    	 }
			    	}
			    reader.close();
		 }catch(NullPointerException e1){
     		e1.printStackTrace();
     		e1.getMessage();
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(ArrayIndexOutOfBoundsException e){
			//write_append(TotalInfo,Forder+"result_fail.csv");			
		}
     }
	}
