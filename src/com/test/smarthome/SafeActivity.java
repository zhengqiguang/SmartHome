package com.test.smarthome;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
    	Sensor s = new Sensor(0);
    	sensor_list.add(s);
    }

    adapter = new SensorAdapter(SafeActivity.this, R.layout.sensor_item, sensor_list);
    final ListView listview = (ListView) findViewById(R.id.list_view);
    listview.setAdapter(adapter);
    listview.setOnItemClickListener(new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			final int p = position;
			
			AlertDialog.Builder builder = new AlertDialog.Builder(SafeActivity.this);
            builder.setIcon(R.drawable.ic_launcher);
            builder.setTitle("ѡ��һ��������");
            //    ָ�������б����ʾ����
            final String[] cities = {"��״̬������", "���崫����", "�ƹ⴫����", "��ʪ�ȴ�����", 
            		"ˮλ������", "ˮ�δ�����","��������������","�����ߴ�����"};
            //    ����һ���������б�ѡ����
            builder.setItems(cities, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
        //            Toast.makeText(SafeActivity.this, "ѡ��ĳ���Ϊ��" + cities[which], Toast.LENGTH_SHORT).show();
                	int t=0;;
    		    	switch(which + 1){
    		    	case 1:
    		    	case 2:
    		    	case 3:
    		    		t=1;
    		    		break;
    		    	case 4:
    		    		t=2;
    		    		break;
    		    	case 5:
    		    	case 6:
    		    	case 7:
    		    		t=3;
    		    		break;
    		    	}
    		    	String a = "e";
    		    	try {
						a = HttpSender.sendCommand("AX" + p + "/AIN/" + t);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    		    	
                    Sensor s = new Sensor(which + 1);
                    sensor_list.set(p, s);
                    try {
						refresh(p);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    adapter = new SensorAdapter(SafeActivity.this, R.layout.sensor_item, sensor_list);
    		    	listview.setAdapter(adapter);
    		    	
                }
            });
            builder.show();
		}
		
	});
    
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
			
			
		}
    	
    });
    
    
    
    //�޸�Ŀ��IP
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
	  String s;
	  s= result_measure(type, result);
//	  s = result;
	  sensor_list.set(location, new Sensor(type, s));
	  
  }
  
  //����������result
  public String result_measure(int type, String result){
	  String s="";
	  if(result.equals("")||result.equals("ERR"))return result;
	  switch(type){
	  case 0:
		  s="";
		  break;
	  case 1:
		  if(result.equals("0"))s="��";
		  else s="δ��";
		  break;
	  case 2:
	  case 3:
		  if(result.equals("0"))s="����";
		  else s="����";
		  break;
	  case 4:
		  String[] t = result.split(",");
		  s=String.valueOf(Double.valueOf(t[0])) + "��" + "/" +String.valueOf(Double.valueOf(t[1])) + "%";
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
  