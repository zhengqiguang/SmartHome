package com.test.smarthome;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
public class SafeActivity extends Activity 
{
  public TextView TextView1,TextView2,TextView3;
  public RadioGroup myRadioGroup1,myRadioGroup2; 
  public RadioButton myRadioButton1,myRadioButton2,myRadioButton3,myRadioButton4; 
  private Button change_ip=null;  
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.safe); 
    //ÐÞ¸ÄÄ¿±êIP
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
}
  