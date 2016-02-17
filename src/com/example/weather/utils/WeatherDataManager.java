package com.example.weather.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class WeatherDataManager {
	//数据库存储路径
	String filepath="data/data/com.example.weather/weather_city.db";
	String path="data/data/com.example.weather";
	//
	SQLiteDatabase data;
	public SQLiteDatabase openDataBase(Context context)  {
		File file=new File(filepath);
		//数据库存在
		if(file.exists()){
			return SQLiteDatabase.openOrCreateDatabase(filepath, null);
		}else {
		//不存在数据库
			File fpath=new File(path);
			if(fpath.mkdir()){
				Log.i("tag", "文件创建成功");
			}else {
				Log.i("tag", "文件创建失败");
			}
			
			AssetManager asset=context.getAssets();//得到资源
			try {
				InputStream in=asset.open("weather_city.db");
				FileOutputStream out=new FileOutputStream(file);
				int count=0; 
				byte[] buffer=new byte[1024];
				while((count=in.read(buffer))>0) {
					out.write(buffer,0,count);
				}
				out.flush();
				out.close();
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}
		return openDataBase(context);
	}

}
