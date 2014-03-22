package com.example.zhagenda.beans;

import java.io.Serializable;
import java.sql.Date;

import com.google.android.maps.GeoPoint;

public class Event implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
	public Date date;
	public String title;
	public String address;
	public String telephone;
	public String category;
	public String description;
	public String photo;
	public int order;
	
	public GeoPoint geoPoint;
	
	public int imageRes;

	public Event(Date date, String title, String address, String telephone,
			String category, String description, String photo, int order,
			GeoPoint geoPoint, int imageRes) {
		super();
		this.date = date;
		this.title = title;
		this.address = address;
		this.telephone = telephone;
		this.category = category;
		this.description = description;
		this.photo = photo;
		this.order = order;
		this.geoPoint = geoPoint;
		this.imageRes = imageRes;
	}

	
	
	
	

}
