package com.example.weather.adapter;

import java.util.List;

import com.example.weather.R;
import com.example.weather.utils.ViewHolder;
import com.exmple.weather.entity.Weather;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FutureWeatherAdapter extends BaseAdapter {
    
	
	
	 private Context context;
	 private LayoutInflater infalter;
	 private List<Weather> weatherList;
	 public FutureWeatherAdapter(Context context,List<Weather> weatherList) {
		// TODO Auto-generated constructor stub
		 this.context=context;
		 this.weatherList=weatherList;
		 infalter=LayoutInflater.from(context);
		 
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return weatherList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return weatherList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if(convertView==null){
			convertView=infalter.inflate(R.layout.weather_list_item, null);

		}
	    TextView date=ViewHolder.get(convertView,R.id.tv_date);
	    TextView weather=ViewHolder.get(convertView,R.id.tv_weather);
	    ImageView weather_pic=ViewHolder.get(convertView, R.id.iv_pic);
	  
	    date.setText(weatherList.get(position).getDate());
	    weather.setText(weatherList.get(position).getWeather());
	    weather_pic.setImageResource(weatherList.get(position).getWeather_pic());
		return convertView;
	}
	
	
	

}
