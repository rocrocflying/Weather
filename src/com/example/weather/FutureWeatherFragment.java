package com.example.weather;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.weather.adapter.FutureWeatherAdapter;
import com.example.weather.adapter.WeatherIndexAdapter;
import com.example.weather.utils.CityName;
import com.exmple.weather.entity.Weather;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cz.msebera.android.httpclient.Header;

public class FutureWeatherFragment extends Fragment{
    
	
	 private FutureWeatherAdapter fAdapter;
	 private ListView wListView;
	 private TextView resultTextView;
	
	 private final String url="http://api.map.baidu.com/telematics/v3/weather?location="+CityName.city+"&output=json&ak=AC0dfc8464eb138eb4fcc720ad363609";
	 private String result="";
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment1, null);
		wListView=(ListView)view.findViewById(R.id.list1);
		resultTextView=(TextView)view.findViewById(R.id.tv_result);
		getWeatherInfo(url) ;
		return view;
	}
    
	private void setInfo() {
		// TODO Auto-generated method stub
		List<Weather> list=new ArrayList<Weather>();
		try {
	    	//tv_date.setText(info); 
			JSONObject jsonObject=new JSONObject(result);
			   if(jsonObject.getInt("error")==0) {
			    	//String date=jsonObject.getString("date");
			    	JSONArray result=jsonObject.getJSONArray("results");
			    	JSONObject index0=result.getJSONObject(0);
			    	JSONArray result1=index0.getJSONArray("weather_data");
			    	
			    	for(int i=0;i<4;i++) {
			    		Weather weather=new Weather();
			    		JSONObject todaywea=result1.getJSONObject(i);
				    	String date=todaywea.getString("date");
				    	String imageUrl=todaywea.getString("dayPictureUrl");
			    
			    	String descri=todaywea.getString("weather");
			    	String wind=todaywea.getString("wind");
			    	String temperature=todaywea.getString("temperature");
			    	if(i==0) weather.setDate(date.substring(0,9));
			    	else weather.setDate(date);
			    	weather.setWeather(temperature);
			    	if(imageUrl.contains("qing")) weather.setWeather_pic(R.drawable.sun3);
			    	else if(imageUrl.contains("yin")) weather.setWeather_pic(R.drawable.yin);
			    	else if(imageUrl.contains("yu")) weather.setWeather_pic(R.drawable.rain);
			    	else if(imageUrl.contains("duoyun")) weather.setWeather_pic(R.drawable.cloudy);
			    	else weather.setWeather_pic(R.drawable.snow);
			     
			    	list.add(weather);
			    	
//			    	if(i==0) {
//			    	temperTextView.setText(date.substring(14, 16));
//			    	weatherTextView.setText(descri);
//			    	}
			    	}
			    	fAdapter=new FutureWeatherAdapter(getActivity(),list);
					wListView.setAdapter(fAdapter);
					fAdapter.notifyDataSetChanged();
//			    	//getBitMapFromUrl(imageUrl);
			    	
//			    	tv_date.setText(FragmentStatus.locationCity+" "+date);
//			    	tv_descri.setText("����: "+descri);
//			    	tv_wind.setText("����: "+wind);
//			    	tv_temper.setText("�¶�: "+temperature);
//			    	
			    	//���ݴ洢�����ݿ��У�������ʱ�������ݿ�����
//			    	weather.setCity(FragmentStatus.locationCity);
//			    	weather.setDate(date);
//			    	weather.setWeather(descri);
//			    	weather.setWindy(wind);
//			    	weather.setTemperature(temperature);
//			    	db.save(weather);
			    	
			    	
		   }
				
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		
	}

	private void getWeatherInfo(String url) {
	
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler(){ 
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				resultTextView.setVisibility(View.VISIBLE);
				
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
			    result=new String(arg2);
			    setInfo();
		
			}

			
		});
	}
	
	public  void updateFragment(String url){
		getWeatherInfo(url) ;
		fAdapter.notifyDataSetChanged();
	}
	

}
