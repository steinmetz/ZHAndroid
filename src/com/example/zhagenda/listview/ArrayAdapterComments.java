package com.example.zhagenda.listview;

import com.example.zhagenda.R;
import com.example.zhagenda.beans.Comments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArrayAdapterComments extends ArrayAdapter<Comments> {

	private int layoutView;
	private Comments[] comments;
	private Context mContext;

	public ArrayAdapterComments(Context context, int resource, Comments[] comments) {
		super(context, resource, comments);
		this.layoutView = resource;
		this.comments = comments;
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
		Comments comment = comments[position];

		TextView tvComment = (TextView)convertView.findViewById(R.id.comment);

		tvComment.setText(comment.comment);
		
		return convertView;
	}
	

}
