package com.example.zhagenda.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
	
	public String sql;
 
	public DBHelper(Context context, String name, CursorFactory factory,
			int version, String sql) {
		super(context, name, factory, version);
		this.sql = sql;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		if(db != null){
			db.execSQL(sql);
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
