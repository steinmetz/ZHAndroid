package com.example.zhagenda.beans;

import java.util.ArrayList;


public class Category {
	public String name = "Categorias";
	public int resourceId;
	public ArrayList<Event> events = new ArrayList<Event>();
	public Category(String name, int resourceId)
	{
		this.name = name;
		this.resourceId = resourceId;
	}
}
