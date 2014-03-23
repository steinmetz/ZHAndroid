package com.example.zhagenda.listview;

import com.example.zhagenda.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArrayAdapterCategory extends ArrayAdapter<Category>  {

	private int layoutView;
	private Category[] categories;
	private Context mContext;
	
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
		Category category = categories[position];

		TextView tvName = (TextView)convertView.findViewById(R.id.categoryName);
		TextView tvSpotlight = (TextView)convertView.findViewById(R.id.categorySpotlight);

		tvName.setText(category.name);
		tvSpotlight.setText(category.events[0].title);
		
		return convertView;
	}
	

}
