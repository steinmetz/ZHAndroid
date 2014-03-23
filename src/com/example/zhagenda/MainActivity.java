package com.example.zhagenda;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
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
import android.graphics.Typeface;
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

	RelativeLayout dataLayout;
	LinearLayout layoutEvent;
	ListView listViewEvents, listViewCategories;
	TextView currentCategoryTV, diaMes, diaSemana;
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
	Category[] categories = new Category[7];
	Integer currentCategory = null;
	ArrayAdapterCategory arrayAdapterCategories;
	
	public Calendar data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dataLayout = (RelativeLayout) findViewById(R.id.data);
		listViewCategories = (ListView) findViewById(R.id.listViewCategorias);
		listViewEvents = (ListView) findViewById(R.id.listViewEvents);
		layoutEvent = (LinearLayout) findViewById(R.id.layoutEvent);
		mapa = (RelativeLayout) findViewById(R.id.mapa);
		currentCategoryTV = (TextView) findViewById(R.id.currentCategory);
		diaMes = (TextView) findViewById(R.id.diaMes);
		diaSemana = (TextView) findViewById(R.id.diaSemanaMes);
		Typeface face = Typeface.createFromAsset(getAssets(),"fonts/msjh.ttf");
		diaMes.setTypeface(face);
		diaSemana.setTypeface(face);

		layoutEvent.setTranslationX(listViewCategories.getWidth());
		dataLayoutH = dataLayout.getLayoutParams().height;

		mapa.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				toolgleListView();
			}
		});



		map = (MapView) findViewById(R.id.map);
		mapController = map.getController();
		mapOverlays = map.getOverlays();
		map.setSatellite(false);
		mapController.setZoom(17);

		populateListView();

		myloc = new MyLocationOverlay(this, map);
		myloc.enableMyLocation();
		myloc.enableCompass();
		mapOverlays.add(myloc);
		map.postInvalidate();

		locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
		locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				milisegundos, metros, this);

	}
	public void proximoDia(View v){
		
	}
	public void diaAnterior(View v){
		
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
		categories[6] = teatro;
//		categories[7] = cinema;

		this.eventos = new ArrayList<Event>();
		
		this.eventos.add(new Event(2,new Date(2014, 03, 21),"Eliane Brum","19h","Livraria Cultura do Bourbon Country - Túlio de Rose, 80",-30.023695,-51.155904, "Autógrafos", "Autógrafos do livro A Menina Quebrada e Outras Colunas, de Eliane Brum. Entrada franca. ", R.drawable.elianebrum, 1, 1));
		this.eventos.add(new Event(3,new Date(2014, 03, 21),"Fabricio Carpinejar","16h","Livraria Saraiva do Praia de Belas - Praia de Belas, 1181",-30.050403,-51.228183, "Autógrafos", "Lançamento do livro 'Espero Alguém', de crônicas publicadas em ZH. Entrada franca.", R.drawable.carpinejar, 1, 1));
		this.eventos.add(new Event(4,new Date(2014, 03, 21),"Claudia Tajes","17h","Livraria FNAC do BarraShoppingSul - Diário de Notícias, 300",-30.084524,-51.245624, "Autógrafos", "Claudia Tajes lança seu novo livro: 'Por Isso sou Vingativa'. Entrada franca.", R.drawable.tajes, 1, 1));
		this.eventos.add(new Event(5,new Date(2014, 03, 21),"Cirque du Soleil","19h30min","BarraShoppingSul - Diário de Notícias, 300",-30.084524,-51.245624, "Exposições", "Corteo, espetáculo do Cirque du Soleil com palhaços, trapezistas e números de malabarismo. Ingressos: R$ 150.", R.drawable.cirque, 1, 1));
		this.eventos.add(new Event(6,new Date(2014, 03, 21),"Galinha Pintadinha","17h","Teatro do CIEE - Dom Pedro II, 861",-30.014075,-51.18728, "Infantil", "Espetáculo oficial da Galinha Pintadinha, com bonecos e fantoches, para crianças de zero a três anos. Ingressos: R$ 10 (crianças) e R$ 20 (adultos).", R.drawable.galinhapintadinha, 1, 1));
		this.eventos.add(new Event(7,new Date(2014, 03, 21),"Balé Vera Bublitz","20h30min","Teatro da Amrigs - Ipiranga, 5311",-30.056753,-51.187127, "Dança", "Apresentação de balé clássico da escola Vera Bublitz. Ingressos à venda na escola por R$ 30.", R.drawable.bublitz, 1, 1));
		this.eventos.add(new Event(8,new Date(2014, 03, 21),"Piquenique Festivo","Das 18h às 23h","Redenção",-30.035328,-51.213459, "Eventos", "Piquenique coletivo e gratuito para a comunidade da Capital. Leve sua comida e sua bebida. ", R.drawable.picnicccc, 1, 1));
		this.eventos.add(new Event(9,new Date(2014, 03, 21),"Claus e Vanessa","21h","NY 72 - Nova York, 72",-30.020689,-51.19537, "Música", "Show de pop rock com a dupla Claus e Vanessa. Ingressos: R$ 10.", R.drawable.elianebrum, 1, 1));
		this.eventos.add(new Event(10,new Date(2014, 03, 21),"Incêndios","20h","Theatro São Pedro - Marechal Deodoro, s/n",-30.028975,-51.230478, "Teatro", "Espetáculo conta a história da árabe Nawal, cuja vida é atravessada por décadas de uma guerra civil que parece nunca ter fim. Ingressos: R$ 40 (galeria). ", R.drawable.klausevanessa, 1, 1));
//		categories[0].events.add(new Event(new Date(2014, 03, 21),"Eliane Brum","19h","Endereco",-30.023695,-51.155904, "Autógrafos", "Desc", R.drawable.elianebrum, 1, 1));

		mapController.setCenter(this.eventos.get(0).getGeoPoint());
		popuparMapa(this.eventos);
		
		for (int i = 0; i < this.eventos.size(); i++) {
			Event e = this.eventos.get(i);
			if (e.category.equals("Autógrafos"))
			{
				autografos.events.add(e);
			}
			if (e.category.equals("Exposições"))
			{
				exposicao.events.add(e);
			}
			if (e.category.equals("Infantil"))
			{
				infantil.events.add(e);
			}
			if (e.category.equals("Dança"))
			{
				danca.events.add(e);
			}
			if (e.category.equals("Eventos"))
			{
				eventos.events.add(e);
			}
			if (e.category.equals("Teatro"))
			{
				teatro.events.add(e);
			}
			if (e.category.equals("Música"))
			{
				musica.events.add(e);
			}
		}

		arrayAdapterCategories = new ArrayAdapterCategory(this,
				R.layout.categorylistitem, categories);
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
		arrayAdapterCategories.positionDisplayCurrent = 0;
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

				arrayAdapterCategories.positionDisplayCurrent = arrayAdapterCategories.positionDisplayCurrent
						% categories[i].events.size();
				int index = arrayAdapterCategories.positionDisplayCurrent;

				TextView tvSpotlight = (TextView) v
						.findViewById(R.id.categorySpotlight);
				TextView tvSpotlightExtra = (TextView) v
						.findViewById(R.id.categorySpotlightExtra);

				if (tvSpotlight.getTranslationX() == 0) {
					tvSpotlightExtra.setTranslationX(size.x);
					tvSpotlightExtra
							.setText(categories[i].events.get(index).title);
					Event e = categories[i].events.get(index);
					tvSpotlightExtra.animate().setDuration(1000)
							.translationX(0);
					tvSpotlight.animate().setDuration(1000)
							.translationX(-size.x);
				} else {
					tvSpotlight.setTranslationX(size.x);
					tvSpotlight.setText(categories[i].events.get(index).title);
					tvSpotlight.animate().setDuration(1000).translationX(0);
					tvSpotlightExtra.animate().setDuration(1000)
							.translationX(-size.x);
				}

			}

			mHandler.postDelayed(r, 5000);
		}
	};
}
