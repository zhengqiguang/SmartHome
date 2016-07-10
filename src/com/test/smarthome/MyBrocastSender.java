package com.test.smarthome;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class MyBrocastSender extends Thread{
	private String dataString;
	public static final int DEFAULT_PORT = 38324;
	private static final int MAX_DATA_PACKET_LENGTH = 40;
	private byte[] buffer = new byte[MAX_DATA_PACKET_LENGTH];
	private String add;
	
	private DatagramSocket udpSocket;
	
	public MyBrocastSender(String dataString, String add) {
		this.dataString = dataString;
		this.add=add;
	}
	
	public void run() {
		DatagramPacket dataPacket = null;
		
		try {
			udpSocket = new DatagramSocket(DEFAULT_PORT);

			dataPacket = new DatagramPacket(buffer, MAX_DATA_PACKET_LENGTH);
			byte[] data = dataString.getBytes();
			dataPacket.setData(data);
			dataPacket.setLength(data.length);
			dataPacket.setPort(DEFAULT_PORT); 

			InetAddress broadcastAddr;

			broadcastAddr = InetAddress.getByName(add);
			dataPacket.setAddress(broadcastAddr);
	//		dataPacket.setAddress(add);
		} catch (Exception e) {
			Log.e("myBrocastSender", e.toString());
		}
		// while( start ){
		try {
			udpSocket.send(dataPacket);
			sleep(10);
		} catch (Exception e) {
			Log.e("myBrocastSender", e.toString());
		}
		// }

		try{
			udpSocket.close();
		} catch (Exception e) {
			Log.e("myBrocastSender", e.toString());
		}
	}
}
	