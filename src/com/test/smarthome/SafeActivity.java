package com.test.smarthome;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
public class SafeActivity extends Activity 
{
  public TextView TextView1,TextView2,TextView3;
  public RadioGroup myRadioGroup1,myRadioGroup2; 
  public RadioButton myRadioButton1,myRadioButton2,myRadioButton3,myRadioButton4; 
  private Button change_ip=null;  
  private List<Sensor> sensor_list = new ArrayList<Sensor>();
  SensorAdapter adapter=null;
  ListView listview =null;
  
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.safe); 
    
    Button refresh = (Button)findViewById(R.id.sensor_refresh);
    
    for(int i=0;i<8;i++){
    	Sensor s = new Sensor(i);
    	sensor_list.add(s);
    }

    adapter = new SensorAdapter(SafeActivity.this, R.layout.sensor_item, sensor_list);
    final ListView listview = (ListView) findViewById(R.id.list_view);
    listview.setAdapter(adapter);
    
    
    refresh.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			for(int i=0;i<8;i++){
		    	try {
					refresh(i);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	adapter = new SensorAdapter(SafeActivity.this, R.layout.sensor_item, sensor_list);
		    	listview.setAdapter(adapter);
		    }
		//	sensor_list.set(0, new Sensor(1, "haha"));
		//	adapter = new SensorAdapter(SafeActivity.this, R.layout.sensor_item, sensor_list);
	    //	listview.setAdapter(adapter);
			
			/*
			try {
				String result = HttpSender.sendCommand("AX" + 7 + "/ARD/");
				Toast.makeText(SafeActivity.this, result, Toast.LENGTH_SHORT).show();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			
		}
    	
    });
    
    
    
    //修改目标IP
    change_ip=(Button)findViewById(R.id.change_ip);
    final EditText edit_ip = (EditText)findViewById(R.id.edit_ip);   
    change_ip.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			HttpSender.ip=edit_ip.getText().toString();
		}	
    });
     
  }
  
  public void refresh(int location) throws InterruptedException{
	  String result = HttpSender.sendCommand("AX" + location + "/ARD/");
	  int type =  sensor_list.get(location).get_type();
//	  String s = result_measure(type, result);
	  sensor_list.set(location, new Sensor(type, result));
	  
  }
  
  //处理返回来的result
  public String result_measure(int type, String result){
	  String s="";
	  if(result.equals("")||result.equals("ERR"))return result;
	  switch(type){
	  case 0:
		  s="";
		  break;
	  case 1:
		  if(result.equals("1"))s="关";
		  else s="未关";
		  break;
	  case 2:
	  case 3:
		  if(result.equals("0"))s="无人";
		  else s="有人";
		  break;
	  case 4:
		  String[] t = result.split(",");
		  s=String.valueOf(Integer.valueOf(t[0])) + "℃" + "/" +String.valueOf(Integer.valueOf(t[1])) + "%";
		  break;
	  case 5:
	  case 6:
	  case 7:
	  case 8:
		  s=result;
		  break;
	  }
	  return s;
  }
  
}
  