package org.inf325.ventarron;

import org.inf325.ventarron.services.UserService;
import org.inf325.ventarron.services.UserServiceImpl;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class MainActivity extends Activity {
	private EditText txtUsername;
	private EditText txtPassword;
	private UserService userService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);
		
		// Carga la referencia a las instancias necesarias
		loadViews();
		
		userService = new UserServiceImpl();
	}

	private void loadViews() {
		this.txtUsername = (EditText) findViewById(R.id.main_txtUsername);
		this.txtPassword = (EditText) findViewById(R.id.main_txtPassword);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void login(View view) {
		String username = txtUsername.getText().toString().trim();
		String password = txtPassword.getText().toString().trim();
		
		if (userService.isValid(username, password)) {
			Intent productListIntent = new Intent(this, ProductListActivity.class);
			startActivity(productListIntent);
		} else {
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Usuario");
			alert.setMessage("Usuario invalido");
			alert.setPositiveButton("Ok", null);
			alert.show();
		}
	}
	
	public void clearFields(View view) {
		txtUsername.setText("");
		txtPassword.setText("");
	}
}
