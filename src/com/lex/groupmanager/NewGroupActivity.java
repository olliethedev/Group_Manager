package com.lex.groupmanager;


import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class NewGroupActivity extends Activity {

	private static final int CONTACT_PICKER =1;
	MyContactsListViewAdapter adapter;
	ArrayList<MyContact> contacts = new ArrayList<MyContact>();
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_group);
		prepButtons();
		prepList();
	}

	private void prepList() 
	{
		ListView contactList=(ListView)findViewById(R.id.contactList);
		adapter = new MyContactsListViewAdapter(this, R.layout.row_for_contacts_listview, contacts);
		contactList.setAdapter(adapter);
		updateList();
	}

	private void prepButtons() 
	{
		Button addContactBtn = (Button)findViewById(R.id.addContactBtn);
		addContactBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				onAddContactClick();
			}
		});
		Button saveGroupBtn = (Button)findViewById(R.id.saveGroupBtn);
		saveGroupBtn.setOnClickListener(new OnClickListener() 
		{			
			@Override
			public void onClick(View v) {
				onSaveGroupBtnClick();
				
			}
		});
	}
	private void onSaveGroupBtnClick() 
	{
		EditText groupNameText = (EditText)findViewById(R.id.groupNameText);
		String groupName = groupNameText.getText().toString();
		
		if(groupName.length()==0)
		{
			this.makeMsgBoxOk("Group Name Warning", "Please enter a group name.");
		}
		else
		{
			try
			{
				MyGroupsData groupsData = new MyGroupsData(this);
				groupsData.addGroup(new MyGroup(this.contacts,groupName));
				groupsData.finish();
				Intent i = new Intent (this, MainActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				this.startActivity(i);
			}
			catch (Exception ex)
			{
				this.makeMsgBoxOk("ERROR", "failed creating a new group");
				ex.printStackTrace();
			}
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if(resultCode==Activity.RESULT_OK)
	    {
		if(requestCode==CONTACT_PICKER)
		    {
			if (data != null) {
		        Uri uri = data.getData();
		        
		        if (uri != null) {
		            Cursor c = null;
		            try {
		                c = getContentResolver().query(uri, null, null, null, null);
                		String id = uri.getLastPathSegment();
  		                MyContactsHelper ch=new MyContactsHelper(this);
  		                addSelectedContact(ch.getContact(id));
		            }
		            finally {
		                if (c != null) {
		                    c.close();
		                }
		            }
		        }
		    }
	    }
	    }
	}
	private void addSelectedContact(MyContact mc) 
	{
		contacts.add(new MyContact(mc.mainPicId,mc.name,mc.phone,mc.email));
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
	private void onAddContactClick() 
	{
		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
		startActivityForResult(intent, CONTACT_PICKER); 			
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
		u.makeLog("NewGroup", msg);
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
