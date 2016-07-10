package com.test.smarthome;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity{
	ImageButton voice_command_button=null;
    private ImageButton button2=null;
    private ImageButton button3=null;
    private ImageButton button4=null;
    private ImageButton button5=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        voice_command_button=(ImageButton)findViewById(R.id.voice_command_button);
        button2=(ImageButton)findViewById(R.id.button2);
        button3=(ImageButton)findViewById(R.id.button3);
        button4=(ImageButton)findViewById(R.id.button4);
        button5=(ImageButton)findViewById(R.id.button5);
        
     
        voice_command_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		//		((ImageButton)voice_command_button).setImageDrawable(getResources().getDrawable(R.drawable.mic1));
				Intent voice_intent=new Intent();
        		voice_intent.setClass(MainActivity.this, SoundActivity.class);
        		MainActivity.this.startActivity(voice_intent);
       // 		((ImageButton)voice_command_button).setImageDrawable(getResources().getDrawable(R.drawable.mic));
			}
        	
        });
           
        voice_command_button.setOnTouchListener(new View.OnTouchListener(){            
            public boolean onTouch(View v, MotionEvent event) {               
                    if(event.getAction() == MotionEvent.ACTION_DOWN){       
                       //重新设置按下时的背景图片  
                       ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.mic1));                              
                    }else if(event.getAction() == MotionEvent.ACTION_UP){       
                        //再修改为抬起时的正常图片  
                        ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.mic));     
                    }  
                    return false;       
            }

			    
        });  
        
        
        
        
        
        
            
        button2.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		Intent intent=new Intent();
        		intent.setClass(MainActivity.this, AirconditonActivity.class);
        		MainActivity.this.startActivity(intent);
        	}
        });
 
        button3.setOnClickListener(new OnClickListener(){
        	@Override 
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		Intent intent2=new Intent();
        		intent2.setClass(MainActivity.this, LightActivity.class);
        		MainActivity.this.startActivity(intent2);
        	}
        });
        button4.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub   	   
        		Intent intent3=new Intent();
        		intent3.setClass(MainActivity.this, TelevisionActivity.class);
        		MainActivity.this.startActivity(intent3);
        	}
        });
        button5.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		Intent intent4=new Intent();
        		intent4.setClass(MainActivity.this, SafeActivity.class);
        		MainActivity.this.startActivity(intent4);
        	}
        });
   
    }
    
    public static InetAddress getBroadcastAddress(Context context) throws UnknownHostException {
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcp = wifi.getDhcpInfo();
        if(dhcp==null) {
            return InetAddress.getByName("255.255.255.255");
        }
      
        int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
        byte[] quads = new byte[4];
        for (int k = 0; k < 4; k++)
            quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
        return InetAddress.getByAddress(quads);
    }
    
    
    public static String getBroadcast() throws SocketException {  
        System.setProperty("java.net.preferIPv4Stack", "true");  
        for (Enumeration<NetworkInterface> niEnum = NetworkInterface.getNetworkInterfaces(); niEnum.hasMoreElements();) {  
            NetworkInterface ni = niEnum.nextElement();  
            if (!ni.isLoopback()) {  
                for (InterfaceAddress interfaceAddress : ni.getInterfaceAddresses()) {  
                    if (interfaceAddress.getBroadcast() != null) {  
                        return interfaceAddress.getBroadcast().toString().substring(1);  
                    }  
                }  
            }  
        }  
        return null;  
    }  
    
}
