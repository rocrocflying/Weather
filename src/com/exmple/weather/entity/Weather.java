package com.exmple.weather.entity;

public class Weather {
	public int  id;
	public String city;
	public String date;
	private int weather_pic;
	public String weather;
	public String windy;
	private String zs;
	private String zsDescription;
	public String temperature;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getWindy() {
		return windy;
	}
	public void setWindy(String windy) {
		this.windy = windy;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWeather_pic() {
		return weather_pic;
	}
	public void setWeather_pic(int weather_pic) {
		this.weather_pic = weather_pic;
	}
	public String getZs() {
		return zs;
	}
	public void setZs(String zs) {
		this.zs = zs;
	}
	public String getZsDescription() {
		return zsDescription;
	}
	public void setZsDescription(String zsDescription) {
		this.zsDescription = zsDescription;
	}

}
