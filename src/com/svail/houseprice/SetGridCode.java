package com.svail.houseprice;

import com.svail.gridprocess.Code;
import com.svail.gridprocess.Coordinate_Transformation;
import com.svail.gridprocess.DataPoint;
import com.svail.gridprocess.Price;
import com.svail.util.FileTool;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ZhouXiang on 2016/7/19.
 */
public class SetGridCode {
    public static Double X_MAX = 2.0542041271351546E7;// 2.0542041271351546E7
    public static Double X_MIN = 2.036373920422157E7;
    public static Double Y_MAX = 4547353.496401368;
    public static Double Y_MIN = 4368434.982578722;
    public static int rows;
    public static int cols;
    public static Double LNG;
    public static Double LAT;
    public static ArrayList<Code> codes = new ArrayList<Code>();
    public static String GRIDFOLDER="D:\\房地产可视化\\";



    public static void main(String[] args) throws IOException {
        setCode(3000);
    }
    /**
     * 给网格创建编码
     * @param index  网格的分辨率
     */
    public static void setCode(int index) {
		/*
		 * 将北京的东北角和西南角的坐标转换成平面坐标
		 * 东北角：BLToGauss(117.500126,41.059244)
		 * 西南角：BLToGauss(115.417284,39.438283)
		 * BLToGauss: 2.0542041271351546E7   4547353.496401368
		 * BLToGauss: 2.036373920422157E7    4368434.982578722
		 * 两点之间的距离是:252593.47127613405 178302.06712997705 178918.51382264588
		 *
		 */
        // 以中央子午线的投影为纵坐标轴x，规定x轴向北为正；以赤道的投影为横坐标轴y，规定y轴向东为正。

        rows = (int) Math.ceil((X_MAX - X_MIN) / index);
        System.out.println(rows);

        cols = (int) Math.ceil((Y_MAX - Y_MIN) / index);
        System.out.println(cols);

        // System.out.print("d_x:"+rows+"\r\n"+"d_y:"+cols+"\r\n");

        // 创建栅格编码
        int mm = 1;
        for (int rr = 1; rr <= rows; rr++) {
            for (int cc = 1; cc <= cols; cc++) {
                Code c = new Code();
                c.setRow(rr);
                c.setCol(cc);
                c.setCode(mm);
                mm++;
                addCode(c);
                // System.out.println(c.getCode(rr, cc));
            }
        }
        for(int k=0;k<codes.size();k++){
            String str="codes的第"+k+"个数是:"+codes.get(k).row+"行,"+codes.get(k).col+"列,"+codes.get(k).code+"\r\n";
            //System.out.print(str);
            FileTool.Dump(str,GRIDFOLDER+"gridecode_"+index+".txt","utf-8");

        }

    }
    public static void addCode(Code c) {
        codes.add(c);
    }

    /**
     * 给单个poi创建网格编码
     * @param poi
     * @param resolution 网格的分辨率
     * @return
     */
    public static int setPoiCode(String poi, int resolution) {

        int code;
        JSONObject jsonObject = JSONObject.fromObject(poi);
        Object lat=jsonObject.get("latitude");
        String type=lat.getClass().getName();

        double latitude=0;
        double longitude=0;
        //java.lang.Double
        if(type.equals("java.lang.Double")){
            latitude = Double.parseDouble(jsonObject.get("latitude").toString());
            longitude =Double.parseDouble(jsonObject.get("longitude").toString());
        }else if(type.equals("java.lang.String")){
            latitude = Double.parseDouble(jsonObject.get("latitude").toString());
            longitude =Double.parseDouble(jsonObject.get("longitude").toString());
        }


        LNG = longitude;
        LAT = latitude;
        double[] Coordinate = new double[2];
        Coordinate = Coordinate_Transformation.BLToGauss(LNG, LAT);

        double X = Coordinate[0];
        double Y = Coordinate[1];

        int row = (int) Math.ceil((X - X_MIN) / resolution); // ceil()：将小数部分一律向整数部分进位。
        // resolution

        int col = (int) Math.ceil((Y - Y_MIN) / resolution);
        int index = (col + cols * (row - 1)); // index=(row-1)*cols+col
        // 依据行列数算出某行某列对应的编码

        code = codes.get(index - 1).code; // 由于codes中的第0个数的编码为1，故所有的index需要减1


        return code;
    }



}
