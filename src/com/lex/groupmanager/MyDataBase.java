package com.lex.groupmanager;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBase extends SQLiteOpenHelper
{
	private Context context;
	private SQLiteDatabase db;
	private String name;
	private int ver;
	private String[] onCreate, onUpdate;
	public MyDataBase(Context context, String name, CursorFactory factory, int version, String[] createQuery, String[] updateQuery) 
	{
		super(context, name, factory, version);
		this.context=context;
		this.name=name;
		this.ver=version;
		this.onCreate=createQuery;
		this.onUpdate=updateQuery;
		db=this.getWritableDatabase();
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		for (int i =0; i<onCreate.length; i++)
		{
			db.execSQL(this.onCreate[i]);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		//db.execSQL(this.onUpdate);
		makeLog("db Update not implemented");
	}
	public void setTableRow(String tableName, ContentValues values)
	{
		db.insert(tableName, null, values);
	}
	public ArrayList<String> getColumnData(String tableName, String columnName)
	{
		ArrayList<String> data =  new ArrayList<String>();
		Cursor c = db.query(tableName, new String[]{columnName}, null, null, null, null, null);
		while(c.moveToNext())
		{
			int colId=c.getColumnIndex(columnName);
			data.add(c.getString(colId));//todo implement other data types logic
		}
		return data;
	}
	public int getRowCellInt(int rowNum,String table, String idColumnName)
	{
		Cursor c = db.query(table, new String[]{idColumnName}, null, null, null, null, null);
		int result=-1;
		if(c.moveToPosition(rowNum))
		{
			int colId=c.getColumnIndex(idColumnName);
			result=c.getInt(colId);
		}
		return result;
	}
	public String getRowCellString(int rowNum,String table, String idColumnName)
	{
		Cursor c = db.query(table, new String[]{idColumnName}, null, null, null, null, null);
		String result = null;
		if(c.moveToPosition(rowNum))
		{
			int colId=c.getColumnIndex(idColumnName);
			result=c.getString(colId);
		}
		return result;
	}
	public int getRowCount(String table)
	{
		Cursor c = db.query(table, null, null, null, null, null, null);
		int result=0;
		while(c.moveToNext())
		{
			result++;
		}
		return result;
	}
	public int getColumnCount(String table)
	{
		Cursor c = db.query(table, null, null, null, null, null, null);
		int result=0;
		result=c.getColumnCount();
		return result;
	}
	public boolean deleteRow(String table, String columnName, String searchable)
	{
		return db.delete(table, columnName + "=" + searchable, null)>0;
	}
	public boolean deleteRowComplex(String table, String[] columnNames, String[] searchablePairs) //note: not tested
	{
		String query="";
		for (int i = 0; i<columnNames.length;i++)
		{
			query+=columnNames+"="+searchablePairs;
			if(i!=columnNames.length-1)
			{
				query+=" AND ";
			}
		}
		return db.delete(table, query, null)>0;
	}
	private void makeLog(String msg)
	{
		MyUtils u = new MyUtils();
		u.makeLog("databas", msg);
	}
}
