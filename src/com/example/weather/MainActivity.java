package com.example.weather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.weather.adapter.DrawLayoutAdapter;
import com.example.weather.adapter.FutureWeatherAdapter;
import com.example.weather.utils.CityName;
import com.example.weather.utils.SystemBartint;
import com.example.weather.utils.WeatherDataManager;
import com.exmple.weather.entity.City;
import com.exmple.weather.entity.Weather;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cz.msebera.android.httpclient.Header;
import net.tsz.afinal.FinalDb;

public class MainActivity extends FragmentActivity implements OnClickListener, OnItemClickListener {

	private FutureWeatherAdapter fAdapter;
	private DrawLayoutAdapter dAdapter;
	private ListView wListView;
	private ListView drawlayoutListView;
	private TextView temperTextView;
	private TextView weatherTextView;
	private TextView dateTextView;
	private TextView todayTextView;
	private TextView futureTextView;
	private TextView pm25TextView;
	private TextView addCityTextView;
	private TextView temperRangeTextView;
	List<Weather> list = new ArrayList<Weather>();
	ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
	private FragmentPagerAdapter adapter;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private ViewPager pager;
	private ImageView weatherImageView;
	ArrayList<Weather> drawlayoutList = new ArrayList<Weather>();
	List<City> cityList = new ArrayList<City>();
	FinalDb db;
	private String url = "http://api.map.baidu.com/telematics/v3/weather?location=" + CityName.city
			+ "&output=json&ak=AC0dfc8464eb138eb4fcc720ad363609";
	private String result = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		// 影藏logo和标题
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(true);
		getActionBar().setTitle(CityName.city + "天气");
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_actionbar));
		SimpleDateFormat format = new SimpleDateFormat("HH");
		// 沉浸式状态栏
		SystemBartint.setSystemBarTint(MainActivity.this);
		String date = format.format(new Date());
		// Toast.makeText(getApplicationContext(), date, 1).show();
		int date1 = Integer.parseInt(date);
		// if (date1 > 18 || date1 < 6) {
		// findViewById(R.id.bg).setBackgroundResource(R.drawable.night);
		// tintManager.setStatusBarTintColor(Color.parseColor("#00A5DA"));
		// }
		initView();

		// mTitle = mDrawerTitle = getTitle();

		todayTextView.setOnClickListener(this);
		futureTextView.setOnClickListener(this);
		addCityTextView.setOnClickListener(this);
		pager.setOffscreenPageLimit(1);

		// 创建数据库对象
		db = FinalDb.create(this);
		cityList = db.findAll(City.class);

		// for (int i = 0; i < 5; i++) {
		// Weather weather = new Weather();
		// weather.setTemperature("1-2");
		// weather.setCity("sfvs");
		// drawlayoutList.add(weather);
		// }

		getWeatherInfo();

		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				setTab(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
				getResources().getDisplayMetrics());
		pager.setPageMargin(pageMargin);

		setSelect(0);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_menu, R.string.drawer_open,
				R.string.drawer_close) {

			/**
			 * Called when a drawer has settled in a completely closed state.
			 */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle(CityName.city + "天气");
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle("城市列表");
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		// 开启ActionBar上APP ICON的功能
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		dAdapter = new DrawLayoutAdapter(getApplicationContext(), cityList);
		drawlayoutListView.setAdapter(dAdapter);
		drawlayoutListView.setOnItemClickListener(this);

		WeatherDataManager s = new WeatherDataManager();
		SQLiteDatabase db = s.openDataBase(getApplicationContext());

	}

	private void initView() {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.fragment1, null);

		wListView = (ListView) view.findViewById(R.id.list1);
		drawlayoutListView = (ListView) findViewById(R.id.lv_left_menu);
		temperTextView = (TextView) findViewById(R.id.temp);
		weatherTextView = (TextView) findViewById(R.id.descri);
		addCityTextView = (TextView) findViewById(R.id.tv_addcity);
		// dateTextView = (TextView) findViewById(R.id.date);
		todayTextView = (TextView) findViewById(R.id.tv_today);
		futureTextView = (TextView) findViewById(R.id.tv_future);
		temperRangeTextView = (TextView) findViewById(R.id.temperature);
		pm25TextView = (TextView) findViewById(R.id.pm25);

		weatherImageView = (ImageView) findViewById(R.id.weather_pic);

		pager = (ViewPager) findViewById(R.id.view_pager);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		Fragment fragment1 = new WeatherIndexFragment();
		Fragment fragment2 = new FutureWeatherFragment();

		fragmentList.add(fragment2);
		fragmentList.add(fragment1);
		adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			public int getCount() {
				// TODO Auto-generated method stub
				return fragmentList.size();
			}

			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return fragmentList.get(arg0);
			}

			@Override
			public int getItemPosition(Object object) {
				// TODO Auto-generated method stub
				if (object instanceof WeatherIndexFragment) {
					((WeatherIndexFragment) object)
							.updateFragment("http://api.map.baidu.com/telematics/v3/weather?location=" + CityName.city
									+ "&output=json&ak=AC0dfc8464eb138eb4fcc720ad363609");
				} else if (object instanceof FutureWeatherFragment) {
					// ((Page1Fragment) object).updateContent(mContent);
					((FutureWeatherFragment) object)
							.updateFragment("http://api.map.baidu.com/telematics/v3/weather?location=" + CityName.city
									+ "&output=json&ak=AC0dfc8464eb138eb4fcc720ad363609");
				}
				return super.getItemPosition(object);
			}

		};
		// adapter=new MyPagerAdapter(getSupportFragmentManager(),fragmentList);
		pager.setAdapter(adapter);

	}

	private void setInfo() {
		// TODO Auto-generated method stub
		try {
			// tv_date.setText(info);
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.getInt("error") == 0) {
				// String date=jsonObject.getString("date");
				JSONArray result = jsonObject.getJSONArray("results");
				JSONObject index0 = result.getJSONObject(0);
				JSONArray result1 = index0.getJSONArray("weather_data");
				String pm25 = index0.getString("pm25");

				for (int i = 0; i < 4; i++) {
					Weather weather = new Weather();
					JSONObject todaywea = result1.getJSONObject(i);
					String date = todaywea.getString("date");
					String imageUrl = todaywea.getString("dayPictureUrl");

					String descri = todaywea.getString("weather");
					String wind = todaywea.getString("wind");
					String temperature = todaywea.getString("temperature");

					if (i == 0)
						weather.setDate(date.substring(0, 9) + "(今天)");
					else
						weather.setDate(date);
					weather.setWeather(temperature);
					if (imageUrl.contains("qing"))
						weather.setWeather_pic(R.drawable.ic_sun);
					else if (imageUrl.contains("yin"))
						weather.setWeather_pic(R.drawable.yin);
					else if (imageUrl.contains("yu"))
						weather.setWeather_pic(R.drawable.rain);
					else if (imageUrl.contains("duoyun"))
						weather.setWeather_pic(R.drawable.cloudy);
					else
						weather.setWeather_pic(R.drawable.snow);

					list.add(weather);

					if (i == 0) {
						// if(date.substring(13, 14).equals(":"))
						String date2 = date.substring(14);
						String date3 = date2.substring(0, date2.length() - 2);
						temperTextView.setText(date3 + "°");
						weatherTextView.setText(" " + descri);
						temperRangeTextView.setText(temperature);
						pm25TextView.setText("PM2.5" + " " + pm25);
						if (imageUrl.contains("qing"))
							weatherImageView.setImageResource(R.drawable.ic_sun);
						else if (imageUrl.contains("yin"))
							weatherImageView.setImageResource(R.drawable.ic_yin);
						else if (imageUrl.contains("yu"))
							weatherImageView.setImageResource(R.drawable.ic_rain);
						else if (imageUrl.contains("duoyun"))
							weatherImageView.setImageResource(R.drawable.ic_cloudy);
						else
							weatherImageView.setImageResource(R.drawable.ic_snow);

					}
				}
				// fAdapter = new ListAdapter(getApplicationContext(), list);
				// wListView.setAdapter(fAdapter);

				// //getBitMapFromUrl(imageUrl);

				// tv_date.setText(FragmentStatus.locationCity+" "+date);
				// tv_descri.setText("天气: "+descri);
				// tv_wind.setText("风力: "+wind);
				// tv_temper.setText("温度: "+temperature);
				//
				// 备份存储到数据库中，无网络时调用数据库数据
				// weather.setCity(FragmentStatus.locationCity);
				// weather.setDate(date);
				// weather.setWeather(descri);
				// weather.setWindy(wind);
				// weather.setTemperature(temperature);
				// db.save(weather);

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void getWeatherInfo() {

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				CityName.netStatus = false;
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				CityName.netStatus = false;
				result = new String(arg2);
				setInfo();

			}

		});
	}

	public void setSelect(int position) {
		pager.setCurrentItem(position);
		setTab(position);
	}

	public void setTab(int position) {
		resetColor();
		switch (position) {
		case 0:
			todayTextView.setTextColor(Color.parseColor("#1BAAFF"));
			break;
		case 1:
			futureTextView.setTextColor(Color.parseColor("#1BAAFF"));
			break;
		}
	}

	private void resetColor() {
		// TODO Auto-generated method stub
		todayTextView.setTextColor(Color.parseColor("#AAAAAA"));
		futureTextView.setTextColor(Color.parseColor("#AAAAAA"));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.tv_today:
			setSelect(0);
			break;
		case R.id.tv_future:
			setSelect(1);
			break;
		case R.id.tv_addcity:
			startActivity(new Intent(MainActivity.this, SearchCityActivity.class));
			finish();
			break;

		}

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// 需要将ActionDrawerToggle与DrawerLayout的状态同步
		// 将ActionBarDrawerToggle中的drawer图标，设置为ActionBar中的Home-Button的Icon
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		CityName.city = cityList.get(arg2).getCityName();
		url = "http://api.map.baidu.com/telematics/v3/weather?location=" + CityName.city
				+ "&output=json&ak=AC0dfc8464eb138eb4fcc720ad363609";

		// getActionBar().setTitle(CityName.cityList.get(arg2).getCityName());
	    dAdapter.notifyDataSetChanged();
		getWeatherInfo();
		adapter.notifyDataSetChanged();

		mDrawerLayout.closeDrawers();
	}

}
