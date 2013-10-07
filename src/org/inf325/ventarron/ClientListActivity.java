package org.inf325.ventarron;

import java.util.List;

import org.inf325.ventarron.adapters.ClientAdapter;
import org.inf325.ventarron.dao.Client;
import org.inf325.ventarron.dao.Role;
import org.inf325.ventarron.services.ClientService;
import org.inf325.ventarron.services.ClientServiceImpl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

import static org.inf325.ventarron.utils.Constants.*;

public class ClientListActivity extends CrudActivity {
	private final int startSaleId = 0x1;
	private final String LOG_TAG = getClass().getSimpleName();
	public final static String EXTRA_CLIENT = "org.inf325.ventarron.EXTRA_CLIENT";
	private EditText txtKeyword;
	private ListView clientListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_list);

		// HACK: Find a way to force setting this field
		super.role = Role.CLIENTS_ROLE;
		
		loadViews();
		initializeData();
	}

	private void loadViews() {
		txtKeyword = (EditText) findViewById(R.id.clientlist_txtKeyword);
		clientListView = (ListView) findViewById(R.id.clientlist);
		clientListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
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

	public void searchClients(View view) {
		ClientService clientService = ClientServiceImpl.createService(getHelper());
		String keyword = txtKeyword.getText().toString();
		
		List<Client> result = clientService.filter(keyword);
		setListViewAdapter(result);
	}

	@Override
	protected void onCreate() {
		Intent intent = new Intent(this, EditClientActivity.class);
		intent.	putExtra(EXTRA_MODE, CREATE_MODE);

		startActivityForResult(intent, 1);	
	}

	@Override
	protected void onEdit(AdapterContextMenuInfo info) {
		Client client;
		ClientAdapter adapter = (ClientAdapter) clientListView.getAdapter();
		client = (Client) adapter.getItem(info.position);
		
		Intent intent = new Intent(this, EditClientActivity.class);
		intent.putExtra(EXTRA_MODE, EDIT_MODE);
		intent.putExtra(EXTRA_CLIENT, client.getId());

		startActivityForResult(intent, 1);
	}

	@Override
	protected void onDelete(AdapterContextMenuInfo info) {
		Client client;
		ClientService service = ClientServiceImpl.createService(getHelper());
		String message = getString(R.string.changes_failed);
		
		client = (Client) clientListView.getAdapter().getItem(info.position);
		
		try {
			service.delete(client);
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

	@Override
	protected void onCustomContextMenu(ContextMenu menu) {
		menu.add(1, this.startSaleId, 1, getString(R.string.start_sale));
	}

	@Override
	protected void onCustomContextItemSelected(AdapterContextMenuInfo info,
			int menuId) {
		if (menuId != startSaleId) {
			return;
		}
		Client client;
		client = (Client) clientListView.getAdapter().getItem(info.position);
		
		startSale(client);
	}

	private void startSale(Client client) {
		Intent intent = new Intent(this, SaleProductListActivity.class);
		startActivity(intent);
	}
}
