package com.example.weather.adapter;

import java.util.List;

import com.example.weather.R;
import com.example.weather.utils.CityName;
import com.example.weather.utils.ViewHolder;
import com.exmple.weather.entity.City;
import com.exmple.weather.entity.Weather;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import net.tsz.afinal.FinalDb;

public class DrawLayoutAdapter extends BaseAdapter {
    
	
	
	 private Context context;
	 private FinalDb db;
	 private LayoutInflater infalter;
	 private List<City> cityList;
	 public DrawLayoutAdapter(Context context,List<City> cityList) {
		// TODO Auto-generated constructor stub
		 this.context=context;
		 this.cityList=cityList;
		 infalter=LayoutInflater.from(context);
		 db=FinalDb.create(context);
		 
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cityList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return cityList.get(position);
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
			convertView=infalter.inflate(R.layout.drawlayout_list_item, null);

		}
	    TextView date=ViewHolder.get(convertView,R.id.tv_city);
	    ImageView pic_select=ViewHolder.get(convertView,R.id.iv_select);

	  
	    date.setText(cityList.get(position).getCityName());
	    if((cityList.get(position).getCityName()).equals(CityName.city))
	    pic_select.setImageResource(R.drawable.ic_city_select);
	    else {
	    	 pic_select.setImageResource(R.drawable.ic_hand);	
	    }
	 
		return convertView;
	}
	
	
	

}
