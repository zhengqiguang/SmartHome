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
			name="δ���봫����";
			break;
		case 1:
			icon_id=R.drawable.door;
			name="��״̬";
			break;
		case 2:
			icon_id=R.drawable.people;
			name="����";
			break;
		case 3:
			icon_id=R.drawable.light;
			name="�ƹ�";
			break;
		case 4:
			icon_id=R.drawable.temperature;
			name="��ʪ��";
			break;
		case 5:
			icon_id=R.drawable.wave;
			name="ˮλ";
			break;
		case 6:
			icon_id=R.drawable.drip;
			name="ˮ��";
			break;
		case 7:
			icon_id=R.drawable.airquality;
			name="��������";
			break;
		case 8:
			icon_id=R.drawable.umbrella;
			name="������";
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
