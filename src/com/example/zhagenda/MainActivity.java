package com.example.zhagenda;

import com.example.zhagenda.listview.ArrayAdapterCategory;
import com.example.zhagenda.listview.ArrayAdapterEvent;
import com.example.zhagenda.listview.Category;
import com.example.zhagenda.listview.Event;

import android.os.Bundle;
import android.app.Activity; 
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	LinearLayout dataLayout;
	ListView listView;
	RelativeLayout mapa;
	int dataLayoutH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataLayout = (LinearLayout)findViewById(R.id.data);
        listView = (ListView)findViewById(R.id.listViewEvents);
        mapa = (RelativeLayout)findViewById(R.id.mapa);
        populateListView();
        dataLayoutH = dataLayout.getLayoutParams().height;
        mapa.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toolgleListView();
			}
		});
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void populateListView()
    {
    	Category[] categories= new Category[8];
    	Category autografos = new Category("Autógrafos");
    	Category exposicao = new Category("Exposições");
    	Category infantil = new Category("Infantil");
    	Category danca = new Category("Dança");
    	Category eventos = new Category("Eventos");
    	Category musica= new Category("Música");
    	Category cinema= new Category("Cinema");
    	Category teatro = new Category("Teatro");

    	categories[0] = autografos;
    	categories[1] = exposicao;
    	categories[2] = infantil;
    	categories[3] = danca;
    	categories[4] = eventos;
    	categories[5] = musica;
    	categories[6] = cinema;
    	categories[7] = teatro;

    	for(int j=0; j<8;j++)
    	{
    		categories[j].events = new Event[20];
	    	for(int i=0; i<20;i++)
	    	{
	    		categories[j].events[i] = new Event();
	    	}
    	}

    	ArrayAdapterCategory arrayAdapterCategories = new ArrayAdapterCategory(this, R.layout.categorylistitem, categories);
//    	ArrayAdapterEvent arrayAdapterEvents = new ArrayAdapterEvent(this, R.layout.eventlistitem, events);
    	listView.setAdapter(arrayAdapterCategories);
    	listView.setOnItemClickListener(l);
    }
    
	private OnItemClickListener l = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			//Abrir o fragment da categoria
			Toast.makeText(MainActivity.this, "Abrir o fragment da categoria", Toast.LENGTH_LONG).show();
			
		}
	};
	
	public void toolgleListView()
	{
		Point size = new Point();
		getWindowManager().getDefaultDisplay().getSize(size);
		ValueAnimator vaMapa, vaData;
		if (mapa.getLayoutParams().height != size.y)
		{
			vaMapa = ValueAnimator.ofInt(200,size.y);
			vaData = ValueAnimator.ofInt(dataLayoutH,0);
		}
		else
		{
			vaMapa = ValueAnimator.ofInt(size.y,200);
			vaData = ValueAnimator.ofInt(0,dataLayoutH);
		}
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
    
}
