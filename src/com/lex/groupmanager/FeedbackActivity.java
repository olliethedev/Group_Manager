package com.lex.groupmanager;


import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FeedbackActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		prepButtons();
		prepText();
	}
	private void prepButtons() 
	{
		((Button)findViewById(R.id.rateBtn)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				rateFromPlayStore();
			}
		});
		
	}
	private void prepText() 
	{
		TextView emailText = (TextView)findViewById(R.id.emailText);
		emailText.setText(Html.fromHtml("<a href=\"mailto:3martynov@gmail.com\">3martynov@gmail.com</a>"));
		emailText.setMovementMethod(LinkMovementMethod.getInstance());
	}
	private void rateFromPlayStore() 
	{
	    Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
	    Intent i = new Intent(Intent.ACTION_VIEW, uri);
	    try {
	        startActivity(i);
	    } catch (ActivityNotFoundException e) {
	    	makeToast("Could not launch Google Play!");
	    }
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
		u.makeLog("Settings", msg);
	}
	private void makeToast(String msg)
	{
		MyUtils u = new MyUtils();
		u.makeToast(this, msg);
	}

}
