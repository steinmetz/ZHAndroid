package com.example.zhagenda.overlay;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Geocoder;
import android.util.Log;

import android.graphics.RectF;
import com.example.zhagenda.EventActivity;
import com.example.zhagenda.beans.Event;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay; 

public class MyOverlay extends Overlay {

	Geocoder geoCoder = null;
	Bitmap bitmap;
	static Event event;
	Context ctx;
	Point point;
	RectF rectf;

	public MyOverlay(Event event, Context ctx) {
		super();
		this.event = event;
		this.ctx = ctx;
		point = new Point(0, 0);
	}

	
	
	@Override
	public boolean onTap(GeoPoint geoPoint, MapView mapView) {
		int tamanho_point = 30;
		point = mapView.getProjection().toPixels(event.geoPoint, null);
		rectf = new RectF(point.x - tamanho_point, point.y - tamanho_point,
				point.x + tamanho_point, point.y + tamanho_point);
		Point clique = mapView.getProjection().toPixels(geoPoint, null);
		if (rectf.contains(clique.x, clique.y)) {

			Intent i = new Intent(ctx, EventActivity.class);
			// i.putExtra("event", event);
			ctx.startActivity(i);
					
		}
		return super.onTap(geoPoint, mapView);
	}

	@Override
	public void draw(Canvas canvas, MapView mapview, boolean shadow) {
		if (shadow) {
			mapview.getProjection().toPixels(event.geoPoint, point);

			Bitmap markerBitmap = BitmapFactory.decodeResource(ctx
					.getApplicationContext().getResources(), event.imageRes);
			canvas.drawBitmap(markerBitmap, point.x,
					point.y - markerBitmap.getHeight(), null);

			super.draw(canvas, mapview, shadow);
		}
	}

}
