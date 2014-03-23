package com.example.zhagenda.beans;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.graphics.Bitmap;

import com.google.android.maps.GeoPoint;

public class Event implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int _id;
	public Date date;
	public String title;
	public String horario;
	public String address;
	public String telephone;
	public String category;
	public String description;
	public Bitmap photo;
	public int order;
	public double latitude;
	public double longitude;
	public int destaque;
	 
	public int imageRes;

	public Event(){
		super();
	}
 
	public GeoPoint getGeoPoint(){
		return new GeoPoint((int)(latitude*1E6),(int)(longitude*1E6)  );
	}
	
	public Event( int id,Date date, String title, String horario, String address,
			 double latitude, double longitude, String category, String description
			 , int imageRes,
			int order,
			int destaque) {
		super();
		this._id = id;
		this.date = date;
		this.title = title;
		this.horario = horario;
		this.address = address;
		this.category = category;
		this.description = description;
		this.order = order;
		this.latitude = latitude;
		this.longitude = longitude;
		this.destaque = destaque;
		this.imageRes = imageRes;
	}



	public String getDateDB() {
		if(date != null){
			return date.getYear()+"-"+(date.getMonth()+1)+"-"+date.getDay();
		}
		return "";
	}

	public void setDateFromBD(String date) {
		String[] spl = date.split("-");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		try {
			this.date = new java.sql.Date(format.parse(date).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		
	}

	
	
}
