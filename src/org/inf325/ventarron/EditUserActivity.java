package org.inf325.ventarron;

import static org.inf325.ventarron.utils.Constants.CHANGES_FAIL;
import static org.inf325.ventarron.utils.Constants.CHANGES_OK;
import static org.inf325.ventarron.utils.Constants.EDIT_MODE;
import static org.inf325.ventarron.utils.Constants.EXTRA_MODE;

import org.inf325.ventarron.dao.DbHelper;
import org.inf325.ventarron.dao.User;
import org.inf325.ventarron.services.UserService;
import org.inf325.ventarron.services.UserServiceImpl;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditUserActivity extends OrmLiteBaseActivity<DbHelper> {
	public static final String EXTRA_USERID = "org.inf325.ventarron.EXTRA_USERID";
	private final String LOG_TAG = getClass().getSimpleName();
	private EditText txtName;
	private EditText txtUsername;
	private EditText txtPassword;
	private String mode;
	private int userId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_user);
		
		loadViews();
		
		UserService userService = UserServiceImpl
				.createService(getHelper());
		mode = getIntent().getStringExtra(EXTRA_MODE);
		if (mode.equals(EDIT_MODE)) {
			userId = getIntent().getIntExtra(UserListActivity.EXTRA_USER,
					0);

			User user = userService.get(userId);
			setUserValues(user);
		}
	}

	private void loadViews() {
		txtName = (EditText) findViewById(R.id.edituser_txtName);
		txtUsername = (EditText) findViewById(R.id.edituser_txtUsername);
		txtPassword = (EditText) findViewById(R.id.edituser_txtPassword);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_user, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.set_permission:
			navigateToPermission();
			return true;
		default:
			return super.onOptionsItemSelected(item);
	}
	}

	private void navigateToPermission() {
		Intent intent = new Intent(this, PermissionActivity.class);
		intent.putExtra(EXTRA_USERID, userId);
		
		startActivityForResult(intent, 1);
	}

	private void setUserValues(User user) {
		txtName.setText(user.getName());
		txtUsername.setText(user.getUsername());
	}
	
	private boolean isValid() {
		String username = txtUsername.getText().toString().trim();
		String name = txtName.getText().toString().trim();
		String password = txtPassword.getText().toString().trim();
		
		return username.length() != 0 && name.length() != 0 && password.length() != 0;
	}
	
	private User viewToUser() {
		User user = new User();
		user.setName(txtName.getText().toString().trim());
		user.setUsername(txtUsername.getText().toString().trim());
		user.setPassword(txtPassword.getText().toString().trim());
		
		return user;
	}
	
	private void changesDone(int resultCode) {
		Intent intent = new Intent();
		setResult(resultCode, intent);
		finish();
	}

	public void saveChanges(View view) {
		if (!isValid()) {
			String message = getString(R.string.invalid_information);
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
			return;
		}

		UserService service = UserServiceImpl.createService(getHelper());
		User user = viewToUser();

		int result = CHANGES_FAIL;
		try {
			if (mode.equals(EDIT_MODE)) {
				user.setId(userId);
				service.update(user);
			} else {
				service.insert(user);
			}
			result = CHANGES_OK;
		} catch (Exception e) {
			Log.e(LOG_TAG, "There were problems with mode: " + mode, e);
		}

		changesDone(result);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String message = getString(R.string.changes_failed);
		if (resultCode == CHANGES_OK) {
			message = getString(R.string.changes_ok);
		}
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
}
