package com.lex.groupmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

public class MyUtils 
{
	public void makeToast(Context c, String msg)
	{
		Toast.makeText(c,msg,Toast.LENGTH_LONG).show();
	}
	public void makeLog(String t, String msg)
	{
		Log.w(t, msg);
	}
	public void makeMsgBoxOk(Context context,String title, String msg)
	{
		AlertDialog.Builder b = new AlertDialog.Builder(context);
		b.setTitle(title);
		b.setMessage(msg);
		b.setPositiveButton("Ok", new DialogInterface.OnClickListener() 
		{	
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.cancel();				
			}
		});
		b.create().show();
	}
}
