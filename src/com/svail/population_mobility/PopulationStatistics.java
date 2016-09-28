package com.svail.population_mobility;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import com.svail.gridprocess.DataPoint;
import com.svail.gridprocess.Price;
import com.svail.population_mobility.CountyPopulation;
import com.svail.test.Demo;
import com.svail.util.FileTool;
import com.svail.util.GetKey;
import com.svail.util.Map_ValueGetKey;
import com.svail.util.Tool;

public class PopulationStatistics {
	public static String code;
	public static String countyname;
	public static String countyLN;
	public static String countyLA;
	public static String countyCoor;
	public static String codeReg;

	public static void main(String[] args) throws IOException {
		// SearchCode();
		// countTotalAmount("D:/人口数据/Task/countFlowout-tidy-countAmounts.txt");

		// getSortFlow("D:/人口数据/Task/countFlowin-tidy-countAmounts.txt");

		// getMainIngredients("D:/人口数据/Task/countFlowout-tidy-countAmounts-sort.txt");

		// ProductJson("D:/人口数据/Task/countFlowout-tidy-countAmounts-sort-MainIngredients.txt");

		// getMax("D:/人口数据/Task/countFlowin-tidy-countAmounts-sort-MainIngredients.txt");

		// CheckRepeatCode("D:/人口数据/Task/排查重复code/countFlowin-tidy-countAmounts-sort-MainIngredients-Max.txt");

		// GatherBigCity("D:/人口数据/Task/汇总大城市各区/countFlowin-tidy-countAmounts-sort.txt");

		// countAmount("D:/人口数据/4级数据-统计某区县流入或者流出的另一区县的总人口/countFlowout-tidy.txt");
		// getSortFlow("D:/人口数据/4级数据-统计某区县流入或者流出的另一区县的总人口/countFlowout-tidy-countAmounts.txt");

		// countTotalAmount("D:/人口数据/5级数据-将每个区县的流动人口数据进行排序/countFlowin-tidy-countAmounts-sort.txt");
		// getTotalAmountsSort("D:/人口数据/5级数据-将每个区县的流动人口数据进行排序/countFlowin-tidy-countAmounts-sort-countTotalAmounts.txt");

		// getMainIngredients("D:/人口数据/7级数据-利用sort文件提取流动主方向/countFlowin-tidy-countAmounts-sort.txt");

		// getMax("D:/人口数据/8级数据-利用主方向数据提取每个区域流动数目最大值/countFlowout-tidy-countAmounts-sort-MainIngredients.txt");

		// CheckRepeatCode("D:/人口数据/9级数据-利用max数据排查区县的新旧code/countFlowout-tidy-countAmounts-sort-MainIngredients-Max.txt");

		// GatherBigCity("D:/人口数据/10级数据-将大城市的区县进行合并再统计流动数据/countFlowout-tidy-countAmounts-sort.txt");

		// getSortFlow("D:/人口数据/11级数据-对合并的数据进行排序/countFlowin-tidy-countAmounts-sort-gatherBigCity.txt");

		// mergeOtherData("D:/人口数据/12级数据-将合并数据的from或to也合并/countFlowin-tidy-countAmounts-sort-gatherBigCity-sort.txt");

		// getMainIngredients("D:/人口数据/13级数据-将合并后的from和to相同的删除并提取主方向/countFlowout-tidy-countAmounts-sort-gatherBigCity-sort-doubleMerge.txt");
/*    
		Vector<String>
		increment=FileTool.CompareFile("D:/人口数据/0414重新处理/13级数据-将主城区code进行合并/countFlowout-NewCode-replaced.txt",
				 "D:/人口数据/0414重新处理/13级数据-将主城区code进行合并/countFlowout-NewCode-replaced-tidy-替换前.txt");
		 for(int i=0;i<increment.size();i++){
		 System.out.println(increment.elementAt(i));
		 FileTool.Dump(increment.elementAt(i),"D:/人口数据/0414重新处理/13级数据-将主城区code进行合并/countFlowout-increment.txt", "utf-8");
		 }
		 System.out.println("总共有"+increment.size()+"条记录");
		 */ 

		// distinguishCode("D:/人口数据/14级数据-通过排查确定新旧code/2015行政区划代码.txt","D:/人口数据/14级数据-通过排查确定新旧code/新旧code.txt");

		// replaceOldCode("D:/人口数据/15级数据-利用NewOld文件排除数据中的新旧code问题/countFlowin-tidy-countAmounts-sort-gatherBigCity-sort-doubleMerge-replaceOldCode.txt","D:/人口数据/15级数据-利用NewOld文件排除数据中的新旧code问题/NewOld_Code.txt");

		// countAmount("D:/人口数据/16级数据-又重新统计替换后的数据的amount数目/countFlowout-tidy-countAmounts-sort-gatherBigCity-sort-doubleMerge-replaceOldCode-replaceOldCode.txt");

		// getSortFlow("D:/人口数据/17级数据-将统计后的数据排序/countFlowout-tidy-countAmounts-sort-gatherBigCity-sort-doubleMerge-replaceOldCode-replaceOldCode-countAmounts.txt");

		// getMainIngredients("D:/人口数据/18级数据-将排序后的数据提取主方向/countFlowout-tidy-countAmounts-sort-gatherBigCity-sort-doubleMerge-replaceOldCode-replaceOldCode-countAmounts-sort.txt");

		// ProductJson("D:/人口数据/19级数据-对主方向数据形成json文件/countFlowin-tidy-countAmounts-sort-gatherBigCity-sort-doubleMerge-replaceOldCode-replaceOldCode-countAmounts-sort-MainIngredients.txt");

		// getMainIngredients("D:/人口数据/18级数据-将排序后的数据提取主方向/countFlowin-gatherBigCity-countAmounts-sort.txt");

		// countAmount("D:/人口数据/16级数据-又重新统计替换后的数据的amount数目/核查/countFlowout-gatherBigCity.txt");
		// getSortFlow("D:/人口数据/17级数据-将统计后的数据排序/countFlowout-gatherBigCity-countAmounts.txt");

		// investigationReg("D:/人口数据/18级数据-将排序后的数据提取主方向/check_oldcode/合并后的problemcode-result.txt");

		// contectcode("D:/人口数据/18级数据-将排序后的数据提取主方向/check_oldcode/2015行政区划代码_result.txt","D:/人口数据/18级数据-将排序后的数据提取主方向/check_oldcode/合并后的problemcode-result-ok.txt");

		// checkLeakCode("D:/人口数据/3级数据-统计人口流入流出数据/0405/14年全国行政区划代码_result.txt","D:/人口数据/3级数据-统计人口流入流出数据/0405/countFlowout-tidy.txt");

		// maptogether("D:/人口数据/3级数据-统计人口流入流出数据/0405/countFlowin-tidy-fromtomap.txt","D:/人口数据/3级数据-统计人口流入流出数据/0405/countFlowout-tidy-fromtomap.txt");

		// checkOldCode("D:/人口数据/3级数据-统计人口流入流出数据/0405/合并后的problemcode.txt","D:/人口数据/3级数据-统计人口流入流出数据/0405/CodeResult.txt");

		//investigationReg("D:/人口数据/3级数据-统计人口流入流出数据/0405/合并后的problemcode-result.txt");
		
		//provinceOrder("D:/人口数据/3级数据-统计人口流入流出数据/0405/新建文件夹/解析没有问题的oldcode数据.txt");
		
		//contectcode("D:/人口数据/3级数据-统计人口流入流出数据/0405/14年全国行政区划代码_result.txt","D:/人口数据/3级数据-统计人口流入流出数据/0405/新建文件夹/解析没有问题的oldcode数据-order-Exception.txt");
		
		//ProductJson("D:/重庆基础数据抓取/基础数据/重庆电网/美团link.txt-result.txt");
		
		//replaceOldCode("D:/人口数据/0414重新处理/1级数据-新旧code/countFlowout-tidy-replaceOldCode.txt","D:/人口数据/0414重新处理/1级数据-新旧code/Standard&OldCode.txt");
		
		//countAmount("D:/人口数据/0414重新处理/2级数据-将原文件中旧code全部替换/countFlowout-NewCode.txt");
		//mainCity("D:\\人口数据\\0414重新处理\\3级数据-将大城市的区县cede合并\\主要城区数据.txt");
		
		//replaceCode("D:/人口数据/0414重新处理/13级数据-将主城区code进行合并/省会-地级市-合并code.txt","D:/人口数据/0414重新处理/13级数据-将主城区code进行合并/countFlowin-NewCode.txt");
		
		//countTo("D:/人口数据/0414重新处理/13级数据-将主城区code进行合并/2014CodeStand.txt","D:/人口数据/0414重新处理/13级数据-将主城区code进行合并/countFlowin-NewCode-replaced.txt");
		
		//countAmount("D:/人口数据/0414重新处理/14级数据-将合并后的数据逐个区县地统计/countFlowout-NewCode-replaced-tidy.txt");
		//getSortFlow("D:/人口数据/0414重新处理/15级数据-对统计后的数据进行排序/countFlowin-NewCode-replaced-tidy-countAmounts.txt");
		
		//countTotalAmount("D:\\人口数据\\0414重新处理\\7级数据-统计区县流入或流出人口总数\\countFlowout-NewCode-replaced-tidy-countAmounts-sort.txt");
		//getMainIngredients("D:/人口数据/0414重新处理/16级数据-利用sort文件提取主方向/countFlowout-NewCode-replaced-tidy-countAmounts-sort.txt");
		
		//ProductJson("D:\\人口数据\\0414重新处理\\9级数据-整理数据生成json文件\\replacedCode.txt");
		
		//mainCity("D:/人口数据/0414重新处理/11级数据-合并地级市主城区/地级市主城区合并.txt");
		//countTotalAmount("D:/人口数据/0414重新处理/21级数据-曲线聚类/2-统计各个区县的流动总人数/countFlowin.txt",0);
		
		
		//pageRankData("D:/人口数据/0414重新处理/21级数据-曲线聚类/2-统计各个区县的流动总人数/countFlowin-countTotalAmounts.txt",
		//		     "D:/人口数据/0414重新处理/21级数据-曲线聚类/2-统计各个区县的流动总人数/countFlowin.txt",0);
		
		/*	
		pageRank("D:/人口数据/0414重新处理/19级数据-pagerank数据/2014CodeStand.txt",
				"D:/人口数据/0414重新处理/19级数据-pagerank数据/countFlowin-pointRate.txt");
			
		*/		
				
		
		//setPopurate("D:/人口数据/0414重新处理/12级别数据-pagerank算法/countFlowout-MainIngredients-countTotalAmounts-pointRate.txt");
	
		//flowRank("D:/人口数据/0414重新处理/19级数据-pagerank数据/2014CodeStand.txt","D:/人口数据/0414重新处理/19级数据-pagerank数据/countFlowin-pointRate-pagerank-无黄金分割-10.txt");
		
		//getSortRate("D:/人口数据/0414重新处理/19级数据-pagerank数据/countFlowin-pointRate-pagerank-无黄金分割-10-addcode.txt");
		//arrayAssignment();
		//addCodeName("D:/人口数据/0414重新处理/19级数据-pagerank数据/一倍权重/countFlowin-pointRate-pagerank-无黄金分割-addcode-sort.txt","D:/人口数据/0414重新处理/19级数据-pagerank数据/2014CodeStand.txt");
		
		//delectRedundantCode("D:/人口数据/0414重新处理/13级数据-将主城区code进行合并/2014CodeStand.txt");
		//delectFromToCode("D:/人口数据/0414重新处理/21级数据-曲线聚类/1-去掉from和to为同一code的数据/countFlowout-NewCode-replaced-tidy-countAmounts-sort.txt");
		
		//createCurveData("D:/人口数据/0414重新处理/21级数据-曲线聚类/3-计算流动人口比率/countFlowin-pointRate.txt",
		//		         "D:/人口数据/0414重新处理/21级数据-曲线聚类/3-计算流动人口比率/2014CodeStand.txt",0);
		
		curveDistance("D:/人口数据/0414重新处理/21级数据-曲线聚类/4-计算每个区县的人口流动曲线点/test.txt",
				      "D:/人口数据/0414重新处理/21级数据-曲线聚类/4-计算每个区县的人口流动曲线点/2014CodeStand.txt");
		System.out.println("ok!");

	}
	/**
	 * 将每类曲线定义成一个CC
	 */
	public static ArrayList<CurveClass> curveclass = new ArrayList<CurveClass>();

