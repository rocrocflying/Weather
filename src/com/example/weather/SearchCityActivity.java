package com.example.weather;

import java.util.ArrayList;
import java.util.List;

import com.example.weather.adapter.CityAdapter;
import com.example.weather.utils.CityName;
import com.example.weather.utils.SystemBartint;
import com.example.weather.utils.WeatherDataManager;
import com.exmple.weather.entity.City;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;

@SuppressLint("ShowToast")
public class SearchCityActivity extends FinalActivity implements TextWatcher,OnClickListener{
	private EditText searchEditText;
	private ListView searchListView;
	private ImageButton btnReturn;
	private FinalDb db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_searchcity);
		// 获取afinal数据库对象db
		db = FinalDb.create(this);
		// 沉浸式状态栏
		SystemBartint.setSystemBarTint(SearchCityActivity.this);
		initView();
		initListView();
		searchEditText.addTextChangedListener(this);

	}

	public class MyOnItemClickListner implements OnItemClickListener {
		ArrayList<String> list;

		public MyOnItemClickListner(ArrayList<String> list) {
			// TODO Auto-generated constructor stub
			this.list = list;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			boolean k = false;
			List<City> cityList=db.findAll(City.class);
			City city = new City();
			city.setCityName(list.get(arg2));
			Toast.makeText(SearchCityActivity.this, list.get(arg2), 1).show();
			//判断是否已经添加该城市数据
			for (int i = 0; i < cityList.size(); i++) {
				if (cityList.get(i).getCityName().equals(list.get(arg2)))
					k = true;
			}
			//若没添加，就写进数据
			if (!k) {
				CityName.cityList.add(city);
				CityName.city = list.get(arg2);
				db.save(city);
			}
			startActivity(new Intent(SearchCityActivity.this, MainActivity.class));
			finish();

		}

	}

	private void initListView() {
		// TODO Auto-generated method stub
		ArrayList<String> list = new ArrayList<String>();
		WeatherDataManager s = new WeatherDataManager();
		SQLiteDatabase db = s.openDataBase(getApplicationContext());
		// 查询数据库中热门城市数据表的数据
		Cursor cursor = db.rawQuery("select *from hotcity ", null);
		String name = null;
		while (cursor.moveToNext()) {
			name = cursor.getString(cursor.getColumnIndex("name"));
			list.add(name);
		}
		CityAdapter adapter = new CityAdapter(getApplicationContext(), list);
		searchListView.setAdapter(adapter);
		// 监听点击事件
		searchListView.setOnItemClickListener(new MyOnItemClickListner(list));
		btnReturn.setOnClickListener(this);

	}

	private void initView() {
		// TODO Auto-generated method stub
		searchEditText = (EditText) findViewById(R.id.et_search);
		searchListView = (ListView) findViewById(R.id.lv_search);
		btnReturn=(ImageButton)findViewById(R.id.btn_return);

	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		if (arg0.length() != 0) {
			ArrayList<String> list = new ArrayList<String>();
			WeatherDataManager s = new WeatherDataManager();
			SQLiteDatabase db = s.openDataBase(getApplicationContext());
			// 根据编辑框输入文字模糊匹配查询城市
			Cursor cursor = db.rawQuery("select distinct name from city where name like '%" + arg0.toString() + "%'",
					null);
			String name = null;
			while (cursor.moveToNext()) {
				// 获取下级县市区字段
				name = cursor.getString(cursor.getColumnIndex("name"));
				list.add(name);
			}
			CityAdapter adapter = new CityAdapter(getApplicationContext(), list);
			searchListView.setAdapter(adapter);
			Toast.makeText(SearchCityActivity.this, "df", 1).show();
			searchListView.setOnItemClickListener(new MyOnItemClickListner(list));

		} else {
			initListView();
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == event.KEYCODE_BACK) {
			startActivity(new Intent(SearchCityActivity.this, MainActivity.class));
			finish();
		}

		return super.onKeyDown(keyCode, event);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_return:
			startActivity(new Intent(SearchCityActivity.this, MainActivity.class));
			finish();
			break;

		default:
			break;
		}
		
	}

}
