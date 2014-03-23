package com.example.zhagenda.sqlite;

import java.util.ArrayList;

import com.example.zhagenda.beans.Event;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
		ContentValues cv = new ContentValues();
		
		
		return 0;
	}
	
	public ArrayList<Event> buscar(){
		ArrayList<Event> events = null;
		
		return events;		
	}
	
}
