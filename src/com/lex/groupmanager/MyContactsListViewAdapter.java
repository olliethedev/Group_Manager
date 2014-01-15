package com.lex.groupmanager;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyContactsListViewAdapter extends ArrayAdapter<MyContact>
{
	public Context context;
	private List<MyContact> myObjects;
	public MyContactsListViewAdapter(Context context, int rowXmlId, List<MyContact> objects) 
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
	       MyContact rowItem = getItem(position);
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
	       if(rowItem.mainPicId==null)
	       {
	    	   holder.mainPicImageView.setImageResource(R.drawable.ic_launcher);
	    	   Log.i("picture", "default image");
	       }
	       else
	       {
	    	   holder.mainPicImageView.setImageURI(rowItem.mainPicId);
	    	   
	       }
	       holder.mainPicImageView.getLayoutParams().height=128;
	       holder.mainPicImageView.getLayoutParams().width=128;
	       holder.titleTextView.setText(rowItem.name);
	       String phone, email;
	       if((phone=rowItem.phone)==null)
	       {
	    	   phone="none";
	       }
	       if((email=rowItem.email)==null)
	       {
	    	   email="none";
	       }
	       holder.contentTextView.setText("Phone: "+phone+"\nEmail: "+email);
	       holder.buttonPicImageView.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
	       holder.buttonPicImageView.setTag(rowItem);
	       OnClickListener onDelClick = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					MyContact r =(MyContact)v.getTag();
					int pos =MyContactsListViewAdapter.this.getPosition(r);
					MyContactsListViewAdapter.this.remove(r);		
					if(r.contactId!=-1)
					{
						MyGroupsData groupData = new MyGroupsData(context);
						groupData.deleteContact(r.contactId);
						groupData.finish();
					}
				}
			};
	       holder.buttonPicImageView.setOnClickListener(onDelClick);
	       return convertView;
	 }
	 	@Override
	 	public void remove(MyContact o)
	 	{
	 		super.remove(o);
	 	}
}
