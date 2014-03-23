package com.example.zhagenda.sqlite;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import com.example.zhagenda.beans.Comments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ComentarioRepositorio {

	private final String databasaName = "zhagenda.db";
	private final String table = "comment";

	private String SQL_CREATE = "CREATE TABLE " + table + " ("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ "event_id INTEGER," + "comment TEXT," + "photo blob,"
			+ "facebook_id INTEGER);";

	private Context ctx;

	DBHelper dbHelper;
	SQLiteDatabase db;

	public ComentarioRepositorio(Context ctx) {
		this.ctx = ctx;
		dbHelper = new DBHelper(ctx, databasaName, null, 1, SQL_CREATE);
		db = dbHelper.getWritableDatabase();
	}

	public long insertComments(Comments comments) {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		comments.photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();

		ContentValues cv = new ContentValues();
		cv.put("comment", comments.comment);
		cv.put("event_id", comments.event_id);
		cv.put("photo", byteArray);
		cv.put("facebook_id", comments.facebook_id);
		long id = db.insert(table, null, cv);
		db.close();
		return id;

	}

	public ArrayList<Comments> find(int evento_id) {

		ArrayList<Comments> comments = new ArrayList<Comments>();

		byte[] bitmapdata;

		Cursor c = db.query(table, null, "event_id = ?",
				new String[] { String.valueOf(evento_id) }, null, null, null,
				null);

		if (c.getCount() > 0) {
			c.moveToFirst();
			do {
				Comments com = new Comments();
				com._id = c.getInt(0);
				com.event_id = c.getInt(1);
				com.comment = c.getString(2);
				bitmapdata = c.getBlob(3);
				com.photo = BitmapFactory.decodeByteArray(bitmapdata, 0,
						bitmapdata.length);
				com.facebook_id = c.getInt(4);
				comments.add(com);
			} while (c.moveToNext());

		}

		return comments;

	}

}
