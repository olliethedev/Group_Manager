package com.lex.groupmanager;

import java.util.ArrayList;

public class MyGroup
{
	public int id =-1;
	public ArrayList<MyContact> contacts;
	public String name;
	public MyGroup(ArrayList<MyContact> contacts,String name)
	{
		this.contacts=contacts;
		this.name=name;		
	}
}
