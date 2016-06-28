package com.svail.houseprice;

import com.mongodb.*;
import com.svail.gridprocess.HousePriceDraw;
import com.svail.util.FileTool;
import net.sf.json.JSONObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

import javax.swing.*;
import java.awt.*;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by ZhouXiang on 2016/6/28.
 */
public class CallMongo {
    public static void main(String[] args){
        callDataFromMongo("RentoutWithCode","fang");
        GridCurve();
    }
    public static void GridCurve(){
        JFrame frame=new JFrame("Java数据统计图");
        frame.setLayout(new GridLayout(1,1,10,10));
        new CallMongo();
        frame.add(CallMongo.getChartPanel());
        frame.setBounds(50, 50, 800, 600);
        frame.setVisible(true);

    }
    public static ArrayList<TimeSeriesPrice> timeseriesprice= new ArrayList<TimeSeriesPrice>();
    public static void addTimeSeriesPrice(TimeSeriesPrice tsp){
        timeseriesprice.add(tsp);
    }
    public static void callDataFromMongo(String collName,String source){
        Mongo m;
        String poi="";
        try {
            System.out.println("运行开始:");
            m = new Mongo("192.168.6.9", 27017);
            //m.dropDatabase("test");
            DB db = m.getDB("houseprice");
            DBCollection coll = db.getCollection(collName);//coll.drop();

            BasicDBObject document = new BasicDBObject();
            document.put("gridcode",15275);

            DBCursor cursor = coll.find(document);
            int count=0;
            while (cursor.hasNext()) {

                poi=cursor.next().toString();
                FileTool.Dump(poi, "E:/房地产可视化/timeseriesprice/15626.txt", "utf-8");
                //System.out.println(poi);

                JSONObject obj=JSONObject.fromObject(poi);
                Object date=obj.get("date");
                JSONObject obj_date=JSONObject.fromObject(date);

                String year=obj_date.getString("year").replace(" ","");
                String month=obj_date.getString("month").replace(" ","");
                String day=obj_date.getString("day").replace(" ","");

                double price=Double.parseDouble(obj.get("price").toString());
                double unit_price=0;
                if(!obj.get("unit_price").equals("#VALUE!")&&obj.get("unit_price").toString().length()!=0){
                    unit_price=Double.parseDouble(obj.get("unit_price").toString());
                }


                TimeSeriesPrice tsp= new TimeSeriesPrice();
                if(count==0){
                    tsp.setYear(year);
                    tsp.setMonth(month);
                    tsp.setDay(day);
                    tsp.setPois(poi);
                    tsp.setPrices(price);
                    tsp.setUnitPrices(unit_price);
                    addTimeSeriesPrice(tsp);//每add一次，timeseriesprice.size()就会加1
                }else{
                    int size=timeseriesprice.size();
                    for(int i=0;i<timeseriesprice.size();){
                        String year_index=timeseriesprice.get(i).year;
                        String month_index=timeseriesprice.get(i).month;
                        String day_index=timeseriesprice.get(i).day;
                        if(year.equals(year_index)&&month.equals(month_index)&&day.equals(day_index)){

                            timeseriesprice.get(i).setPois(poi);
                            timeseriesprice.get(i).setPrices(price);
                            timeseriesprice.get(i).setUnitPrices(unit_price);

                            size=timeseriesprice.size();

                            break;

                        }else if(i<timeseriesprice.size()-1){
                            i++;
                            size=timeseriesprice.size();
                            continue;
                        }else{
                            tsp= new TimeSeriesPrice();
                            tsp.setYear(year);
                            tsp.setMonth(month);
                            tsp.setDay(day);
                            tsp.setPois(poi);
                            tsp.setPrices(price);
                            tsp.setUnitPrices(unit_price);
                            addTimeSeriesPrice(tsp);
                            i++;
                            size=timeseriesprice.size();//每add一次，timeseriesprice.size()就会加1
                            break;
                        }

                    }
                }
                count++;
            }
            //System.out.println(count);

           // System.out.println(timeseriesprice.size());
            for(int i=0;i<timeseriesprice.size();i++){
                String year_index=timeseriesprice.get(i).year;
                String month_index=timeseriesprice.get(i).month;
                String day_index=timeseriesprice.get(i).day;

               // System.out.println(year_index+"-"+month_index+"-"+day_index+":");
                FileTool.Dump(year_index+"-"+month_index+"-"+day_index, "E:/房地产可视化/timeseriesprice/time.txt", "utf-8");

                Vector<String> pois= timeseriesprice.get(i).pois;
                for(int p=0;p<pois.size();p++){
                    //System.out.println(pois.elementAt(p));
                    FileTool.Dump(pois.elementAt(p), "E:/房地产可视化/timeseriesprice/15626-poi.txt", "utf-8");
                }
                Vector<Double> prices= timeseriesprice.get(i).prices;
                for(int p=0;p<prices.size();p++){
                   // System.out.println(prices.elementAt(p));
                }
                Vector<Double> unitprices= timeseriesprice.get(i).unitprices;
                for(int p=0;p<unitprices.size();p++){
                    //System.out.println(unitprices.elementAt(p));
                }
            }


        }catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MongoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            System.out.println("发生异常的原因为 :"+e.getMessage());
            e.printStackTrace();
        } catch(NumberFormatException e){
            System.out.println("problem:"+poi);
        }

    }

    static ChartPanel frame;

    public CallMongo(){
		 /*
         * 绘制折线图
         */
        XYDataset xydataset = createDataset();

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
        xylineandshaperenderer.setBaseShapesVisible(false);
        //设置曲线显示各数据点的值
        XYItemRenderer xyitem = plot.getRenderer();
        xyitem.setBaseItemLabelsVisible(false);
        xyitem.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.INSIDE12, TextAnchor.BOTTOM_CENTER));//.OUTSIDE12  BASELINE_LEFT
        xyitem.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
        xyitem.setBaseItemLabelFont(new Font("Dialog", 1, 14));

        dateaxis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
        frame=new ChartPanel(jfreechart,true);
        dateaxis.setLabelFont(new Font("黑体",Font.BOLD,14));  //水平底部标题
        dateaxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));  //垂直标题
        ValueAxis rangeAxis1=plot.getRangeAxis();//获取柱状
        rangeAxis1.setLabelFont(new Font("黑体",Font.BOLD,15));
        jfreechart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
        jfreechart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体
    }
    public static ChartPanel getChartPanel(){
        return frame;

    }
    private static XYDataset createDataset() {
        @SuppressWarnings("deprecation")
        TimeSeries timeseries = new TimeSeries("北京租房价格（元/月）", org.jfree.data.time.Day.class);
        for(int i=0;i<timeseriesprice.size();i++){
            String year_index=timeseriesprice.get(i).year;
            String month_index=timeseriesprice.get(i).month;
            String day_index=timeseriesprice.get(i).day;

            Vector<Double> prices= timeseriesprice.get(i).prices;
            double Prices=0;
            double Price;
            int count=0;
            for(int p=0;p<prices.size();p++){
                if(prices.elementAt(p)!=0){
                    Prices+=prices.elementAt(p);
                    count++;
                }
            }
            Price=Prices/count;
            int year=0;
            int month=0;
            int day=0;
            try{
                year=Integer.parseInt(year_index);
                month=Integer.parseInt(month_index);
                day=Integer.parseInt(day_index);
            }catch(NumberFormatException e){
                System.out.println(timeseriesprice.get(i).prices);
            }


            String str=day+","+month+","+year+","+Price;
            FileTool.Dump(str,"E:/房地产可视化/timeseriesprice/15626-time-and-price.txt","utf-8");
            timeseries.addOrUpdate(new Day(day,month,year), Price);
        }
        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
        timeseriescollection.addSeries(timeseries);

        return timeseriescollection;
    }
}
