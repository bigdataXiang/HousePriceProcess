package com.svail.gridprocess;

import java.io.IOException;

public class Coordinate_Transformation {
	public static void main(String[] args) throws IOException
	 {

		 //System.out.println(39.442785-39.438283);
		 double k=5*((0.011791000000002327+0.011580999999992514+0.011891000000005647+0.011880999999988262)/40);
		 System.out.println("经度差："+k);
		 System.out.println(115.417284+k);
		 double l=5*(0.009003999999997347/10);
		 System.out.println("纬度差："+l);
		 System.out.println(39.438283+l);

		 double test[]=BLToGauss(115.417284,41.059244);
		 double test1[]=BLToGauss(115.417284,(41.059244-l));
		 double dist=Math.sqrt(Math.pow((test[0]-test1[0]),2)+Math.pow((test[1]-test1[1]),2));//计算两点之间的平面距离
		 System.out.println("只改变纬度的情况下:"+dist);

		 double test2[]=BLToGauss(115.417284,41.059244);
		 double test3[]=BLToGauss(115.417284-k,41.059244);
		 dist=Math.sqrt(Math.pow((test2[0]-test3[0]),2)+Math.pow((test2[1]-test3[1]),2));//计算两点之间的平面距离
		 System.out.println("只改变经度的情况下:"+dist);

	 }
	 /** 
     * 由经纬度反算成高斯投影坐标 
     *  
     * @param longitude 
     * @param latitude 
     */  
    public static double[] BLToGauss(double longitude, double latitude) {  
  
        int ProjNo = 0;  
  
        // 带宽  
        int ZoneWide = 6;  
  
        double longitude1, latitude1, longitude0, X0, Y0, xval, yval;  
        double a, f, e2, ee, NN, T, C, A, M, iPI;  
  
        // 3.1415926535898/180.0;  
        iPI = 0.0174532925199433;  
  
        // 54年北京坐标系参数  
        a = 6378245.0;  
        f = 1.0 / 298.3;  
  
        // 80年西安坐标系参数  
        // a=6378140.0;  
        // f=1/298.257;  
  
        ProjNo = (int) (longitude / ZoneWide);  
        longitude0 = ProjNo * ZoneWide + ZoneWide / 2;  
        longitude0 = longitude0 * iPI;  
  
        // 经度转换为弧度  
        longitude1 = longitude * iPI;  
  
        // 纬度转换为弧度  
        latitude1 = latitude * iPI;  
  
        e2 = 2 * f - f * f;  
        ee = e2 * (1.0 - e2);  
        NN = a / Math.sqrt(1.0 - e2 * Math.sin(latitude1) * Math.sin(latitude1));  
        T = Math.tan(latitude1) * Math.tan(latitude1);  
        C = ee * Math.cos(latitude1) * Math.cos(latitude1);  
        A = (longitude1 - longitude0) * Math.cos(latitude1);  
        M = a * ((1 - e2 / 4 - 3 * e2 * e2 / 64 - 5 * e2 * e2 * e2 / 256) * latitude1 - (3 * e2 / 8 + 3 * e2 * e2 / 32 + 45 * e2 * e2 * e2 / 1024) * Math.sin(2 * latitude1) + (15 * e2 * e2 / 256 + 45 * e2 * e2 * e2 / 1024) * Math.sin(4 * latitude1) - (35 * e2 * e2 * e2 / 3072) * Math.sin(6 * latitude1));  
        xval = NN * (A + (1 - T + C) * A * A * A / 6 + (5 - 18 * T + T * T + 72 * C - 58 * ee) * A * A * A * A * A / 120);  
        yval = M + NN * Math.tan(latitude1) * (A * A / 2 + (5 - T + 9 * C + 4 * C * C) * A * A * A * A / 24 + (61 - 58 * T + T * T + 600 * C - 330 * ee) * A * A * A * A * A * A / 720);  
        X0 = 1000000L * (ProjNo + 1) + 500000L;  
        Y0 = 0;  
        xval = xval + X0;  
        yval = yval + Y0;  
        return new double[] { xval, yval };  
    }  
  
