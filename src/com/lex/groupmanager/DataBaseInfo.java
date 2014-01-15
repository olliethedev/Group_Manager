package com.lex.groupmanager;

public class DataBaseInfo 
{
	public final static String DATABASE_NAME="database";
	public final static int DATABASE_VER=1;
	
	public final static String TABLE_GROUP="group_table";
	public final static String COLUMN_GROUP_ID="groupId";
	public final static String COLUMN_GROUP_NAME="groupName";
	
	public final static String TABLE_CONTACT ="contact_table";
	public final static String COLUMN_CONTACT_ID="contactId";
	public final static String COLUMN_CONTACT_NAME="contactName";
	public final static String COLUMN_CONTACT_PHONE="contactPhone";
	public final static String COLUMN_CONTACT_EMAIL="contactEmail";
	public final static String COLUMN_CONTACT_GROUP_ID_FK="contactGroupId";
	public final static String COLUMN_CONTACT_PICTURE_URI="contactPictureUri";
	
	public final static String CREATE_TABLE_CONTACT = "create table "+TABLE_CONTACT+" ("+COLUMN_CONTACT_ID+" integer primary key autoincrement, "+COLUMN_CONTACT_NAME+" TEXT not null, "+COLUMN_CONTACT_PHONE+" TEXT, "+COLUMN_CONTACT_EMAIL+" TEXT, "+COLUMN_CONTACT_PICTURE_URI+" TEXT, "+COLUMN_CONTACT_GROUP_ID_FK+" integer not null)";
	public final static String UPDATE_TABLE_CONTACT = "DROP table if exists "+TABLE_CONTACT;
	
	public final static String CREATE_TABLE_GROUP="create table "+TABLE_GROUP+" ("+COLUMN_GROUP_ID+" integer primary key autoincrement, "+COLUMN_GROUP_NAME+" TEXT not null)";
	public final static String UPDATE_TABLE_GROUP = "DROP table if exists "+TABLE_GROUP;
}
