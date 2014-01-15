package com.lex.groupmanager;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class MyContactsHelper 
{
	private Context c;
	public MyContactsHelper(Context context)
	{
		this.c=context;
	}
	public String getContactPhone(String id)
	{
		String phone=null;
		Cursor pCur = c.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?", new String[] { id }, null);
		if (pCur.moveToFirst()) 
		{
            phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

        }
		return phone;
	}
	public String getContactEmail(String id)
	{
		String email =null;
		Cursor eCurs = c.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,ContactsContract.CommonDataKinds.Email.CONTACT_ID + " =?", new String[] { id }, null);
		if(eCurs.moveToFirst())
		{
			email=eCurs.getString(eCurs.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
		}
		return email;
	}
	public String getContactName(String id)
	{
		String name=null;
		Cursor pCur = c.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?", new String[] { id }, null);
		if (pCur.moveToFirst()) 
		{
            name = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        }
		return name;
		
	}
	public Uri getContactPic(String id)
	{
	    try 
	    {
	        Cursor cur = c.getContentResolver().query(ContactsContract.Data.CONTENT_URI,null, ContactsContract.Data.CONTACT_ID + "=" + id + " AND "+ ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", null,null);
	        if (cur != null) 
	        {
	            if (!cur.moveToFirst()) 
	            {
	                return null; 
	            }
	            else
	            {
	            	 byte[] photoBlob = cur.getBlob(cur.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO));
	            	 if(photoBlob==null)
	            	 {
	            		 return null;
	            	 }
	            }
	        } else {
	            return null; 
	        }
	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	        return null;
	    }
	    Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(id));
	    return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
	}
	public MyContact getContact(String id)
	{
		return new MyContact(getContactPic(id),getContactName(id),getContactPhone(id),getContactEmail(id));
	}

}
