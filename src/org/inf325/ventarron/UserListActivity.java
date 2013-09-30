package org.inf325.ventarron;

import static org.inf325.ventarron.utils.Constants.CREATE_MODE;
import static org.inf325.ventarron.utils.Constants.EDIT_MODE;
import static org.inf325.ventarron.utils.Constants.EXTRA_MODE;

import java.util.List;

import org.inf325.ventarron.adapters.UserAdapter;
import org.inf325.ventarron.dao.Role;
import org.inf325.ventarron.dao.User;
import org.inf325.ventarron.services.UserService;
import org.inf325.ventarron.services.UserServiceImpl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
//import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class UserListActivity extends CrudActivity {
	private final String LOG_TAG = getClass().getSimpleName();
	public final static String EXTRA_USER = "org.inf325.ventarron.EXTRA_USER";
//	private EditText txtKeyword;
	private ListView userListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_list);

		super.role = Role.USERS_ROLE;

		loadViews();
		initializeData();
	}

	private void loadViews() {
//		txtKeyword = (EditText) findViewById(R.id.userlist_txtKeyword);
		userListView = (ListView) findViewById(R.id.userlist);
	}

	private void initializeData() {
		UserService service = UserServiceImpl.createService(getHelper());
		List<User> usersList = service.getUsers();
		setListViewAdapter(usersList);

		registerForContextMenu(userListView);
	}

	private void setListViewAdapter(List<User> userList) {
		UserAdapter userAdapter = new UserAdapter(this,
				R.layout.userlistview_item_row, userList);
		
		userListView.setAdapter(userAdapter);
	}
	
	public void searchUsers(View view) {
		// TODO: Add search feature here!!!
	}

	@Override
	protected void onCreate() {
		Intent intent = new Intent(this, EditUserActivity.class);
		intent.putExtra(EXTRA_MODE, CREATE_MODE);
		
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onEdit(AdapterContextMenuInfo info) {
		User user;
		user = (User) userListView.getAdapter().getItem(info.position);
		
		Intent intent = new Intent(this, EditUserActivity.class);
		intent.putExtra(EXTRA_MODE, EDIT_MODE);
		intent.putExtra(EXTRA_USER, user.getId());

		startActivityForResult(intent, 1);
	}

	@Override
	protected void onDelete(AdapterContextMenuInfo info) {
		User user;
		UserService service = UserServiceImpl.createService(getHelper());
		String message = getString(R.string.changes_failed);
		
		user = (User) userListView.getAdapter().getItem(info.position);
		
		try {
			service.delete(user);
			initializeData();
			message = getString(R.string.changes_ok);
		} catch(Exception e) {
			Log.e(LOG_TAG, "Cannot delete record", e);
		}
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onResult() {
		initializeData();
	}
}
