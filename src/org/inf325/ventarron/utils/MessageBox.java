package org.inf325.ventarron.utils;

import org.inf325.ventarron.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class MessageBox {
	public static final String YES_TEXT = "Si";
	public static final String NO_TEXT = "No";
	public static final String OK_TEXT = "Ok";
	
	public static void alert(Context context, String message, String title) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setMessage(message);
		alert.setTitle(title);
		alert.setPositiveButton(OK_TEXT, null);
		
		alert.show();
	}

	public static void confirm(Context context, String message, String title,
			OnClickListener okAction) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setMessage(message);
		alert.setTitle(title);
		alert.setPositiveButton(YES_TEXT, okAction);
		alert.setNegativeButton(NO_TEXT, null);

		alert.show();
	}

	public static void numeric(Context context, int min, int max, String title,
			final NumericDialogListener listener) {

		final View view = initializeNumericView(context,
				R.layout.dialog_number_picker, min, max);
		
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle(title);
		alert.setView(view);
		alert.setPositiveButton(OK_TEXT, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditText txtNumber = (EditText) view
						.findViewById(R.id.dialog_number);

				int value = 0;
				try {
					value = Integer.parseInt(txtNumber.getText().toString());
				} catch(Exception e) { }
				listener.onResult(value);
			}
		});
		
		alert.show();
	}

	private static View initializeNumericView(Context context, int viewId, int min, int max) {
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View contentView = inflater.inflate(viewId, null);
		EditText txtNumber = (EditText) contentView
				.findViewById(R.id.dialog_number);

		txtNumber
				.setFilters(new InputFilter[] { new InputFilterMinMax(min, max) });

		return contentView;
	}
}
