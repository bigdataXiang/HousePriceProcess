package com.svail.population_mobility;

import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import com.svail.gridprocess.CurveDrawing;
import com.svail.util.FileTool;
import com.svail.util.Tool;

public class Histogram {

	public static void main(String[] args) {
		
		//AmountsSort("D:/Test/浙江省温州市苍南县.txt");
		
		
		

		 
		JFrame frame = new JFrame("Java数据统计图");
		frame.setLayout(new GridLayout(2, 2, 10, 10));
		//String[] codeArry={"120111","120112","120113"};
		//for(int i=0;i<codeArry.length;i++){
			new Histogram("D:/人口数据/0414重新处理/10级数据-提取每个区县的直方图特征/countFlowin-NewCode-replaced-tidy-countAmounts-sort-MainIngredients.txt","浏阳","430181");
			frame.add(Histogram.getChartPanel());
			frame.setBounds(50, 50, 4000, 4000);
			frame.setVisible(true);
		//}
		
	
		
		System.out.println(P_rand(5));
		//PrincipalComponent1("D:/Test/区县人口流动直方图/浙江省温州市苍南县-sort.txt");
		
	}
	public static void AmountsSort(String file) {
		Vector<String> people = FileTool.Load(file, "utf-8");
		double[] arr = new double[people.size()];
		String[] pois=new String[ people.size()];
		for (int i = 0; i < people.size(); i++) {
			String poi = people.elementAt(i);
			double num = Double.parseDouble(Tool.getStrByKey(poi, "<amounts>", "</amounts>", "</amounts>"));
			arr[i] = num;
			pois[i]=poi;
			}
		InsertSortArray(people.size(),arr,pois);
		for(int i=0;i<arr.length;i++){
			System.out.println(pois[i]);
			FileTool.Dump(pois[i], file.replace(".txt", "")+"-sort.txt", "utf-8");
		}
		
		
	}
	public static void PrincipalComponent1(String file){
		Vector<String> pois=FileTool.Load(file, "utf-8");
		Double[] arr=new Double[pois.size()];
		for(int i=0;i<pois.size();i++){
			String poi=pois.elementAt(i);
			String amounts=Tool.getStrByKey(poi, "<amounts>", "</amounts>", "</amounts>");
			arr[i]=Double.parseDouble(amounts);
		}
		int num=arr.length;
		double index;
		index=(arr[num-2]-arr[0])/10;
		for(int i=0;i<arr.length;i++){
			double db=arr[i];
			if(db>index){
				System.out.println(db);
				FileTool.Dump(pois.elementAt(i), file.replace(".txt", "")+"-pc.txt", "utf-8");
			}
		}
		
	}
	public static void PrincipalComponent(String file){
		Vector<String> pois=FileTool.Load(file, "utf-8");
		Double[] arr=new Double[pois.size()];
		for(int i=0;i<pois.size();i++){
			String poi=pois.elementAt(i);
			String amounts=Tool.getStrByKey(poi, "<amounts>", "</amounts>", "</amounts>");
			arr[i]=Double.parseDouble(amounts);
		}
		int num=arr.length;
		double average;
		average=(arr[num-2]+arr[num-3]+arr[num-4])/30;
		for(int i=0;i<arr.length;i++){
			double db=arr[i];
			if(db>average){
				System.out.println(db);
				FileTool.Dump(pois.elementAt(i), file.replace(".txt", "")+"-pc.txt", "utf-8");
			}
		}
		
	}
	public static double P_rand(double Lamda){      // 泊松分布
		 double x=0,b=1,c=Math.exp(-Lamda),u; 
		 do {
		  u=Math.random();
		  b *=u;
		  if(b>=c)
		   x++;
		  }while(b>=c);
		 return x;
		 }

	static ChartPanel frame1;

	public Histogram(String file,String name,String code) {
		/*
		 * 绘制柱状图
		 */
		CategoryDataset dataset = getDataSet(file, code);
		JFreeChart chart = ChartFactory.createBarChart3D(name, // 图表标题
				"区县", // 目录轴的显示标签
				"数量", // 数值轴的显示标签
				dataset, // 数据集
				PlotOrientation.VERTICAL, // 图表方向：水平、垂直
				true, // 是否显示图例(对于简单的柱状图必须是false)
				false, // 是否生成工具
				false // 是否生成URL链接
		);

		// 从这里开始
		CategoryPlot plot = chart.getCategoryPlot();// 获取图表区域对象
		CategoryAxis domainAxis = plot.getDomainAxis(); // 水平底部列表
		domainAxis.setLabelFont(new Font("黑体", Font.BOLD, 14)); // 水平底部标题
		domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12)); // 垂直标题
		ValueAxis rangeAxis = plot.getRangeAxis();// 获取柱状
		rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));// 设置标题字体

		// 到这里结束，虽然代码有点多，但只为一个目的，解决汉字乱码问题

		frame1 = new ChartPanel(chart, true); // 这里也可以用chartFrame,可以直接生成一个独立的Frame
	}

	private static CategoryDataset getDataSet(String file,String code) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Vector<String> people = FileTool.Load(file, "utf-8");
		for (int i = 0; i < people.size(); i++) {
			String poi = people.elementAt(i);
			double num = Double.parseDouble(Tool.getStrByKey(poi, "<amounts>", "</amounts>", "</amounts>"));
			String to = Tool.getStrByKey(poi, "<to>", "</to>", "</to>");
			String from=Tool.getStrByKey(poi, "<from>", "</from>", "</from>");
			
			if(code.equals(to)){
				dataset.addValue(num, "", from);
				System.out.println(poi);
			}
			
		}
		return dataset;
	}

	public static ChartPanel getChartPanel() {
		return frame1;

	}

	

	public static void InsertSortArray(int n, double[] arr,String[] pois) {
	
		for (int i = 1; i < n; i++)// 循环从第二个数组元素开始，因为arr[0]作为最初已排序部分
		{
			double temp = arr[i];// temp标记为未排序第一个元素
			String strtemp=pois[i];
			int j = i - 1;
			while (j >= 0 && arr[j] > temp)/* 将temp与已排序元素从小到大比较，寻找temp应插入的位置 */
			{
				arr[j + 1] = arr[j];
				pois[j + 1] = pois[j];
				j--;
			}
			arr[j + 1] = temp;
			pois[j + 1] = strtemp;
		}
	}

}
