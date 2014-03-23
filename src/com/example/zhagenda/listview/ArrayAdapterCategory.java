package com.example.zhagenda.listview;

import java.util.Timer;
import java.util.TimerTask;

import com.example.zhagenda.R;
import com.example.zhagenda.beans.Category;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ArrayAdapterCategory extends ArrayAdapter<Category>  {

	private int layoutView;
	private Category[] categories;
	private Context mContext;
	public int positionDisplayCurrent = 0;
	
	public ArrayAdapterCategory(Context context, int resource, Category[] categories) {
		super(context, resource, categories);
		this.layoutView = resource;
		this.categories = categories;
		this.mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
		{
			LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
			convertView = inflater.inflate(layoutView, parent, false);
		}
		if (position % 2 == 0)
			convertView.setBackgroundColor(Color.rgb(241, 224, 181));
		else
			convertView.setBackgroundColor(Color.rgb(233, 203, 131));
		Category category = categories[position];

		ImageView imgIcon = (ImageView)convertView.findViewById(R.id.categoryIcon);
		TextView tvName = (TextView)convertView.findViewById(R.id.categoryName);
		TextView tvSpotlight = (TextView)convertView.findViewById(R.id.categorySpotlight);
		tvSpotlight.setTranslationX(0);
		TextView tvSpotlightExtra = (TextView)convertView.findViewById(R.id.categorySpotlightExtra);
		tvSpotlightExtra.setText("");
		tvSpotlightExtra.setTranslationX(tvSpotlight.getWidth());

		imgIcon.setImageResource(category.resourceId);
		tvName.setText(category.name);
		tvSpotlight.setText(category.events.get(positionDisplayCurrent).title);
		
		return convertView;
	}
	

}
