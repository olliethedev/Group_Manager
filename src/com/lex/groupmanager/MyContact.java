package com.lex.groupmanager;


import android.net.Uri;

public class MyContact 
{
	public String name;
	public String phone;
	public String email;
	public Uri mainPicId=null;
	public int groupId=-1;
	public int contactId=-1;
	
	public MyContact(Uri pic,String name, String phone, String email)
	{
		this.name=name;
		this.phone=phone;
		this.email = email;
		this.mainPicId=pic;
	}
}
