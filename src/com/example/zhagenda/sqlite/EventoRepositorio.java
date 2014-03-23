package com.example.zhagenda.sqlite;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
 
import com.example.zhagenda.beans.Event;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class EventoRepositorio {
	
	private final String databasaName = "zhagenda.db";
	private final String table = "event";

	private String SQL_CREATE = "CREATE TABLE " + table + " ("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ "date Date,"
			+ "titulo TEXT," +
			"endereco TEXT," +
			"telefone TEXT," +
			"categoria TEXT," +
			"destaque INTEGER," +
			"descricao TEXT," +
			"foto blob," +
			"latitude double," +
			"longitude double," +
			"ordem integer);";
	
	private Context ctx;

	DBHelper dbHelper;
	SQLiteDatabase db;

	
	public EventoRepositorio(Context ctx){
		this.ctx = ctx;
		dbHelper = new DBHelper(ctx, databasaName, null, 1, SQL_CREATE);
		db = dbHelper.getWritableDatabase();
	}

	
	public long insert(Event event){
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		event.photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		
		ContentValues cv = new ContentValues();
		cv.put("date", event.getDateDB());
		cv.put("titulo", event.title);
		cv.put("endereco", event.address);
		cv.put("telefone", event.telephone);
		cv.put("categoria", event.category);
		cv.put("destaque", event.destaque);
		cv.put("descricao", event.description);
		cv.put("foto", byteArray);
		cv.put("latitude", event.latitude);
		cv.put("longitude", event.longitude);
		cv.put("ordem", event.order);
		
		return db.insert(table, null, cv);
	}
	
	public ArrayList<Event> buscar(){
		ArrayList<Event> events = null;
		
		Cursor c = db.query(table, null, null, null, null, null, null, null); 
		
		if(c.getCount() > 0){
			
			byte[] bitmapdata ;
			
			c.moveToFirst();
			Event event = new Event();
			event._id = c.getInt(0);
			event.setDateFromBD(c.getString(1));
			event.title = c.getString(2);  		
			event.address = c.getString(3);
			event.telephone = c.getString(4);
			event.category = c.getString(5);
			event.destaque = c.getInt(6);
			event.description  = c.getString(7);
			
			bitmapdata = c.getBlob(8);
			event.photo = BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata.length);
			event.latitude = c.getDouble(9);
			event.longitude = c.getDouble(10);
			event.order = c.getInt(11);
			
			events.add(event);
			
		}
		
		return events;		
	}
	
}
