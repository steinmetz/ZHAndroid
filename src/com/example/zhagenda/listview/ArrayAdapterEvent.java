package com.example.zhagenda.listview;

import com.example.zhagenda.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArrayAdapterEvent extends ArrayAdapter<Event> {

	private int layoutView;
	private Event[] events;
	private Context mContext;

	public ArrayAdapterEvent(Context context, int resource, Event[] events) {
		super(context, resource, events);
		this.layoutView = resource;
		this.events = events;
		this.mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
		{
			LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
			convertView = inflater.inflate(layoutView, parent, false);
		}
		Event event = events[position];

		TextView tvName = (TextView)convertView.findViewById(R.id.eventName);
		TextView tvEndereco = (TextView)convertView.findViewById(R.id.eventEndereco);

		tvName.setText(event.name);
		tvEndereco.setText(event.endereco);
		
		return convertView;
	}
	

}
