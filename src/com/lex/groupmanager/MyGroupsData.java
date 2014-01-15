package com.lex.groupmanager;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

public class MyGroupsData 
{
	MyDataBase db;
	Context context;
	
	public MyGroupsData(Context c)
	{
		this.context=c;
		this.db=new MyDataBase(c, DataBaseInfo.DATABASE_NAME, null, DataBaseInfo.DATABASE_VER,new String[]{DataBaseInfo.CREATE_TABLE_GROUP,DataBaseInfo.CREATE_TABLE_CONTACT},new String[]{DataBaseInfo.UPDATE_TABLE_GROUP,DataBaseInfo.UPDATE_TABLE_CONTACT});
		this.db.getReadableDatabase();
	}
	public void addGroup(MyGroup group)
	{
		ContentValues groupData=new ContentValues();
		groupData.put(DataBaseInfo.COLUMN_GROUP_NAME,group.name);
		db.setTableRow(DataBaseInfo.TABLE_GROUP, groupData);
		int groupId=db.getRowCellInt(db.getRowCount(DataBaseInfo.TABLE_GROUP)-1, DataBaseInfo.TABLE_GROUP, DataBaseInfo.COLUMN_GROUP_ID);
		for(MyContact contact : group.contacts)
		{
			ContentValues contactsData = new ContentValues();
			contactsData.put(DataBaseInfo.COLUMN_CONTACT_NAME, contact.name);
			if(contact.phone!=null)
			{
				contactsData.put(DataBaseInfo.COLUMN_CONTACT_PHONE, contact.phone);
			}
			if(contact.email!=null)
			{
				contactsData.put(DataBaseInfo.COLUMN_CONTACT_EMAIL, contact.email);
			}
			if(contact.mainPicId!=null)
			{
				contactsData.put(DataBaseInfo.COLUMN_CONTACT_PICTURE_URI, contact.mainPicId.toString());
			}
			contactsData.put(DataBaseInfo.COLUMN_CONTACT_GROUP_ID_FK, groupId);
			db.setTableRow(DataBaseInfo.TABLE_CONTACT, contactsData);
		}
		
	}
	public void deleteGroup(int groupId)
	{
		db.deleteRow(DataBaseInfo.TABLE_CONTACT, DataBaseInfo.COLUMN_CONTACT_GROUP_ID_FK, ""+groupId);
		db.deleteRow(DataBaseInfo.TABLE_GROUP, DataBaseInfo.COLUMN_GROUP_ID, ""+groupId);
	}
	public ArrayList<MyGroup> getGroups()
	{
		int groupCount= db.getRowCount(DataBaseInfo.TABLE_GROUP);
		ArrayList<MyGroup> groups = new ArrayList<MyGroup>();
		for(int i = 0; i<groupCount; i++)
		{
			int groupId = db.getRowCellInt(i, DataBaseInfo.TABLE_GROUP, DataBaseInfo.COLUMN_GROUP_ID);
			ArrayList<MyContact> contacts = getContacts(groupId);
			MyGroup group= new MyGroup(contacts, db.getRowCellString(i, DataBaseInfo.TABLE_GROUP, DataBaseInfo.COLUMN_GROUP_NAME));
			group.id=groupId;
			groups.add(group);
		}
		return groups;
	}
	public void deleteContact(int contactId)
	{
		db.deleteRow(DataBaseInfo.TABLE_CONTACT, DataBaseInfo.COLUMN_CONTACT_ID, ""+contactId);
	}
	public ArrayList<MyContact> getContacts(int groupId)
	{
		int contactCount = db.getRowCount(DataBaseInfo.TABLE_CONTACT);
		ArrayList<MyContact> contacts = new ArrayList<MyContact>();
		for(int c=0; c<contactCount;c++)
		{
			int contactGroupId = db.getRowCellInt(c, DataBaseInfo.TABLE_CONTACT, DataBaseInfo.COLUMN_CONTACT_GROUP_ID_FK);
			if(contactGroupId==groupId)
			{
					String picUriStr = db.getRowCellString(c, DataBaseInfo.TABLE_CONTACT, DataBaseInfo.COLUMN_CONTACT_PICTURE_URI);
					Uri picUri =null;
					if(picUriStr!=null)
					{
						picUri=Uri.parse(picUriStr);
					}
					String phoneStr = db.getRowCellString(c, DataBaseInfo.TABLE_CONTACT, DataBaseInfo.COLUMN_CONTACT_PHONE);
					if(phoneStr==null)
					{
						phoneStr="none";
					}
					String emailStr = db.getRowCellString(c, DataBaseInfo.TABLE_CONTACT, DataBaseInfo.COLUMN_CONTACT_EMAIL);
					if(emailStr==null)
					{
						emailStr="none";
					}
					MyContact contact =new MyContact(picUri, db.getRowCellString(c, DataBaseInfo.TABLE_CONTACT, DataBaseInfo.COLUMN_CONTACT_NAME), phoneStr, emailStr);
					contact.groupId = groupId;
					contact.contactId =  db.getRowCellInt(c, DataBaseInfo.TABLE_CONTACT, DataBaseInfo.COLUMN_CONTACT_ID);
					contacts.add(contact);
			}
		}
		return contacts;
	}
	public void finish()
	{
		db.close();
	}
	private void makeLog(String msg)
	{
		MyUtils u = new MyUtils();
		u.makeLog("DataBase", msg);
	}

}
