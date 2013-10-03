package org.inf325.ventarron;

import static org.inf325.ventarron.utils.Constants.*;

import org.inf325.ventarron.dao.DbHelper;
import org.inf325.ventarron.utils.MessageBox;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public abstract class CrudActivity extends OrmLiteBaseActivity<DbHelper> {
	protected String role;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.newElement:
				if (hasCreatePermission()) {
					onCreate();
				} else {
					Toast.makeText(this, getString(R.string.no_create), 
							Toast.LENGTH_SHORT).show();
				}
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.edit, menu);
		onCustomContextMenu(menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info;
		info = (AdapterContextMenuInfo) item.getMenuInfo();

		switch (item.getItemId()) {
		case R.id.edit:
			if (hasEditPermission()) { 
				onEdit(info);
			} else {
				Toast.makeText(this, getString(R.string.no_update), 
						Toast.LENGTH_SHORT).show();
			}
			return true;
		case R.id.delete:
			if (hasDeletePermission()) {
				confirmDelete(info);
			} else {
				Toast.makeText(this, getString(R.string.no_delete), 
						Toast.LENGTH_SHORT).show();
			}
			return true;
		default:
			onCustomContextItemSelected(info, item.getItemId());
			return true;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String message = getString(R.string.changes_failed);
		if (resultCode == CHANGES_OK) {
			onResult();
			message = getString(R.string.changes_ok);
		}
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	protected void confirmDelete(final AdapterContextMenuInfo item) {
		MessageBox.confirm(this, getString(R.string.question_delete_item),
				getString(R.string.alert), new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						onDelete(item);
					}
				});
	}
	
	private boolean hasPermission(int permission) {
		SessionManager manager = SessionManager
				.getInstance(getSharedPreferences(SETTINGS_FILE, 0));
		// Verify if the user can create for this role
		return manager.hasPermissionFor(permission, role, getHelper());
	}

	protected boolean hasCreatePermission() {
		return hasPermission(PERMISSION_CREATE);
	}
	
	protected boolean hasReadPermission() {
		return hasPermission(PERMISSION_READ);
	}
	
	protected boolean hasEditPermission() {
		return  hasPermission(PERMISSION_UPDATE);
	}
	
	protected boolean hasDeletePermission() {
		return hasPermission(PERMISSION_DELETE);
	}
	
	protected abstract void onCreate();
	
	protected abstract void onEdit(AdapterContextMenuInfo info);

	protected abstract void onDelete(AdapterContextMenuInfo info);
	
	protected abstract void onResult();
	
	protected abstract void onCustomContextMenu(ContextMenu menu);
	protected abstract void onCustomContextItemSelected(AdapterContextMenuInfo info, int menuId);
}
