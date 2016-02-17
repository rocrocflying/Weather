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
	//���ݿ�洢·��
	String filepath="data/data/com.example.weather/weather_city.db";
	String path="data/data/com.example.weather";
	//
	SQLiteDatabase data;
	public SQLiteDatabase openDataBase(Context context)  {
		File file=new File(filepath);
		//���ݿ����
		if(file.exists()){
			return SQLiteDatabase.openOrCreateDatabase(filepath, null);
		}else {
		//���������ݿ�
			File fpath=new File(path);
			if(fpath.mkdir()){
				Log.i("tag", "�ļ������ɹ�");
			}else {
				Log.i("tag", "�ļ�����ʧ��");
			}
			
			AssetManager asset=context.getAssets();//�õ���Դ
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
