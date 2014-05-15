package com.example.zhagenda.beans;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

	public String  getDiaSemana()
	{

		Calendar data = new GregorianCalendar(2014, Calendar.MARCH, 23);
		String dias[] = {"Domingo","Segunda","Terça","Quarta","Quinta","Sexta","Sábado"};
		String meses[] = {"jan","fev","mar","abr","mai","jun","jul","ago","set", "out","nov","dez"};
		int week = data.get(Calendar.DAY_OF_WEEK) - 1;
		int month = data.get(Calendar.MONTH);
		return dias[week];
	}

	public String  getDiaMes()
	{
		Calendar data = new GregorianCalendar(2014, Calendar.MARCH, 23);
		String dias[] = {"Segunda","Terça","Quarta","Quinta","Sexta","Sábado","Domingo"};
		String meses[] = {"jan","fev","mar","abr","mai","jun","jul","ago","set", "out","nov","dez"};
		int week = data.get(Calendar.DAY_OF_WEEK);
		int month = data.get(Calendar.MONTH);
		return String.valueOf(23) + " - "+ meses[month];
	}

	
	
}
