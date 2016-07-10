package com.test.smarthome;

public class Sensor {
	public int icon_id;
	public String name;
	public int type;
	public String data="0";
	
	public Sensor(int type){
		this.type=type;
		switch(this.type){
		case 0:
			icon_id=R.drawable.no_sensor;
			name="未插入传感器";
			break;
		case 1:
			icon_id=R.drawable.door;
			name="门状态";
			break;
		case 2:
			icon_id=R.drawable.people;
			name="人体";
			break;
		case 3:
			icon_id=R.drawable.light;
			name="灯光";
			break;
		case 4:
			icon_id=R.drawable.temperature;
			name="温湿度";
			break;
		case 5:
			icon_id=R.drawable.wave;
			name="水位";
			break;
		case 6:
			icon_id=R.drawable.drip;
			name="水滴";
			break;
		case 7:
			icon_id=R.drawable.airquality;
			name="空气质量";
			break;
		case 8:
			icon_id=R.drawable.umbrella;
			name="紫外线";
			break;	
		}
	}
	
	public Sensor(int type, String data){
		this(type);
		this.data=data;
	}
	
	public int get_icon_id(){
		return icon_id;
	}

	public int get_type(){
		return type;
	}
	public String get_name(){
		return name;
	}
	public String get_data(){
		return data;
	}
}
