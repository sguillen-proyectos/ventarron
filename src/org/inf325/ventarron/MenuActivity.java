package org.inf325.ventarron;

import org.inf325.ventarron.dao.DbHelper;
import org.inf325.ventarron.dao.Role;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import static org.inf325.ventarron.utils.Constants.*;

/**
 *
 * If this activity is reached, there is an active session 
 * @author donkeysharp
 *
 */
public class MenuActivity extends OrmLiteBaseActivity<DbHelper> {
	private Button btnUsers;
	private Button btnProducts;
	private Button btnClients;
	private TextView lblNoPermission;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		loadViews();
		setPermissions();
	}

	/**
	 * Get the permission for existing tables
	 * and hide the corresponding buttons if
	 * user doesn't have any permission on a
	 * certain table
	 */
	private void setPermissions() {
		SessionManager manager = SessionManager
				.getInstance(getSharedPreferences(SETTINGS_FILE, 0));
		
		// Checks how many roles the user has permission in
		boolean noPermission = true;
		if (!manager.hasPermissionFor(Role.USERS_ROLE, getHelper())) {
			hideButton(btnUsers);
		} else {
			noPermission = false;
		}
		if (!manager.hasPermissionFor(Role.PRODUCTS_ROLE, getHelper())) {
			hideButton(btnProducts);
		} else {
			noPermission = false;
		}
		if (!manager.hasPermissionFor(Role.CLIENTS_ROLE, getHelper())) {
			hideButton(btnClients);
		} else {
			noPermission = false;
		}
		
		if (noPermission) {
			lblNoPermission.setVisibility(View.VISIBLE);
		}
	}

	private void loadViews() {
		btnUsers = (Button) findViewById(R.id.menu_btnUsers);
		btnProducts = (Button) findViewById(R.id.menu_btnProducts);
		btnClients = (Button) findViewById(R.id.menu_btnClients);
		lblNoPermission = (TextView) findViewById(R.id.menu_lblNoOptions);
	}

	private void hideButton(Button button) {
		button.setVisibility(View.GONE);
	}
	
	public void navigateToUsers(View view) {
		Intent intent = new Intent(this, UserListActivity.class);
		startActivity(intent);
	}
	
	public void navigateToProducts(View view) {
		Intent intent = new Intent(this, ProductListActivity.class);
		startActivity(intent);
	}
	
	public void navigateToClients(View view) {
		Intent intent = new Intent(this, ClientListActivity.class);
		startActivity(intent);
	}
}
