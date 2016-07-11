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
    // 函数调用返回值
    int ret = 0;
    // 语法、词典临时变量
 	String mContent;
 	// 缓存
 	private SharedPreferences mSharedPreferences;
 	
 	ApkInstaller mInstaller;
 	
	private static String TAG = SoundActivity.class.getSimpleName();
	
	// 语音识别对象
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
		// 初始化识别对象
		mAsr = SpeechRecognizer.createRecognizer(SoundActivity.this, mInitListener);
		
		//初始化语法
	//	String mCloudGrammar= "#ABNF 1.0 UTF-8; languagezh-CN; mode voice; root $main; $main = $place1 $place2 ; $place1 = 打开 | 关闭; $place2 = 客厅灯 | 卧室灯 | 卫生间灯; "; 
		String mCloudGrammar = FucUtil.readFile(this,"c.abnf","utf-8");
		
		//一些设置
		mContent = new String(mCloudGrammar);
		mAsr.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
		mAsr.setParameter(SpeechConstant.TEXT_ENCODING,"utf-8");
	    ret = mAsr.buildGrammar("abnf", mContent, mCloudGrammarListener);
	    if(ret != ErrorCode.SUCCESS)
			showTip("语法构建失败,错误码：" + ret);
	    mSharedPreferences = getSharedPreferences(getPackageName(),	MODE_PRIVATE);
	    
	    
	    
	    {
	    	if(!setParam())
				showTip("设置失败");
			ret = mAsr.startListening(mRecognizerListener);
			if (ret != ErrorCode.SUCCESS) {
				if(ret == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED){
					//未安装则跳转到提示安装页面
				//	mInstaller.install();
				}else {
					showTip("识别失败,错误码: " + ret);	
				}
			}
	    	
	    }
	    finish();
	}
	
	@Override
	public void onClick(View view){
		switch(view.getId()){
		case R.id.voice_start:
		//	showTip("开始");
			if(!setParam())
				showTip("设置失败");
			ret = mAsr.startListening(mRecognizerListener);
			if (ret != ErrorCode.SUCCESS) {
				if(ret == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED){
					//未安装则跳转到提示安装页面
				//	mInstaller.install();
				}else {
					showTip("识别失败,错误码: " + ret);	
				}
			}
			break;
		case R.id.voice_stop:
			 		
			
		//	showTip("结束");
			break;
		}
	 }
	
	/**
     * 识别监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener(){

		@Override
		public void onBeginOfSpeech() {
			// TODO Auto-generated method stub
	//		showTip("开始说话");
		}

		@Override
		public void onEndOfSpeech() {
			// TODO Auto-generated method stub
		//	showTip("结束说话");
		}

		@Override
		public void onError(SpeechError error) {
			// TODO Auto-generated method stub
			showTip("onError Code："	+ error.getErrorCode());
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
		//	ImageButton voice_command_button=(ImageButton)findViewById(R.id.voice_command_button);
	//		((ImageButton)voice_command_button).setImageDrawable(getResources().getDrawable(R.drawable.mic));
			/*
			if (null != result){
				if(com.equals("打开客厅灯"))
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
     * 云端构建语法监听器。
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
			//	showTip("语法构建成功：" + grammarId);
			}else{
				showTip("语法构建失败,错误码：" + error.getErrorCode());
				
			}			
		}
	};
	
	
	
	/**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			Log.d(TAG, "SpeechRecognizer init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
        		showTip("初始化失败,错误码："+code);
        	}
		}
    };
    
    
    void showTip(final String str) {
    	Toast.makeText(this, str, 
				Toast.LENGTH_SHORT).show();
    }
    
    
    public boolean setParam(){
		boolean result = false;
		//设置识别引擎
		mAsr.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
		//设置返回结果为json格式
		mAsr.setParameter(SpeechConstant.RESULT_TYPE, "json");
		
		String grammarId = mSharedPreferences.getString(KEY_GRAMMAR_ABNF_ID, null);
		if(TextUtils.isEmpty(grammarId))
		{
			result =  false;
		}else {
			//设置云端识别使用的语法id
			mAsr.setParameter(SpeechConstant.CLOUD_GRAMMAR, grammarId);
			result =  true;
		}

		
		// 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
		// 注：AUDIO_FORMAT参数语记需要更新版本才能生效
	//	mAsr.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
	//	mAsr.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/asr.wav");
		return result;
	}
    
    public boolean command_measure(String command){
    	try {
    		if(command.equals("打开客厅的灯"))
    		{		HttpSender.sendCommand("RR0/SON/"); showTip(command + " 指令已发送!");}
    		else if(command.equals("关闭客厅的灯"))
    		{	HttpSender.sendCommand("RR0/SOF/"); showTip(command + " 指令已发送!");}
    		else if(command.equals("打开卫生间的灯"))
    		{	HttpSender.sendCommand("RR2/SON/"); showTip(command + " 指令已发送!");}
    		else if(command.equals("关闭卫生间的灯"))
    		{	HttpSender.sendCommand("RR2/SOF/"); showTip(command + " 指令已发送!");}
    		else if(command.equals("打开灯"))
    		{	HttpSender.sendCommand("RR3/SON/"); showTip(command + " 指令已发送!");}
    		else if(command.equals("关闭灯"))
    		{	HttpSender.sendCommand("RR3/SOF/"); showTip(command + " 指令已发送!");}
    		else if(command.equals("打开卧室的灯"))
    		{	HttpSender.sendCommand("RR1/SON/"); showTip(command + " 指令已发送!");}
    		else if(command.equals("关闭卧室的灯"))
    		{	HttpSender.sendCommand("RR1/SOF/"); showTip(command + " 指令已发送!");}
    		else if(command.equals("打开空调"))
    		{	HttpSender.sendCommand("AC0/SST/?temp=" + String.valueOf(26) + "&mode=" + "c" + "&time=" + 0); showTip(command + " 指令已发送!");}
    		else if(command.equals("关闭空调"))
    		{	HttpSender.sendCommand("AC0/SOF/"); showTip(command + " 指令已发送!");}
    		else if(command.equals("一小时后关闭空调"))
    		{	HttpSender.sendCommand("AC0/SST/?temp=" + String.valueOf(26) + "&mode=" + "c" + "&time=" + 0); showTip(command + " 指令已发送!");}
    		else if(command.equals("打开电视"))
    		{	HttpSender.sendCommand("TV0/SON/"); showTip(command + " 指令已发送!");}
    		else if(command.equals("关闭电视"))
    		{	HttpSender.sendCommand("TV0/SOF/"); showTip(command + " 指令已发送!");}
    		else if(command.equals("往后改台"))
    		{	HttpSender.sendCommand("STP/-1"); showTip(command + " 指令已发送!");}
    		else if(command.equals("往前改台"))
    		{	HttpSender.sendCommand("STP/-2"); showTip(command + " 指令已发送!");}
    		else if(command.equals("调大声音"))
    		{	HttpSender.sendCommand("STV/-1"); showTip(command + " 指令已发送!");}
    		else if(command.equals("调小声音"))
    		{	HttpSender.sendCommand("STV/-2"); showTip(command + " 指令已发送!");}
    		else if(command.equals("调高温度"))
    		{	HttpSender.sendCommand("AC0/SST/?temp=" + String.valueOf(++AirconditonActivity.tmp) + "&mode=" + AirconditonActivity.mode + "&time=" + AirconditonActivity.time); showTip(command + " 指令已发送!");}
    		else if(command.equals("调低温度"))
    		{	HttpSender.sendCommand("AC0/SST/?temp=" + String.valueOf(--AirconditonActivity.tmp) + "&mode=" + AirconditonActivity.mode + "&time=" + AirconditonActivity.time); showTip(command + " 指令已发送!");}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
    		
    	
    	
    	return false;
    }
    
}