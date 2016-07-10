package com.test.smarthome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.speech.util.ApkInstaller;
import com.iflytek.speech.util.FucUtil;
import com.iflytek.speech.util.JsonParser;


import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class SoundActivity extends Activity implements OnClickListener{
	private static final String KEY_GRAMMAR_ABNF_ID = "grammar_abnf_id";
	//private Button start=null;
  //  private Button stop=null;
    // �������÷���ֵ
    int ret = 0;
    // �﷨���ʵ���ʱ����
 	String mContent;
 	// ����
 	private SharedPreferences mSharedPreferences;
 	
 	ApkInstaller mInstaller;
 	
	private static String TAG = SoundActivity.class.getSimpleName();
	
	// ����ʶ�����
	private SpeechRecognizer mAsr;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sound_activity);
	//	start=(Button)findViewById(R.id.voice_start);
	//	start=(Button)findViewById(R.id.voice_stop);
		//initLayout();
	//	showTip("start");
		findViewById(R.id.voice_start).setOnClickListener(SoundActivity.this);
		findViewById(R.id.voice_stop).setOnClickListener(SoundActivity.this);
		// ��ʼ��ʶ�����
		mAsr = SpeechRecognizer.createRecognizer(SoundActivity.this, mInitListener);
		
		//��ʼ���﷨
	//	String mCloudGrammar= "#ABNF 1.0 UTF-8; languagezh-CN; mode voice; root $main; $main = $place1 $place2 ; $place1 = �� | �ر�; $place2 = ������ | ���ҵ� | �������; "; 
		String mCloudGrammar = FucUtil.readFile(this,"c.abnf","utf-8");
		
		//һЩ����
		mContent = new String(mCloudGrammar);
		mAsr.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
		mAsr.setParameter(SpeechConstant.TEXT_ENCODING,"utf-8");
	    ret = mAsr.buildGrammar("abnf", mContent, mCloudGrammarListener);
	    if(ret != ErrorCode.SUCCESS)
			showTip("�﷨����ʧ��,�����룺" + ret);
	    mSharedPreferences = getSharedPreferences(getPackageName(),	MODE_PRIVATE);
	    
	    
	    
	    {
	    	if(!setParam())
				showTip("����ʧ��");
			ret = mAsr.startListening(mRecognizerListener);
			if (ret != ErrorCode.SUCCESS) {
				if(ret == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED){
					//δ��װ����ת����ʾ��װҳ��
				//	mInstaller.install();
				}else {
					showTip("ʶ��ʧ��,������: " + ret);	
				}
			}
	    	
	    }
	    finish();
	}
	
	@Override
	public void onClick(View view){
		switch(view.getId()){
		case R.id.voice_start:
		//	showTip("��ʼ");
			if(!setParam())
				showTip("����ʧ��");
			ret = mAsr.startListening(mRecognizerListener);
			if (ret != ErrorCode.SUCCESS) {
				if(ret == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED){
					//δ��װ����ת����ʾ��װҳ��
				//	mInstaller.install();
				}else {
					showTip("ʶ��ʧ��,������: " + ret);	
				}
			}
			break;
		case R.id.voice_stop:
			 		
			
		//	showTip("����");
			break;
		}
	 }
	
	/**
     * ʶ���������
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener(){

		@Override
		public void onBeginOfSpeech() {
			// TODO Auto-generated method stub
	//		showTip("��ʼ˵��");
		}

		@Override
		public void onEndOfSpeech() {
			// TODO Auto-generated method stub
		//	showTip("����˵��");
		}

		@Override
		public void onError(SpeechError error) {
			// TODO Auto-generated method stub
			showTip("onError Code��"	+ error.getErrorCode());
		}

		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onResult(final RecognizerResult result, boolean isLast) {
			// TODO Auto-generated method stub
		//	showTip(result.getResultString());
		//	String text = JsonParser.parseGrammarResult(result.getResultString());	
		//	showTip(text);
			
			String com = "null";
			JSONObject json;
			try {
				json = new JSONObject(result.getResultString());
				JSONArray jsonArray=json.getJSONArray("ws");
				json = (JSONObject) jsonArray.get(0);			
				jsonArray = json.getJSONArray("cw");
				json = (JSONObject) jsonArray.get(0);
				com = (String) json.get("w");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			showTip(com);
			
			command_measure(com);
			ImageButton voice_command_button=(ImageButton)findViewById(R.id.voice_command_button);
			((ImageButton)voice_command_button).setImageDrawable(getResources().getDrawable(R.drawable.mic));
			/*
			if (null != result){
				if(com.equals("�򿪿�����"))
				{
					
					try {
						HttpSender.sendCommand("L1/ON");
						showTip("command sent");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
			}
			*/
		}

		@Override
		public void onVolumeChanged(int arg0, byte[] arg1) {
			// TODO Auto-generated method stub
			
		}
    	
    };
	
	/**
     * �ƶ˹����﷨��������
     */
	private GrammarListener mCloudGrammarListener = new GrammarListener() {
		@Override
		public void onBuildFinish(String grammarId, SpeechError error) {
			if(error == null){
				String grammarID = new String(grammarId);
		//		showTip(grammarID);
				
				Editor editor = mSharedPreferences.edit();
			//	if(!TextUtils.isEmpty(grammarId))
					editor.putString(KEY_GRAMMAR_ABNF_ID, grammarID);
				editor.commit();
			//	showTip("�﷨�����ɹ���" + grammarId);
			}else{
				showTip("�﷨����ʧ��,�����룺" + error.getErrorCode());
				
			}			
		}
	};
	
	
	
	/**
     * ��ʼ����������
     */
    private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			Log.d(TAG, "SpeechRecognizer init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
        		showTip("��ʼ��ʧ��,�����룺"+code);
        	}
		}
    };
    
    
    void showTip(final String str) {
    	Toast.makeText(this, str, 
				Toast.LENGTH_SHORT).show();
    }
    
    
    public boolean setParam(){
		boolean result = false;
		//����ʶ������
		mAsr.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
		//���÷��ؽ��Ϊjson��ʽ
		mAsr.setParameter(SpeechConstant.RESULT_TYPE, "json");
		
		String grammarId = mSharedPreferences.getString(KEY_GRAMMAR_ABNF_ID, null);
		if(TextUtils.isEmpty(grammarId))
		{
			result =  false;
		}else {
			//�����ƶ�ʶ��ʹ�õ��﷨id
			mAsr.setParameter(SpeechConstant.CLOUD_GRAMMAR, grammarId);
			result =  true;
		}

		
		// ������Ƶ����·����������Ƶ��ʽ֧��pcm��wav������·��Ϊsd����ע��WRITE_EXTERNAL_STORAGEȨ��
		// ע��AUDIO_FORMAT���������Ҫ���°汾������Ч
	//	mAsr.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
	//	mAsr.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/asr.wav");
		return result;
	}
    
    public boolean command_measure(String command){
    	try {
    		if(command.equals("�򿪿�����"))
    		{		HttpSender.sendCommand("L1/ON"); showTip(command + " ָ���ѷ���!");}
    		else if(command.equals("�رտ�����"))
    		{	HttpSender.sendCommand("L1/OFF"); showTip(command + " ָ���ѷ���!");}
    		else if(command.equals("���������"))
    		{	HttpSender.sendCommand("L3/ON"); showTip(command + " ָ���ѷ���!");}
    		else if(command.equals("�ر��������"))
    		{	HttpSender.sendCommand("L3/OFF"); showTip(command + " ָ���ѷ���!");}
    		else if(command.equals("�����ҵ�"))
    		{	HttpSender.sendCommand("L2/ON"); showTip(command + " ָ���ѷ���!");}
    		else if(command.equals("�ر����ҵ�"))
    		{	HttpSender.sendCommand("L2/OFF"); showTip(command + " ָ���ѷ���!");}
    		else if(command.equals("�����ҿյ�"))
    		{	HttpSender.sendCommand("AC2/ON"); showTip(command + " ָ���ѷ���!");}
    		else if(command.equals("�ر����ҿյ�"))
    		{	HttpSender.sendCommand("AC2/OFF"); showTip(command + " ָ���ѷ���!");}
    		else if(command.equals("�򿪿����յ�"))
    		{	HttpSender.sendCommand("AC1/ON"); showTip(command + " ָ���ѷ���!");}
    		else if(command.equals("�رտ����յ�"))
    		{	HttpSender.sendCommand("AC1/OFF"); showTip(command + " ָ���ѷ���!");}
    		else if(command.equals("���ҿյ�һСʱ����"))
    		{	HttpSender.sendCommand("TD/0"); showTip(command + " ָ���ѷ���!");}
    		else if(command.equals("���ҿյ�һСʱ��ر�"))
    		{	HttpSender.sendCommand("TD/1"); showTip(command + " ָ���ѷ���!");}
    		else if(command.equals("�򿪵���"))
    		{	HttpSender.sendCommand("TV1/ON"); showTip(command + " ָ���ѷ���!");}
    		else if(command.equals("�رյ���"))
    		{	HttpSender.sendCommand("TV/OFF"); showTip(command + " ָ���ѷ���!");}
    		else if(command.equals("����Ƶ����"))
    		{	HttpSender.sendCommand("TVC/UP"); showTip(command + " ָ���ѷ���!");}
    		else if(command.equals("����Ƶ����"))
    		{	HttpSender.sendCommand("TVC/DOWN"); showTip(command + " ָ���ѷ���!");}
    		else if(command.equals("����������"))
    		{	HttpSender.sendCommand("TVS/UP"); showTip(command + " ָ���ѷ���!");}
    		else if(command.equals("����������"))
    		{	HttpSender.sendCommand("TVS/DOWN"); showTip(command + " ָ���ѷ���!");}
    		else if(command.equals("�յ��¶ȼ�"))
    		{	HttpSender.sendCommand("TMP/UP"); showTip(command + " ָ���ѷ���!");}
    		else if(command.equals("�յ��¶ȼ�"))
    		{	HttpSender.sendCommand("TMP/DOWN"); showTip(command + " ָ���ѷ���!");}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
    		
    	
    	
    	return false;
    }
    
}