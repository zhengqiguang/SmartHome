package com.test.smarthome;

import android.app.Application;
import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechUtility;

public class SpeechApp extends Application {

	@Override
	public void onCreate() {
		// Ӧ�ó�����ڴ����ã������ֻ��ڴ��С��ɱ����̨���̺�ͨ����ʷintent����Activity���SpeechUtility����Ϊnull
		// ����Application�е��ó�ʼ������Ҫ��Mainifest��ע���Applicaiton
		// ע�⣺�˽ӿ��ڷ������̵��û᷵��null���������ڷ�������ʹ���������ܣ������Ӳ�����SpeechConstant.FORCE_LOGIN+"=true"
		// ������ʹ�ð�ǡ�,���ָ���
		// �����������Ӧ��appid,������'='��appid֮����ӿո񼰿�ת���
		
		// ע�⣺ appid ��������ص�SDK����һ�£���������10407����
		
		SpeechUtility.createUtility(SpeechApp.this, "appid=" + "5762a70e");
			
		// �����������������־���أ�Ĭ�Ͽ����������ó�falseʱ�ر�������SDK��־��ӡ
		// Setting.setShowLog(false);
		super.onCreate();
	}
	
}

