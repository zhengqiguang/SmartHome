package com.test.smarthome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.widget.Toast;


public class HttpSender extends Thread{
	boolean flag = false;
	String command;
	String strURL;
	String res="";
	static String ip="210.41.100.57";
	URL url=null;
	HttpSender(String command){
		this.command=command;
		strURL = "http://" + ip + "/" +command;
	//	"http://210.41.100.57/hello";

		try{
			url=new URL(strURL);
		}catch (Exception e)
        {
            e.printStackTrace();
        }
		
	}

	public void run(){
		HttpURLConnection urlConn;
		try {
			urlConn = (HttpURLConnection)url.openConnection();
		
		InputStreamReader  in = new InputStreamReader(urlConn.getInputStream());
		BufferedReader bufferReader = new BufferedReader(in);
		String result = bufferReader.readLine();
		res = result;
		in.close();
		urlConn.disconnect();
		flag=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static String sendCommand(String command) throws InterruptedException{
		HttpSender a=new HttpSender(command);
		a.start();
		int wait=0;
		while(true){
			if(a.flag==true || wait++ > 100)return a.res;
			sleep(10);
		}
		
		
	}
}
