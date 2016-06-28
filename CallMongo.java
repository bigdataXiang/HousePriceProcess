package com.svail.houseprice;

import com.mongodb.*;
import com.svail.util.FileTool;
import net.sf.json.JSONObject;

import java.net.UnknownHostException;
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
            document.put("gridcode",15626);

            DBCursor cursor = coll.find(document);
            int count=0;
            while (cursor.hasNext()) {

                poi=cursor.next().toString();
                FileTool.Dump(poi, "E:/房地产可视化/timeseriesprice/15626.txt", "utf-8");
                //System.out.println(poi);

                JSONObject obj=JSONObject.fromObject(poi);
                Object date=obj.get("date");
                JSONObject obj_date=JSONObject.fromObject(date);

                String year=obj_date.getString("year");
                String month=obj_date.getString("month");
                String day=obj_date.getString("day");

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

                //System.out.println(year_index+"-"+month_index+"-"+day_index+":");
                FileTool.Dump(year_index+"-"+month_index+"-"+day_index, "E:/房地产可视化/timeseriesprice/time.txt", "utf-8");

                Vector<String> pois= timeseriesprice.get(i).pois;
                for(int p=0;p<pois.size();p++){
                    //System.out.println(pois.elementAt(p));
                    FileTool.Dump(pois.elementAt(p), "E:/房地产可视化/timeseriesprice/15626-poi.txt", "utf-8");
                }
                Vector<Double> prices= timeseriesprice.get(i).prices;
                for(int p=0;p<prices.size();p++){
                    //System.out.println(prices.elementAt(p));
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
}
