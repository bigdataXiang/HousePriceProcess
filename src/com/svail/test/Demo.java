package com.svail.test;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import org.htmlparser.util.ParserException;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;

import com.svail.chongqing.GetJsonText;
import com.svail.population_mobility.CountyPopulation;
import com.svail.population_mobility.JsonData;
import com.svail.population_mobility.PopulationStatistics;
import com.svail.util.FileTool;
import com.svail.util.Tool;

public class Demo {
	public static String code;
	public static String countyname;
	public static String countyLN;
	public static String countyLA;
	public static String countyCoor;
	public static String folder = "D:/人口数据/";
	public String PostCode;
	public String Code;
	public String to;
	public String from;
	public String amounts;

	public static void main(String argv[]) throws Exception {

		// fileCut(710662,"D:/人口数据/2-postCode.txt");
		// System.out.println("导入每个区划的数据：");
		// setCounty();

		// System.out.println("开始生成个人postCode数据：");
		// for(int i=1;i<=88;i++){
		// String f=folder+i+".txt";
		// ClassifyStatistic(f);
		// }
		// countTo("D:/zhouxiang/人口数据/宾馆数据/人口统计/CodeResult.txt","D:/人口数据/3级数据-统计人口流入流出数据/countFlowout.txt");
		// countAmount("D:/人口数据/countFlowout-tidy.txt");
		// productJson("D:/人口数据/countFlowin-tidy-countAmounts.txt");
		// delectMistake("D:/Crawldata_BeiJing/fang/resold/0314/fang_resold0108_zhoubian/fang_resold0108_zhoubian.txt");
		// Check_countFlowout_tidy("D:/人口数据/3级数据-统计人口流入流出数据/countFlowout-tidy.txt");

		// Check_countFlowout("D:/人口数据/3级数据-统计人口流入流出数据/countFlowout.txt");
		//CompareAmouts("D:/人口数据/3级数据-统计人口流入流出数据/key2.txt","D:/人口数据/3级数据-统计人口流入流出数据/countFlowout-tidy-Check.txt");
		//PopulationStatistics.getSortFlow("D:/人口数据/4级数据-统计某区县流入或者流出的另一区县的总人口/countFlowin-tidy-countAmounts.txt");
		//PopulationStatistics.countTotalAmount("D:/人口数据/5级数据-将每个区县的流动人口数据进行排序/countFlowin-tidy-countAmounts-sort.txt");
		//getSortFlow("D:/人口数据/4级数据-统计某区县流入或者流出的另一区县的总人口/countFlowin-tidy-countAmounts.txt");
		
		//Vector<String> increment=FileTool.CompareFile("D:/人口数据/4级数据-统计某区县流入或者流出的另一区县的总人口/countFlowin-tidy-countAmounts-sort.txt", "D:/人口数据/4级数据-统计某区县流入或者流出的另一区县的总人口/countFlowin-tidy-countAmounts-sort1.txt");
		
		//countTotalAmount("D:/人口数据/5级数据-将每个区县的流动人口数据进行排序/countFlowout-tidy-countAmounts-sort.txt");

		//countAmount("D:/人口数据/16级数据-又重新统计替换后的数据的amount数目/核查/countFlowin-gatherBigCity.txt");
		
		//getSortFlow("D:/人口数据/17级数据-将统计后的数据排序/countFlowin-gatherBigCity-countAmounts.txt");
		
		//creatlist("D:/MyDownloads/b.txt");
		
		//checkLeakCode("D:/人口数据/18级数据-将排序后的数据提取主方向/2015行政区划代码.txt","D:/人口数据/18级数据-将排序后的数据提取主方向/countFlowout-gatherBigCity-countAmounts-sort.txt");
		
		//maptogether("D:/人口数据/18级数据-将排序后的数据提取主方向/countFlowin-gatherBigCity-countAmounts-sort-fromtomap.txt","D:/人口数据/18级数据-将排序后的数据提取主方向/countFlowout-gatherBigCity-countAmounts-sort-fromtomap.txt");
		
		//checkOldCode("D:/人口数据/18级数据-将排序后的数据提取主方向/check_oldcode/合并后的problemcode.txt","D:/人口数据/18级数据-将排序后的数据提取主方向/check_oldcode/CodeResult.txt");
		
		//countFrom("D:/人口数据/0414重新处理/3级数据-将大城市的区县cede合并/14年全国行政区划代码.txt","D:/人口数据/0414重新处理/3级数据-将大城市的区县cede合并/countFlowout-NewCode-replaced.txt");
		
		//countAmount("D:\\人口数据\\0414重新处理\\5级数据-将排完序的数据进行统计\\countFlowin-NewCode-replaced-tidy.txt");
		
		//getSortFlow("D:\\人口数据\\0414重新处理\\6级数据-对统计后的数据进行排序\\countFlowin-NewCode-replaced-tidy-countAmounts.txt");
		
		//mergeStandarCode("D:/人口数据/0414重新处理/13级数据-将主城区code进行合并/2014CodeStand.txt","D:/人口数据/0414重新处理/13级数据-将主城区code进行合并/省会-地级市-合并code.txt");
		
		//countTo("D:/人口数据/0414重新处理/13级数据-将主城区code进行合并/2014CodeStand.txt","D:/人口数据/0414重新处理/13级数据-将主城区code进行合并/countFlowout-NewCode-replaced.txt");
		
		//PopulationStatistics.replaceCode("D:/人口数据/0414重新处理/13级数据-将主城区code进行合并/省会-地级市-合并code.txt","D:/人口数据/0414重新处理/13级数据-将主城区code进行合并/countFlowout-NewCode.txt");
		//countAmount("D:/人口数据/0414重新处理/14级数据-将合并后的数据逐个区县地统计/countFlowin-NewCode-replaced-tidy.txt");
		
		//GetJsonText.jsonAddressMatch("D:/baidu/tizhigaige/tizhigaige-Result-delectRedundancy.txt");
		//addQuotes("D:/Alibaba/alibabatag2.txt");

		//getSA2Link("D:\\SA-2\\SA-2.txt");
		//getRemoteSensingId("D:\\SA-2\\SA-2-json.txt");
		getRemoteSensingLink("D:\\SA-2\\SA-2-json.txt-uuid.txt");
		System.out.println("OK!");
	}
	public static void getSA2Link(String folder){
		Vector<String> pois= FileTool.Load(folder,"utf-8");
		String poi=pois.elementAt(0);
		JSONArray array=JSONArray.fromObject(poi);
		for(int i=0;i<array.size();i++){
			JSONObject obj= (JSONObject) array.get(i);
			//System.out.println(obj.toString());
			FileTool.Dump(obj.toString(),"D:\\SA-2-json.txt","utf-8");

		}

	}
	public static void getRemoteSensingLink(String folder){
		Vector<String> pois= FileTool.Load(folder,"utf-8");
		int nn=pois.size();
		for(int i=0;i<nn;i++){
			String poi=pois.elementAt(i);
			String url="https://scihub.copernicus.eu/dhus/odata/v1/Products('"+poi+"')/$value";//url.txt
			FileTool.Dump(url,folder+"url.txt","utf-8");

		}


	}
	public static void getRemoteSensingId(String folder){
		try {
			Vector<String> pois= FileTool.Load(folder,"utf-8");
			int nn=pois.size();
			for(int i=0;i<nn;i++){
				String poi=pois.elementAt(i);
				JSONObject object=JSONObject.fromObject(poi);
				String id=object.get("uuid").toString();
				System.out.println(id);
				FileTool.Dump(id.toString(),folder+"-uuid.txt","utf-8");

			}


		}catch (NullPointerException e) {
			System.out.println(e.getMessage());
			//FileTool.Dump(url, folder + "NullLink.txt", "utf-8");
		}
	}

