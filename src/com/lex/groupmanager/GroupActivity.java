package com.lex.groupmanager;

import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class GroupActivity extends Activity 
{
	private ArrayList<MyContact> contacts = new ArrayList<MyContact>();
	private MyContactsListViewAdapter adapter;
	public static final String GROUP_ID ="id";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group);
		int groupId =this.getIntent().getIntExtra(GROUP_ID,-1);
		if(groupId!=-1)
		{
			MyGroupsData groupData = new MyGroupsData(this);			
			ArrayList<MyGroup> groups =groupData.getGroups();
			groupData.finish();
			for(MyGroup g : groups)
			{
				if(g.id==groupId)
				{
					this.setTitle("Group: "+g.name);
					contacts = g.contacts;
				}
			}
			prepList();
			prepButtons();
		}
	}

	private void prepButtons() 
	{
		Button emailBtn = (Button)findViewById(R.id.mailGroupBtn);
		emailBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				onEmailBtnClick();				
			}
		});
		Button textMsgBtn=(Button)findViewById(R.id.textGroupBtn);
		textMsgBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				onTextBtnClick();
			}
		});
		Button phoneBtn=(Button)findViewById(R.id.callGroupBtn);
		phoneBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onPhoneBtnCLick();
			}
		});
	}
	private void onEmailBtnClick() 
	{
		Intent i = new Intent (Intent.ACTION_SEND);
		i.setType("plain/text");
		String[] email = new String[contacts.size()];
		for(int c=0; c<contacts.size();c++)
		{
			if(contacts.get(c).email!=null&&!contacts.get(c).email.equals("none"))
			{
				email[c]=contacts.get(c).email;
			}
		}
		i.putExtra(Intent.EXTRA_EMAIL, email);
		try
		{
			this.startActivity(Intent.createChooser(i,"Send mail..."));
		}
		catch (ActivityNotFoundException e) 
		{
	    	makeToast("Could not launch email app!");
	    }
	}
	private void onTextBtnClick()
	{
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setType("vnd.android-dir/mms-sms");
		String phones="";
		for(int c=0; c<contacts.size();c++)
		{
			if(contacts.get(c).phone!=null&&!contacts.get(c).phone.equals("none"))
			{
			 phones+=contacts.get(c).phone;
			 if(c!=contacts.size()-1)
			 {
				 phones+=";";
			 }
			}
		}
		i.putExtra("address",phones);
		try
		{
			this.startActivity(Intent.createChooser(i,"Send SMS"));
		}
		catch (ActivityNotFoundException e) 
		{
	    	makeToast("Could not launch text messaging app!");
	    }
	}
	private void onPhoneBtnCLick()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Phones:");
		builder.setCancelable(true);
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
		for(int i =0; i<contacts.size(); i++)
		{
			arrayAdapter.add(contacts.get(i).name+":"+contacts.get(i).phone);
		}
		builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
					String phone = contacts.get(which).phone;
					if(phone==null||phone.equals("none"))
					{
						makeToast("Contact has no number!");
					}
					else
					{
						Intent i = new Intent(Intent.ACTION_CALL);
						i.setData(Uri.parse("tel:"+phone));
						try
						{
						startActivity(i);
						}
						catch (ActivityNotFoundException e) 
						{
					    	makeToast("Could not launch phone app!");
					    }
					}					
			}
		});

		
		builder.show();
	}
	private void prepList() 
	{
		ListView contactList=(ListView)findViewById(R.id.groupMembersList);
		adapter = new MyContactsListViewAdapter(this, R.layout.row_for_contacts_listview, contacts);
		contactList.setAdapter(adapter);
		updateList();
	}

	private void updateList() 
	{
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
