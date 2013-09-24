package org.inf325.ventarron;

import java.util.List;

import org.inf325.ventarron.adapters.ClientAdapter;
import org.inf325.ventarron.dao.Client;
import org.inf325.ventarron.dao.DbHelper;
import org.inf325.ventarron.services.ClientService;
import org.inf325.ventarron.services.ClientServiceImpl;
import org.inf325.ventarron.utils.MessageBox;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

import static org.inf325.ventarron.utils.Constants.*;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class ClientListActivity extends OrmLiteBaseActivity<DbHelper> {
	private final String LOG_TAG = getClass().getSimpleName();
	public final static String EXTRA_CLIENT = "org.inf325.ventarron.EXTRA_CLIENT";
	private EditText txtKeyword;
	private ListView clientListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_list);

		loadViews();
		initializeData();
	}

	private void loadViews() {
		txtKeyword = (EditText) findViewById(R.id.clientlist_txtKeyword);
		clientListView = (ListView) findViewById(R.id.clientlist);
	}

	private void initializeData() {
		ClientService clientService = ClientServiceImpl
				.createService(getHelper());
		List<Client> clientList = clientService.getClients();
		setListViewAdapter(clientList);
		
		// In order to launch float context menu when long pressing
		registerForContextMenu(clientListView);
	}

	private void setListViewAdapter(List<Client> clientList) {
		ClientAdapter adapter = new ClientAdapter(this,
				R.layout.clientlistview_item_row, clientList);
		
		clientListView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.client_list, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.clientList_newClient:
				Intent intent = new Intent(this, EditClientActivity.class);
				intent.putExtra(EXTRA_MODE, CREATE_MODE);

				startActivityForResult(intent, 1);				
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.clientlist_edit, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Client client;
		AdapterContextMenuInfo info;
		
		info = (AdapterContextMenuInfo) item.getMenuInfo();
		client = (Client) clientListView.getAdapter().getItem(info.position);

		switch (item.getItemId()) {
		case R.id.edit:
			editClient(client);
			return true;
		case R.id.delete:
			confirmDeleteClient(client);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	
	private void confirmDeleteClient(final Client client) {
		MessageBox.confirm(this, getString(R.string.question_delete_item),
				getString(R.string.alert), new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						deleteClient(client);
					}
				});
	}

	private void deleteClient(Client client) {
		ClientService service = ClientServiceImpl.createService(getHelper());
		String message = getString(R.string.changes_failed);
		try {
			service.delete(client);
			initializeData();
			message = getString(R.string.changes_ok);
		} catch(Exception e) {
			Log.e(LOG_TAG, "Cannot delete record", e);
		}
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	private void editClient(Client client) {
		Intent intent = new Intent(this, EditClientActivity.class);
		intent.putExtra(EXTRA_MODE, EDIT_MODE);
		intent.putExtra(EXTRA_CLIENT, client.getId());

		startActivityForResult(intent, 1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String message = getString(R.string.changes_failed);
		if (resultCode == CHANGES_OK) {
			initializeData();
			message = getString(R.string.changes_ok);
		}
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	public void searchClients(View view) {
		ClientService clientService = ClientServiceImpl.createService(getHelper());
		String keyword = txtKeyword.getText().toString();
		
		List<Client> result = clientService.filter(keyword);
		setListViewAdapter(result);
	}
}
