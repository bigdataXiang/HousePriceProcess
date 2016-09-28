package com.svail.chongqing;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Vector;

import com.google.gson.JsonSyntaxException;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.svail.util.FileTool;

import net.sf.json.JSONObject;

public class ChongqingPowerGrid {
    public static void main(String[] args) throws IOException {
        importMongoDB("E:\\重庆基础数据抓取\\基础数据\\重庆电网\\营业厅_result.txt");
    }

    public static void importMongoDB(String folder) {
        try {
            Mongo mongo = new Mongo("192.168.6.9", 27017);
            DB db = mongo.getDB("chongqing");  // 数据库名称


            DBCollection coll = db.getCollection("ElectricityBusinessHall");
            //coll.drop();//清空表
            //ElectricityFeesCollectingPoint:电费代收点
            //ElectricityPaymentOffice:电力缴费厅
            //ElectricityFeeBank:缴电费银行
            //ElectricityBusinessHall：电力营业厅

            try {
                Vector<String> pois = FileTool.Load(folder, "utf-8");
                for (int i = 0; i < pois.size(); i++) {
                    String poi = pois.elementAt(i);
                    JSONObject obj = JSONObject.fromObject(poi);

                    //将JSONObject转BasicDBObject
                    BasicDBObject document = new BasicDBObject();
                    Iterator<String> joKeys = obj.keys();
                    while (joKeys.hasNext()) {
                        String key = joKeys.next();
                        Object value = obj.get(key);
                        if (value.equals("null")) {
                            value = "";
                        }
                        document.put(key, value);
                    }

                    DBCursor rls = coll.find(document);
                    if (rls == null || rls.size() == 0) {
                        coll.insert(document);
                    } else {
                        System.out.println("该数据已经存在!");
                    }
                }


            } catch (JsonSyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NullPointerException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                //FileTool.Dump(photo.toString(), poiError, "utf-8");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (UnknownHostException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        } catch (MongoException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }


    }
}
