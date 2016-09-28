package com.svail.crawl;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.svail.crawl.crawldata.Anjuke;
public class Crawler {  
	private static long getTimeMillis(String time) {  
	    try {  
	        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");  

	        DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");  
	        Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time); 
	        return curDate.getTime(); 
	    } catch (ParseException e) {  
	        e.printStackTrace();  
	    }  
	    return 0;  
	}  
    public static void main(String[] args) {  

    	ScheduledExecutorService service = Executors.newScheduledThreadPool(Anjuke.regions.length * 2 + 4);  //Java通过Executors提供四种线程池,其中newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
        
    	long oneDay = 24 * 60 * 60 * 1000;  
    	long initDelay = getTimeMillis("17:49:00") - System.currentTimeMillis();  
    	initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;  

    	// 重庆 求租 抓取
        Runnable cqRentalTask = new Runnable()//Runnable是Thread的接口，在大多数情况下“推荐用接口的方式”生成线程，因为接口可以实现多继承，况且Runnable只有一个run方法，很适合继承。
        {  
            public void run() {  
            	Anjuke.getRentalInfo();
            }  
        };  
    	service.scheduleAtFixedRate(cqRentalTask, initDelay, 12 * 60 * 1000, TimeUnit.MILLISECONDS); // 5分钟访问频率
    	//scheduleAtFixedRate(TimerTask task,long delay,long period) 方法用于安排指定的任务进行重复的固定速率执行，在指定的延迟后开始。
    	// 重庆 出租 抓取
    	for (int n = 0; n < Anjuke.regions.length; n ++)
    	{
    		final String region = Anjuke.regions[n];
    		/**
    		 * 将方法声明为final，那就说明你已经知道这个方法提供的功能已经满足你要求，不需要进行扩展，并且也不允许任何从此类继承的类来覆写这个方法，但是继承
    		 * 仍然可以继承这个方法，也就是说可以直接使用。
    		 * 当你将final用于类身上时，你就需要仔细考虑，因为一个final类是无法被任何人继承的，那也就意味着此类在一个继承树中是一个叶子类，并且此类的设计
    		 * 已被认为很完美而不需要进行修改或扩展。对于final类中的成员，你可以定义其为final，也可以不是final。而对于方法，由于所属类为final的关系，
    		 * 自然也就成了final型的。
    		 * final类不能被继承，没有子类，final类中的方法默认是final的。
    		 * final方法不能被子类的方法覆盖，但可以被继承。
    		 * final成员变量表示常量，只能被赋值一次，赋值后值不再改变。
    		 * final不能用于修饰构造方法。
    		 */
    		Runnable cqRentOutTask = new Runnable() {  
                public void run() {  
                	Anjuke.getRentOutInfo(new String(region));//函数存在于Geofang类中
                }
            };  
        	service.scheduleAtFixedRate(cqRentOutTask, initDelay, 12 * 60 * 1000, TimeUnit.MILLISECONDS); // 5分钟访问频率
        	//scheduleAtFixedRate(Runnable command,long initialDelay,long period,TimeUnit unit);
    	}
    	
    	// 重庆 二手房 抓取
    	for (int n = 0; n < Anjuke.regions.length; n ++)
    	{
    		final String region = Anjuke.regions[n];
    		Runnable cqResoldApparmentTask = new Runnable() {  
                public void run() {  
                	Anjuke.getResoldApartmentInfo(new String(region));
                }
            };  
        	service.scheduleAtFixedRate(cqResoldApparmentTask, initDelay, 12 * 60 * 1000, TimeUnit.MILLISECONDS); // 5分钟访问频率
        	
    	}
    	initDelay = getTimeMillis("23:50:00") - System.currentTimeMillis();  
    	initDelay = initDelay > 0 ? initDelay : oneDay + initDelay; 

    	// 搜房网 新房 按照1天更新频率
    	Runnable NewCQHouseTask = new Runnable() {  
           public void run() {  
            	Calendar cal = Calendar.getInstance();//Calendar类的静态方法getInstance()可以初始化一个日历对象:Calendar cal = Calendar.getInstance(); 
            	
            	/**
            	 * 字符串转换日期格式  
                   DateFormat fmtDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
                                                         得到日期格式对象  
                   Date date = fmtDateTime.parse(strDateMake);  
  
                                                          完整显示日期时间  
                   String str = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")).format(new Date());  
                   System.out.println(str); 
                                                          初始化 Calendar 对象，但并不必要，除非需要重置时间  
                    calendar.setTime(new Date());   
            	 */
//            	Geofang.getNewBuildingInfo(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
            	//该函数在geofang中
            }  
        };  
    	service.scheduleAtFixedRate(NewCQHouseTask, initDelay, oneDay, TimeUnit.MILLISECONDS); 
    	
    }  
}  