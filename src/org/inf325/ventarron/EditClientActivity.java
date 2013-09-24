package org.inf325.ventarron;

import org.inf325.ventarron.dao.Client;
import org.inf325.ventarron.dao.DbHelper;
import org.inf325.ventarron.services.ClientService;
import org.inf325.ventarron.services.ClientServiceImpl;
import static org.inf325.ventarron.utils.Constants.*;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class EditClientActivity extends OrmLiteBaseActivity<DbHelper>{
	private final String LOG_TAG = getClass().getSimpleName();
	private EditText txtName;
	private EditText txtEmail;
	private EditText txtPhone;
	private EditText txtAddress;
	private String mode;
	private int clientId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_client);
		
		loadViews();
		
		ClientService clientService = ClientServiceImpl.createService(getHelper());
		mode = getIntent().getStringExtra(EXTRA_MODE);
		if (mode.equals(EDIT_MODE)) {
			clientId = getIntent().getIntExtra(
					ClientListActivity.EXTRA_CLIENT, 0);
			
			Client client = clientService.getClient(clientId);
			setClientValues(client);
		}
	}

	private void setClientValues(Client client) {
		txtName.setText(client.getName());
		txtEmail.setText(client.getEmail());
		txtPhone.setText(client.getPhone());
		txtAddress.setText(client.getAddress());
	}

	private void loadViews() {
		txtName = (EditText) findViewById(R.id.editclient_txtName);
		txtEmail = (EditText) findViewById(R.id.editclient_txtEmail);
		txtPhone = (EditText) findViewById(R.id.editclient_txtPhone);
		txtAddress = (EditText) findViewById(R.id.editclient_txtAddress);
	}
	
	private boolean isValid() {
		String name = txtName.getText().toString().trim();
		String phone = txtPhone.getText().toString().trim();
		String email = txtEmail.getText().toString().trim();
		
		return (name.length() != 0 && phone.length() != 0 && email.length() != 0);
	}
	
	private Client viewToClient() {
		Client client = new Client();
		client.setName(txtName.getText().toString().trim());
		client.setEmail(txtEmail.getText().toString().trim());
		client.setPhone(txtPhone.getText().toString().trim());
		client.setAddress(txtAddress.getText().toString().trim());
		
		return client;
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
		
		ClientService service = ClientServiceImpl.createService(getHelper());
		Client client = viewToClient();
		
		int result = CHANGES_FAIL;
		try {
			if (mode.equals(EDIT_MODE)) {
				client.setId(clientId);
				service.update(client);
			} else {
				service.insert(client);
			}
			result = CHANGES_OK;
		} catch(Exception e) {
			Log.e(LOG_TAG, "There were problems with mode: " + mode, e);
		}
		
		changesDone(result);
	}
}
