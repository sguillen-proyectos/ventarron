package org.inf325.ventarron;

import org.inf325.ventarron.dao.DbHelper;
import org.inf325.ventarron.dao.User;
import org.inf325.ventarron.services.UserService;
import org.inf325.ventarron.services.UserServiceImpl;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import static org.inf325.ventarron.utils.Constants.*;

public class MainActivity extends OrmLiteBaseActivity<DbHelper> {
	private EditText txtUsername;
	private EditText txtPassword;
	private UserService userService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		loadViews();
		userService = UserServiceImpl.createService(getHelper());
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
			SessionManager session = SessionManager
					.getInstance(getSharedPreferences(SETTINGS_FILE, 0));
			
			session.startSession(username);

			User user = session.getSessionUser(getHelper());

			Class<?> activityClass = RoleActivityMap.getActivityClass(user.getRole());
			Intent productListIntent = new Intent(this,activityClass);
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
