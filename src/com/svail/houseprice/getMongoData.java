package com.svail.houseprice;

import com.mongodb.*;
import com.svail.util.FileTool;
import net.sf.json.JSONObject;

import java.net.UnknownHostException;
import java.util.Vector;

/**
 * Created by ZhouXiang on 2016/7/18.
 */
public class getMongoData {
    public static void main(String[] args){
        callDataFromMongo("rentout_code","fang");

    }
    public static void callDataFromMongo(String collName,String source){
        Mongo m;
        String poi="";
        try {
            System.out.println("运行开始:");
            m = new Mongo("192.168.6.9", 27017);
            DB db = m.getDB("houseprice");
            DBCollection coll = db.getCollection(collName);//coll.drop();

            BasicDBObject document = new BasicDBObject();
            document.put("source",source);

            DBCursor cursor = coll.find(document);
            int count=0;
            while (cursor.hasNext()) {

                poi=cursor.next().toString();
                FileTool.Dump(poi, "E:\\房地产可视化\\rentout_code\\fang.txt", "utf-8");

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