	//由经纬度反算成高斯投影坐标
	public static void GaussToBLToGauss(double longitude, double latitude)
	{
	 int ProjNo=0; int ZoneWide; ////带宽
	 double longitude1,latitude1, longitude0,latitude0, X0,Y0, xval,yval;
	 double a,f, e2,ee, NN, T,C,A, M, iPI;
	 iPI = 0.0174532925199433; ////3.1415926535898/180.0;
	 ZoneWide = 6; ////6度带宽
	 a=6378245.0; f=1.0/298.3; //54年北京坐标系参数
	 ////a=6378140.0; f=1/298.257; //80年西安坐标系参数
	 ProjNo = (int)(longitude / ZoneWide) ;
	 longitude0 = ProjNo * ZoneWide + ZoneWide / 2;
	 longitude0 = longitude0 * iPI ;
	 latitude0 = 0;
	 //System.out.println(latitude0);
	 longitude1 = longitude * iPI ; //经度转换为弧度
	 latitude1 = latitude * iPI ; //纬度转换为弧度
	 e2=2*f-f*f;
	 ee=e2*(1.0-e2);
	 NN=a/Math.sqrt(1.0-e2*Math.sin(latitude1)*Math.sin(latitude1));
	 T=Math.tan(latitude1)*Math.tan(latitude1);
	 C=ee*Math.cos(latitude1)*Math.cos(latitude1);
	 A=(longitude1-longitude0)*Math.cos(latitude1);
	 M=a*((1-e2/4-3*e2*e2/64-5*e2*e2*e2/256)*latitude1-(3*e2/8+3*e2*e2/32+45*e2*e2
	 *e2/1024)*Math.sin(2*latitude1)
	 +(15*e2*e2/256+45*e2*e2*e2/1024)*Math.sin(4*latitude1)-(35*e2*e2*e2/3072)*Math.sin(6*latitude1));
	 xval = NN*(A+(1-T+C)*A*A*A/6+(5-18*T+T*T+72*C-58*ee)*A*A*A*A*A/120);
	 yval = M+NN*Math.tan(latitude1)*(A*A/2+(5-T+9*C+4*C*C)*A*A*A*A/24
	 +(61-58*T+T*T+600*C-330*ee)*A*A*A*A*A*A/720);
	 X0 = 1000000L*(ProjNo+1)+500000L;
	 Y0 = 0;
	 xval = xval+X0; yval = yval+Y0;
	 //*X = xval;
	 //*Y = yval;
	 System.out.println("x："+xval);
	 System.out.println("y："+yval);
	 }
	//由经纬度反算成米勒投影坐标
	 public static double[] MillierConvertion(double lat, double lon)  
	    {  
	         double L = 6381372 * Math.PI * 2;//地球周长  
	         double W=L;// 平面展开后，x轴等于周长  
	         double H=L/2;// y轴约等于周长一半  
	         double mill=2.3;// 米勒投影中的一个常数，范围大约在正负2.3之间  
	         double x = lon * Math.PI / 180;// 将经度从度数转换为弧度  
	         double y = lat * Math.PI / 180;// 将纬度从度数转换为弧度  
	         y=1.25 * Math.log( Math.tan( 0.25 * Math.PI + 0.4 * y ) );// 米勒投影的转换  
	         // 弧度转为实际距离  
	         x = ( W / 2 ) + ( W / (2 * Math.PI) ) * x;  
	         y = ( H / 2 ) - ( H / ( 2 * mill ) ) * y;  
	         double[] result=new double[2];  
	         result[0]=x;  
	         result[1]=y;  
	         return result;  
	    } 
	//  由高斯投影坐标反算成经纬度
	 public static double[] GaussToBL(double X, double Y)//, double *longitude, double *latitude)
	 {
	   int ProjNo; int ZoneWide; ////带宽
	   double[] output = new double[2];
	   double longitude1,latitude1, longitude0, X0,Y0, xval,yval;//latitude0,
	   double e1,e2,f,a, ee, NN, T,C, M, D,R,u,fai, iPI;
	   iPI = 0.0174532925199433; ////3.1415926535898/180.0;
	   //a = 6378245.0; f = 1.0/298.3; //54年北京坐标系参数
	   a=6378140.0; f=1/298.257; //80年西安坐标系参数
	   ZoneWide = 6; ////6度带宽
	   ProjNo = (int)(X/1000000L) ; //查找带号
	   longitude0 = (ProjNo-1) * ZoneWide + ZoneWide / 2;
	   longitude0 = longitude0 * iPI ; //中央经线
	   
	   
	   X0 = ProjNo*1000000L+500000L;
	   Y0 = 0;
	   xval = X-X0; yval = Y-Y0; //带内大地坐标
	   e2 = 2*f-f*f;
	   e1 = (1.0-Math.sqrt(1-e2))/(1.0+Math.sqrt(1-e2));
	   ee = e2/(1-e2);
	   M = yval;
	   u = M/(a*(1-e2/4-3*e2*e2/64-5*e2*e2*e2/256));
	   fai = u+(3*e1/2-27*e1*e1*e1/32)*Math.sin(2*u)+(21*e1*e1/16-55*e1*e1*e1*e1/32)*Math.sin(
	   4*u)
	   +(151*e1*e1*e1/96)*Math.sin(6*u)+(1097*e1*e1*e1*e1/512)*Math.sin(8*u);
	   C = ee*Math.cos(fai)*Math.cos(fai);
	   T = Math.tan(fai)*Math.tan(fai);
	   NN = a/Math.sqrt(1.0-e2*Math.sin(fai)*Math.sin(fai));
	   R = a*(1-e2)/Math.sqrt((1-e2*Math.sin(fai)*Math.sin(fai))*(1-e2*Math.sin(fai)*Math.sin(fai))*(1-e2*Math.sin
	   (fai)*Math.sin(fai)));
	   D = xval/NN;
	   //计算经度(Longitude) 纬度(Latitude)
	   longitude1 = longitude0+(D-(1+2*T+C)*D*D*D/6+(5-2*C+28*T-3*C*C+8*ee+24*T*T)*D
	   *D*D*D*D/120)/Math.cos(fai);
	   latitude1 = fai -(NN*Math.tan(fai)/R)*(D*D/2-(5+3*T+10*C-4*C*C-9*ee)*D*D*D*D/24
	   +(61+90*T+298*C+45*T*T-256*ee-3*C*C)*D*D*D*D*D*D/720);
	   //转换为度 DD
	   output[0] = longitude1 / iPI;
	   output[1] = latitude1 / iPI;
	   return output;
	   //*longitude = longitude1 / iPI;
	   //*latitude = latitude1 / iPI;
	 }

}
