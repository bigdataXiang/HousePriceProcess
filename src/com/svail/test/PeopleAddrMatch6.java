package com.svail.test;

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
import com.svail.util.FileTool;
import com.svail.util.HTMLTool;

public class PeopleAddrMatch6{
	 static String Folder="D:/zhouxiang/人口数据/区划数据/test/2_hotel/resultAddr/Result_3/Result_3.txt";
	 public static void main(String argv[]) throws Exception{
		 //parseLngLat("太原");
		 processCSV(Folder);
		// parseLngLat("北京地理所");
		
	 }
	 public static void processCSV(String file) throws IOException
		{
			
			Vector<String> pois = FileTool.Load(file, "utf-8");
			String request ="http://192.168.6.9:8080/p41?f=json";
			//http://geocode.svail.com:8080/p41?f=xml
			//http://192.168.6.9:8080/p41?f=json
			String parameters = "&key=206DA5B15B5211E5BFE0B8CA3AF38727&queryStr=";

			boolean batch = true;
			Gson gson = new Gson();
			if (batch)
				request = "http://192.168.6.9:8080/p4b?";
			StringBuffer sb = new StringBuffer();
			int offset = 0;
			String poi="";
			int count = 0;
			Vector<String> validpois = new Vector<String>();
			for (int n = 0; n < pois.size(); n ++) {
				if (batch) {
					
					String rs = pois.get(n);
					String addr0=getStrByKey(rs,"<Postal_Address>","</Postal_Address>").replace(" ", "");//"<Hometown>","<Hometown>"
					//String addr1=getStrByKey(rs,"<Postal_Address>","</Postal_Address>").replace(" ", "");
					validpois.add(rs);
					count ++;
					sb.append(addr0).append("\n");
					if (((count == 1000) ||  n == pois.size() - 1)) {
						String urlParameters = sb.toString();
						System.out.print("批量处理开始：");
						count = 0;
						byte[] postData;
						try {
							postData = (parameters + java.net.URLEncoder.encode(urlParameters,"UTF-8")).getBytes(Charset.forName("UTF-8"));
							int postDataLength = postData.length;
					            
							URL url = new URL(request);
							//System.out.println(request + urlParameters);
							HttpURLConnection cox = (HttpURLConnection) url.openConnection();
							cox.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; rv:11.0) like Gecko");
							cox.setDoOutput(true);
							cox.setDoInput(true);
							cox.setInstanceFollowRedirects(false);
							cox.setRequestMethod("POST");
							// cox.setRequestProperty("Accept-Encoding", "gzip");  
							cox.setRequestProperty("Content-Type",
									"application/x-www-form-urlencoded");
							cox.setRequestProperty("charset", "utf-8");
							cox.setRequestProperty("Content-Length",
									Integer.toString(postDataLength));
							cox.setUseCaches(false);
							
							try (DataOutputStream wr = new DataOutputStream(
									cox.getOutputStream())) {
								
								wr.write(postData);
								
								InputStream is = cox.getInputStream();
								if (is != null) {
									byte[] header = new byte[2];
									BufferedInputStream bis = new BufferedInputStream(is);
									bis.mark(2);
									int result = bis.read(header);

									// reset输入流到开始位置
									bis.reset();
									BufferedReader reader = null;
									// 判断是否是GZIP格式
									int ss = (header[0] & 0xff) | ((header[1] & 0xff) << 8);
									if (result != -1 && ss == GZIPInputStream.GZIP_MAGIC) {
										// System.out.println("为数据压缩格式...");
										reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(bis), "utf-8"));
									} else {
										// 取前两个字节
										reader = new BufferedReader(new InputStreamReader(bis, "utf-8"));
									}
									
									// 创建一个JsonParser
									JsonParser parser = new JsonParser();
									String txt ="";
									//通过JsonParser对象可以把json格式的字符串解析成一个JsonElement对象
									try {
										
										txt = reader.readLine();
										if (txt == null) {
											System.out.println("txt为null！");
											for(int i=0;i<validpois.size();i++){
												FileTool.Dump(validpois.get(i), file.replace(".txt", "") + "_NullException.txt", "UTF-8");
												
											}
										}
										else {
											int index1=txt .indexOf("chinesename");
											String index3=",}";
											if(index1!=-1&&index3!=null)
												txt =txt .replace(",}", "}");
											 JsonElement el = parser.parse(txt);
											// JsonElement el = parser.parse(tesobj.toString());
											//把JsonElement对象转换成JsonObject
											JsonObject jsonObj = null;
											if(el.isJsonObject())
											{
												jsonObj = el.getAsJsonObject();
												//System.out.println(jsonObj);
	  										    GeoQuery gq = gson.fromJson(jsonObj, GeoQuery.class);
												String lnglat = "";
												String Admin="";
												if (gq != null && gq.getResult() != null && gq.getResult().size() > 0)
												{
													System.out.println("这批数据没有问题！");
													for (int m = 0; m < gq.getResult().size(); m ++)
													{
														if (gq.getResult().get(m) != null && gq.getResult().get(m).getLocation() != null)
														{
															if(gq.getResult().get(m).getLocation().getRegion()!=null)
															Admin="<Postal_Admin>"+gq.getResult().get(m).getLocation().getRegion().getProvince()+","+gq.getResult().get(m).getLocation().getRegion().getCity()+","+gq.getResult().get(m).getLocation().getRegion().getCounty()+","+gq.getResult().get(m).getLocation().getRegion().getTown()+"</Postal_Admin>";
															else
																Admin="暂无";
															//System.out.println(Admin);
															lnglat = "<Postal_Coor>" + gq.getResult().get(m).getLocation().getLng() + ";" + gq.getResult().get(m).getLocation().getLat()+"</Postal_Coor>";
															String poitemp= validpois.elementAt(m);
											                poi=poitemp.substring(0, poitemp.indexOf("</Name>")+"</Name>".length())+lnglat+Admin+poitemp.substring(poitemp.indexOf("</Name>")+"</Name>".length());//,poitemp.indexOf("</id>")+"</id>".length()
															FileTool.Dump(poi, file.replace(".txt", "") + "_result.txt", "UTF-8");
															//System.out.println(poi);
															
														}
														else
														{
															FileTool.Dump(validpois.elementAt(m), file.replace(".txt", "") + "_nonPostalCoor1.txt", "UTF-8");
															//System.out.print(validpois.elementAt(m));
														}
													}
												}
											}
										}

									}catch (JsonSyntaxException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										System.out.println(e.getMessage());
										System.out.println("存在JsonSyntaxException异常！");
										for(int i=0;i<validpois.size();i++){
											FileTool.Dump(validpois.get(i), file.replace(".txt", "") + "_JsonSyntax.txt", "UTF-8");
											
										}
										FileTool.Dump(txt, file.replace(".txt", "") + "_JsonSyntaxException.txt", "UTF-8");
									}

								}
							}

						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}catch(NullPointerException e){
							e.printStackTrace();
							FileTool.Dump(poi, file.replace(".txt", "") + "_PostalNull1.txt", "UTF-8");
						}

						validpois.clear();
						sb.setLength(0);
					}

				} else {

					try {
						
						String xml ="";
						String rs = pois.get(n);
						String addr0=getStrByKey(rs,"<Postal_Address>","</Postal_Address>").replace(" ", "");
						//String addr1=getStrByKey(rs,"<Postal_Address>","</Postal_Address>").replace(" ", "");
						
						xml = parseLngLat(addr0,Folder);
						poi=rs.substring(0, rs.indexOf("</Name>")+"</Name>".length())+xml+rs.substring(rs.indexOf("</Name>")+"</Name>".length());//,rs.indexOf("</id>")+"</id>".length()
						if (xml != null)
						{
							FileTool.Dump(poi,file.replace(".txt", "") + "_result.txt", "UTF-8");
							System.out.println("Line " + n + " [" + xml + "]");							
						}
						else
							FileTool.Dump(poi,file.replace(".txt", "") + "_nonPostalCoor2", "UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}catch(NullPointerException e){
						e.printStackTrace();
						FileTool.Dump(poi, file.replace(".txt", "") + "_PostalNull2.txt", "UTF-8");
					}				
				}
			}
			
		}

	public static String getStrByKey(String sContent, String sStart, String sEnd) {
		String sOut ="";
		try{
			
			int fromIndex = 0;
			int iBegin = 0;
			int iEnd = 0;
			int iStart=sContent.indexOf("</Postal_Address>");
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
			
		}catch(NullPointerException e){
			e.printStackTrace();
			FileTool.Dump(sContent, Folder.replace(".txt", "") + "PostalNull.txt", "UTF-8");
		}	
		
		  return sOut;
	}
	
	public static String parseLngLat(String query,String folder) throws UnsupportedEncodingException{
		String request = "http://192.168.6.9:8080/p41?f=json";
		String parameters ="&key=206DA5B15B5211E5BFE0B8CA3AF38727&queryStr=";

		Gson gson = new Gson();
		String lnglat = "";
		String admin="";
		String uri = null;
		try {
			uri = request + parameters+ java.net.URLEncoder.encode(query, "UTF-8");
			String xml = HTMLTool.fetchURL(uri, "UTF-8", "post");
			//{"status":"OK","total":1,"result":[{{"status":"OK","query_string":"南京","location":{"chinesename":"南京","englishname":"Nanjing","lng":118.7689056,"lat":32.04836655,}}}
			//xml="{'status':'OK','total':1,'result':[{{'status':'OK','query_string':'南京','location':{'chinesename':'南京','englishname':'Nanjing','lng':118.7689056,'lat':32.04836655,}}}";
			//
			if (xml.length()!=0)
			{
				int index1=xml.indexOf("chinesename");
				String index2="result':[{{'";
				String index3=",}}}";
				if(index1!=-1&&index2!=null&&index3!=null)
					xml=xml.replace("result':[{{'", "result':[{'").replace(",}}}", "}}]}");
				// 创建一个JsonParser
				JsonParser parser = new JsonParser();
		
				//通过JsonParser对象可以把json格式的字符串解析成一个JsonElement对象
				try {
					JsonElement el = parser.parse(xml);

					//把JsonElement对象转换成JsonObject
					JsonObject jsonObj = null;
					if(el.isJsonObject())
					{
						jsonObj = el.getAsJsonObject();
						GeoQuery gq = gson.fromJson(jsonObj, GeoQuery.class);
						
						if (gq != null && gq.getResult() != null && gq.getResult().size() > 0 && gq.getResult().get(0).getLocation() != null)
						{
							if(gq.getResult().get(0).getLocation().getRegion()!=null)
							admin="<Postal_Admin>"+gq.getResult().get(0).getLocation().getRegion().getProvince()+","+gq.getResult().get(0).getLocation().getRegion().getCity()+","+gq.getResult().get(0).getLocation().getRegion().getCounty()+","+gq.getResult().get(0).getLocation().getRegion().getTown()+"</Postal_Admin>";
							else
								admin="暂无";
							lnglat ="<Postal_Coor>"+gq.getResult().get(0).getLocation().getLng() + ";" + gq.getResult().get(0).getLocation().getLat()+ "</Postal_Coor>" ;
							
						}
					}
					
					
				}catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(e.getMessage());
					System.out.println("存在JsonSyntaxException异常！");
					FileTool.Dump(xml, folder.replace(".txt", "") + "_JsonSyntax.txt", "UTF-8");		
				}
           }
			return lnglat+admin;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		return null;
	}
	
}
