package com.example.zhagenda;

import java.util.ArrayList;
import java.util.List;

import com.example.zhagenda.beans.Event;
import com.example.zhagenda.overlay.MyOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.content.Intent;
import android.os.Bundle; 
import android.widget.Toast;

public class ActivityMap extends MapActivity {

	private MapView map;
	
	private ArrayList<Event> eventos;
	
	public MapController mapController;
	public List<Overlay> mapOverlays;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa);
				
		Intent i = getIntent();
		
		if(i.hasExtra("evetos")){
			this.eventos = (ArrayList<Event>) i.getExtras().get("eventos");
		}else {
			Toast.makeText(getApplicationContext(), "Não há eventos para mostrar", Toast.LENGTH_SHORT).show();
		}		
		
		map = (MapView) findViewById(R.id.map);
		mapController = map.getController();
		mapOverlays = map.getOverlays();
		map.setSatellite(true);
		mapController.setZoom(17);
		map.setBuiltInZoomControls(true);
		//mapController.setCenter(eventos.get(0).geoPoint);
		
	}
	
	public void popuparMapa(){
		if(eventos == null || eventos.size()<1){
			Toast.makeText(getApplicationContext(), "Sem eventos", Toast.LENGTH_SHORT).show();
			return;			
		}
		
	}

	

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	} 

	
	
	
}
