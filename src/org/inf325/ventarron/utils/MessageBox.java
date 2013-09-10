package org.inf325.ventarron.utils;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public class MessageBox {
	public static final String OK_TEXT = "Si";
	public static final String NO_TEXT = "No";
	
	public static void confirm(Context context, String message, String title, OnClickListener okAction) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setMessage(message);
		alert.setTitle(title);
		alert.setPositiveButton(OK_TEXT, okAction);
		alert.setNegativeButton(NO_TEXT, null);
		
		alert.show();
	}
}
