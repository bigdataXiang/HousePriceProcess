package com.svail.chongqing;

import java.net.InetAddress;
import java.rmi.UnknownHostException;
import java.util.Timer;




public class ScheduleRun {
	public static void main(String[] args){

        Timer timer= new Timer();
        timer.schedule(new Task(), 10 * 1000, 60 * 1000);

	}
	
	
}
