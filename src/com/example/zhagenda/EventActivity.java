package com.example.zhagenda;

import java.io.File;
import java.sql.Date;

import com.example.zhagenda.beans.Comments;
import com.example.zhagenda.beans.Event;
import com.example.zhagenda.utils.DownloadUtils;
import com.google.android.maps.GeoPoint;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventActivity extends Activity {

	private TextView eventTxtTitle;

	private Event event;
	private WebView eventTxtDescription;
	private LinearLayout listViewComments;

	private LinearLayout facebookContainer;

	private String textWeb = "<img src='https://pbs.twimg.com/profile_images/2409354752/4e4wbg5xpzyt1qxez665.jpeg' /><strong>Teste</strong> <i>123</i>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vitae enim felis. Fusce lobortis sem rhoncus scelerisque fermentum. Integer eget magna pulvinar, feugiat est sit amet, adipiscing ligula. Suspendisse sagittis auctor lectus, a gravida ipsum. Nulla non leo purus. In hac habitasse platea dictumst. Aliquam vel sapien eu tellus ullamcorper molestie. In aliquam, urna sodales tempor tempus, mauris felis gravida purus, at rhoncus risus ligula sit amet justo. Etiam molestie velit magna, vitae viverra tortor facilisis vitae. Nulla et lacinia sapien. Proin auctor fringilla feugiat. Duis non rutrum sapien. ";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);

		Intent it = getIntent();
		if (it.hasExtra("event")) {
			event = (Event) it.getExtras().get("event");
		} else {
			event = new Event(new Date(1), "Event Title",
					"Av. Ipiranga, nº 1000", "", "Exposição", textWeb, "", 1,
					new GeoPoint(0, 0), 1);
		}

		eventTxtTitle = (TextView) findViewById(R.id.eventTxtTitle);
		eventTxtDescription = (WebView) findViewById(R.id.eventTxtDescription);
		listViewComments = (LinearLayout) findViewById(R.id.listViewComments);
		facebookContainer = (LinearLayout) findViewById(R.id.facebookContainer);

		fillEvent();
	}

	public void fillEvent() {
		eventTxtTitle.setText(event.title);
		eventTxtDescription.loadData(event.description, "text/html", null);

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		Comments[] comments = new Comments[20];
		for (int i = 0; i < 20; i++) {
			comments[i] = new Comments();

			View commentView = inflater.inflate(R.layout.commentlistitem, null);
			TextView tvName = (TextView) commentView
					.findViewById(R.id.commentName);
			TextView tvComment = (TextView) commentView
					.findViewById(R.id.comment);

			tvName.setText(comments[i].name);
			tvComment.setText(comments[i].comment);
			listViewComments.addView(commentView);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				DownloadUtils downloadUtils = new DownloadUtils();
				final Bitmap b = downloadUtils
						.downloadBitmap("http://blog.lucascaton.com.br/wp-content/uploads/2013/09/android.png");
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

						for (int i = 0; i < 5; i++) {
							View inflated = inflater.inflate(
									R.layout.image_item, null);
							LinearLayout layout = (LinearLayout) inflated
									.findViewById(R.id.facebookLayout);
							final ImageView facebookImage = (ImageView) inflated
									.findViewById(R.id.facebookImage);
							facebookImage.setImageBitmap(b);
							facebookContainer.addView(layout);
						}

					}
				});
			}
		}).start();

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
