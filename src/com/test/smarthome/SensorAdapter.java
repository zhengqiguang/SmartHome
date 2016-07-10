package com.test.smarthome;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SensorAdapter extends ArrayAdapter<Sensor> {
	private int resourceId;
	public TextView sensor_data=null;
	public TextView sensor_name=null;
	public ImageView sensor_icon=null;
	public SensorAdapter(Context context, int resource, List<Sensor> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		resourceId = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		Sensor sensor = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		sensor_icon = (ImageView) view.findViewById(R.id.sensor_icon);
	//	TextView sensor_id=(TextView)view.findViewById(R.id.sensor_id);
		sensor_name=(TextView)view.findViewById(R.id.sensor_name);
		sensor_data=(TextView)view.findViewById(R.id.sensor_data);
		sensor_icon.setImageResource(sensor.get_icon_id());
		sensor_name.setText(sensor.get_name());
		sensor_data.setText(sensor.get_data());
		return view;
	}
}