	/**
	 * 对每个CC进行数据的填充
	 */
	public static void addCurveClass(CurveClass cc) {
		curveclass.add(cc);

	}
	//	 * @param ccmap 用来通过category值索引曲线类,当key为0即索引出0类曲线类
	/**
	 * 计算0类曲线中各条曲线与曲线a的距离
	 * @param map    用来通过code值索引出其对应的曲线点组成的数组
	 * @param cc     即原始曲线类0；
	 * @param codea  即曲线a对应的code
	 * @return    0类曲线中与曲线a的距离最小的曲线对应的code
	 */
	public static String curve0_iDistance(Map<String,String[]> map,CurveClass cc,String codea){

				String[] arraya=map.get(codea);//通过曲线a的code获得曲线a的点数组
				double[] distance=new double[arraya.length];//用于承载0类曲线中的某曲线与a曲线对应点的差值的绝对值
				double[] dismax=new double[cc.codes.size()];//用于承载0类曲线中不同曲线与a曲线的距离
				String[] codesarray=new String[cc.codes.size()];//用于承载dismax数组中的元素（某曲线与a曲线的距离）对应的code值
				for(int i=0;i<cc.codes.size();i++){
					String tempcode=cc.codes.get(i).toString();
					String[] array=map.get(tempcode);
					for(int k=0;k<array.length;k++){
						double d=Math.abs( Double.parseDouble(array[k])-Double.parseDouble(arraya[k]));
						//System.out.println(d);
						distance[k]=d;
					}
					//计算曲线a与0类曲线中各条曲线的距离
					int max=Tool.getMaxNum(distance);
					dismax[i]=distance[max];
					codesarray[i]=tempcode;
				}
				//计算0类曲线中与曲线a距离最小的曲线
				int min=Tool.getMinNum(dismax);
				String codeb=codesarray[min];
				
				return codeb;
	}
	/**
	 * 计算曲线a和曲线b的距离
	 * @param arraya  曲线a对应的点数组
	 * @param arrayb  曲线b对应的点数组
	 * @return 曲线a和曲线b的距离
	 */
	public static double ab_Distance(String[] arraya,String [] arrayb){
		double[] distance= new double[arraya.length];
		for(int k=0;k<arraya.length-1;k++){
			double d=Math.abs( Double.parseDouble(arrayb[k])-Double.parseDouble(arraya[k]));
			System.out.println(k+":"+d);
			distance[k]=d;
		}
		double ab_max=distance[Tool.getMaxNum(distance)];
		return ab_max;
	}
	
	/**
	 * 求K类曲线的质心曲线
	 * @param map  用来通过code值索引出其对应的曲线点组成的数组
	 * @param ccmap  用来通过category值索引曲线类,当key为0即索引出0类曲线类
	 * @param k    曲线类别k
	 * @return  曲线类k的质心曲线对应的点数组
	 */
	public static double[] curveCentroid(Map<String,String[]> map,Map<Integer,CurveClass> ccmap,int k){
		//求1类曲线的质心
		CurveClass cc=new CurveClass();
		cc=ccmap.get(k);
		int length=map.get(cc.codes.get(0).toString()).length;
		
		//定义centroid为质心曲线对应的点数组
		double[] centroid=new double[length];
		
		for(int i=0;i<length;i++){
			centroid[i]=0;			
			for(int j=0;j<cc.codes.size();j++){
				String[] array=map.get(cc.codes.get(j).toString());
				centroid[i]+=Double.parseDouble(array[i]);
			}
			centroid[i]=centroid[i]/cc.codes.size();
		}
		return centroid;
	}
	/**
	 * 在k类曲线中将code对应的曲线删掉
	 * @param k   曲线类别k
	 * @param ccmap   用来通过category值索引曲线类,当key为0即索引出0类曲线类
	 * @param code    要被删除的曲线对应的code值
	 */
	public static void delectCurve(int k,Map<Integer,CurveClass> ccmap,String code){

		CurveClass cc=new CurveClass();
		cc=ccmap.get(k);
		cc.codes.remove(code);
	}
	
	/**
	 * 将code对应的曲线加入曲线类k中		
	 * @param k  曲线类别k
	 * @param ccmap 用来通过category值索引曲线类,当key为0即索引出0类曲线类
	 * @param code   要被添加的曲线对应的code值
	 */
	public static void addCurve(int k,Map<Integer,CurveClass> ccmap,String code){
		//将曲线a归为1类曲线
		CurveClass cc=new CurveClass();
		
		if(ccmap.get(k)!=null){
			cc=ccmap.get(k);
			cc.setCodes(code);
			addCurveClass(cc);
		}else{
			cc.setCategory(k);
			cc.setCodes(code);
			addCurveClass(cc);
			ccmap.put(cc.category, cc);
		}	
	}

	/**
	 * 计算初始类曲线0的相似精度（初始阈值）
	 * @param indexmap   用来检索map中某个具体的code所在的位置
	 * @param map     用来通过code值索引出其对应的曲线点组成的数组
	 * @return result数组，第0个元素是阈值，第1个元素是距离最大的两条曲线对应的code
	 */
	public static String[] InitialThreshold(Map<Integer,String> indexmap,Map<String,String[]> map){
                
		        String[] result=new String[2];
				double[][] DC=new double[indexmap.size()][indexmap.size()];
				String[][] codes=new String[indexmap.size()][indexmap.size()];
				double[] Threshold=new double[indexmap.size()];
				String[] Codes=new String[indexmap.size()];
				
				//计算两两曲线之间的距离，取最大的距离作为曲线类0的相似精度，即初始阈值
				for(int i=0;i<indexmap.size();i++){
					//System.out.println("i="+i+":");
					String codei=indexmap.get(i);
					String[] arrayi=map.get(codei);
					
					double[] distance=new double[arrayi.length];
					
					for(int j=i+1;j<indexmap.size();j++){
						String codej=indexmap.get(j);
						String[] arrayj=map.get(codej);
						int n=arrayi.length;
						int m=arrayj.length;
						
						DC[i][j]=ab_Distance(arrayi,arrayj);
						//System.out.println(DC[i][j]);
						codes[i][j]=codei+"-"+codej;
						//System.out.println(codes[i][j]);
						
						/*
						for(int k=0;k<n;k++){
							double d=Math.abs( Double.parseDouble(arrayi[k])-Double.parseDouble(arrayj[k]));
							//System.out.println(d);
							distance[k]=d;
						}
						//计算曲线codei和曲线codes_j之间的距离
						int max=Tool.getMaxNum(distance);
						*/	
					}
					
					int index=Tool.getMaxNum(DC[i]);//计算曲线i与其他曲线的距离所生成的数组DC[i]中最大距离所对应的DC[i]数组位置
			        double DC_max=DC[i][index];  
			        String Code_max=codes[i][index];//最大距离所对应的两条曲线code
			        
			        Threshold[i]=DC_max;
			       // System.out.println(DC_max);
			        Codes[i]=Code_max;
			       // System.out.println(Code_max);
				}				
				int index=Tool.getMaxNum(Threshold);
				double threshold=Threshold[index];
				String code=Codes[index];
				
				result[0]=Double.toString(threshold);
				System.out.println(threshold);
				result[1]=code;
				System.out.println(code);
				
				return result;
	}

	/**
	 * 当引入一条不属于C的新的曲线Li而得到新的曲线类C′后,要判断是否有D(C′)大于 D(C),使用式(3)计算新曲线类C′的相似精度
	 * @param newcode  新曲线Li
	 * @param k    曲线类别k
	 * @param ccmap  用来通过category值索引曲线类,当key为0即索引出0类曲线类
	 * @param map   用来通过code值索引出其对应的曲线点组成的数组
	 * @return  加入新曲线Li的新曲线类C′的相似精度max_distance
	 */
    public static double newSimilarAccuracy(String newcode,int k,Map<Integer,CurveClass> ccmap,Map<String,String[]> map){
    	 CurveClass cc=new  CurveClass();
    	 cc=ccmap.get(k);
    	 
    	 String[] arraynew=map.get(newcode);
    	 double[] distance=new double[cc.codes.size()];
    	 double max_distance=0;
    	 
    	 for(int i=0;i<cc.codes.size();i++){
    		 String code=cc.codes.get(i).toString();
    		 String[] array=map.get(code);
    		 distance[i]=ab_Distance(arraynew,array);   		 
    	 }
    	 
    	 max_distance=distance[Tool.getMaxNum(distance)];
    	 
    	 return max_distance;
    }
    /**
     * 计算曲线类count的质心曲线与曲线类0的各个曲线的距离，并返回最小值
     * @param map   用来通过code值索引出其对应的曲线点组成的数组
     * @param ccmap   用来通过category值索引曲线类,当key为0即索引出0类曲线类
     * @param centroid  曲线类count的质心曲线对应的点数组
     * @return  曲线类count的质心曲线与曲线类0的各个曲线的距离最小值对应的code
     */
    public static String curve0_centroidDistance(Map<String,String[]> map,Map<Integer,CurveClass> ccmap,double[] centroid){
    	
    	CurveClass cc=new CurveClass();
    	cc=ccmap.get(0);
		double[] distance=new double[centroid.length];//用于承载0类曲线中的某曲线与a曲线对应点的差值的绝对值
		double[] dismax=new double[cc.codes.size()];//用于承载0类曲线中不同曲线与a曲线的距离
		String[] codesarray=new String[cc.codes.size()];//用于承载dismax数组中的元素（某曲线与a曲线的距离）对应的code值
		for(int i=0;i<cc.codes.size();i++){
			String tempcode=cc.codes.get(i).toString();
			String[] array=map.get(tempcode);
			for(int k=0;k<array.length;k++){
				double d=Math.abs( Double.parseDouble(array[k])-centroid[k]);
				//System.out.println(d);
				distance[k]=d;
			}
			//计算曲线a与0类曲线中各条曲线的距离
			int max=Tool.getMaxNum(distance);
			dismax[i]=distance[max];
			codesarray[i]=tempcode;
		}
		//计算0类曲线中与曲线a距离最小的曲线
		int min=Tool.getMinNum(dismax);
		String codeb=codesarray[min];
		
		return codeb;
    }

