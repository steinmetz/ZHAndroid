package com.example.zhagenda;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;

import com.example.zhagenda.beans.Comments;
import com.example.zhagenda.beans.Event;
import com.example.zhagenda.sqlite.ComentarioRepositorio;
import com.example.zhagenda.utils.DownloadUtils;
import com.google.android.maps.GeoPoint;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EventActivity extends Activity {

	private TextView eventTxtTitle,categoryName;

	private Event event;
	private TextView eventDesc, eventHour, nroFriends, diaMes, diaSemanaMes1;
	private EditText commentText;
	private ImageView eventImage, sendComment;
	private LinearLayout listViewComments;
	private LinearLayout facebookContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_event);

		Intent it = getIntent();
		event = (Event) it.getExtras().get("event");

		eventTxtTitle = (TextView) findViewById(R.id.eventTxtTitle);
		categoryName = (TextView) findViewById(R.id.categoryName);
		eventDesc = (TextView) findViewById(R.id.eventDesc);
		eventHour = (TextView) findViewById(R.id.eventHour);
		eventImage = (ImageView) findViewById(R.id.eventImage);
		listViewComments = (LinearLayout) findViewById(R.id.listViewComments);
		facebookContainer = (LinearLayout) findViewById(R.id.facebookContainer);
		sendComment = (ImageView) findViewById(R.id.sendComment);
		commentText = (EditText) findViewById(R.id.commentText);
		diaMes = (TextView) findViewById(R.id.diaMes);
		diaSemanaMes1 = (TextView) findViewById(R.id.diaSemanaMes1);
		nroFriends = (TextView) findViewById(R.id.nroFriends);

		sendComment.setOnClickListener(l);

		fillEvent();
	}

	public void fillEvent() {
		eventTxtTitle.setText(event.title);
		eventDesc.setText(Html.fromHtml(event.description));
		diaMes.setText(event.getDiaMes());
		diaSemanaMes1.setText(event.getDiaSemana());
		eventHour.setText(Html.fromHtml(event.horario));
		eventImage.setImageResource(event.imageRes);
		categoryName.setText(event.category);
		
		loadComments();

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
						for (int i = 0; i < 0; i++) {
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
	
	public void loadComments()
	{
		listViewComments.removeAllViews();
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		ComentarioRepositorio rep = new ComentarioRepositorio(
				EventActivity.this);
		ArrayList<Comments> comments = rep.find(event._id);
		for (int i = 0; i < comments.size(); i++) {

			View commentView = inflater.inflate(R.layout.commentlistitem, null);
			TextView tvComment = (TextView) commentView
					.findViewById(R.id.comment);
			ImageView imageView = (ImageView) commentView
					.findViewById(R.id.commentIcon);
			imageView.setImageBitmap(comments.get(i).photo);

			tvComment.setText(comments.get(i).comment);
			listViewComments.addView(commentView);
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

	private OnClickListener l = new OnClickListener() {

		@Override
		public void onClick(View v) {
			ComentarioRepositorio rep = new ComentarioRepositorio(
					EventActivity.this);
			Comments comments = new Comments();
			comments.event_id = event._id;
			comments.comment = commentText.getText().toString();
			comments.photo = BitmapFactory.decodeResource(getResources(), R.drawable.euzinha);
			
			rep.insertComments(comments);

			loadComments();
		}
	};

}
