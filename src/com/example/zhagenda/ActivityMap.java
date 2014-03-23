package com.example.zhagenda;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.zhagenda.beans.Event;
import com.example.zhagenda.overlay.MyOverlay;
import com.facebook.android.Facebook;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

public class ActivityMap extends MapActivity {

	private MapView map;

	private ArrayList<Event> eventos;

	public MapController mapController;
	public List<Overlay> mapOverlays;
	MyLocationOverlay myloc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa);

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
		map.setBuiltInZoomControls(false);
		mapController.setCenter(eventos.get(0).geoPoint);
		myloc = new MyLocationOverlay(this, map);
		myloc.enableMyLocation();
		myloc.disableCompass();
		popuparMapa();
		

		
	} 	
	@Override
	protected void onResume() {
		 
		super.onResume();
		myloc.enableMyLocation();

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
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	

}
