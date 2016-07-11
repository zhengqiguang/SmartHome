package com.test.smarthome;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class AirconditonActivity extends Activity implements OnSeekBarChangeListener
{
  public TextView textView1,textView2,textView3,textView4,textView5,textView6;
  public RadioGroup myRadioGroup1,myRadioGroup2,myRadioGroup3;  
  public RadioButton myRadioButton1,myRadioButton2,myRadioButton3,myRadioButton4,myRadioButton5,myRadioButton6; ; 
  public SeekBar seekBar1; 
  TextView tmp_text = null;
  TextView tmp_text_t = null;
  static public int tmp=26;
  static public String mode = "c";
  static public int time = 0;
  
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.airconditon); 
    

 //   final Button tmp_up = (Button)findViewById(R.id.air_tmp_up);
 //   final Button tmp_down = (Button)findViewById(R.id.air_tmp_down);
    
    tmp_text = (TextView)findViewById(R.id.textView3);
    tmp_text_t = (TextView)findViewById(R.id.textView7);
    final ImageView mode_image = (ImageView)findViewById(R.id.imageView1);
    
    
    //空调开关
    final Switch ac1 = (Switch)this.findViewById(R.id.ac1_switch);
    ac1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {   
        @Override  
        public void onCheckedChanged(CompoundButton buttonView,  
                boolean isChecked) {  
            // TODO Auto-generated method stub  
            if (isChecked) { 
            	try {
    				HttpSender.sendCommand(
    						"AC0/SST/?temp=" + String.valueOf(tmp) + "&mode=" + mode + "&time=" + time
    						);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
            } else {  
            	try {
					HttpSender.sendCommand("AC0/SOF/");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//关闭ac1
            }  
        }  
    });  
    
  
    SeekBar ac1_t = (SeekBar)this.findViewById(R.id.ac1_seekBar);
    ac1_t.setOnSeekBarChangeListener((OnSeekBarChangeListener) this);
    
    
    
    
    
    
    //风向选择
    /*
    RadioGroup group_wind = (RadioGroup)this.findViewById(R.id.ac1_w);
    group_wind.setOnCheckedChangeListener(new OnCheckedChangeListener(){

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			try {
				switch(group.getCheckedRadioButtonId()){
				case R.id.ac1_w_up:
					HttpSender.sendCommand("");//之后加上风向指令：上、中、下、自动
				case R.id.ac1_w_middle:
					//HttpSender.sendCommand("TD/0");
				case R.id.ac1_w_down:
					//HttpSender.sendCommand("TD/0");
				case R.id.ac1_w_auto:
					//HttpSender.sendCommand("TD/0");
				}
			}catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}	
    });
    */
    
    final Button ac_td_button = (Button)findViewById(R.id.ac_td_button);
    ac_td_button.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(time){
			case 0:
			case 1:
			case 2:
			case 3:
				time++;
				break;
			case 4:
				time=0;
				break;
			}
			if(time==0)
				ac_td_button.setText("无");
			else
				ac_td_button.setText(time + "小时后关机");
			try {
				HttpSender.sendCommand(
						"AC0/SST/?temp=" + String.valueOf(tmp) + "&mode=" + mode + "&time=" + time
						);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ac1.setChecked(true);
			Toast.makeText(AirconditonActivity.this, "AC0/SST/?temp=" + String.valueOf(tmp) + "&mode=" + mode + "&time=" + time, Toast.LENGTH_SHORT).show();
		}
    	
    });
    
    /*
    
  //卧室空调定时AC2
    RadioGroup group3 = (RadioGroup)this.findViewById(R.id.ac2_td_group);
    group3.setOnCheckedChangeListener(new OnCheckedChangeListener(){
    	@Override
    	public void onCheckedChanged(RadioGroup arg0, int arg1){
    		switch(arg0.getCheckedRadioButtonId()){
    		case R.id.ac_td_cancel:
    			//取消定时
    			time=0;
    			try {
    				HttpSender.sendCommand(
    						"AC0/SST/?temp=" + String.valueOf(tmp) + "&mode=" + mode + "&time=" + time
    						);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			ac1.setChecked(true);
    			Toast.makeText(AirconditonActivity.this, "AC0/SST/?temp=" + String.valueOf(tmp) + "&mode=" + mode + "&time=" + time, Toast.LENGTH_SHORT).show();
    			break;
    		case R.id.ac2_td_1:
    			//定时1小时关
    			time=1;
    			try {
    				HttpSender.sendCommand(
    						"AC0/SST/?temp=" + String.valueOf(tmp) + "&mode=" + mode + "&time=" + time
    						);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			ac1.setChecked(true);
    			Toast.makeText(AirconditonActivity.this, "AC0/SST/?temp=" + String.valueOf(tmp) + "&mode=" + mode + "&time=" + time, Toast.LENGTH_SHORT).show();
    			break;
    		case R.id.ac2_td_2:
    			//定时2小时关
    			time=2;
    			try {
    				HttpSender.sendCommand(
    						"AC0/SST/?temp=" + String.valueOf(tmp) + "&mode=" + mode + "&time=" + time
    						);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			ac1.setChecked(true);
    			Toast.makeText(AirconditonActivity.this, "AC0/SST/?temp=" + String.valueOf(tmp) + "&mode=" + mode + "&time=" + time, Toast.LENGTH_SHORT).show();
    			break;
    		case R.id.ac2_td_3:
    			//定时3小时关
    			time=3;
    			try {
    				HttpSender.sendCommand(
    						"AC0/SST/?temp=" + String.valueOf(tmp) + "&mode=" + mode + "&time=" + time
    						);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			ac1.setChecked(true);
    			Toast.makeText(AirconditonActivity.this, "AC0/SST/?temp=" + String.valueOf(tmp) + "&mode=" + mode + "&time=" + time, Toast.LENGTH_SHORT).show();
    			break;
    		case R.id.ac2_td_4:
    			//定时4小时关
    			time=4;
    			try {
    				HttpSender.sendCommand(
    						"AC0/SST/?temp=" + String.valueOf(tmp) + "&mode=" + mode + "&time=" + time
    						);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			ac1.setChecked(true);
    			Toast.makeText(AirconditonActivity.this, "AC0/SST/?temp=" + String.valueOf(tmp) + "&mode=" + mode + "&time=" + time, Toast.LENGTH_SHORT).show();
    			break;
    		}
    	}
    });
    */
    RadioGroup modegroup = (RadioGroup)this.findViewById(R.id.ac_f_group);
    modegroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){

		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1) {
			// TODO Auto-generated method stub
			switch(arg0.getCheckedRadioButtonId()){
			case R.id.ac_f_auto:
				mode="a";
				mode_image.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_auto));
				break;
			case R.id.ac_f_cold:
				mode="c";
				mode_image.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_namecool));
				break;
			case R.id.ac_f_hot:
				mode_image.setImageDrawable(getResources().getDrawable(R.drawable.ic_stat_namehot));
				mode="h";
				break;
			}
			try {
				HttpSender.sendCommand(
						"AC0/SST/?temp=" + String.valueOf(tmp) + "&mode=" + mode + "&time=" + time
						);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ac1.setChecked(true);		
			Toast.makeText(AirconditonActivity.this, "AC0/SST/?temp=" + String.valueOf(tmp) + "&mode=" + mode + "&time=" + time, Toast.LENGTH_SHORT).show();
		}
    });
    
    
   //温度加
   /*
    tmp_up.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			try {
				HttpSender.sendCommand("TMP/UP");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    
    });
    */
    
    //温度减
    /*
    tmp_down.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				HttpSender.sendCommand("TMP/DOWN");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    
    });
    */
    
    
    
    
    
    
    
    
  }

@Override
public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	// TODO Auto-generated method stub
	tmp = progress + 17;
	tmp_text_t.setText("温度:" + tmp);
	
}

@Override
public void onStartTrackingTouch(SeekBar seekBar) {
	// TODO Auto-generated method stub
	
}

@Override
public void onStopTrackingTouch(SeekBar seekBar) {
	// TODO Auto-generated method stub
	tmp_text.setText(String.valueOf(tmp) + "℃");
	Toast.makeText(AirconditonActivity.this, "AC0/SST/?temp=" + String.valueOf(tmp) + "&mode=" + mode + "&time=" + time, Toast.LENGTH_SHORT).show();
	try {
		HttpSender.sendCommand(
				"AC0/SST/?temp=" + String.valueOf(tmp) + "&mode=" + mode + "&time=" + time
				);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}