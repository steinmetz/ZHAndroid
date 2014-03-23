package com.example.zhagenda;

import java.util.ArrayList;
import java.util.List;

import com.example.zhagenda.beans.Event;
import com.example.zhagenda.listview.ArrayAdapterCategory;
import com.example.zhagenda.listview.ArrayAdapterEvent;
import com.example.zhagenda.listview.Category;
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
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends MapActivity implements LocationListener {

	LinearLayout dataLayout;
	ListView listView;
	RelativeLayout mapa;
	int dataLayoutH;

	private LocationManager locMgr;
	private int metros = 0;
	private int milisegundos = 2000;

	private ArrayList<Event> eventos;

	public MapController mapController;
	public List<Overlay> mapOverlays;

	private MapView map;

	MyLocationOverlay myloc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dataLayout = (LinearLayout) findViewById(R.id.data);
		listView = (ListView) findViewById(R.id.listViewEvents);
		mapa = (RelativeLayout) findViewById(R.id.mapa);
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
				"descricao", "asdf", 1, new GeoPoint(-29717171, -53717171),
				R.drawable.marker));

		if (i.hasExtra("evetos")) {
			this.eventos = (ArrayList<Event>) i.getExtras().get("eventos");
		} else {
			Toast.makeText(getApplicationContext(),
					"Não há eventos para mostrar", Toast.LENGTH_SHORT).show();
		}

		map = (MapView) findViewById(R.id.map);
		mapController = map.getController();
		mapOverlays = map.getOverlays();
		map.setSatellite(true);
		mapController.setZoom(17);
		mapController.setCenter(eventos.get(0).geoPoint);

		myloc = new MyLocationOverlay(this, map);
		myloc.enableMyLocation();
		myloc.enableCompass();
		mapOverlays.add(myloc);
		map.postInvalidate();

		locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
		locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				milisegundos, metros, this);

		popuparMapa();
	}

	public void popuparMapa() {

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
		Category[] categories = new Category[8];
		Category autografos = new Category("Autógrafos");
		Category exposicao = new Category("Exposições");
		Category infantil = new Category("Infantil");
		Category danca = new Category("Dança");
		Category eventos = new Category("Eventos");
		Category musica = new Category("Música");
		Category cinema = new Category("Cinema");
		Category teatro = new Category("Teatro");

		categories[0] = autografos;
		categories[1] = exposicao;
		categories[2] = infantil;
		categories[3] = danca;
		categories[4] = eventos;
		categories[5] = musica;
		categories[6] = cinema;
		categories[7] = teatro;

		for (int j = 0; j < 8; j++) {
			categories[j].events = new Event[20];
			for (int i = 0; i < 20; i++) {
				categories[j].events[i] = new Event(null, "Titulo", "Endereço",
						"fone", "categoria", "descricao", "asdf", 1,
						new GeoPoint(-29717171, -53717171), R.drawable.marker);
			}
		}

		ArrayAdapterCategory arrayAdapterCategories = new ArrayAdapterCategory(
				this, R.layout.categorylistitem, categories);
		// ArrayAdapterEvent arrayAdapterEvents = new ArrayAdapterEvent(this,
		// R.layout.eventlistitem, events);
		listView.setAdapter(arrayAdapterCategories);
		listView.setOnItemClickListener(l);
	}

	private OnItemClickListener l = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// Abrir o fragment da categoria
			Toast.makeText(MainActivity.this, "Abrir o fragment da categoria",
					Toast.LENGTH_LONG).show();

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
		mapController.animateTo(new GeoPoint((int) arg0.getLatitude(),
				(int) arg0.getLongitude()));
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

}