	/**
	 * 
	 * @param flowfolder
	 * @param codefile
	 */
	public static void curveDistance(String flowfolder,String codefile){
		Vector<String> Folder= FileTool.Load(flowfolder, "utf-8");
		Map<String,String[]> map=new HashMap<String,String[]>();
		Map<Integer,String> indexmap=new HashMap<Integer,String>();
		
		//建立一个map，每个code对应一条曲线数组
		int invalid=0;
		for(int i=0;i<Folder.size();i++){
			String poi=Folder.elementAt(i);
			String[] temp=poi.split(":");
			String code=temp[0];
			String[] ratearray=temp[1].split(",");
			
			if(ratearray[0].equals("0")||ratearray[0].equals("1.0")){
				invalid++;
			}else{
				map.put(code, ratearray);
				indexmap.put(i, code); //建立一个indexmap，用来检索map中某个具体的code所在的位置
			}
			
		}
		System.out.println("无效的点的个数有："+invalid);	
		
		//建立一个map，用来通过category值索引曲线类
		Map<Integer,CurveClass> ccmap=new HashMap<Integer,CurveClass>();
		
		//初始状态下将所有的code放入同0类曲线中
		CurveClass cc=new CurveClass();
		cc.setCategory(0);
		for(int i=0;i<indexmap.size();i++){
			String code=indexmap.get(i);
			cc.setCodes(code);
		}
		addCurveClass(cc);
		ccmap.put(cc.category, cc);
		
	    //计算曲线类C的相似精度D(C)
		String[] result=InitialThreshold(indexmap,map);
		double threshold=Double.parseDouble(result[0]);
		String code=result[1];
		
		//选取两两之间距离最大的曲线即为a
		String a=code.substring(code.indexOf("-")+"-".length());
		
		//curve0是指初始0类曲线中两两距离最大的曲线中仍然留在0类曲线中的那条曲线
		String curve0=code.substring(0,code.indexOf("-"));
		
	    //将曲线a归为1类曲线
		addCurve(1,ccmap,a);
		
		
		//在0类曲线中将a删除
		delectCurve(0,ccmap,a);
		
		
		//计算0类曲线中各条曲线与1类曲线中曲线a的距离，得到0类中与曲线a距离最近的曲线b
		CurveClass cca=new CurveClass();
		cca=ccmap.get(1);
		String codea=cca.codes.get(0).toString();
		String codeb=curve0_iDistance(map,cc,codea);
		String[] arryb=map.get(codeb);
		String[] arraya=map.get(codea);
		
		//计算曲线a和曲线b的距离ab_max
		double ab_distance=newSimilarAccuracy(codeb,1,ccmap,map);
		ab_distance=ab_Distance(arraya,arryb); //调试时注意上下两个的距离是否一样

		int count=1;
		
		CurveClass cc0=new CurveClass();
		cc0=ccmap.get(0);//0类曲线
		
		while(cc0.codes.size()>1){
			if(ab_distance>=threshold){
			//	if(cc0.codes.size()>1){
					//计算曲线类C的相似精度D(C)
					result=InitialThreshold(indexmap,map);
					threshold=Double.parseDouble(result[0]);
					code=result[1];
					
					//选取两两之间距离最大的曲线即为a
		            a=code.substring(code.indexOf("-")+"-".length());
					
				    //将曲线a归为count类曲线
		            count++;
					addCurve(count,ccmap,a);
					
					//在0类曲线中将a删除
					delectCurve(0,ccmap,a);
					
					//计算0类曲线中各条曲线与count类曲线中曲线a的距离，得到0类中与曲线a距离最近的曲线b
					cca=new CurveClass();
					cca=ccmap.get(count);
					codea=cca.codes.get(0).toString();
					codeb=curve0_iDistance(map,cc,codea);
					arryb=map.get(codeb);
					arraya=map.get(codea);
					
					//计算曲线a和曲线b的距离ab_max
					ab_distance=newSimilarAccuracy(codeb,1,ccmap,map);
					ab_distance=ab_Distance(arraya,arryb); //调试时注意上下两个的距离是否一样
			//	}
				
				
			}else{
				//将曲线b归为count类曲线
				cc=ccmap.get(count);
				cc.setCodes(codeb);
				addCurveClass(cc);
				
				//求count类曲线的质心
				double[] curvecentroid=curveCentroid(map,ccmap,count);
			
				
				//在0类曲线中将b删除
				delectCurve(0,ccmap,codeb);
				
				//在曲线类0中,计算各条曲线与曲线类count(曲线质心)的距离，得到最小距离对应的codeb
				codeb=curve0_centroidDistance(map,ccmap,curvecentroid);
				
				ab_distance=newSimilarAccuracy(codeb,count,ccmap,map);	
				System.out.println(ab_distance);
			}
		}
		
		System.out.println("总共有"+ccmap.size()+"类曲线");
		for(int i=0;i<ccmap.size();i++){
			cc=new CurveClass();
			cc=ccmap.get(i);
			System.out.println(cc.category);
			for(int j=0;j<cc.codes.size();j++){
				System.out.println(cc.codes.get(j));
			}
			
		}
		
	}

