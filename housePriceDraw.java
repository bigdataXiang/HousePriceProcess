package com.svail.gridprocess;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Week;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

import com.svail.tomongo.DateTransfer;
import com.svail.util.FileTool;
import com.svail.util.Tool;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
public class HousePriceDraw {
	public static void main(String[] args){
		GridCurve("D:/Crawldata_BeiJing/anjuke/rentout/0326/1000/15446/pricecurve.txt");
	}
	public static void GridCurve(String folder){
		JFrame frame=new JFrame("Java数据统计图");
	    frame.setLayout(new GridLayout(1,1,10,10));
		new HousePriceDraw(folder);
		frame.add(HousePriceDraw.getChartPanel());
	    frame.setBounds(50, 50, 800, 600);
	    frame.setVisible(true);
		
	}
	
	
	static ChartPanel frame;
	
	public HousePriceDraw(String folder){
		 /*
         * 绘制折线图
         */
        XYDataset xydataset = createDataset(folder);
        
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("北京租房价格", "", "价格",xydataset, true, false, true);
        XYPlot plot = (XYPlot) jfreechart.getPlot();
        DateAxis dateaxis = (DateAxis)plot.getDomainAxis();
        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)plot.getRenderer();
        //设置网格背景颜色
        plot.setBackgroundPaint(Color.white);
        //设置网格竖线颜色
        plot.setDomainGridlinePaint(Color.pink);
        //设置网格横线颜色
        plot.setRangeGridlinePaint(Color.pink);
        //设置曲线图与xy轴的距离
        plot.setAxisOffset(new RectangleInsets(10D, 10D, 10D, 0.1D));
        //设置曲线是否显示数据点
        xylineandshaperenderer.setBaseShapesVisible(true);
        //设置曲线显示各数据点的值
        XYItemRenderer xyitem = plot.getRenderer();   
        xyitem.setBaseItemLabelsVisible(true);   
        xyitem.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.INSIDE12, TextAnchor.BOTTOM_CENTER));//.OUTSIDE12  BASELINE_LEFT
        xyitem.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
        xyitem.setBaseItemLabelFont(new Font("Dialog", 1, 14));
        
        dateaxis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
        frame=new ChartPanel(jfreechart,true);
        dateaxis.setLabelFont(new Font("黑体",Font.BOLD,14));         //水平底部标题
        dateaxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));  //垂直标题
        ValueAxis rangeAxis1=plot.getRangeAxis();//获取柱状
        rangeAxis1.setLabelFont(new Font("黑体",Font.BOLD,15));
        jfreechart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
        jfreechart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体
	}
	private static XYDataset createDataset(String folder) {  
        @SuppressWarnings("deprecation")
		TimeSeries timeseries = new TimeSeries("北京租房价格（元/月*平方米）", org.jfree.data.time.Day.class);
        //org.jfree.data.time.Day.Day(int day, int month, int year)
        Vector<String> Pois=FileTool.Load(folder, "utf-8");
        for(int i=0;i<Pois.size();i++){
        	String[] poi=Pois.elementAt(i).split(",");
        	
        	String price=poi[0];
        	Double Price=Double.parseDouble(price);
        	int year=Integer.parseInt(poi[1]);
        	int month=Integer.parseInt(poi[2]);
        	int day=Integer.parseInt(poi[3]);
        	timeseries.addOrUpdate(new Day(day,month,year), Price);
        }
        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
        timeseriescollection.addSeries(timeseries);
       
        return timeseriescollection;
    }
  public static ChartPanel getChartPanel(){
        return frame;
         
    }


}
