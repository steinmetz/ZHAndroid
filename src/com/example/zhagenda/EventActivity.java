package com.example.zhagenda;

import java.io.File;

import com.example.zhagenda.beans.Event;
import com.example.zhagenda.utils.DownloadUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EventActivity extends Activity {

	private TextView eventTxtTitle;

	private Event event;

	private LinearLayout facebookContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);

		Intent i = getIntent();
		if (i.hasExtra("event")) {
			event = (Event) i.getExtras().get("event");
		} else {
			Toast.makeText(getApplicationContext(), "Sem evento",
					Toast.LENGTH_SHORT).show();
		}

		eventTxtTitle = (TextView) findViewById(R.id.eventTxtTitle);

		facebookContainer = (LinearLayout) findViewById(R.id.facebookContainer);

		fillEvent();
	}

	public void fillEvent() {
		// if(event != null){
		if (true) {
			// eventTxtTitle.setText(event.title);

			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View inflated = inflater.inflate(R.layout.image_item, null);
			LinearLayout layout = (LinearLayout) inflated
					.findViewById(R.id.facebookLayout);
			final ImageView facebookImage = (ImageView) inflated
					.findViewById(R.id.facebookImage);
			TextView facebookName = (TextView) inflated
					.findViewById(R.id.facebookName);
			
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					DownloadUtils downloadUtils = new DownloadUtils();
					final Bitmap b = downloadUtils.downloadBitmap("http://blog.lucascaton.com.br/wp-content/uploads/2013/09/android.png");
					Log.i("SSS", "adsfasd");
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							facebookImage.setImageBitmap(b);
							
						}
					});
				}
			}).start();
			
			
			
			facebookContainer.addView(layout);

			for (int i = 0; i < 5; i++) {
				//facebookContainer.addView(layout);
			}

		}
	}

	private String getFilenameForKey(String key) {
		int firstHalfLength = key.length() / 2;
		String localFilename = String.valueOf(key.substring(0, firstHalfLength)
				.hashCode());
		localFilename += String.valueOf(key.substring(firstHalfLength)
				.hashCode());
		return localFilename;
	}

	public Bitmap getBitmap(String url) {

		final String volleyFileName = getFilenameForKey(url);
		try {
			if (getCacheDir().listFiles() != null)
				for (File file : getCacheDir().listFiles()) {
					if (file.getName().equals(volleyFileName))
						return BitmapFactory.decodeFile(file.getName());
				}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
