package com.test.smarthome;
import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
public class LightActivity extends Activity 
{
  public TextView textView1,textView2,textView3,textView4;
  public RadioGroup myRadioGroup1,myRadioGroup2,myRadioGroup3; 
  public RadioButton myRadioButton1,myRadioButton2,myRadioButton3,myRadioButton4,myRadioButton5,myRadioButton6; 
  
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.light); 
    
   /* 
    try {
    	stat1.setText("��ǰ״̬�� "+((HttpSender.sendCommand("L1/ONQ").equals("1"))?"����":"�ر�"));
    	stat2.setText("��ǰ״̬�� "+((HttpSender.sendCommand("L2/ONQ").equals("1"))?"����":"�ر�"));
    	stat3.setText("��ǰ״̬�� "+((HttpSender.sendCommand("L3/ONQ").equals("1"))?"����":"�ر�"));
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    */
    Switch l1 = (Switch)this.findViewById(R.id.l1_switch);
    Switch l2 = (Switch)this.findViewById(R.id.l2_switch);
    Switch l3 = (Switch)this.findViewById(R.id.l3_switch);
    Switch l4 = (Switch)this.findViewById(R.id.lall_switch);
    

    
    //������L1
    l1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {   
        @Override  
        public void onCheckedChanged(CompoundButton buttonView,  
                boolean isChecked) {  
            // TODO Auto-generated method stub  
            if (isChecked) { 
            	try {
					HttpSender.sendCommand("AC0/SON");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//��ac1
            } else {  
            	try {
					HttpSender.sendCommand("AC0/SOF");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//�ر�ac1
            }  
        }  
    });
    
    //���ҵ�L2
    l2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {   
        @Override  
        public void onCheckedChanged(CompoundButton buttonView,  
                boolean isChecked) {  
            // TODO Auto-generated method stub  
            if (isChecked) { 
            	try {
					HttpSender.sendCommand("AC0/SON");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//��ac1
            } else {  
            	try {
					HttpSender.sendCommand("AC0/SOF");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//�ر�ac1
            }  
        }  
    });
    
    
    //�������L3
    l3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {   
        @Override  
        public void onCheckedChanged(CompoundButton buttonView,  
                boolean isChecked) {  
            // TODO Auto-generated method stub  
            if (isChecked) { 
            	try {
					HttpSender.sendCommand("AC0/SON");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//��ac1
            } else {  
            	try {
					HttpSender.sendCommand("AC0/SOF");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//�ر�ac1
            }  
        }  
    });
    
    
    
    //ȫ��
    l4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {   
        @Override  
        public void onCheckedChanged(CompoundButton buttonView,  
                boolean isChecked) {  
            // TODO Auto-generated method stub  
            if (isChecked) { 
            	try {
					HttpSender.sendCommand("AC0/SON");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//��ac1
            } else {  
            	try {
					HttpSender.sendCommand("AC0/SOF");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//�ر�ac1
            }  
        }  
    });
    
  }
}