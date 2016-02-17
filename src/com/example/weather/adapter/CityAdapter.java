package com.example.weather.adapter;

import java.util.ArrayList;

import com.example.weather.R;
import com.example.weather.utils.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CityAdapter extends BaseAdapter {
	private LayoutInflater infater;
	private Context context;
	private ArrayList<String> list;

	public CityAdapter(Context context, ArrayList<String> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		infater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = infater.inflate(R.layout.searchcity_list_item, null);

		}
		TextView city = ViewHolder.get(convertView, R.id.tv_searchcity);

		city.setText(list.get(position));

		return convertView;

	}

}