	/**
	 * 每个code对应生成一条区县L
	 * @param flowfolder
	 * @param codefile
	 * @param n 1为flowout，0为flowin
	 */
	public static void createCurveData(String flowfolder,String codefile,int n){
		Vector<String> Folder= FileTool.Load(flowfolder, "utf-8");
		Vector<String> CodeFile=FileTool.Load(codefile, "utf-8");
		
		double[][] curvedata=new double[2578][2578];
		
		for(int i=0;i<curvedata[0].length;i++){
			for(int j=0;j<curvedata.length;j++){
				curvedata[i][j]=0;
			}	
		}
		
		//设置矩阵的每一行为一个code的比率数组
		Map<Integer, String> map = new HashMap<Integer, String>();
		for(int i=0;i<CodeFile.size();i++){
			String code=Tool.getStrByKey(CodeFile.elementAt(i), "<code>", "</code>", "</code>");
			map.put(i, code);
		}
		
		setPopurate_Descending(flowfolder,n);
		
		Map<String,CountyPopuRate> codemap=new HashMap<String,CountyPopuRate>();
		//建立一个map，key值为各个区县的code，value为popurate中元素的code值为key类
		for(int i=0;i<popurate.size();i++){
				String code=popurate.get(i).code;
				CountyPopuRate cpr=popurate.get(i);
				//System.out.println(code+""+cpr.map+"\n");
				codemap.put(code, cpr);
		}
		
		for(int row=0;row<curvedata.length;row++){
			String code=map.get(row);
			for (Entry<String,CountyPopuRate> entry :codemap.entrySet()) {
				String key=entry.getKey();
				CountyPopuRate value=entry.getValue();
				
				if(code.equals(key)){
					for(int i=0;i<value.map_intkey.size();i++){
						
						curvedata[row][i]=value.map_intkey.get(i);
						//System.out.println(curvedata[row][i]);
					}
				}
			}
			String arry="";
			for(int i=0;i<curvedata[row].length;i++){
				arry+=curvedata[row][i]+",";
			}
			System.out.println(code+":"+arry);
			FileTool.Dump(code+":"+arry, flowfolder.replace(".txt", "") + "-curvedata.txt", "utf-8");
		}
    }
	public static void setPopurate_Descending(String folder,int n){

		Vector<String> pois=FileTool.Load(folder, "utf-8");
		CountyPopuRate cpr=new CountyPopuRate();
		
		boolean flowout;
		if(n==1){
			flowout=true;
		}else{
			flowout=false;
		}
		
		if(flowout){
			int count=0;
			for(int i=0;i<pois.size();i++){
				String[] poi=pois.elementAt(i).split(",");
				String from=poi[0];
				String to=poi[1];
				double rate=Double.parseDouble(poi[2]);
				
				String index ="";
				
				if (i == 0) {
					//flowout:from
					//flowin:to
					cpr.setCode(from);
					
					//flowout:to
					//flowin:from
					cpr.setMap_intkey(count, rate);
				}else{
					String[] before=pois.elementAt(i-1).split(",");
					//flowout:0
					//flowin:1
					index = before[0];
					
					//flowout:from
					//flowin:to
					if(from.equals(index)){
						//flowout:to
						//flowin:from
						count++;
						cpr.setMap_intkey(count, rate);
						
						if(i+1==pois.size()){
							addCountyPopuRate(cpr);
						}
					}else{
						addCountyPopuRate(cpr);
						cpr=new CountyPopuRate();
						//flowout:from
						//flowin:to
						cpr.setCode(from);
						//flowout:to
						//flowin:from
						count=0;
						cpr.setMap_intkey(count, rate);

					}
				}
			}	
		}else{
			int count=0;
			for(int i=0;i<pois.size();i++){
				String[] poi=pois.elementAt(i).split(",");
				String from=poi[0];
				String to=poi[1];
				double rate=Double.parseDouble(poi[2]);
				
				String index ="";
				
				if (i == 0) {
					//flowout:from
					//flowin:to
					cpr.setCode(to);
					
					//flowout:to
					//flowin:from
					cpr.setMap_intkey(count, rate);
				}else{
					String[] before=pois.elementAt(i-1).split(",");
					//flowout:0
					//flowin:1
					index = before[1];
					
					//flowout:from
					//flowin:to
					if(to.equals(index)){
						//flowout:to
						//flowin:from
						count++;
						cpr.setMap_intkey(count, rate);
						
						if(i+1==pois.size()){
							addCountyPopuRate(cpr);
						}
						
					}else{
						addCountyPopuRate(cpr);
						cpr=new CountyPopuRate();
						//flowout:from
						//flowin:to
						cpr.setCode(to);
						//flowout:to
						//flowin:from
						count=0;
						cpr.setMap_intkey(count, rate);
					}
				}
			}
		}
		
		
		//for (int i = 0; i < popurate.size(); i++) {
			//System.out.println(popurate.get(i).code+":");
			//System.out.println("共有"+popurate.get(i).map.size()+"个区县");
		//	for (Entry<String, Double> entry : popurate.get(1).map.entrySet()) {
		//		System.out.println(entry.getKey()+":"+entry.getValue());
		//	}
        //}		
	}
	/**
	 * 将from和to的code相同的数据去掉
	 * @param folder
	 */
	public static void delectFromToCode(String folder){
		Vector<String> Folder= FileTool.Load(folder, "utf-8");
		int count=0;
		for (int k = 0; k < Folder.size(); k++) {
			String poi = Folder.elementAt(k);
			String from=Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
			String to=Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
			if((from.equals(to))){
				count++;
			}else{
				FileTool.Dump(poi, folder.replace(".txt", "") + "-delectfromto.txt", "utf-8");
			}
		}
		System.out.println(count);
		
	}
	/**
	 * 将合并后多余的code去掉
	 * @param folder
	 */
	public static void delectRedundantCode(String folder){
		Vector<String> Folder= FileTool.Load(folder, "utf-8");
		Map<Integer, String> map = new HashMap<Integer, String>();
		
		for (int k = 0; k < Folder.size(); k++) {
			String poi = Folder.elementAt(k);
			int code=Integer.parseInt(Tool.getStrByKey(poi, "<code>", "</code>", "</code>"));
			
			if(k==0){
				map.put(code, poi);
			}else{
				if(map.get(code)==null){
					map.put(code, poi);
				}
			}
			
		}
		
		int[] code=new int[map.size()];
		String[] poi=new String[map.size()];
		int count=0;
		for (Entry<Integer, String> entry : map.entrySet()) {
			int key=entry.getKey();
			String value = entry.getValue();
			
			code[count]=key;
			poi[count]=value;
			count++;
		}
		
		Tool.InsertSortArray_Ascending(map.size(),code,poi);

		for (int i = 0; i < poi.length; i++) {
			System.out.println(poi[i]);
			FileTool.Dump(poi[i], folder.replace(".txt", "") + "-delectRedundantCode.txt", "utf-8");
		}
		
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
	 * 
	 * @param data
	 * @param codefile
	 */
	public static void addCodeName(String data,String codefile){
		Vector<String> Data=FileTool.Load(data, "utf-8");
		Vector<String> Code=FileTool.Load(codefile, "utf-8");
		Map<String,String> codemap=new HashMap<String,String>();
		for(int i=0;i<Code.size();i++){
			String poi=Code.elementAt(i);
			String code=Tool.getStrByKey(poi, "<code>", "</code>", "</code>");
			String name=Tool.getStrByKey(poi, "<sname>", "</sname>", "</sname>");

			codemap.put(code, name);
		}
		for(int i=0;i<Data.size();i++){
			String[] data1=Data.elementAt(i).split(",");
			String code=data1[0];
			String weight=data1[1];
			if(codemap.get(code)!=null){
				String str=data1[0]+","+codemap.get(code)+","+data1[1];
				FileTool.Dump(str, data.replace(".txt", "")+"-addname.txt", "utf-8");
				
			}
			
		}
		
	}
	/**
	 * 给矩阵部分元素赋值，其他元素为0
	 */
	public static void arrayAssignment(){
		//数组值arr[x][y]表示指定的是第x行第y列的值。
		//在使用二维数组对象时，注意length所代表的长度，
		//数组名后直接加上length(如arr.length)，所指的是有几行(Row)；
		//指定索引后加上length(如arr[0].length)，指的是该行所拥有的元素，也就是列(Column)数目。
				
		double[][] array=new double[3][3];

		//一列一列地赋值
		for(int i=0;i<array[0].length;i++){
			for(int j=0;j<array.length;j++){
				array[i][j]=0;
			}	
		}
		
		array[0][1]=0.1;
		array[0][2]=0.2;
		
		for(int i=0;i<array[0].length;i++){
			for(int j=0;j<array.length;j++){
				System.out.println(array[i][j]);
			}
			System.out.println("\n");
		}
		
		
	}


	/**
	 * 将得到的rankpage结果进行排序
	 * @param folder
	 */
	public static void getSortRate(String folder){
		Vector<String> pois=FileTool.Load(folder, "utf-8");
		String[] code_rate=new String[pois.size()];
		double[] rate=new double[pois.size()];
		for(int i=0;i<pois.size();i++){
			String poi=pois.elementAt(i);
			code_rate[i]=poi;
			String[] data=pois.elementAt(i).split(",");
			rate[i]=Double.parseDouble(data[1]);
		}
		Tool.InsertSortArray(code_rate.length, rate, code_rate);
		for(int i=0;i<code_rate.length;i++){
			FileTool.Dump(code_rate[i], folder.replace(".txt", "")+"-sort.txt", "utf-8");
		}
	}
	/**
	 * 
	 * @param codefile
	 * @param ratefile
	 */
	public static void flowRank(String codefile,String ratefile){
		Vector<String> codes=FileTool.Load(codefile, "utf-8");
		Vector<String> rates=FileTool.Load(ratefile, "utf-8");
		for(int i=0;i<codes.size();i++){
			String code=Tool.getStrByKey(codes.elementAt(i), "<code>", "</code>", "</code>");
			String rate=rates.elementAt(i);
			String str=code+","+rate;
			FileTool.Dump(str, ratefile.replace(".txt", "")+"-addcode.txt", "utf-8");
		}
	}
	public static  ArrayList<CountyPopuRate> popurate=new ArrayList<CountyPopuRate>();
	public static void addCountyPopuRate(CountyPopuRate cpr) {
		popurate.add(cpr);
    }
	/**
	 * 将该区县流入到其他各个区县的比率存入 popurate中
	 * @param folder
	 */
	public static void setPopurate(String folder,int n){
		Vector<String> pois=FileTool.Load(folder, "utf-8");
		CountyPopuRate cpr=new CountyPopuRate();
		
		boolean flowout;
		if(n==1){
			flowout=true;
		}else{
			flowout=false;
		}
		
		if(flowout){
			for(int i=0;i<pois.size();i++){
				String[] poi=pois.elementAt(i).split(",");
				String from=poi[0];
				String to=poi[1];
				double rate=Double.parseDouble(poi[2]);
				
				String index ="";
				
				if (i == 0) {
					//flowout:from
					//flowin:to
					cpr.setCode(from);
					
					//flowout:to
					//flowin:from
					cpr.setMap(to, rate);
				}else{
					String[] before=pois.elementAt(i-1).split(",");
					//flowout:0
					//flowin:1
					index = before[0];
					
					//flowout:from
					//flowin:to
					if(from.equals(index)){
						//flowout:to
						//flowin:from
						cpr.setMap(to, rate);
						
						if(i+1==pois.size()){
							addCountyPopuRate(cpr);
						}
					}else{
						addCountyPopuRate(cpr);
						cpr=new CountyPopuRate();
						//flowout:from
						//flowin:to
						cpr.setCode(from);
						//flowout:to
						//flowin:from
						cpr.setMap(to, rate);

					}
				}
			}	
		}else{
			for(int i=0;i<pois.size();i++){
				String[] poi=pois.elementAt(i).split(",");
				String from=poi[0];
				String to=poi[1];
				double rate=Double.parseDouble(poi[2]);
				
				String index ="";
				
				if (i == 0) {
					//flowout:from
					//flowin:to
					cpr.setCode(to);
					
					//flowout:to
					//flowin:from
					cpr.setMap(from, rate);
				}else{
					String[] before=pois.elementAt(i-1).split(",");
					//flowout:0
					//flowin:1
					index = before[1];
					
					//flowout:from
					//flowin:to
					if(to.equals(index)){
						//flowout:to
						//flowin:from
						cpr.setMap(from, rate);
						
						if(i+1==pois.size()){
							addCountyPopuRate(cpr);
						}
					}else{
						addCountyPopuRate(cpr);
						cpr=new CountyPopuRate();
						//flowout:from
						//flowin:to
						cpr.setCode(to);
						//flowout:to
						//flowin:from
						cpr.setMap(from, rate);

					}
				}
			}
		}
		
		
		//for (int i = 0; i < popurate.size(); i++) {
			//System.out.println(popurate.get(i).code+":");
			//System.out.println("共有"+popurate.get(i).map.size()+"个区县");
		//	for (Entry<String, Double> entry : popurate.get(1).map.entrySet()) {
		//		System.out.println(entry.getKey()+":"+entry.getValue());
		//	}
			
			
		//}
		
	}
	/**
	 * pagerank算法计算每个区县的流动权重
	 * @param folder ：行政区划标准文件
	 * @param flowrate ：流动人口数据文件
	 */
	public static void pageRank(String folder,String flowrate){
		Vector<String> pois = FileTool.Load(folder, "utf-8");
		Vector<String> flow = FileTool.Load(flowrate, "utf-8");
		Map<String, Integer> map = new HashMap<String,Integer>();
		Map<String,CountyPopuRate> codemap=new HashMap<String,CountyPopuRate>();
		
		for(int i=0;i<pois.size();i++){
			String poi=pois.elementAt(i);
			String code=Tool.getStrByKey(poi, "<code>", "</code>", "</code>");
			
			//flowout:from
			//flowin:to
			String to=code;
			
			//flowout:from
			//flowin:to
			map.put(to, i);//标识每个code所在的行列号
		}
		System.out.println(map.size());
		double[][] arry=new double[map.size()][map.size()];
		
		//将每个区县的code和流入（或者流出）的map存入到popurate中去
		
		//flowout:
		//flowin:
		setPopurate(flowrate,0);
		
		//建立一个map，key值为各个区县的code，value为popurate中元素的code值为key类
		for(int i=0;i<popurate.size();i++){
			String code=popurate.get(i).code;
			CountyPopuRate cpr=popurate.get(i);
			//System.out.println(code+""+cpr.map+"\n");
			codemap.put(code, cpr);
		}
	
		//首先对矩阵进行初始化，全部赋值为零
		//一列一列地赋值
		for(int i=0;i<arry[0].length;i++){
			for(int j=0;j<arry.length;j++){
				arry[i][j]=0;
			}	
		}
				
		
		//对矩阵中有值的元素进行赋值，其他元素保持为0
		for(int col=0;col<map.size();col++){
			Map_ValueGetKey mvg=new Map_ValueGetKey(map);
			//flowout:from
			//flowin:to
			String to=(String) mvg.getKey(col);
			int ok=0;
			
			
			for (Entry<String,CountyPopuRate> entry : codemap.entrySet()) {
					String key=entry.getKey();
					CountyPopuRate value=entry.getValue();
					//flowout:from
					//flowin:to
					if(to.equals(key)){
						for(int row=0;row<map.size();row++){
							//flowout:to
							//flowin:from
							String from= mvg.getKey(row);
							                                      //Object val=value.map.get(from);
							//flowout:to
							//flowin:from
							if(value.map.get(from)!=null){
								//flowout:to
								//flowin:from
								arry[row][col]=value.map.get(from);
								ok++;
								//System.out.println("to"+to+"from"+value.map+":"+arry[row][col]);
							}
						}	
					}
				}

				/*
				for(int i=0;i<popurate.size();i++){
					String code=popurate.get(i).code;
					//flowout:from
					//flowin:to
					if(code.equals(to)){
						//flowout:to
						//flowin:from
						if(popurate.get(i).map.get(from)!=null){
							//flowout:to
							//flowin:from
							arry[row][col]=popurate.get(i).map.get(from);
						    //System.out.println(to+":"+arry[row][col]);
						    ok++;
						}
					}	
				}
				*/

			//flowout:from
			//flowin:to
			System.out.println(to+"-ok:"+ok);
			
	 }
		double[] v=new double[map.size()];
		for(int i=0;i<v.length;i++){
			double ratio=(double)(1)/(map.size());
			v[i]=ratio;
		}
		double[] result=new double[v.length];
		 for(int i=0;i<10;i++){
			 if(i==0){
				result=matrixOperation(arry,v); 
			 }else{
				result=matrixOperation(arry,result); 
			 }
			 
		 }
		 
		 for(int i=0;i<result.length;i++){
				System.out.println(result[i]);
				FileTool.Dump(Double.toString(result[i]), flowrate.replace(".txt", "")+"-pagerank-无黄金分割-10.txt", "utf-8");
		}	
		 System.out.println("arry[0].length:"+arry[0].length);
		 System.out.println("arrylength:"+arry.length);
		
	}
	/**
	 * n*n矩阵与n*1维矩阵相乘
	 * @param arry
	 * @param v
	 * @return
	 */
	public static double[] matrixOperation(double[][] arry,double[] v){
		/*double[] v=new double[2];
		for(int i=0;i<v.length;i++){
			double ratio=(double)(1)/(2);
			v[i]=ratio;
		}
		*/
		double[] result=new double[v.length];
		double temp=0;
		
		//double[][] arry={{1,2},{3,4}};
		for(int i=0;i<arry.length;i++){
			for(int k=0;k<arry[0].length;k++){

					//temp+=0.618*arry[i][k]*v[k]+0.328*v[k]; //利用公式V'=αMV+(1-α)e
					
					temp+=arry[i][k]*v[k]*10;
				
				 
			}
			result[i]=temp;
			temp=0;
		}
		
		//for(int i=0;i<result.length;i++){
		//	System.out.println(result[i]);
		//}
		
		return result;
	}
	

	/**
	 * 计算某县流入到该区县的人口占该区县总人口的比例
	 * @param folder 某区县的总人数文件
	 * @param flowfile 人口流动具体文件
	 * @param n  1为flowout，0为flowin
	 */
	public static void pageRankData(String folder,String flowfile,int n){
		Vector<String> pois = FileTool.Load(folder, "utf-8");
		Vector<String> flows = FileTool.Load(flowfile, "utf-8");
		
		System.out.println(pois.size());
		
		System.out.println(flows.size());
		Map<String, Double> map = new HashMap<String, Double>();
		
		boolean flowout;
		if(n==1){
			flowout=true;
		}else{
			flowout=false;
		}
		
		if(flowout){
			for (int k = 0; k < pois.size(); k++) {
				String poi = pois.elementAt(k);
				
				String from= Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
				String to= Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
				double  amounts=Double.parseDouble(Tool.getStrByKey(poi, "<amounts>", "</amounts>", "</amounts>"));
				
				//flowout:from
				//flowin:to
				map.put(from,amounts);
			}
			System.out.println(map.size());
			
			for(int i=0;i<flows.size();i++){
				String flow=flows.elementAt(i);
				String from= Tool.getStrByKey(flow, "<from>", "</from>", "</from>");
				String to= Tool.getStrByKey(flow, "<to>", "</to>", "</to>");
				double  amounts=Double.parseDouble(Tool.getStrByKey(flow, "<amounts>", "</amounts>", "</amounts>"));
				for (Entry<String, Double> entry : map.entrySet()) {
					String key=entry.getKey();
					Double value = entry.getValue();
					//flowout:from
					//flowin:to
					if(from.equals(key)){
						double rate=(amounts)/(value);
						String str=from+","+to+","+rate;
						System.out.println(str);
						FileTool.Dump(str, flowfile.replace(".txt", "") + "-pointRate.txt", "utf-8");
					}				
				}
				
			}
		}else{
			for (int k = 0; k < pois.size(); k++) {
				String poi = pois.elementAt(k);

				String from= Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
				String to= Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
				double  amounts=Double.parseDouble(Tool.getStrByKey(poi, "<amounts>", "</amounts>", "</amounts>"));
				
				//flowout:from
				//flowin:to
				map.put(to,amounts);
			}
			System.out.println(map.size());
			
			for(int i=0;i<flows.size();i++){
				String flow=flows.elementAt(i);
				String from= Tool.getStrByKey(flow, "<from>", "</from>", "</from>");
				String to= Tool.getStrByKey(flow, "<to>", "</to>", "</to>");
				double  amounts=Double.parseDouble(Tool.getStrByKey(flow, "<amounts>", "</amounts>", "</amounts>"));
				for (Entry<String, Double> entry : map.entrySet()) {
					String key=entry.getKey();
					Double value = entry.getValue();
					//flowout:from
					//flowin:to
					if(to.equals(key)){
						double rate=(amounts)/(value);
						String str=from+","+to+","+rate;
						System.out.println(str);
						FileTool.Dump(str, flowfile.replace(".txt", "") + "-pointRate.txt", "utf-8");
					}				
				}
				
			}
			
		}
		
	}
	/**
	 * 将文件中的大城市的主要区县的code替换成简要的code
	 * @param folder 主要城区数据-citymaincode.txt
	 * @param file countFlowin-NewCode.txt
	 */
	public static void replaceCode(String folder,String file){
		Vector<String> pois = FileTool.Load(folder, "utf-8");
		Map<String, String> map = new HashMap<String, String>();
		for (int k = 0; k < pois.size(); k++) {
			String poi = pois.elementAt(k);
			String scode= Tool.getStrByKey(poi, "<scode>", "</scode>", "</scode>");
			String subcode=Tool.getStrByKey(poi, "<subcode>", "</subcode>", "</subcode>");
			map.put(scode, subcode);
		}
		
		Vector<String> flow = FileTool.Load(file, "utf-8");
		for (int k = 0; k < flow.size(); k++) {
			String poi=flow.elementAt(k);
			String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
			String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
			String amount = Tool.getStrByKey(poi, "<amount>", "</amount>", "</amount>"); 
			
			for (Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey().toString();
				String value = entry.getValue();
				if(from.equals(key)){
					from=value;
				}
			}
			
			for (Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey().toString();
				String value = entry.getValue();
				if(to.equals(key)){
					to=value;
				}
			}
			
			String str="<from>"+from+"</from>"+"<to>"+to+"</to>"+"<amount>"+amount+"</amount>";
			//System.out.println(str);
			FileTool.Dump(str, file.replace(".txt", "") + "-replaced.txt", "utf-8");	
		}
		
	}
	//mainCity("D:/人口数据/0414重新处理/11级数据-合并地级市主城区/地级市主城区合并.txt");
	/**
	 * 将城市的主要区县code全部编程缩略的四位数code
	 */
	public static void mainCity(String folder){
		Vector<String> pois = FileTool.Load(folder, "utf-8");
		for (int k = 0; k < pois.size(); k++) {
			String poi = pois.elementAt(k);
			String code= Tool.getStrByKey(poi, "<scode>", "</scode>", "</scode>");
			String subcode=code.substring(0, 4);
			String temp="<scode>"+code+"</scode>"+"<subcode>"+subcode+"</subcode>";
			FileTool.Dump(temp, folder.replace(".txt", "") + "-citymaincode.txt", "utf-8");	
		}
	}
	
	
	
/**
 * 将文件中的poi以省分类排列
 * @param folder
 */
	public static void provinceOrder(String folder) {
		Vector<String> pois = FileTool.Load(folder, "utf-8");
		for (int k = 0; k < pois.size(); k++) {
			for (int i = 0; i < pois.size(); i++) {
				String poi = pois.elementAt(i);
				String addr = Tool.getStrByKey(poi, "<CodeAddr>", "</CodeAddr>", "</CodeAddr>");
				if ((addr.indexOf("北京") != -1)&&k==0) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("天津") != -1)&&k==1) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("河北") != -1)&&k==2) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("山西") != -1)&&k==3) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("内蒙古") != -1)&&k==4) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("辽宁") != -1)&&k==5) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("吉林") != -1)&&k==6) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("黑龙江") != -1)&&k==7) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("上海") != -1)&&k==8) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("江苏") != -1)&&k==9) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("浙江") != -1)&&k==10) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("安徽") != -1)&&k==11) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("福建") != -1)&&k==12) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("江西") != -1)&&k==13) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("山东") != -1)&&k==14) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("河南") != -1)&&k==15) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("湖北") != -1)&&k==16) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("湖南") != -1)&&k==17) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("广东") != -1)&&k==18) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("广西") != -1)&&k==19) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("海南") != -1)&&k==20) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("重庆") != -1)&&k==21) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("四川") != -1)&&k==22) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("贵州") != -1)&&k==23) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("云南") != -1)&&k==24) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("西藏") != -1)&&k==25) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("陕西") != -1)&&k==26) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("甘肃") != -1)&&k==27) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("青海") != -1)&&k==28) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("新疆") != -1)&&k==29) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				if ((addr.indexOf("宁夏回族自治区") != -1)&&k==30) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-order.txt", "utf-8");
				}
				
			}
		}

	}

	/**
	 * 将f1和f2中code所对应的地址相同的连接起来，形成新旧code
	 * 
	 * @param f1 standcode
	 * @param f2 oldcode
	 */
	public static void contectcode(String f1, String f2) {
		Vector<String> standCode = FileTool.Load(f1, "utf-8");
		Vector<String> oldCode = FileTool.Load(f2, "utf-8");
		for (int i = 0; i < oldCode.size(); i++) {
			String poi = oldCode.elementAt(i);
			String oldcode=Tool.getStrByKey(poi, "<Code>", "</Code>", "</Code>");
			String oldname=Tool.getStrByKey(poi, "<CodeAddr>", "</CodeAddr>", "</CodeAddr>");
			String reg = Tool.getStrByKey(poi, "<CodeReg>", "</CodeReg>", "</CodeReg>").replace(",", "").replace("null","");
			for (int k = 0; k < standCode.size(); k++) {
				String stand = standCode.elementAt(k);
				String standcode=Tool.getStrByKey(stand, "<scode>", "</scode>", "</scode>");
				String standname=Tool.getStrByKey(stand, "<sname>", "</sname>", "</sname>");
				String standcoor=Tool.getStrByKey(stand, "<scoor>", "</scoor>", "</scoor>");
				String standreg = Tool.getStrByKey(stand, "<sreg>", "</sreg>", "</sreg>").replace(",", "").replace("null","");
				if (reg.equals(standreg)) {

					String ok = "<standcode>" + standcode+ "</standcode>" + "<standname>" + standname
							+ "</standname>" + "<standcoor>" +standcoor + "</standcoor>" + "<standreg>" + standreg
							+ "</standreg>"+"<oldcode>"+oldcode+"</oldcode>"+"<oldname>"+oldname+"</oldname>";
					//System.out.println(ok);
					FileTool.Dump(ok, f2.replace(".txt", "") + "-Standard.txt", "utf-8");
					break;
					
				}else if((k==standCode.size()-1)&&(!(reg.equals(standreg)))){
					System.out.println(poi);
					FileTool.Dump(poi, f2.replace(".txt", "") + "-Exception.txt", "utf-8");
				}

			}
		}

	}

	/**
	 * 核查“合并后的problemcode-result.txt”文件中哪些reg是有地级市数据的，哪些没有
	 * 
	 * @param folder
	 */
	public static void investigationReg(String folder) {
		Vector<String> POI = FileTool.Load(folder, "utf-8");
		for (int i = 0; i < POI.size(); i++) {
			String poi = POI.elementAt(i);
			String reg = Tool.getStrByKey(poi, "<CodeReg>", "</CodeReg>", "</CodeReg>");
			String[] CodeReg = reg.split(",");
			if (CodeReg[2].equals("null")) {
				FileTool.Dump(poi, folder.replace(".txt", "") + "-null.txt", "utf-8");
			} else {
				FileTool.Dump(poi, folder.replace(".txt", "") + "-ok.txt", "utf-8");
			}
		}
	}

	/**
	 * 找到旧code所对应的具体地址
	 * 
	 * @param f1
	 *            合并后的problemcode.txt
	 * @param f2
	 *            CodeResult.txt
	 */
	public static void checkOldCode(String f1, String f2) {
		Map<String, String> map = new HashMap<String, String>();
		Vector<String> code1 = FileTool.Load(f1, "utf-8");
		Vector<String> code2 = FileTool.Load(f2, "utf-8");

		for (int k = 0; k < code2.size(); k++) {
			String poi = code2.elementAt(k);
			String code = Tool.getStrByKey(poi, "<Code>", "</Code>", "</Code");
			map.put(code, poi);
		}
		int count = 0;
		for (int i = 0; i < code1.size(); i++) {
			String code = code1.elementAt(i);
			if (map.get(code) != null) {
				System.out.println(code + ":" + map.get(code));
				FileTool.Dump(map.get(code), f1.replace(".txt", "") + "-result.txt", "utf-8");
				count++;
			} else {
				System.out.println(code);
				FileTool.Dump(code, f1.replace(".txt", "") + "-codenull.txt", "utf-8");
			}
		}
		System.out.println(count);

	}

	/**
	 * 将 countFlowin-gatherBigCity-countAmounts-sort-fromtomap.txt和countFlowout-
	 * gatherBigCity-countAmounts-sort-fromtomap.txt合并 生成“合并后的problemcode.txt”
	 * 
	 * @param f1：countFlowin-gatherBigCity-countAmounts-sort-fromtomap.txt
	 * @param f2：countFlowout-gatherBigCity-countAmounts-sort-fromtomap.txt
	 */
	public static void maptogether(String f1, String f2) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		Vector<String> code1 = FileTool.Load(f1, "utf-8");
		Vector<String> code2 = FileTool.Load(f2, "utf-8");
		for (int i = 0; i < code1.size(); i++) {
			map.put(code1.elementAt(i), i);
		}
		for (int i = 0; i < code2.size(); i++) {
			map.put(code2.elementAt(i), i);
		}
		for (Entry<String, Integer> entry : map.entrySet()) {
			System.out.println(entry.getKey());
			FileTool.Dump(entry.getKey(), "D:/人口数据/3级数据-统计人口流入流出数据/0405/合并后的problemcode.txt", "utf-8");
		}
	}

	/**
	 * 检查pengdingcode文件中的不符合standcode中的code
	 * 
	 * @param standcode
	 *            标准code系统，为2015行政区划代码
	 * @param pendingcode
	 *            需要核查code的文件
	 */
	public static void checkLeakCode(String standcode, String pendingcode) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> fromtomap = new HashMap<String, String>();
		Map<String, String> tomap = new HashMap<String, String>();
		Vector<String> Standcode = FileTool.Load(standcode, "utf-8");
		Vector<String> Pendingcode = FileTool.Load(pendingcode, "utf-8");
		for (int i = 0; i < Standcode.size(); i++) {
			String poi = Standcode.elementAt(i);
			String scode = Tool.getStrByKey(poi, "<scode>", "</scode>", "</scode>");
			String sname = Tool.getStrByKey(poi, "<sname>", "</sname>", "</sname>");
			map.put(scode, sname);

		}
		for (int i = 0; i < Pendingcode.size(); i++) {
			String poi = Pendingcode.elementAt(i);
			String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
			String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
			if (map.get(from) == null && from.length() > 4) {
				// System.out.println("fromproblem:"+poi);
				FileTool.Dump(from, pendingcode.replace(".txt", "") + "-fromproblem.txt", "utf-8");
				fromtomap.put(from, Integer.toString(i));
			}
			if (map.get(to) == null && to.length() > 4) {
				// System.out.println("toproblem:"+poi);
				FileTool.Dump(to, pendingcode.replace(".txt", "") + "-toroblem.txt", "utf-8");
				fromtomap.put(to, Integer.toString(i));
			}
		}
		System.out.println("fromtomap:");
		for (Entry<String, String> entry : fromtomap.entrySet()) {
			System.out.println(entry.getKey());
			FileTool.Dump(entry.getKey(), pendingcode.replace(".txt", "") + "-fromtomap.txt", "utf-8");
		}
	}

	/**
	 * 利用NewOld_Code文件将待处理文件的from和to全都通过替换统一成“2015行政区划代码.txt”中的code
	 * 
	 * @param pending 待处理的文件
	 * @param code 标准的code文件
	 */
	public static void replaceOldCode(String pending, String code) {
		Map<String, String> standard_map = new HashMap<String, String>();
		Vector<String> Pending = FileTool.Load(pending, "utf-8");
		Vector<String> Code = FileTool.Load(code, "utf-8");

		for (int i = 0; i < Code.size(); i++) {
			String poi = Code.elementAt(i);
			String newtag = Tool.getStrByKey(poi, "<standcode>", "</standcode>", "</standcode>");
			String oldtag = Tool.getStrByKey(poi, "<oldcode>", "</oldcode>", "</oldcode>");
			standard_map.put(oldtag, newtag);
		}
		int count = 0;
		for (int i = 0; i < Pending.size(); i++) {
			String poi = Pending.elementAt(i);
			String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
			String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
			String amount = Tool.getStrByKey(poi, "<amount>", "</amount>", "</amount>");
			String newfrom= standard_map.get(from);// from和to
			String newto= standard_map.get(to);
			

			if (newto!= null) {// from和to
				String str = "<from>" +from + "</from>" + "<to>" +newto + "</to>" + "<amount>" + amount
						+ "</amount>";// from和to
				count++;
				FileTool.Dump(str, pending.replace(".txt", "") + "-replaceOldCode.txt", "utf-8");
			} else {
				FileTool.Dump(poi, pending.replace(".txt", "") + "-replaceOldCode.txt", "utf-8");
			}

		}
		System.out.println("共有" + count + "处替换");

	}

	/**
	 * 排查出区县的新旧code
	 * 
	 * @param standard
	 *            用于排查的2014年行政区划代码文件
	 * @param pendingCode
	 *            将要被排查的文件
	 */
	public static void distinguishCode(String standard, String pendingCode) {
		Map<String, String> standard_map = new HashMap<String, String>();
		Map<String, String> pending_map = new HashMap<String, String>();
		Map<String, String> reg_map = new HashMap<String, String>();
		Vector<String> standard_code = FileTool.Load(standard, "utf-8");
		Vector<String> pending_code = FileTool.Load(pendingCode, "utf-8");

		for (int i = 0; i < standard_code.size(); i++) {
			String poi = standard_code.elementAt(i);
			String[] code = poi.split(",");
			standard_map.put(code[0], code[1]);
		}

		for (int i = 0; i < pending_code.size(); i++) {
			String poi = pending_code.elementAt(i);
			String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
			String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
			String reg = Tool.getStrByKey(poi, "<toReg>", "<toReg>", "<toReg>");
			pending_map.put(from, to);
			reg_map.put(to, reg);

		}

		for (Entry<String, String> pending_entry : pending_map.entrySet()) {
			String from = pending_entry.getKey();
			String to = pending_entry.getValue();
			for (Entry<String, String> standard_entry : standard_map.entrySet()) {
				String code = standard_entry.getKey();
				String name = standard_entry.getValue();
				String poi = "";
				if (from.equals(code)) {
					poi = "<new>" + code + "</new>" + "<old>" + to + "</old>" + "<standardname>" + name
							+ "</standardname>" + "<regname>" + reg_map.get(to) + "</regname>";
					System.out.println(poi);
					FileTool.Dump(poi, "D:/人口数据/14级数据-通过排查确定新旧code/NewOld_Code.txt", "utf-8");
					break;
				} else if (to.equals(code)) {
					poi = "<new>" + code + "</new>" + "<old>" + from + "</old>" + "<standardname>" + name
							+ "</standardname>" + "<regname>" + reg_map.get(to) + "</regname>";
					System.out.println(poi);
					FileTool.Dump(poi, "D:/人口数据/14级数据-通过排查确定新旧code/NewOld_Code.txt", "utf-8");
					break;
				}

			}

		}

	}

	/**
	 * 将合并数据的另一个没有合并成大城市的from或者to合并，并且统计
	 * 
	 * @param folder
	 */
	public static void mergeOtherData(String folder) {
		int i = 0;
		try {
			String poi = "";
			Map<String, Integer> map = new HashMap<String, Integer>();
			Vector<String> file = FileTool.Load(folder, "utf-8");
			int count = 0;
			for (i = 0; i < file.size(); i++) {
				poi = file.elementAt(i);
				String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
				String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
				int amount = Integer.parseInt(Tool.getStrByKey(poi, "<amounts>", "</amounts>", "</amounts>"));
				String subfrom = "";
				String subto = "";
				if (from.length() >= 4) {
					subfrom = from.substring(0, 4);// from和to
				}

				if (i == 0) {
					FileTool.Dump(poi, folder.replace(".txt", "") + "-doubleMerge.txt", "utf-8");
				} else {
					String probe = Tool.getStrByKey(file.elementAt(i - 1), "<to>", "</to>", "</to>");// from和to
					if (to.equals(probe)) {// from和to
						if ((subfrom.equals(to)) && (to.length() == 4)) {// from和to

							if (count == 0) {
								map.put(subfrom, amount);// from和to
								count++;
							} else {
								int before = map.get(subfrom);// from和to
								map.put(subfrom, amount + before);// from和to
							}

						} else {
							FileTool.Dump(poi, folder.replace(".txt", "") + "-doubleMerge.txt", "utf-8");
						}
					} else {
						if (map.size() != 0) {
							for (Entry<String, Integer> entry : map.entrySet()) {
								String key = entry.getKey().toString();
								int value = entry.getValue();

								// from和to
								String str = "<from>" + key + "</from>" + "<to>" + probe + "</to>" + "<amounts>" + value
										+ "</amounts>";
								System.out.println(str);
								FileTool.Dump(str, folder.replace(".txt", "") + "-doubleMerge.txt", "utf-8");
							}
							map.clear();
							count = 0;
						}
						FileTool.Dump(poi, folder.replace(".txt", "") + "-doubleMerge.txt", "utf-8");
					}

				}

			}
		} catch (StringIndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
			System.out.println(i);
		}

	}

	/**
	 * 对countFlowin-tidy-countAmounts-sort-countTotalAmounts.txt文件进行排序
	 * 
	 * @param folder
	 */
	public static void getTotalAmountsSort(String folder) {
		Vector<String> file = FileTool.Load(folder, "utf-8");
		String[] arry_index = new String[3161];
		int[] arry_amounts = new int[3161];
		for (int i = 0; i < file.size(); i++) {
			String poi = file.elementAt(i);
			String index = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");// from和to
			String amounts = Tool.getStrByKey(poi, "<amounts>", "</amounts>", "</amounts>");
			arry_index[i] = index;
			arry_amounts[i] = Integer.parseInt(amounts);

		}
		Tool.InsertSortArray_Descending(arry_index.length, arry_amounts, arry_index);

		for (int i = 0; i < arry_index.length; i++) {
			String poi = "<to>" + arry_index[i] + "</to>" + "<amounts>" + arry_amounts[i] + "</amounts>";// from和to
			System.out.println(poi);
			FileTool.Dump(poi, folder.replace(".txt", "") + "-sort.txt", "utf-8");
		}
	}

	/**
	 * 比较两个文件中的记录是否相同，并且将相同的记录写下来，从而找出两个文件是否存在不相同的记录
	 * 
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
			if (map2.get(arr2[0]) != null) {
				int s = map2.get(arr2[0]);
				System.out.println(arr2[0] + ":" + s + arr2[1]);
				map2.put(arr2[0], Integer.parseInt(s + arr2[1]));
			} else {
				map2.put(arr2[0], Integer.parseInt(arr2[1]));
			}
			map2.put(arr2[0], Integer.parseInt(arr2[1]));
		}
		Iterator<String> it1 = map1.keySet().iterator();
		System.out.println("map1.size()=" + map1.size());
		while (it1.hasNext()) {

			String key1;
			int value1;

			key1 = (String) it1.next();
			value1 = map1.get(key1);

			// System.out.println("map1-" + key1 + ":" + value1);
			Iterator<String> it2 = map2.keySet().iterator();
			System.out.println("map2.size()=" + map2.size());
			while (it2.hasNext()) {
				String key2;
				int value2;
				key2 = (String) it2.next();
				value2 = map2.get(key2);
				// System.out.println("map2-" + key2 + ":" + value2);
				if ((key1.equals(key2)) && (value1 == value2)) {
					// FileTool.Dump(key1 + ":" + value1,
					// "D:/人口数据/3级数据-统计人口流入流出数据/key1=key2.txt", "utf-8");
					// FileTool.Dump(key2 + ":" + value2,
					// "D:/人口数据/3级数据-统计人口流入流出数据/key2.txt", "utf-8");
					it2.remove();
					break;
				} else if ((key1.equals(key2)) && (value1 != value2)) {
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
		setCounty("D:/zhouxiang/人口数据/宾馆数据/人口统计/CodeResult.txt");
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
			FileTool.Dump(county.get(i).code + ":" + acount, folder + "", "utf-8");
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
					count = 0;
				}
			}
		}
	}

	/**
	 * 利用countFlowin-tidy-countAmounts-sort.txt文件和bigcityCode.txt文件，
	 * 将主要省会城市的code合并起来，重新对数据进行统计
	 * 
	 * @param folder
	 */

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
				int amount = Integer.parseInt(Tool.getStrByKey(poi, "<amount>", "</amount>", "</amount>"));
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

	/**
	 * 利用-Max.txt文件和CodeResult.txt文件排查一个区县的新旧code
	 * 如果from和to的code不一样，但是他们code对应的name一致，则视这两个code为新旧code
	 * 
	 * @param folder
	 */

	public static void CheckRepeatCode(String folder) {
		String poi = "";
		try {
			setCounty("D:/zhouxiang/人口数据/宾馆数据/人口统计/CodeResult.txt");
			Vector<String> Pois = FileTool.Load(folder, "utf-8");
			for (int a = 0; a < Pois.size(); a++) {
				poi = Pois.elementAt(a);
				String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
				String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
				String frompoi = "";
				String topoi = "";
				String fromReg = "";
				String toReg = "";
				for (int i = 0; i < county.size(); i++) {
					String code = county.get(i).code;
					if (from.equals(code)) {
						fromReg = county.get(i).codereg.replace("null", "").replace(",", "");
						frompoi = "<from>" + from + "</from>" + "<fromName>" + county.get(i).countyname + "</fromName>";
					}
				}
				for (int i = 0; i < county.size(); i++) {
					String code = county.get(i).code;
					if (to.equals(code)) {
						toReg = county.get(i).codereg.replace("null", "").replace(",", "");
						topoi = "<to>" + to + "</to>" + "<toName>" + county.get(i).countyname + "</toName>" + "<toReg>"
								+ toReg + "<toReg>";
					}
				}
				if (fromReg.equals(toReg)) {
					System.out.println(frompoi + topoi);
					FileTool.Dump(frompoi + topoi, folder.replace(".txt", "") + "-重复code.txt", "utf-8");
				}
			}

		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			FileTool.Dump(poi, folder.replace(".txt", "") + "-exception.txt", "utf-8");
		}

	}

	/**
	 * 利用-MainIngredients.txt文件提取每个区县流动人口数目最大值的记录
	 * 
	 * @param folder
	 */
	public static void getMax(String folder) {

		String poi = "";
		try {
			Vector<String> Pois = FileTool.Load(folder, "utf-8");
			List<Integer> Amounts = new ArrayList<Integer>();
			List<String> FromTo = new ArrayList<String>();
			System.out.println("begin:");
			for (int a = 0; a < Pois.size(); a++) {
				poi = Pois.elementAt(a);
				String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
				String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
				int amount = Integer.parseInt(Tool.getStrByKey(poi, "<amounts>", "</amounts>", "</amounts>"));

				String index = "";
				if (a == 0) {

					Amounts.add(0, amount);
					FromTo.add(0, poi);

				} else {
					index = Tool.getStrByKey(Pois.elementAt(a - 1), "<from>", "</from>", "</from>"); // from和to
					int counter = 0;
					if (from.equals(index)) { // from和to

						// from和to
						if (from.equals(Tool.getStrByKey(Pois.elementAt(0), "<from>", "</from>", "</from>"))) {// from和to
							counter++;
							Amounts.add(counter, amount);
							FromTo.add(counter, poi);
						} else {

							Amounts.add(counter, amount);
							FromTo.add(counter, poi);
							counter++;
						}

						int s = Pois.size();
						if ((a + 1) == s) {
							double max = 0;
							String maxStr = "";
							max = Amounts.get(Amounts.size() - 1);
							maxStr = FromTo.get(Amounts.size() - 1);
							System.out.println(maxStr);
							FileTool.Dump(maxStr, folder.replace(".txt", "") + "-Max.txt", "utf-8");

						}

					} else {
						double max = 0;
						String maxStr = "";
						max = Amounts.get(Amounts.size() - 1);
						maxStr = FromTo.get(Amounts.size() - 1);
						System.out.println(maxStr);
						FileTool.Dump(maxStr, folder.replace(".txt", "") + "-Max.txt", "utf-8");
						Amounts.clear();
						FromTo.clear();

						Amounts.add(0, amount);
						FromTo.add(0, poi);

					}

				}
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			FileTool.Dump(poi, folder.replace(".txt", "") + "-exception.txt", "utf-8");
		}

	}

	/**
	 * 生成json文件
	 * 
	 * @param Folder
	 */
	//ProductJson("D:\\人口数据\\0414重新处理\\9级数据-整理数据生成json文件\\citymaincode.txt");
	public static void ProductJson(String Folder) {
		JSONObject jsonObj = new JSONObject();// 创建json格式的数据

		JSONArray jsonArr = new JSONArray();// json格式的数组

		try {
			Vector<String> rds = FileTool.Load(Folder, "UTF-8");
			for (int i = 0; i < rds.size(); i++) {
				String element = rds.elementAt(i);
				
				JsonData jsondata = new JsonData(element);
				JSONObject jsonObjArr = new JSONObject();
				
				//String[] poi=element.split(",");

				jsonObjArr.put("scode",jsondata.scode);
				jsonObjArr.put("subcode",jsondata.subcode);

				FileTool.Dump(jsonObjArr.toString(), Folder.replace(".txt", "") + "-Json.json", "utf-8");
				//jsonArr.put(jsonObjArr);

			}
			//System.out.println("开始写入txt中");
			

		} catch (JSONException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}

	/**
	 * 提取每个区县的主要流动方向
	 * 
	 * @param folder
	 */

	@SuppressWarnings("unchecked")
	//getMainIngredients("D:\\人口数据\\0414重新处理\\8级数据-利用sort文件提取流动主方向\\countFlowin-NewCode-replaced-tidy-countAmounts-sort.txt");
	public static void getMainIngredients(String folder) {

		String poi = "";
		int a=0;
		try {
			Vector<String> Pois = FileTool.Load(folder, "utf-8");
			Map<Integer,Integer> Amounts = new HashMap<Integer,Integer>();
			Map<Integer,String> FromTo = new HashMap<Integer,String>();
			System.out.println("begin:");
			int counter = 0;
			
			for (a = 0; a < Pois.size(); a++) {
				poi = Pois.elementAt(a);
				String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
				String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
				int amount = Integer.parseInt(Tool.getStrByKey(poi, "<amounts>", "</amounts>", "</amounts>"));
				

				String index = "";
				if (a == 0) {
					if (!(from.equals(to))) {
						Amounts.put(0, amount);// add(int index, Integer
												// element)
						FromTo.put(0, poi);
					}
				} else {
					// flowout:from
					// flowin:to
					index = Tool.getStrByKey(Pois.elementAt(a - 1), "<from>", "</from>", "</from>");
					
					// flowout:from
					// flowin:to
					if (from.equals(index)) { 

						// flowout:from
						// flowin:to
						if (from.equals(Tool.getStrByKey(Pois.elementAt(0), "<from>", "</from>", "</from>"))) {
							if (!(from.equals(to))) {
								
								Amounts.put(counter, amount);
								FromTo.put(counter, poi);
								counter++;
							}

						} else {
							if (!(from.equals(to))) {
								Amounts.put(counter, amount);
								FromTo.put(counter, poi);
								counter++;
							}
						}
						int s = Pois.size();
						if ((a + 1) == s) {
							int num = Amounts.size();
							double average = 0;
							if (Amounts.size() > 5) {
								int a1 = Amounts.get(1);
								int b = Amounts.get(2);
								int c = Amounts.get(3);
								average = (Amounts.get(1) + Amounts.get(2) + Amounts.get(3)+Amounts.get(4)) /40;
							} else {
								average = Amounts.get(0);
							}
							for (int i = 0; i < Amounts.size(); i++) {
								double db = Amounts.get(i);
								if (db > average) {
									System.out.println(FromTo.get(i));
									FileTool.Dump(FromTo.get(i), folder.replace(".txt", "") + "-MainIngredients.txt",
											"utf-8");
								}
							}
						}

					} else {
						int num = Amounts.size();
						//System.out.println(num);
						double average=0;
						if (Amounts.size() > 5) {
							int a1 = Amounts.get(1);
							int b = Amounts.get(2);
							int c = Amounts.get(3);
							average = ((Amounts.get(1) + Amounts.get(2) + Amounts.get(3)+Amounts.get(4)) /40);
						} else if(num!=0) {
							average = Amounts.get(0);
						}

						for (int i = 0; i < Amounts.size(); i++) {
							double db = Amounts.get(i);
							if (db > average) {
								System.out.println(FromTo.get(i));
								FileTool.Dump(FromTo.get(i), folder.replace(".txt", "") + "-MainIngredients.txt",
										"utf-8");
							}
						}
						Amounts.clear();
						FromTo.clear();
						
						counter = 0;

						if (!(from.equals(to))) {
							Amounts.put(counter, amount);
							FromTo.put(counter, poi);
							counter++;
						}
						

					}

				}
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			System.out.println(a);
			FileTool.Dump(poi, folder.replace(".txt", "") + "-exception.txt", "utf-8");
		}

	}

	/**
	 * 利用-countAmounts.txt文件，将每个区县的记录按照流动人口数目的大小顺序排列 同时删除from和to的code一样的数据
	 * 
	 * @param folder
	 */
    //getSortFlow("D:\\人口数据\\0414重新处理\\6级数据-对统计后的数据进行排序\\countFlowout-NewCode-replaced-tidy-countAmounts.txt");
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

						//}

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

								// flowout:"<from>" + index + "</from>" + "<to>"
								// + key + "</to>"
								// flowin:"<from>" + key + "</from>" + "<to>" +index + "</to>"
								FromTo[counts] = "<from>" + key + "</from>" + "<to>" +index + "</to>"+ "<amounts>" // from和to
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
							// flowout:"<from>" + index + "</from>" + "<to>" +
							// key + "</to>"
							// flowin:"<from>" + key + "</from>" + "<to>" +
							// index + "</to>"
							FromTo[counts] ="<from>" + key + "</from>" + "<to>" +index + "</to>"+ "<amounts>" + value
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
							// flowin:from
							map.put(from, amount);

						//}

					}

				}
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			FileTool.Dump(poi, folder.replace(".txt", "") + "-exception.txt", "utf-8");
		}

	}


	//countTotalAmount("D:/人口数据/0414重新处理/12级别数据-pagerank算法/countFlowout-NewCode-replaced-tidy-countAmounts-sort-MainIngredients.txt");
	/**
	 * 统计最终一个区县的人流动到另外一个区县去的总人数
	 * @param folder 需要统计的文件
	 * @param n n为1表示flowout，n为0表示flowin
	 */
	public static void countTotalAmount(String folder,int n) {

		String poi = "";
		boolean flowout;
		if(n==1){
			flowout=true;
		}else{
			flowout=false;
		}
		
		if(flowout){
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
						// flowout:to
						// flowin:from
						map.put(to, amount);
					} else {
						// flowout:from
						// flowin:to
						index = Tool.getStrByKey(Pois.elementAt(a - 1), "<from>", "</from>", "</from>");
						
						// flowout:from
						// flowin:to
						//与index一致
						if (from.equals(index)) {
							
							// 对每个区域逐个统计
							
							
							// flowout:to
							// flowin:from
							map.put(to, amount);

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
								
								// flowout:"<from>" + index + "</from>"
								// flowin:"<to>" + index + "</to>"
								String str ="<from>" + index + "</from>"+ "<amounts>" + Total + "</amounts>";
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
							
							// flowout:"<from>" + index + "</from>"
							// flowin:"<to>" + index + "</to>"
							String str ="<from>" + index + "</from>"+ "<amounts>" + Total + "</amounts>";
							System.out.println(str);
							FileTool.Dump(str, folder.replace(".txt", "") + "-countTotalAmounts.txt", "utf-8");

							map.clear();
							
							// flowout:to
							// flowin:from
							map.put(from, amount);

						}

					}
				}
			} catch (NullPointerException e) {
				System.out.println(e.getMessage());
				FileTool.Dump(poi, folder.replace(".txt", "") + "-exception.txt", "utf-8");
			}
		}else{
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
						// flowout:to
						// flowin:from
						map.put(from, amount);
					} else {
						// flowout:from
						// flowin:to
						index = Tool.getStrByKey(Pois.elementAt(a - 1), "<to>", "</to>", "</to>");
						
						// flowout:from
						// flowin:to
						//与index一致
						if (to.equals(index)) {
							
							// 对每个区域逐个统计
							
							
							// flowout:to
							// flowin:from
							map.put(from, amount);

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
								
								// flowout:"<from>" + index + "</from>"
								// flowin:"<to>" + index + "</to>"
								String str ="<to>" + index + "</to>"+ "<amounts>" + Total + "</amounts>";
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
							
							// flowout:"<from>" + index + "</from>"
							// flowin:"<to>" + index + "</to>"
							String str ="<to>" + index + "</to>"+ "<amounts>" + Total + "</amounts>";
							System.out.println(str);
							FileTool.Dump(str, folder.replace(".txt", "") + "-countTotalAmounts.txt", "utf-8");

							map.clear();
							
							// flowout:to
							// flowin:from
							map.put(from, amount);

						}

					}
				}
			} catch (NullPointerException e) {
				System.out.println(e.getMessage());
				FileTool.Dump(poi, folder.replace(".txt", "") + "-exception.txt", "utf-8");
			}
		}
		

	}

	/**
	 * 统计某个区县流向其他区县的人口总数，将相同的流向叠加起来，使得该区县的所有流向不重复
	 * 
	 * @param folder
	 */
	// countAmount("D:\\人口数据\\0414重新处理\\5级数据-将排完序的数据进行统计\\countFlowout-NewCode-replaced-tidy.txt");
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
					map.put(to, amount);
					end++;
				} else {
					// flowout:from
					// flowin:to
					index = Tool.getStrByKey(Pois.elementAt(a - 1), "<from>", "</from>", "</from>");
					// flowout:from
					// flowin:to
					// 与index一致
					if (from.equals(index)) {
						int count = 0;
						for (int b = start; b < end; b++) {
							// flowout:to
							// flowin:from
							String probe = Tool.getStrByKey(Pois.elementAt(b), "<to>", "</to>", "</to>");
							// flowout:to
							// flowin:from
							if (to.equals(probe)) {
								//如果map中之前有一个和to一样的code
								if (map.get(probe) != null) {
									int s = map.get(probe);
									map.put(probe, s + amount);
									end++;
									break;
								} else {
									// flowout:to
									// flowin:from
									map.put(to, amount);
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
							map.put(to, amount);
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
								// flowin: "<from>" + key + "</from>" + "<to>" +
								// index + "</to>"
								String str = "<from>" + index + "</from>" + "<to>" + key + "</to>" + "<amounts>" + value
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
							// flowin: "<from>" + key + "</from>" + "<to>" +
							// index + "</to>"
							String str = "<from>" + index + "</from>" + "<to>" + key + "</to>" + "<amounts>" + value
									+ "</amounts>";

							FileTool.Dump(str, folder.replace(".txt", "") + "-countAmounts.txt", "utf-8");
						}
						map.clear();
						
						start=end;

						// flowout:to
						// flowin:from
						map.put(to, amount);
						end++;
					}

				}
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			FileTool.Dump(poi, folder.replace(".txt", "") + "-exception.txt", "utf-8");
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
					if (to.equals(probe)) {
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

	/**
	 * 统计最终一个区县的人流动到另外一个区县去的总人数，将重复的流向区县的数目叠加,不过该算法太慢了，果断抛弃
	 * 
	 * @param codefolder
	 * @param countfile
	 */
	public static void countAmounts(String codefolder, String countfile) {
		try {
			String Code = "";
			File file = new File(codefolder);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = null;
			String tempString = null;

			reader = new BufferedReader(isr);
			while ((tempString = reader.readLine()) != null) {
				Code = Tool.getStrByKey(tempString, "<Code>", "</Code>", "</Code>");
				Vector<String> CodePois = FileTool.Load(codefolder, "utf-8");
				for (int i = 0; i < CodePois.size(); i++) {
					String probe = Tool.getStrByKey(CodePois.elementAt(i), "<Code>", "</Code>", "</Code>");
					Vector<String> countFile = FileTool.Load(countfile, "utf-8");
					int amounts = 0;
					for (int k = 0; k < countFile.size(); k++) {
						String poi = countFile.elementAt(k);
						String from = Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
						String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>").replace(" ", "");
						String amount = Tool.getStrByKey(poi, "<amounts>", "</amounts>", "</amounts>");
						int n = Integer.parseInt(amount);
						// System.out.println(to);
						// System.out.println(Code);
						if (from.equals(Code)) {
							if (probe.equals(to)) {
								amounts += n;
							}
						}
					}
					if (amounts != 0) {
						String total = "<from>" + Code + "</from>" + "<to>" + probe + "</to>" + "<amounts>" + amounts
								+ "</amounts>";
						FileTool.Dump(total, countfile.replace(".txt", "") + "-countAmounts.txt", "utf-8");
					}
					System.out.println("完成" + Code + "区域" + probe + "的统计");
				}

				// System.out.println("");
			}
			reader.close();
		} catch (NullPointerException e1) {
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
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 查询某个区县的人口情况 统计各个区县的流入流出人口的数量情况
	 * 
	 * @throws IOException
	 */
	public static void SearchCode() throws IOException {

		String folder1 = "D:/人口数据/";
		for (int j = 1; j <= 89; j++) {
			System.out.println("第" + j + "次将区县数据存到CountyPopulation中：");
			setCounty("D:/zhouxiang/人口数据/宾馆数据/人口统计/CodeResult.txt");

			System.out.println("开始处理第" + j + "个文件：");
			String f = folder1 + j + "-postCode.txt";
			ClassifyStatistic(f);

			System.out.println("开始逐个查询：");
			Vector<String> codes = FileTool.Load("D:/zhouxiang/人口数据/宾馆数据/人口统计/CodeResult.txt", "utf-8");
			for (int s = 0; s < codes.size(); s++) {
				String code = Tool.getStrByKey(codes.elementAt(s), "<Code>", "</Code>", "</Code>");
				for (int i = 0; i < county.size(); i++) {
					if (county.get(i).code.equals(code)) {
						// 查询该地区的户籍人口
						// System.out.println(code+"地区的户籍人口：");
						for (int k = 0; k < county.get(i).homepois.size(); k++) {

							String homeStr = county.get(i).homepois.get(k);
							// System.out.println(homeStr);

						}
						// System.out.println(county.get(i).getpostCodes());
						String postCodes = county.get(i).getpostCodes().toString().replace(", ", ",").replace("[", "")
								.replace("]", "").replace(" ", "");

						// 统计该区县人口流出外地的情况
						countFlowout(postCodes, county.get(i).code, folder1);

						// 查询拥有该地区通讯地址的人口
						// System.out.println("拥有"+code+"地区通讯地址的人口：");
						for (int k = 0; k < county.get(i).postpois.size(); k++) {

							// System.out.println(county.get(i).postpois.get(k));

						}
						// System.out.println(county.get(i).gethomeCodes());
						String homeCodes = county.get(i).gethomeCodes().toString().replace(", ", ",").replace("[", "")
								.replace("]", "").replace(" ", "");

						// 统计外地人口流入该区县的情况
						countFlowin(homeCodes, county.get(i).code, folder1);

					}
				}
			}
			county.clear();
			System.out.println("第" + j + "次清空county");
		}
		// ClassifyStatistic("D:/人口数据/1-postCode.txt");
		System.out.println("OK!");

		// 从CountyPopulation中查询所需要的数据
		// System.out.println("输入所要查询的区县的行政代码：");
		// Scanner input = new Scanner(System.in);
		// String code = input.next();

	}

	/**
	 * 统计某个区县流向各个地区的人数
	 * 
	 * @param line
	 *            流出去的区县的行政代码集合
	 * @param code
	 *            该县的行政代码
	 * @param folder
	 *            统计后的数据存放路径
	 */
	public static void countFlowout(String line, String code, String folder) {
		if (line.indexOf(",") != -1) {
			String[] Code = line.split(",");
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (String i : Code) {
				if (map.get(i) != null) {
					map.put(i, map.get(i) + 1);
				} else {
					map.put(i, 1);
				}
			}
			for (Entry<String, Integer> entry : map.entrySet()) {
				if (entry.getValue() > 0) {
					// System.out.println("字符串："+entry.getKey()+";次数:"+entry.getValue());
					String str = "<from>" + code + "</from>" + "<to>" + entry.getKey() + "</to>" + "<amount>"
							+ entry.getValue() + "</amount>";
					// System.out.println(str);
					FileTool.Dump(str, folder + "countFlowout.txt", "utf-8");
				}
			}
		}

	}

	/**
	 * 统计流入该县的人口主要来自于哪些地区
	 * 
	 * @param line
	 *            流入该县的人口来源地的行政区划代码集合
	 * @param code
	 *            该区县的行政代码
	 * @param folder
	 *            统计后的数据存放路径
	 */
	public static void countFlowin(String line, String code, String folder) {
		if (line.indexOf(",") != -1) {
			String[] Code = line.split(",");
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (String i : Code) {
				if (map.get(i) != null) {
					map.put(i, map.get(i) + 1);
				} else {
					map.put(i, 1);
				}
			}
			for (Entry<String, Integer> entry : map.entrySet()) {
				if (entry.getValue() > 0) {
					// System.out.println("字符串："+entry.getKey()+";次数:"+entry.getValue());
					String str = "<from>" + entry.getKey() + "</from>" + "<to>" + code + "</to>" + "<amount>"
							+ entry.getValue() + "</amount>";
					// System.out.println(str);
					FileTool.Dump(str, folder + "countFlowin.txt", "utf-8");
				}
			}
		}

	}

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
	

	
	

	/**
	 * 设置虚拟的区县盒子，每一个区县相当于一个盒子，每个盒子有code、名称、坐标等基本信息的设置
	 * 
	 * @param folder
	 *            利用CodeResult.txt中的数据来设置不同的区县盒子
	 */
	public static void setCounty(String folder) {

		try {
			// 设置每个区县的区县代码、区县名字以及坐标
			File file = new File(folder);
			FileInputStream fis;
			fis = new FileInputStream(file);

			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader reader = null;
			String tempString = null;

			reader = new BufferedReader(isr);
			while ((tempString = reader.readLine()) != null) {
				code = Tool.getStrByKey(tempString, "<Code>", "</Code>", "</Code>");
				countyname = Tool.getStrByKey(tempString, "<CodeAddr>", "</CodeAddr>", "</CodeAddr>");
				countyCoor = Tool.getStrByKey(tempString, "<CodeCoor>", "</CodeCoor>", "</CodeCoor>");
				codeReg = Tool.getStrByKey(tempString, "<CodeReg>", "</CodeReg>", "</CodeReg>");
				String[] coor = countyCoor.split(";");
				countyLN = coor[0];
				countyLA = coor[1];
				CountyPopulation cp = new CountyPopulation();
				cp.setCode(code);
				cp.setCountyname(countyname);
				cp.setCodeReg(codeReg);
				double l = Double.parseDouble(countyLN);
				cp.setLongitude(l);
				cp.setLatitude(Double.parseDouble(countyLA));
				addCountyPopulation(cp);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 分区县统计每个区县的人口记录 将人口的户籍地址或者通讯地址的代码分配到各个区县
	 * 
	 * @param folder
	 * @throws IOException
	 */
	public static void ClassifyStatistic(String folder) throws IOException {
		// 该folder是“D:\人口数据\原始数据”中的数据
		File file1 = new File(folder);

		FileInputStream fis1 = new FileInputStream(file1);
		InputStreamReader isr1 = new InputStreamReader(fis1, "UTF-8");
		BufferedReader reader1 = null;
		String tempString1 = null;

		reader1 = new BufferedReader(isr1);
		while ((tempString1 = reader1.readLine()) != null) {
			// tempString1:<Name>冯喜强</Name><PostCoor>109.536324;22.299784</PostCoor><PostReg>广西壮族自治区,钦州市,浦北县,小江镇</PostReg><Code>452826</Code><CodeAddr>广西壮族自治区钦州地区浦北县</CodeAddr><CodeCoor>109.552798;22.27452501</CodeCoor><CodeReg>广西壮族自治区,钦州市,浦北县,null</CodeReg><CtfTp>ID</CtfTp><CtfId>452826197802134612</CtfId><Home>广西壮族自治区钦州地区浦北县</Home><Gender>M</Gender><Birth>19780213</<Birth><PostAddr>广西浦北县小江镇沙江村委会下车塘村20号</PostAddr><Mobile>no</Mobile><Nation>汉</Nation><Version>2011-12-166:41:57</Version>

			// 获取该tempString1的户籍地code
			String admincode = Tool.getStrByKey(tempString1, "<Code>", "</Code>", "</Code>");

			// 获取该tempString1的通讯地code
			String postcode = Tool.getStrByKey(tempString1, "<PostCode>", "</PostCode>", "</PostCode>");

			for (int i = 0; i < county.size(); i++) {
				String Code = county.get(i).code;
				// 在county中找到与户籍地code一样的code，并且把该tempString1及其包含的通讯地址放到county中对应的那个code的盒子中
				// 该盒子就像虚拟的区县，每个盒子有一个行政区划代码code，该盒子管理着户籍地为该地区的人的记录以及他们的通讯地址code
				// 此外，该盒子还管理着通讯地是该地区的人的记录以及这些的户籍地址code
				if (admincode.equals(Code)) {
					county.get(i).sethomePois(tempString1);
					county.get(i).setpostCodes(postcode);
					break;
				}
			}

			// 在county中找到与通讯地code一样的code，并且把该tempString1及其包含的户籍地址放到county中对应的那个code的盒子中
			for (int i = 0; i < county.size(); i++) {
				String Code = county.get(i).code;
				if (postcode.equals(Code)) {
					county.get(i).setpostPois(tempString1);
					county.get(i).sethomeCodes(admincode);
					break;
				}
			}

		}
		reader1.close();

	}

}
