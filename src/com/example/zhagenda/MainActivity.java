package com.example.zhagenda;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.zhagenda.beans.Category;
import com.example.zhagenda.beans.Event;
import com.example.zhagenda.listview.ArrayAdapterCategory;
import com.example.zhagenda.listview.ArrayAdapterEvent;
import com.example.zhagenda.overlay.MyOverlay;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends MapActivity implements LocationListener {

	LinearLayout dataLayout, layoutEvent;
	ListView listViewEvents, listViewCategories;
	TextView currentCategoryTV;
	RelativeLayout mapa;
	int dataLayoutH;

	private LocationManager locMgr;
	private int metros = 0;
	private int milisegundos = 1000;

	private ArrayList<Event> eventos;

	public MapController mapController;
	public List<Overlay> mapOverlays;

	private MapView map;

	MyLocationOverlay myloc;
	Category[] categories = new Category[8];
	Integer currentCategory = null;
	ArrayAdapterCategory arrayAdapterCategories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dataLayout = (LinearLayout) findViewById(R.id.data);
		listViewCategories = (ListView) findViewById(R.id.listViewCategorias);
		listViewEvents = (ListView) findViewById(R.id.listViewEvents);
		layoutEvent = (LinearLayout) findViewById(R.id.layoutEvent);
		mapa = (RelativeLayout) findViewById(R.id.mapa);
		currentCategoryTV = (TextView) findViewById(R.id.currentCategory);

		layoutEvent.setTranslationX(listViewCategories.getWidth());
		populateListView();
		dataLayoutH = dataLayout.getLayoutParams().height;

		mapa.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				toolgleListView();
			}
		});

		Intent i = getIntent();

		// apagar
		eventos = new ArrayList<Event>();
		eventos.add(new Event(null, "Titulo", "Endereço", "fone", "categoria",
				"descricao", null, 1,-29.717171,-53.717171,1,
				R.drawable.marker));

		map = (MapView) findViewById(R.id.map);
		mapController = map.getController();
		mapOverlays = map.getOverlays();
		map.setSatellite(false);
		mapController.setZoom(17);
		mapController.setCenter(eventos.get(0).getGeoPoint());

		myloc = new MyLocationOverlay(this, map);
		myloc.enableMyLocation();
		myloc.enableCompass();
		mapOverlays.add(myloc);
		map.postInvalidate();

		locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
		locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				milisegundos, metros, this);

		popuparMapa(eventos);
	}

	public void popuparMapa(ArrayList<Event> eventos) {

		if (eventos == null || eventos.size() < 1) {
			Toast.makeText(getApplicationContext(), "Sem eventos",
					Toast.LENGTH_SHORT).show();
			return;
		}
		for (int i = 0; i < eventos.size(); i++) {
			mapOverlays.add(new MyOverlay(eventos.get(i), this));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void populateListView() {

		Category autografos = new Category("Autógrafos", R.drawable.autografos);
		Category exposicao = new Category("Exposições", R.drawable.exposicoes);
		Category infantil = new Category("Infantil", R.drawable.infantil);
		Category danca = new Category("Dança", R.drawable.danca);
		Category eventos = new Category("Eventos", R.drawable.eventos);
		Category musica = new Category("Música", R.drawable.musica);
		Category cinema = new Category("Cinema", R.drawable.cinema);
		Category teatro = new Category("Teatro", R.drawable.teatro);

		categories[0] = autografos;
		categories[1] = exposicao;
		categories[2] = infantil;
		categories[3] = danca;
		categories[4] = eventos;
		categories[5] = musica;
		categories[6] = cinema;
		categories[7] = teatro;

		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 20; i++) {
				categories[j].events.add(new Event(null, "Titulo " + i,
						"Endereço", "fone", "categoria", "Descricao", null, 1,
						-29.717171,-53.717171,1, R.drawable.marker));
			}
		}

		arrayAdapterCategories = new ArrayAdapterCategory(
				this, R.layout.categorylistitem, categories);
		listViewCategories.setAdapter(arrayAdapterCategories);
		listViewCategories.setOnItemClickListener(listenerCategories);

		mHandler.postDelayed(r, 4000);
	}

	private OnItemClickListener listenerCategories = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			currentCategory = position;
			Event[] events = new Event[categories[currentCategory].events
					.size()];
			for (int i = 0; i < categories[currentCategory].events.size(); i++) {
				events[i] = categories[currentCategory].events.get(i);
			}
			ArrayAdapterEvent arrayAdapterEvents = new ArrayAdapterEvent(
					MainActivity.this, R.layout.eventlistitem, events);
			currentCategoryTV.setText(categories[currentCategory].name);
			listViewEvents.setAdapter(arrayAdapterEvents);
			listViewEvents.setOnItemClickListener(listenerEvents);
			listViewCategories.animate().translationX(
					-listViewCategories.getWidth());
			layoutEvent.setVisibility(View.VISIBLE);
			layoutEvent.setTranslationX(listViewCategories.getWidth());
			layoutEvent.animate().translationX(0);
			popuparMapa(categories[currentCategory].events);

		}
	};

	private OnItemClickListener listenerEvents = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent it = new Intent(MainActivity.this, EventActivity.class);
			it.putExtra("event",
					categories[currentCategory].events.get(position));
			startActivity(it);
		}
	};

	public void toolgleListView() {
		Point size = new Point();
		getWindowManager().getDefaultDisplay().getSize(size);
		ValueAnimator vaMapa, vaData;
		if (mapa.getLayoutParams().height != size.y) {
			vaMapa = ValueAnimator.ofInt(300, size.y);
			vaData = ValueAnimator.ofInt(dataLayoutH, 0);
		} else {
			vaMapa = ValueAnimator.ofInt(size.y, 300);
			vaData = ValueAnimator.ofInt(0, dataLayoutH);
		}
		vaMapa.setDuration(1000);
		vaData.setDuration(1000);

		vaMapa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				Integer value = (Integer) animation.getAnimatedValue();
				mapa.getLayoutParams().height = value;
				mapa.requestLayout();
			}
		});

		vaData.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				Integer value = (Integer) animation.getAnimatedValue();
				dataLayout.getLayoutParams().height = value;
				dataLayout.requestLayout();
			}
		});

		vaMapa.start();
		vaData.start();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLocationChanged(Location arg0) {
		mapController.animateTo(new GeoPoint((int) (arg0.getLatitude() * 1E6),
				(int) (arg0.getLongitude() * 1E6)));
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBackPressed() {
		if (currentCategory == null)
			super.onBackPressed();
		else {
			currentCategory = null;
			listViewCategories.animate().translationX(0);
			layoutEvent.animate().translationX(listViewCategories.getWidth());
		}
	}

	Handler mHandler = new Handler();
	Runnable r = new Runnable() {

		@Override
		public void run() {
			Point size = new Point();
			getWindowManager().getDefaultDisplay().getSize(size);
			arrayAdapterCategories.positionDisplayCurrent++;
			for (int i = 0; i < listViewCategories.getChildCount(); i++) {
				View v = listViewCategories.getChildAt(i);

				int index = arrayAdapterCategories.positionDisplayCurrent
						% categories[i].events.size();

				TextView tvSpotlight = (TextView) v
						.findViewById(R.id.categorySpotlight);
				TextView tvSpotlightExtra = (TextView) v
						.findViewById(R.id.categorySpotlightExtra);

				if (tvSpotlight.getTranslationX() == 0) {
					tvSpotlightExtra.setTranslationX(size.x);
					tvSpotlightExtra
							.setText(categories[i].events.get(index).title);
					Event e = categories[i].events.get(index);
					tvSpotlightExtra.animate().setDuration(1000).translationX(0);
					tvSpotlight.animate().setDuration(1000).translationX(-size.x);
				} else {
					tvSpotlight.setTranslationX(size.x);
					tvSpotlight.setText(categories[i].events.get(index).title);
					tvSpotlight.animate().setDuration(1000).translationX(0);
					tvSpotlightExtra.animate().setDuration(1000).translationX(
							-size.x);
				}

			}

			mHandler.postDelayed(r, 5000);
		}
	};
}
