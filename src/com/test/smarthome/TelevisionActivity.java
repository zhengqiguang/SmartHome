package com.test.smarthome;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
public class TelevisionActivity extends Activity implements OnSeekBarChangeListener
{
  public TextView textView1,textView2,textView3,textView4;
  public RadioGroup myRadioGroup1; 
  public RadioButton myRadioButton1,myRadioButton2; 
  public ImageButton imageButton1,imgageButton2; 
  public SeekBar seekBar1; 
  TextView tv_s_text = null;
  TextView tv_c_text = null;
  int sound=1;
  int channel=1;
  EditText input_text = null;
  
  boolean is_tv_on = false;
  
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.television); 
 //   final TextView stat1 = (TextView)findViewById(R.id.tv_stat);
    final ImageButton tvc_up = (ImageButton)findViewById(R.id.tv_c_up);
    final ImageButton tvc_down = (ImageButton)findViewById(R.id.tv_c_down);
    final Button tvs_up = (Button)findViewById(R.id.tv_s_up);
    final Button tvs_down = (Button)findViewById(R.id.tv_s_down);
    input_text = (EditText)findViewById(R.id.tv_input_c);
    tv_s_text = (TextView)findViewById(R.id.tv_textView5);
    tv_c_text = (TextView)findViewById(R.id.textView3);
 /*   
    try {
    	stat1.setText("开关： "+((HttpSender.sendCommand("TV1/ONQ").equals("1"))?"开启":"关闭"));
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	*/
  //电视TV1
    
    final Button tv1 = (Button)this.findViewById(R.id.button1);
    tv1.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
				try {
					if(is_tv_on)
					{
						HttpSender.sendCommand("TV0/SOF/");
						Toast.makeText(TelevisionActivity.this, "tv on", Toast.LENGTH_SHORT).show();
						tv1.setTextColor(Color.BLACK);
						is_tv_on=false;
					}
					else
					{
						HttpSender.sendCommand("TV0/SON/");
						Toast.makeText(TelevisionActivity.this, "tv off", Toast.LENGTH_SHORT).show();
						tv1.setTextColor(Color.RED);
						is_tv_on=true;
					}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
		}
    	
    });
    /*
    Switch tv1 = (Switch)this.findViewById(R.id.tv_switch);
    tv1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {   
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
				}//打开ac1
            } else {  
            	try {
					HttpSender.sendCommand("AC0/SOF");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//关闭ac1
            }  
        }  
    });  
    */
    
    
    
    //频道
    final Button tv = (Button)this.findViewById(R.id.tv_input_button);
    tv.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			channel = Integer.valueOf(input_text.getText().toString());
			try {
				HttpSender.sendCommand("STP/" + channel);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tv_c_text.setText("频道" + channel);
		}
    	
    });
    
    
    SeekBar tv1_s = (SeekBar)this.findViewById(R.id.tv_seekBar);
    tv1_s.setOnSeekBarChangeListener((OnSeekBarChangeListener) this);
    
    
    tvc_up.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			channel++;
			try {
				HttpSender.sendCommand("STP/-1");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tv_c_text.setText("频道" + channel);
		}
    	
    });  
    
    tvc_down.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			channel--;
			try {
				HttpSender.sendCommand("STP/-2");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tv_c_text.setText("频道" + channel);
		}
    	
    });  
    
    tvs_up.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				HttpSender.sendCommand("STV/-1");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    });  
    
    tvs_down.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				HttpSender.sendCommand("STV/-2");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    });  
    
  }

@Override
public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	// TODO Auto-generated method stub
	sound = progress;
	tv_s_text.setText("音量：" + sound);
}

@Override
public void onStartTrackingTouch(SeekBar seekBar) {
	// TODO Auto-generated method stub
	
}

@Override
public void onStopTrackingTouch(SeekBar seekBar) {
	// TODO Auto-generated method stub
	Toast.makeText(TelevisionActivity.this, "TV1/STV/" + String.valueOf(sound), Toast.LENGTH_SHORT).show();
	try {
		HttpSender.sendCommand(
				"TV1/STV/" + String.valueOf(sound)
				);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}