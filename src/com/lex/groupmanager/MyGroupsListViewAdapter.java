package com.lex.groupmanager;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyGroupsListViewAdapter extends ArrayAdapter<MyGroup>
{
	public Context context;
	private List<MyGroup> myObjects;
	public MyGroupsListViewAdapter(Context context, int rowXmlId, List<MyGroup> objects) 
	{
		super(context, rowXmlId, objects);
		this.context=context;
		this.myObjects=objects;
	}
	public class ViewHolder
	{
		ImageView mainPicImageView;
		TextView titleTextView;
		TextView contentTextView;
		ImageView buttonPicImageView;
	}
	 public View getView(int position, View convertView, ViewGroup parent) 
	 {
	       ViewHolder holder = null;
	       MyGroup rowItem = getItem(position);
	       LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	       if(convertView==null)
	       {
	    	   convertView = inflater.inflate(R.layout.row_for_contacts_listview, null);
	    	   holder=new ViewHolder();
	    	   holder.mainPicImageView=(ImageView)convertView.findViewById(R.id.mainPic);
	    	   holder.titleTextView=(TextView)convertView.findViewById(R.id.titleTextView);
	    	   holder.contentTextView=(TextView)convertView.findViewById(R.id.contentTextView);
	    	   holder.buttonPicImageView=(ImageView)convertView.findViewById(R.id.buttonPic);
	    	   convertView.setTag(holder);
	       }
	       else
	       {
	    	   holder=(ViewHolder)convertView.getTag();
	       }

	       holder.mainPicImageView.setImageResource(R.drawable.groupmanagergroup);
	       holder.mainPicImageView.getLayoutParams().height=128;
	       holder.mainPicImageView.getLayoutParams().width=128;
	       OnClickListener onTextClick = new OnClickListener() {
				@Override
				public void onClick(View v) 
				{
					MyGroup g =(MyGroup)v.getTag();
					int id =g.id;
					Intent i = new Intent (MyGroupsListViewAdapter.this.context,GroupActivity.class);
					i.putExtra(GroupActivity.GROUP_ID, id);
					context.startActivity(i);
				}
	       };
	       holder.titleTextView.setText(rowItem.name);
	       holder.titleTextView.setTag(rowItem);
	       holder.titleTextView.setOnClickListener(onTextClick);
	       String content="";
	       for(int i =0; i<rowItem.contacts.size();i++)
	       {
	    	content+=rowItem.contacts.get(i).name;  
	    	content+=rowItem.contacts.size()-1==i?".":", ";
	       }
	       holder.contentTextView.setText(content);
	       holder.contentTextView.setTag(rowItem);
	       holder.contentTextView.setOnClickListener(onTextClick);
	       holder.buttonPicImageView.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
	       holder.buttonPicImageView.setTag(rowItem);
	       OnClickListener onDelClick = new OnClickListener() 
	       {				
				@Override
				public void onClick(View v) 
				{
					MyGroup g =(MyGroup)v.getTag();
					MyGroupsListViewAdapter.this.remove(g);	
					MyGroupsData groupsData = new MyGroupsData(context);
					groupsData.deleteGroup(g.id);
					groupsData.finish();
				}
			};
	       holder.buttonPicImageView.setOnClickListener(onDelClick);
	       return convertView;
	 }
	 	@Override
	 	public void remove(MyGroup o)
	 	{
	 		super.remove(o);
	 	}
}
