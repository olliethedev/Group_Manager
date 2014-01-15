package com.lex.groupmanager;


import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity  
{
	MyGroupsListViewAdapter adapter;
	ArrayList<MyGroup> groups = new ArrayList<MyGroup>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		prepList();
	}

	private void prepList() 
	{
			MyGroupsData groupsData = new MyGroupsData(this);
			groups =groupsData.getGroups();
			groupsData.finish();
			ListView groupList=(ListView)findViewById(R.id.groupsList);
			adapter = new MyGroupsListViewAdapter(this, R.layout.row_for_groups_listview, groups);
			groupList.setAdapter(adapter);
			updateList();
	}

	private void updateList() 
	{
		adapter.notifyDataSetChanged();		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if(item.getItemId()==R.id.action_settings)
		{
			Intent i = new Intent(this,FeedbackActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(i);
		}
		if(item.getItemId()==R.id.action_groups)
		{
			Intent i = new Intent(this,MainActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(i);
		}
		if(item.getItemId()==R.id.action_newgroup)
		{
			Intent i = new Intent(this,NewGroupActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(i);
		}
		
		return true;
	}
	private void makeLog(String msg)
	{
		MyUtils u = new MyUtils();
		u.makeLog("Groups", msg);
	}
	private void makeToast(String msg)
	{
		MyUtils u = new MyUtils();
		u.makeToast(this, msg);
	}
	private void makeMsgBoxOk(String title, String msg)
	{
		MyUtils u = new MyUtils();
		u.makeMsgBoxOk(this, title, msg);
	}
}
