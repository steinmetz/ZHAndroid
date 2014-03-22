package com.example.zhagenda.overlay;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Geocoder;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MyOverlay extends Overlay{

	Geocoder geoCoder = null;

    public MyOverlay() {
        super();
    }


    @Override
    public boolean onTap(GeoPoint geoPoint, MapView mapView){ 
        return super.onTap(geoPoint,mapView);
    }

    @Override
    public void draw(Canvas canvas, MapView mapV, boolean shadow){

        if(shadow){
            

            super.draw(canvas,mapV,shadow);
        }
    }
	
}
