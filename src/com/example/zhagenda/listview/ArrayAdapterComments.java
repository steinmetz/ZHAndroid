package com.example.zhagenda.listview;

import com.example.zhagenda.R;
import com.example.zhagenda.beans.Comments;

import android.app.Activity;
import android.content.Context;
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
		Comments comment = comments[position];

		TextView tvName = (TextView)convertView.findViewById(R.id.commentName);
		TextView tvComment = (TextView)convertView.findViewById(R.id.comment);

		//tvName.setText(comment.name);
		tvComment.setText(comment.comment);
		
		return convertView;
	}
	

}
