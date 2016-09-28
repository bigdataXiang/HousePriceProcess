package com.svail.chongqing;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.svail.crawl.crawldata.Anjuke;
public class DataCrawl { 
	
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

    	ScheduledExecutorService service = Executors.newScheduledThreadPool(4);  //Java通过Executors提供四种线程池,其中newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
        
    	long oneDay = 24 * 60 * 60 * 1000;
    	long timemillis=getTimeMillis("15:54:00");
    	long timenow=System.currentTimeMillis();
    	long initDelay = timemillis - timenow;  
    	initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;  

    	// 重庆交通抓取
        Runnable trafficTask = new Runnable()//Runnable是Thread的接口，在大多数情况下“推荐用接口的方式”生成线程，因为接口可以实现多继承，况且Runnable只有一个run方法，很适合继承。
        {  
            public void run() { 
            	System.out.println("开始定期抓取");
            	//Traffic.getTrafficInfo("http://e.t.qq.com/cqjiaowei","D:/重庆基础数据抓取/基础数据/交通/");
            }  
        };  
    	service.scheduleAtFixedRate(trafficTask, initDelay, 12 * 60 * 1000, TimeUnit.MILLISECONDS);//scheduleAtFixedRate(TimerTask task,long delay,long period) 方法用于安排指定的任务进行重复的固定速率执行，在指定的延迟后开始。
        
    }  
}  