	public static void addQuotes(String file){
       Vector<String> pois= FileTool.Load(file,"utf-8");
		String str="";
		for(int i=0;i<pois.size();i++){
			String poi=pois.elementAt(i);
			str+="\""+poi+"\""+",";
		}
		FileTool.Dump(str,file.replace(".txt","")+".tidy.txt","utf-8");
	}
	
	/**
	 * 将“2014CodeStand.txt”中已经合并了的区县换成合并后的代码
	 * @param source ： 2014CodeStand.txt
	 * @param reference ：省会-地级市-合并code.txt
	 */
	public static void mergeStandarCode(String source,String reference){
		Vector<String> Source= FileTool.Load(source, "utf-8");
		Vector<String> Reference = FileTool.Load(reference, "utf-8");
		
		Map<String, String> map = new HashMap<String, String>();
		for (int k = 0; k < Reference.size(); k++) {
			String poi = Reference.elementAt(k);
			String scode= Tool.getStrByKey(poi, "<scode>", "</scode>", "</scode>");
			String subcode=Tool.getStrByKey(poi, "<subcode>", "</subcode>", "</subcode>");
			map.put(scode, subcode);
		}
		
		for(int i=0;i<Source.size();i++){
			String poi=Source.elementAt(i);
			String scode = Tool.getStrByKey(poi, "<scode>", "</scode>", "</scode>");
			
			for (Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey().toString();
				String value = entry.getValue();
				if(scode.equals(key)){
					scode=value;
				}
			}
			
			String str="<code>"+scode+"</code>"+poi.substring(poi.indexOf("</scode>")+"</scode>".length());
			//System.out.println(str);
			FileTool.Dump(str, source.replace(".txt", "") + "-replaced.txt", "utf-8");
			
			
		}
	}
	/**
	 * 按照区县顺序整理从某个区县流动到另外一个区县的记录，这里不包括同一目的地的数量叠加
	 * 
	 * @param codefolder
	 *            有每个区县code的CodeResult.txt文件
	 * @param countfoder
	 *            需要整理的文件的路径
	 */
	//countTo("D:/人口数据/0414重新处理/3级数据-将大城市的区县cede合并/14年全国行政区划代码.txt","D:/人口数据/0414重新处理/3级数据-将大城市的区县cede合并/countFlowin-NewCode-replaced.txt");
	public static void countFrom(String codefolder, String countfoder) {
		
			Vector<String> codefile = FileTool.Load(codefolder, "utf-8");

			// 读取CodeResult.txt中的一条数据：
			// <Code>110100</Code><CodeAddr>北京市</CodeAddr><CodeCoor>116.3847599;39.90230163</CodeCoor><CodeReg>北京市,null,null,null</CodeReg>
			for(int k=0;k<codefile.size();k++){
				String tempString=codefile.elementAt(k);
				String[] admin=tempString.split(",");
				String probe=admin[0];

				// 读取countFlowout.txt文件中的数据
				Vector<String> countfile = FileTool.Load(countfoder, "utf-8");
				// 从countFlowout.txt文件中的第一条记录开始判断比较
				for (int i = 0; i < countfile.size(); i++) {
					String poi = countfile.elementAt(i);
					String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
					//String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");

					// 如果CodeResult.txt中的code与countFlowout.txt中的from相同，则将countFlowout.txt中的poi写下来
					// 其实是按照CodeResult.txt中code顺序，将countFlowout.txt中的poi排列，先将code为110101的poi写下，再将code为110102的poi写下来
					if (from.equals(probe)) {
						System.out.println(poi);
						FileTool.Dump(poi, countfoder.replace(".txt", "") + "-tidy.txt", "utf-8");
					}

				}

			}
	}
	/**
	 * 找到旧code所对应的具体地址
	 * @param f1：合并后的problemcode.txt
	 * @param f2：CodeResult.txt
	 */
	public static void checkOldCode(String f1,String f2){
		Map<String,String> map = new HashMap<String,String>();
		Vector<String> code1=FileTool.Load(f1, "utf-8");
		Vector<String> code2=FileTool.Load(f2, "utf-8");
		
		for(int k=0;k<code2.size();k++){
			String poi=code2.elementAt(k);
			String code=Tool.getStrByKey(poi, "<Code>", "</Code>", "</Code");
			map.put(code, poi);
		}
		int count=0;
		for(int i=0;i<code1.size();i++){
			String code=code1.elementAt(i);
			if(map.get(code)!=null){
				System.out.println(code+":"+map.get(code));
				FileTool.Dump(map.get(code),f1.replace(".txt", "")+"-result.txt", "utf-8");
				count++;
			}
		}
		System.out.println(count);
		
	}
	/**
	 *将 countFlowin-gatherBigCity-countAmounts-sort-fromtomap.txt和countFlowout-gatherBigCity-countAmounts-sort-fromtomap.txt合并
	 *生成“合并后的problemcode.txt”
	 * @param f1：countFlowin-gatherBigCity-countAmounts-sort-fromtomap.txt
	 * @param f2：countFlowout-gatherBigCity-countAmounts-sort-fromtomap.txt
	 */
	public static void maptogether(String f1,String f2){
		Map<String,Integer> map = new HashMap<String,Integer>();
		Vector<String> code1=FileTool.Load(f1, "utf-8");
		Vector<String> code2=FileTool.Load(f2, "utf-8");
		for(int i=0;i<code1.size();i++){
			map.put(code1.elementAt(i), i);
		}
        for(int i=0;i<code2.size();i++){
        	map.put(code2.elementAt(i), i);
		}
        for (Entry<String, Integer> entry : map.entrySet()) {
			System.out.println(entry.getKey());
			FileTool.Dump(entry.getKey(),"D:/人口数据/18级数据-将排序后的数据提取主方向/合并后的problemcode.txt", "utf-8");
		}
	}
	/**
	 * 检查pengdingcode文件中的不符合standcode中的code
	 * @param standcode 标准code系统，为2015行政区划代码
	 * @param pendingcode 需要核查code的文件
	 */
	public static void checkLeakCode(String standcode,String pendingcode){
		Map<String,String> map = new HashMap<String,String>();
		Map<String,String> fromtomap = new HashMap<String,String>();
		Map<String,String> tomap = new HashMap<String,String>();
		Vector<String> Standcode=FileTool.Load(standcode, "utf-8");
		Vector<String> Pendingcode=FileTool.Load(pendingcode, "utf-8");
		for(int i=0;i<Standcode.size();i++){
			String poi=Standcode.elementAt(i);
			String[] code=poi.split(",");
			map.put(code[0], code[1]);
			
		}
		for(int i=0;i<Pendingcode.size();i++){
			String poi=Pendingcode.elementAt(i);
			String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
			String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
			if(map.get(from)==null&&from.length()>4){
				//System.out.println("fromproblem:"+poi);
				FileTool.Dump("fromproblem:"+poi, pendingcode.replace(".txt", "")+"-fromproblem.txt", "utf-8");
				fromtomap.put(from,Integer.toString(i));
			}
			if(map.get(to)==null&&to.length()>4){
				//System.out.println("toproblem:"+poi);
				FileTool.Dump("toproblem:"+poi, pendingcode.replace(".txt", "")+"-toroblem.txt", "utf-8");
				fromtomap.put(to,Integer.toString(i));
			}	
		}
		System.out.println("fromtomap:");
		for (Entry<String, String> entry : fromtomap.entrySet()) {
			System.out.println(entry.getKey());
			FileTool.Dump(entry.getKey(), pendingcode.replace(".txt", "")+"-fromtomap.txt", "utf-8");
		}
	}
	public static void creatlist(String folder){
		Map<String,String> map = new HashMap<String,String>();
		Vector<String> HV=FileTool.Load(folder, "utf-8");
		String value="";
		String key="";
		for(int i=0;i<HV.size();i++){
			String hv=HV.elementAt(i);
			if(hv.indexOf(".tif")!=-1&&hv.indexOf("tfw")==-1&&hv.indexOf("aux.xml")==-1&&hv.indexOf("ovr")==-1&&hv.indexOf("xml")==-1){
				key=hv.substring(hv.indexOf("h"), hv.indexOf(".tif"));
				value=hv;
				if(map.get(key)!=null){
					String subvalue=map.get(key);
					map.put(key, subvalue+","+value);
					
				}else{
					map.put(key,value);
				}				
				//FileTool.Dump(substr+"["+hv+"]", folder.replace(".txt", "")+"-result.txt", "utf-8");
			}
			
		}
		for (Entry<String, String> entry : map.entrySet()) {
			key = entry.getKey().toString();
		    value = entry.getValue();
		    
		    System.out.println(key+":"+value);
		    FileTool.Dump(key+"["+value+"]", folder.replace(".txt", "")+"-Result.txt", "utf-8");
		}
	}
	public static void GatherBigCity(String folder) {

		String poi = "";
		// Tool.ID_Hashtable("D:/人口数据/Task/汇总大城市各区/bigcityCode.txt");
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			Vector<String> Pois = FileTool.Load(folder, "utf-8");
			int tempNum = 0;
			for (int a = 0; a < Pois.size(); a++) {
				poi = Pois.elementAt(a);
				String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
				String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
				int amount = Integer.parseInt(Tool.getStrByKey(poi, "<amounts>", "</amounts>", "</amounts>"));
				int tolen = to.length();
				String subto = "";
				int sub;
				/*
				 * if((from.indexOf("110100")!=-1)||(from.indexOf("5132")!=-1)){
				 * //from和to subto=from.substring(1, tolen-2);
				 * sub=Integer.parseInt(subto);//from和to }else{
				 */
				subto = from.substring(0, 4);
				sub = Integer.parseInt(subto);// from和to
				// }

				int citycode;
				String city = "";
				Vector<String> CityPois = FileTool.Load("D:/人口数据/10级数据-将大城市的区县进行合并再统计流动数据/bigcityCode.txt", "utf-8");
				int tag = 0;
				int CityPoisindex;
				for (CityPoisindex = 0; CityPoisindex < CityPois.size();) {
					// 判断sub是否已经找到过合适的code，如果找到了则不执行if里面的语句，没找到才执行
					if (tag == 0) {
						city = CityPois.elementAt(CityPoisindex);
						citycode = Integer.parseInt(Tool.getStrByKey(city, "<code>", "</code>", "</code>"));
						if (sub == citycode) {
							tag++; // 表示该sub找到了合适的code
							// 对每个区域逐个统计
							int count = 0;
							for (int b = 0; b < a; b++) {
								String probe = Tool.getStrByKey(Pois.elementAt(b), "<to>", "</to>", "</to>");// from和to
								// from和to
								if (to.equals(probe)) {
									if ((map.get(probe) != null)) {// 如果前一组数中有两个code与这一组数的code相同，则会有问题
										int s = map.get(probe);
										map.put(probe, s + amount);
										tempNum++; // 表示该poi被处理
										break; // 跳出最里层循环
									} else {
										map.put(to, amount);// from和to
										break;
									}
								} else {
									count++;
								}

							}
							if (count == a) {
								map.put(to, amount);// from和to
								break;
							}
						} else {
							CityPoisindex++;
							// from和to
							String before = Tool.getStrByKey(Pois.elementAt(a - 1), "<from>", "</from>", "</from>"); // 上一次执行的poi
							int beforelen = before.length();
							String subbefore = "";
							int subindex = 0;
							// from和to
							/*
							 * if((from.indexOf("110100")!=-1)||(from.indexOf(
							 * "5132")!=-1)){ subbefore=before.substring(1,
							 * beforelen-2);
							 * subindex=Integer.parseInt(subbefore); }else{
							 */
							subbefore = before.substring(0, 4);
							subindex = Integer.parseInt(subbefore);
							// }
							// 判断此次的sub与上次是否相同，如果不同则把上次的map值全都打印出来，然后再清空
							if (sub != subindex) {
								String key = "";
								for (Entry<String, Integer> entry : map.entrySet()) {
									key = entry.getKey().toString();
									int value = entry.getValue();

									// from和to
									String str = "<from>" + subindex + "</from>" + "<to>" + key + "</to>" + "<amounts>"
											+ value + "</amounts>";
									System.out.println(str);
									FileTool.Dump(str, folder.replace(".txt", "") + "-gatherBigCity.txt", "utf-8");
								}
								map.clear();
								// map.put(from, amount);
							}

						}
					} else {
						break;
					}
				}
				if (CityPoisindex == CityPois.size()) {
					System.out.println(poi);
					FileTool.Dump(poi, folder.replace(".txt", "") + "-gatherBigCity.txt", "utf-8");
				}

			}

		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			FileTool.Dump(poi, folder.replace(".txt", "") + "-exception.txt", "utf-8");
		}

	
	}
	public static void countTotalAmount(String folder) {

		String poi = "";
		try {
			Vector<String> Pois = FileTool.Load(folder, "utf-8");
			Map<String, Integer> map = new HashMap<String, Integer>();
			System.out.println("begin:");
			for (int a = 0; a < Pois.size(); a++) {
				poi = Pois.elementAt(a);
				String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
				String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
				int amount = Integer.parseInt(Tool.getStrByKey(poi, "<amounts>", "</amounts>", "</amounts>"));
				String index = "";
				if (a == 0) {
					map.put(to, amount);//from和to
				} else {
					index = Tool.getStrByKey(Pois.elementAt(a - 1), "<from>", "</from>", "</from>");//from和to
					if (from.equals(index)) {//from和to
						// 对每个区域逐个统计
						map.put(to, amount);//from和to

						int s = Pois.size();
						if ((a + 1) == s) {
							int[] totalAmounts = new int[map.size()];
							int counts = 0;
							String key = "";
							int Total = 0;
							for (Entry<String, Integer> entry : map.entrySet()) {
								
								int value = entry.getValue();
								totalAmounts[counts] = value;
								counts++;
							}
							for (int i = 0; i < totalAmounts.length; i++) {
								Total += totalAmounts[i];
							}
							//from和to
							String str = "<from>" + index + "</from>" + "<amounts>" + Total + "</amounts>";//from和to
							System.out.println(str);
							FileTool.Dump(str, folder.replace(".txt", "") + "-countTotalAmounts.txt", "utf-8");
						}

					} else {
						int[] totalAmounts = new int[map.size()];
						int counts = 0;
						String key = "";
						int Total = 0;
						for (Entry<String, Integer> entry : map.entrySet()) {
							
							int value = entry.getValue();
							totalAmounts[counts] = value;
							counts++;

							
						}
						for (int i = 0; i < totalAmounts.length; i++) {
							Total += totalAmounts[i];
						}
						//from和to
						String str = "<from>" + index + "</from>" + "<amounts>" + Total + "</amounts>";//from和to
						System.out.println(str);
						FileTool.Dump(str, folder.replace(".txt", "") + "-countTotalAmounts.txt", "utf-8");

						map.clear();
						map.put(to, amount);//from和to

					}

				}
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			FileTool.Dump(poi, folder.replace(".txt", "") + "-exception.txt", "utf-8");
		}

	}
	/**
	 * 利用-countAmounts.txt文件，将每个区县的记录按照流动人口数目的大小顺序排列
	 * 同时删除from和to的code一样的数据
	 * @param folder
	 */

	//getSortFlow("D:\\人口数据\\0414重新处理\\6级数据-对统计后的数据进行排序\\countFlowin-NewCode-replaced-tidy-countAmounts.txt");
	public static void getSortFlow(String folder) {

		String poi = "";
		try {
			Vector<String> Pois = FileTool.Load(folder, "utf-8");
			Map<String, Integer> map = new HashMap<String, Integer>();
			System.out.println("begin:");
			for (int a = 0; a < Pois.size(); a++) {
				poi = Pois.elementAt(a);
				String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
				String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
				int amount = Integer.parseInt(Tool.getStrByKey(poi, "<amounts>", "</amounts>", "</amounts>"));
				String index = "";
				if (a == 0) {
					//if (!(from.equals(to))) {
						// flowout:to
						// flowin:from
						map.put(from, amount);
					//}

				} else {
					// flowout:from
					// flowin:to
					index = Tool.getStrByKey(Pois.elementAt(a - 1), "<to>", "</to>", "</to>");
					// flowout:from
					// flowin:to
					if (to.equals(index)) { 
					
						//if (!(from.equals(to))) {
							// flowout:to
							// flowin:from
							map.put(from, amount); 

					//	}

						int s = Pois.size();
						if ((a + 1) == s) {
							int[] Amounts = new int[map.size()];
							String[] FromTo = new String[map.size()];
							int counts = 0;
							String key = "";
							for (Entry<String, Integer> entry : map.entrySet()) {
								key = entry.getKey().toString();
								int value = entry.getValue();
								Amounts[counts] = value;
								
								//flowout:"<from>" + index + "</from>" + "<to>" + key + "</to>" 
								// flowin:"<from>" + key + "</from>" + "<to>" + index + "</to>" 
								FromTo[counts] = "<from>" + key + "</from>" + "<to>" + index + "</to>" + "<amounts>" // from和to
										+ value + "</amounts>";
								counts++;
							}
							Tool.InsertSortArray_Descending(Amounts.length, Amounts, FromTo);
							for (int i = 0; i < FromTo.length; i++) {
								System.out.println(FromTo[i]);
								FileTool.Dump(FromTo[i], folder.replace(".txt", "") + "-sort.txt", "utf-8");
							}
						}

					} else {

						int[] Amounts = new int[map.size()];
						String[] FromTo = new String[map.size()];
						int counts = 0;
						String key = "";
						for (Entry<String, Integer> entry : map.entrySet()) {
							key = entry.getKey().toString();
							int value = entry.getValue();
							Amounts[counts] = value;
							//flowout:"<from>" + index + "</from>" + "<to>" + key + "</to>" 
							// flowin:"<from>" + key + "</from>" + "<to>" + index + "</to>" 
							FromTo[counts] = "<from>" + key + "</from>" + "<to>" + index + "</to>" + "<amounts>" + value
									+ "</amounts>";
							counts++;

						}
						Tool.InsertSortArray_Descending(Amounts.length, Amounts, FromTo);
						for (int i = 0; i < FromTo.length; i++) {
							System.out.println(FromTo[i]);
							FileTool.Dump(FromTo[i], folder.replace(".txt", "") + "-sort.txt", "utf-8");
						}

						map.clear();
						//if (!(from.equals(to))) {
							// flowout:to
							//  flowin:from
							map.put(from, amount); 

					//	}

					}

				}
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			FileTool.Dump(poi, folder.replace(".txt", "") + "-exception.txt", "utf-8");
		}

	}
/**
 * 比较两个文件中的记录是否相同，并且将相同的记录写下来，从而找出两个文件是否存在不相同的记录
 * @param folder1
 * @param folder2
 */
	public static void CompareAmouts(String folder1, String folder2) {
		Map<String, Integer> map1 = new HashMap<String, Integer>();
		Map<String, Integer> map2 = new HashMap<String, Integer>();
		String poi1 = "";
		String poi2 = "";
		Vector<String> Pois1 = FileTool.Load(folder1, "utf-8");
		for (int a1 = 0; a1 < Pois1.size(); a1++) {
			poi1 = Pois1.elementAt(a1);
			String[] arr1 = poi1.split(":");
			map1.put(arr1[0], Integer.parseInt(arr1[1]));
		}
		Vector<String> Pois2 = FileTool.Load(folder2, "utf-8");
		for (int a2 = 0; a2 < Pois2.size(); a2++) {
			poi2 = Pois2.elementAt(a2);
			String[] arr2 = poi2.split(":");
			if(map2.get(arr2[0])!=null){
				int s=map2.get(arr2[0]);
				System.out.println(arr2[0]+":"+s+arr2[1]);
				map2.put(arr2[0], Integer.parseInt(s+arr2[1]));
			}else{
				map2.put(arr2[0], Integer.parseInt(arr2[1]));
			}
		    map2.put(arr2[0], Integer.parseInt(arr2[1]));
		}
			Iterator<String> it1 = map1.keySet().iterator();
			System.out.println("map1.size()="+map1.size());
			while (it1.hasNext()) {

				String key1;
				int value1;

				key1 = (String) it1.next();
				value1 = map1.get(key1);

				//System.out.println("map1-" + key1 + ":" + value1);
				Iterator<String> it2 = map2.keySet().iterator();
				System.out.println("map2.size()="+map2.size());
				while (it2.hasNext()) {
					String key2;
					int value2;
					key2 = (String) it2.next();
					value2 = map2.get(key2);
					//System.out.println("map2-" + key2 + ":" + value2);
					if ((key1.equals(key2)) && (value1 == value2)) {
						//FileTool.Dump(key1 + ":" + value1, "D:/人口数据/3级数据-统计人口流入流出数据/key1=key2.txt", "utf-8");
						//FileTool.Dump(key2 + ":" + value2, "D:/人口数据/3级数据-统计人口流入流出数据/key2.txt", "utf-8");
						it2.remove();
						break;
					}else if((key1.equals(key2)) && (value1 != value2)){
						System.out.println("map1-" + key1 + ":" + value1);
						System.out.println("map2-" + key2 + ":" + value2);
						break;
                     }
				}
               

			}
		}

	/**
	 * 通过检查countFlowout.txt文件中每个区县的poi条数来找出多出的poi
	 */
	public static void Check_countFlowout(String folder) {
		String poi = "";
		setCounty();
		Vector<String> Pois = FileTool.Load(folder, "utf-8");
		for (int a = 0; a < Pois.size(); a++) {
			poi = Pois.elementAt(a);
			String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
			for (int i = 0; i < county.size(); i++) {
				if (from.equals(county.get(i).code)) {
					county.get(i).setPoiamounts(1);
				}
			}
		}
		int acount = 0;
		for (int i = 0; i < county.size(); i++) {
			System.out.println(county.get(i).poiamounts);

			for (int k = 0; k < county.get(i).poiamounts.size(); k++) {
				acount += county.get(i).poiamounts.get(k);
			}
			System.out.println(county.get(i).code + ":" + acount);
			FileTool.Dump(county.get(i).code + ":" + acount, folder.replace(".txt", "") + "-Check.txt", "utf-8");
			acount = 0;
		}
	}

	/**
	 * 通过检查-tidy.txt文件中每个区县的poi条数来找出多出的poi
	 */
	public static void Check_countFlowout_tidy(String folder) {
		String poi = "";
		int count = 1;
		Vector<String> Pois = FileTool.Load(folder, "utf-8");
		for (int a = 0; a < Pois.size(); a++) {
			poi = Pois.elementAt(a);
			String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
			String beforefrom = "";
			if (a > 0) {
				beforefrom = Tool.getStrByKey(Pois.elementAt(a - 1), "<from>", "</from>", "</from>");
				if (from.equals(beforefrom)) {
					count++;
				} else {
					System.out.println(beforefrom + ":" + count);
					FileTool.Dump(beforefrom + ":" + count, folder.replace(".txt", "") + "-Check.txt", "utf-8");
					count = 1;
				}
			}
		}
	}

	public static void delectMistake(String folder) {
		try {
			File file = new File(folder);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = null;
			String tempString = null;

			reader = new BufferedReader(isr);
			while ((tempString = reader.readLine()) != null) {
				if (tempString.indexOf("<PRICE>") != -1) {
					FileTool.Dump(tempString.replace(" ", "").trim(), folder.replace(".txt", "") + "-tidy.txt",
							"utf-8");
				} else {
					FileTool.Dump(tempString.replace(" ", "").trim(), folder.replace(".txt", "") + "-mistake.txt",
							"utf-8");
				}

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

	public Demo(String line) {

		if (line.indexOf("<from>") != -1)
			from = Tool.getStrByKey(line, "<from>", "</from>", "</from>");
		if (line.indexOf("<to>") != -1)
			to = Tool.getStrByKey(line, "<to>", "</to>", "</to>");
		if (line.indexOf("<amounts>") != -1)
			amounts = Tool.getStrByKey(line, "<amounts>", "</amounts>", "</amounts>");

	}

	public static void productJson(String folder) {

		JSONArray jsonArr = new JSONArray();// json格式的数组

		try {
			File file = new File(folder);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = null;
			String tempString = null;

			reader = new BufferedReader(isr);
			while ((tempString = reader.readLine()) != null) {
				Demo demo = new Demo(tempString);
				JSONObject jsonObjArr = new JSONObject();

				jsonObjArr.put("from", demo.from);
				jsonObjArr.put("to", demo.to);
				jsonObjArr.put("amounts", demo.amounts);
				//jsonArr.put(jsonObjArr);
			}

			// 将json格式的数据放到json格式的数组里

			// jsonObj.put("Points", jsonObjArr);//再将这个json格式的的数组放到最终的json对象中。

			// System.out.println(jsonArr);
			System.out.println("开始写入txt中");
			FileTool.Dump(jsonArr.toString(), folder.replace(".txt", "") + "-Json .txt", "utf-8");
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
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    // countAmount("D:\\人口数据\\0414重新处理\\5级数据-将排完序的数据进行统计\\countFlowin-NewCode-replaced-tidy.txt");
	public static void countAmount(String folder) {
		String poi = "";
		try {
			Vector<String> Pois = FileTool.Load(folder, "utf-8");
			Map<String, Integer> map = new HashMap<String, Integer>();
			System.out.println("begin:");
			int start=0;
			int end=0;
			for (int a = 0; a < Pois.size(); a++) {
				poi = Pois.elementAt(a);
				String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
				String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
				int amount = Integer.parseInt(Tool.getStrByKey(poi, "<amount>", "</amount>", "</amount>"));
				String index = "";
				if (a == 0) {
					// flowout:to
					// flowin:from
					map.put(from, amount);
					end++;
				} else {
					// flowout:from
					// flowin:to
					index = Tool.getStrByKey(Pois.elementAt(a - 1), "<to>", "</to>", "</to>");
					// flowout:from
					// flowin:to
					// 与index一致
					if (to.equals(index)) {
						int count = 0;
						for (int b = start; b < end; b++) {
							// flowout:to
							// flowin:from
							String probe = Tool.getStrByKey(Pois.elementAt(b), "<from>", "</from>", "</from>");
							// flowout:to
							// flowin:from
							if (from.equals(probe)) {
								//如果map中之前有一个和to一样的code
								if (map.get(probe) != null) {
									int s = map.get(probe);
									map.put(probe, s + amount);
									end++;
									break;
								} else {
									// flowout:to
									// flowin:from
									map.put(from, amount);
									end++;
									break; // 此处的break必须要加，要不然可能会出现同一个from统计两次的情况
								}
							} else {
								count++;
							}
						}
						if (count == (end-start)) {
							// flowout:to
							// flowin:from
							map.put(from, amount);
							end++;
						}
						int s = Pois.size();
						if ((a + 1) == s) {
							String key = "";
							String value = "";
							for (Entry<String, Integer> entry : map.entrySet()) {
								key = entry.getKey().toString();
								value = entry.getValue().toString();

								// flowout: "<from>" + index + "</from>" +
								// "<to>" + key + "</to>"
								// flowin: "<from>" + key + "</from>" + "<to>" + index + "</to>"
								String str =  "<from>" + key + "</from>" + "<to>" + index + "</to>"+"<amounts>" + value
										+ "</amounts>";

								FileTool.Dump(str, folder.replace(".txt", "") + "-countAmounts.txt", "utf-8");
							}

						}

					} else {
						String key = "";
						String value = "";
						for (Entry<String, Integer> entry : map.entrySet()) {
							key = entry.getKey().toString();
							value = entry.getValue().toString();

							// flowout: "<from>" + index + "</from>" + "<to>" +
							// key + "</to>"
							// flowin: "<from>" + key + "</from>" + "<to>" + index + "</to>"
							String str ="<from>" + key + "</from>" + "<to>" + index + "</to>" + "<amounts>" + value
									+ "</amounts>";

							FileTool.Dump(str, folder.replace(".txt", "") + "-countAmounts.txt", "utf-8");
						}
						map.clear();
						
						start=end;

						// flowout:to
						// flowin:from
						map.put(from, amount);
						end++;
					}

				}
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			FileTool.Dump(poi, folder.replace(".txt", "") + "-exception.txt", "utf-8");
		}

	}
	//countTo("D:/人口数据/0414重新处理/13级数据-将主城区code进行合并/2014CodeStand.txt","D:/人口数据/0414重新处理/13级数据-将主城区code进行合并/countFlowout-NewCode-replaced.txt");
	public static void countTo(String codefolder, String countfoder) {

		try {
			File file = new File(codefolder);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = null;
			String tempString = null;
			reader = new BufferedReader(isr);

			// 读取2014CodeStand.txt中的一条数据：
			// <code>1101</code><sname>北京市</sname><scoor>116.3847599;39.90230163</scoor><sreg>北京市,null,null,null</sreg>
			while ((tempString = reader.readLine()) != null) {
				//String[] admin=tempString.split(",");
				String probe=Tool.getStrByKey(tempString,"<code>", "</code>", "</code>");

				// 读取countFlowout.txt文件中的数据
				Vector<String> countfile = FileTool.Load(countfoder, "utf-8");
				// 从countFlowout.txt(或者countFlowin.txt)文件中的第一条记录开始判断比较
				for (int i = 0; i < countfile.size(); i++) {
					String poi = countfile.elementAt(i);
					
					//flowin:to
					//flowout:from
					
					String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
					String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
					String amount=Tool.getStrByKey(poi, "<amount>", "</amount>", "</amount>");

					// 如果CodeResult.txt中的code与countFlowout.txt中的from相同，则将countFlowout.txt中的poi写下来
					// 其实是按照CodeResult.txt中code顺序，将countFlowout.txt中的poi排列，先将code为110101的poi写下，再将code为110102的poi写下来
					
					if(from.indexOf("110000")!=-1){
						from=from.substring(1).replace("110000", "1101");
					}
					if(to.indexOf("110000")!=-1){
						to=to.substring(1).replace("110000", "1101");
					}
					
					//flowin:to
					//flowout:from
					if (from.equals(probe)) {
						FileTool.Dump(poi, countfoder.replace(".txt", "") + "-tidy-替换前.txt", "utf-8");
						poi="<from>"+from+"</from>"+"<to>"+to+"</to>"+"<amount>"+amount+"</amount>";
						FileTool.Dump(poi, countfoder.replace(".txt", "") + "-tidy.txt", "utf-8");
					}

				}

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
/*
	public static void countTo(String codefolder, String countfoder) {
		try {
			File file = new File(codefolder);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = null;
			String tempString = null;
			reader = new BufferedReader(isr);
			int counts1 = 0;
			int counts2 = 0;
			while ((tempString = reader.readLine()) != null) {
				counts2++;
				Vector<String> countfile = FileTool.Load(countfoder, "utf-8");
				int counts3 = countfile.size();
				for (int i = 0; i < countfile.size(); i++) {
					String poi = countfile.elementAt(i);
					Demo demo = new Demo(poi);
					String code = Tool.getStrByKey(tempString, "<Code>", "</Code>", "</Code>");
					if (code.equals(demo.from)) {
						// System.out.println("line-"+(i+1)+": "+poi);
						counts1++;
						FileTool.Dump("line-" + (i + 1) + ": " + poi,
								countfoder.replace(".txt", "") + "-tidy-tonight.txt", "utf-8");
					}

				}

			}
			System.out.println("共有" + counts1 + "条记录被写下！");
			System.out.println("共有" + counts2 + "个code被校对！");
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
	*/

	/**
	 * 将每个区县定义成一个county
	 */
	public static ArrayList<CountyPopulation> county = new ArrayList<CountyPopulation>();

	/**
	 * 对每个county进行数据的填充
	 */
	public static void addCountyPopulation(CountyPopulation cp) {
		county.add(cp);

	}

	public static void setCounty() {
		try {
			// 设置每个区县的区县代码、区县名字以及坐标
			File file = new File("D:/zhouxiang/人口数据/宾馆数据/人口统计/CodeResult.txt");
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = null;
			String tempString = null;

			reader = new BufferedReader(isr);
			while ((tempString = reader.readLine()) != null) {
				code = Tool.getStrByKey(tempString, "<Code>", "</Code>", "</Code>");
				countyname = Tool.getStrByKey(tempString, "<CodeAddr>", "</CodeAddr>", "</CodeAddr>");
				CountyPopulation cp = new CountyPopulation();
				// System.out.println(code);
				cp.setCode(code);
				// System.out.println(countyname);
				cp.setCountyname(countyname);
				addCountyPopulation(cp);
			}
			reader.close();
		} catch (IOException e) {
			e.getStackTrace();
		}

	}

	public static void ClassifyStatistic(String folder) throws IOException {

		// 将人口的通讯地址的代码分配到各个区县
		File file1 = new File(folder);
		FileInputStream fis1 = new FileInputStream(file1);
		InputStreamReader isr1 = new InputStreamReader(fis1, "UTF-8");
		BufferedReader reader1 = null;
		String tempString1 = null;

		reader1 = new BufferedReader(isr1);
		while ((tempString1 = reader1.readLine()) != null) {

			if (tempString1.indexOf("<PostReg>") != -1) {
				String PostReg = Tool.getStrByKey(tempString1, "<PostReg>", "</PostReg>", "</PostReg>");
				String[] PostAddr = PostReg.split(",");
				String addr = (PostAddr[0] + PostAddr[1] + PostAddr[2]).replace("null", "").replace("苏州工业园区", "吴中区")
						.replace("汕尾市汕尾市", "汕尾市").replace("黑河市黑河市", "黑河市").replace("重庆市潼南县", "重庆市潼南区")
						.replace("重庆市县", "重庆市").replace("包头市白云区", "包头市白云鄂博矿区").replace("重庆市荣昌县", "重庆市荣昌区")
						.replace("桂林市荔浦县", "桂林市荔蒲县").replace("承德市营子矿区", "承德市鹰手营子矿区").replace("乌鲁木齐市东山区", "乌鲁木齐市米东区")
						.replace("甘肃省临泽县", "甘肃省张掖市临泽县").replace("固原市海原县", "中卫市海原县").replace("吴忠市红寺堡", "吴忠市红寺堡开发区")
						.replace("广西壮族自治区富川瑶族自治县", "广西壮族自治区贺州市富川瑶族自治县").replace("甘肃省肃州区", "甘肃省酒泉市肃州区")
						.replace("甘肃省静宁县", "甘肃省平凉市静宁县").replace("甘肃省灵台县", "甘肃省平凉市灵台县").replace("", "").replace("", "")
						.replace("", "");

				int count = 0;
				String poi = "";
				for (int i = 0; i < county.size(); i++) {

					if (addr.equals(county.get(i).countyname)) {
						poi = "<PostCode>" + county.get(i).code + "</PostCode>" + tempString1;
						// county.get(i).setpostPois(poi);
						FileTool.Dump(poi, folder.replace(".txt", "") + "-postCode.txt", "utf-8");
						break;
					} else {
						count++;
					}
				}

				if (count == county.size()) {
					FileTool.Dump(tempString1, "D:/人口数据/找不到对应行政区划数据.txt", "utf-8");
				}

			}

		}

		reader1.close();
	}

	public static void fileCut(int n, String folder) {
		try {
			File file = new File(folder);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = null;
			String rs = null;
			reader = new BufferedReader(isr);
			System.out.println("文件分类开始：");
			int count = 0;
			while ((rs = reader.readLine()) != null) {
				count++;
				if (count < n) {
					FileTool.Dump(rs, folder.replace(".txt", "") + "_1.txt", "utf-8");

				} else {
					FileTool.Dump(rs, folder.replace(".txt", "") + "_2.txt", "utf-8");
				}
			}
			System.out.println("文件分类结束！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
