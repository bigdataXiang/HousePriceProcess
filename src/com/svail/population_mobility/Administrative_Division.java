package com.svail.population_mobility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class Administrative_Division {
	static Hashtable<String,String> hm = new Hashtable<String,String>();
	public static String Forder="D:/zhouxiang/人口数据/区划数据/test/8_hotel/";
	 public static void main(String[] args) throws IOException {
		// System.out.println(Hashtabe("431222"));
		 ID_Hashtable();
		 ID_Match(Forder+"1_hotel.txt");
		 
	 }
	 public static void ID_Hashtable(){
		 try
	    	{
	    		File file=new File("D:/zhouxiang/人口数据/区划数据/test/户口所在地行政区划代码_文本.txt");
	 	        FileInputStream fis=new FileInputStream(file);
	 	        InputStreamReader isr=new InputStreamReader(fis,"UTF-8");
	 	        BufferedReader reader=null;      
	 	        String tempString=null;
	    		
	    		System.out.println("以行为单位读取文件内容,一次读一行:");
			    reader = new BufferedReader(isr);  
			    while((tempString=reader.readLine())!=null){
			    	String[] code=tempString.split(",");
			    	String key=code[0].substring(0, 6);
			    	String key_value=code[1];
			    	hm.put(key, key_value);
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
			}
		 
	 }
	 public static String Hashtabe(String keyword)
	    {
	    	String value ="";
	    	String num;
	    	try
	    	{
	        	Set<String> keySet = hm.keySet();//在方法调用返回此映射中包含的键的set视图。
	        	Iterator<String> it = keySet.iterator();
	        	while(it.hasNext())
	        	{
	        		num = it.next();
		        	if(num.equals(keyword))
		        	{
		        		value = hm.get(num);
			        	//System.out.println(value);
		        	}
	        	}
	        }catch(NullPointerException e1){
	        		e1.printStackTrace();
	        		System.out.println(e1.getMessage());
	        } 
	    	return value;
	    }
	 public static void ID_Match(String folder){
		 try
	    	{
	    		File file=new File(folder);
	 	        FileInputStream fis=new FileInputStream(file);
	 	        InputStreamReader isr=new InputStreamReader(fis,"UTF-8");
	 	        BufferedReader reader=null;      
	 	        String tempString=null;

			    reader = new BufferedReader(isr);  
			    while((tempString=reader.readLine())!=null){

			    	 int n=0;
			    	 String TotalInfo="";
			    	 String [] result = tempString.split(",");
						for(int a = 0;a<result.length;a++){
						   //System.out.print(result[a]+"\r\n");
						   if(a==0){
							   TotalInfo+="<Name>"+result[a]+"</Name>";
						   }
						   if(a==1){
							   TotalInfo+="<CardNo>"+result[a]+"</CardNo>";
						   }
						   if(a==2){
							   TotalInfo+="<Descriot>"+result[a]+"</Descriot>";
						   }
						   if(a==3){
							   TotalInfo+="<CtfTp>"+result[a]+"</CtfTp>";
						   }
						   if(a==4&&result[a].length()>6){
							   if(result[a-1].equals("ID")){
								   String keyword=result[a].substring(0, 6);
								   String hometown=Hashtabe(keyword);
								   n=hometown.length();
								   TotalInfo+="<CtfId>"+result[a]+"</CtfId>"+"<Hometown>"+hometown+"</Hometown>";
								   if(n==0)
									   write_append(tempString,Forder+"Hometown_Null.txt");  
							   }else{
										   
										   write_append(tempString,Forder+"ID_Null.txt");
							   }
							     
						   }
						   if(a==4&&result[a].length()<6){
							   write_append(tempString,Forder+"ID_Null.txt");
						   }
						   if(a==5){
							   TotalInfo+="<Gender>"+result[a]+"</Gender>";
						   }
						   if(a==6){
							   TotalInfo+="<Birthday>"+result[a]+"</Birthday>";
						   }
						   if(a==7){
							   TotalInfo+="<Postal_Address>"+result[a]+"</Postal_Address>";
						   }
						   if(a==8){
							   TotalInfo+="<Zip>"+result[a]+"</Zip>";
						   }
						   if(a==9){
							   TotalInfo+="<Dirty>"+result[a]+"</Dirty>";
						   }
						   if(a==10){
							   TotalInfo+="<District1>"+result[a]+"</District1>";
						   }
						   if(a==11){
							   TotalInfo+="<District2>"+result[a]+"</District2>";
						   }
						   if(a==12){
							   TotalInfo+="<District3>"+result[a]+"</District3>";
						   }
						   if(a==13){
							   TotalInfo+="<District4>"+result[a]+"</District4>";
						   }
						   if(a==14){
							   TotalInfo+="<District5>"+result[a]+"</District5>";
						   }
						   if(a==15){
							   TotalInfo+="<District6>"+result[a]+"</District6>";
						   }
						   if(a==16){
							   TotalInfo+="<FirstNm>"+result[a]+"</FirstNm>";
						   }
						   if(a==17){
							   TotalInfo+="<LastNm>"+result[a]+"</LastNm>";
						   }
						   if(a==18){
							   TotalInfo+="<Duty>"+result[a]+"</Duty>";
						   }
						   if(a==19){
							   
							   TotalInfo+="<Mobile>"+result[a]+"</Mobile>";
								   
						   }
						   if(a==20){
							   TotalInfo+="<Tel>"+result[a]+"</Tel>";
						   }
						   if(a==21){
							   TotalInfo+="<Fax>"+result[a]+"</Fax>";
						   }
						   if(a==22){
							   TotalInfo+="<EMail>"+result[a]+"</EMail>";
						   }
						   if(a==23){
							   TotalInfo+="<Nation>"+result[a]+"</Nation>";
						   }
						   if(a==24){
							   TotalInfo+="<Taste>"+result[a]+"</Taste>";
						   }
						   if(a==25){
							   TotalInfo+="<Education>"+result[a]+"</Education>";
						   }
						   if(a==26){
							   TotalInfo+="<Company>"+result[a]+"</Company>";
						   }
						   if(a==27){
							   TotalInfo+="<CTel>"+result[a]+"</CTel>";
						   }
						   if(a==28){
							   TotalInfo+="<CAddress>"+result[a]+"</CAddress>";
						   }
						   if(a==29){
							   TotalInfo+="<CZip>"+result[a]+"</CZip>";
						   }
						   if(a==30){
							   TotalInfo+="<Family>"+result[a]+"</Family>";
						   }
						   if(a==31){
							   TotalInfo+="<Version>"+result[a]+"</Version>";
						   }
						   if(a==32){
							   TotalInfo+="<id>"+result[a]+"</id>";
						   }
						 }
						//&&当第一个条件不成之后，后面的条件都不执行了，而&则还是继续执行，直到整个条件语句执行完为止。
						if((n!=0)&&(result[7].length()>1)&&(result[3].equals("ID"))){
							write_append(tempString,Forder+"result_ok.txt");
							write_append(TotalInfo,Forder+"Result.txt");
							
							System.out.println(TotalInfo);
						}else if(!((n!=0)&&(result[7].length()>1)&&(result[3].equals("ID")))){
							write_append(tempString,Forder+"result_fail.txt");
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
	
}
