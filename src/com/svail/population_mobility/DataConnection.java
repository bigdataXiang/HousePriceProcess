package com.svail.population_mobility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import com.svail.util.FileTool;
import com.svail.util.Tool;

public class DataConnection {
	static Hashtable<String,String> hm = new Hashtable<String,String>();
	public static String Forder1="D:/zhouxiang/人口数据/宾馆数据/精简版需要地址匹配的数据/1_hotel/";
	public static String Forder2="D:/zhouxiang/人口数据/宾馆数据/精简版需要地址匹配的数据/2_hotel/";
	public static String Forder3="D:/zhouxiang/人口数据/宾馆数据/精简版需要地址匹配的数据/3_hotel/";
	public static String Forder4="D:/zhouxiang/人口数据/宾馆数据/精简版需要地址匹配的数据/4_hotel/";
	public static String Forder5="D:/zhouxiang/人口数据/宾馆数据/精简版需要地址匹配的数据/5_hotel/";
	public static String Forder6="D:/zhouxiang/人口数据/宾馆数据/精简版需要地址匹配的数据/6_hotel/";
	public static String Forder7="D:/zhouxiang/人口数据/宾馆数据/精简版需要地址匹配的数据/7_hotel/";
	public static String Forder8="D:/zhouxiang/人口数据/宾馆数据/精简版需要地址匹配的数据/8_hotel/";
	 public static void main(String[] args) throws IOException {
		
		 ID_Hashtable();
		 DataConect(Forder1+"ResultLean.txt");
		 DataConect(Forder2+"ResultLean.txt");
		 DataConect(Forder3+"ResultLean.txt");
		 DataConect(Forder4+"ResultLean.txt");
		 DataConect(Forder5+"ResultLean.txt");
		 DataConect(Forder6+"ResultLean.txt");
		 DataConect(Forder7+"ResultLean.txt");
		 DataConect(Forder8+"ResultLean.txt");
		 System.out.println("OK!");
			 
		 }
	 public static void ID_Hashtable(){
         try
	    	{
	    		File file=new File("D:/zhouxiang/人口数据/宾馆数据/行政区划代码/CodeResult.txt");
	 	        FileInputStream fis=new FileInputStream(file);
	 	        InputStreamReader isr=new InputStreamReader(fis,"UTF-8");
	 	        BufferedReader reader=null;      
	 	        String tempString=null;
	    		
	    		System.out.println("以行为单位读取文件内容,一次读一行:");
			    reader = new BufferedReader(isr);  
			    while((tempString=reader.readLine())!=null){
			    	String key=Tool.getStrByKey(tempString, "Code>", "</Code>", "</Code>");
			    	String key_value=tempString;
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
	 public static void DataConect(String folder){

		 try
	    	{
	    		File file=new File(folder);
	 	        FileInputStream fis=new FileInputStream(file);
	 	        InputStreamReader isr=new InputStreamReader(fis,"UTF-8");
	 	        BufferedReader reader=null;      
	 	        String tempString=null;

			    reader = new BufferedReader(isr);  
			    while((tempString=reader.readLine())!=null){
			    	String code=Tool.getStrByKey(tempString, "<CtfId>", "</CtfId>", "</CtfId>").substring(0, 6);
			    	String temp=Hashtabe(code);
			    	String poi=tempString.substring(0, tempString.indexOf("</Name>")+"</Name>".length())+temp+tempString.substring(tempString.indexOf("</Name>")+"</Name>".length());
			    	FileTool.Dump(poi.replaceAll("\\s*", "").replaceAll(" ", ""), folder.replace("ResultLean.txt", "")+"ResultLean_OK.txt", "utf-8");
			    	
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
	 public static void ID_Match(String folder,String folder1){

		 try
	    	{
	    		File file=new File(folder);
	 	        FileInputStream fis=new FileInputStream(file);
	 	        InputStreamReader isr=new InputStreamReader(fis,"UTF-8");
	 	        BufferedReader reader=null;      
	 	        String tempString=null;

			    reader = new BufferedReader(isr); 
			    int count_ok=0;
			    int count_fail=0;
			    int count=0;
			    while((tempString=reader.readLine())!=null){
                   count++;
			    	String temp=""; 
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
						   
						   if(a==4&&result[a].length()>6&&!(result[a].matches(".*[^a-zA-Z0-9]+.*"))){
							      // System.out.println(result[a].matches(".*[^a-zA-Z0-9]+.*"));
							       String keyword=result[4].substring(0, 6);
								   temp=Hashtabe(keyword);
								   String hometown=Tool.getStrByKey(temp, "AdminAddr>", "</AdminAddr", "Hukou_Admin");
								   if(hometown!=null)
								       TotalInfo+="<CtfId>"+result[a]+"</CtfId>"+"<Hometown>"+hometown+"</Hometown>";
								   else
									   Tool.write_append(tempString,folder1+"Hometown_Null.txt");   
							     
						   }else if(a==4){
							   Tool.write_append(tempString,folder+"invalidID.txt");
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
						
 						if((result[7].length()>1)&&temp.length()>0){
							Tool.write_append(tempString,folder1+"result_ok.txt");
							count_ok++;
							String str=TotalInfo.substring(0, TotalInfo.indexOf("/Name>")+"/Name>".length());
							TotalInfo="<POI>"+str+temp+TotalInfo.substring(TotalInfo.indexOf("</Name>")+"</Name>".length())+"</POI>";
							Tool.write_append(TotalInfo,folder1+"Result.txt");
							
							System.out.println(TotalInfo);
						}else{
							Tool.write_append(tempString,folder1+"result_fail.txt");
							count_fail++;
						}
			    }
			     System.out.println(count);
	        	 System.out.println(count_ok);
	        	 System.out.println(count_fail);
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


