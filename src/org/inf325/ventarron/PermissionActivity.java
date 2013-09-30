package org.inf325.ventarron;

import java.util.List;

import org.inf325.ventarron.dao.DbHelper;
import org.inf325.ventarron.dao.Role;
import org.inf325.ventarron.dao.UserRole;
import org.inf325.ventarron.services.RoleService;
import org.inf325.ventarron.services.RoleServiceImpl;
import org.inf325.ventarron.services.SecurityService;
import org.inf325.ventarron.services.SecurityServiceImpl;

import static org.inf325.ventarron.utils.Constants.*;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

public class PermissionActivity extends OrmLiteBaseActivity<DbHelper> {
	private CheckBox chkCreate;
	private CheckBox chkRead;
	private CheckBox chkUpdate;
	private CheckBox chkDelete;
	private Spinner cmbGroups;
	private String roleId;
	private int userId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_permission);

		loadViews();
		initializeData();

		userId = getIntent().getIntExtra(EditUserActivity.EXTRA_USERID, 0);
	}

	private void loadViews() {
		chkCreate = (CheckBox) findViewById(R.id.permission_create);
		chkRead = (CheckBox) findViewById(R.id.permission_read);
		chkUpdate = (CheckBox) findViewById(R.id.permission_update);
		chkDelete = (CheckBox) findViewById(R.id.permission_delete);
		cmbGroups = (Spinner) findViewById(R.id.permission_groups);
	}

	private void initializeData() {
		RoleService service = RoleServiceImpl.createService(getHelper());
		List<Role> roles = service.getAll();
		ArrayAdapter<Role> rolesAdapter = new ArrayAdapter<Role>(this,
				android.R.layout.simple_spinner_item, roles);
		rolesAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cmbGroups.setAdapter(rolesAdapter);
		cmbGroups.setOnItemSelectedListener(new RolesItemSelectedListener());
	}

	private void changesDone(int resultCode) {
		Intent intent = new Intent();
		setResult(resultCode, intent);
		finish();
	}

	public void saveChanges(View view) {
		SecurityService securityService = SecurityServiceImpl
				.createService(getHelper());
		boolean hasCreate, hasRead, hasUpdate, hasDelete;
		int permission;

		hasCreate = chkCreate.isChecked();
		hasRead = chkRead.isChecked();
		hasUpdate = chkUpdate.isChecked();
		hasDelete = chkDelete.isChecked();

		permission = UserRole.parseToBitMask(hasCreate, hasRead, hasUpdate,
				hasDelete);

		securityService.assignPermission(userId, roleId, permission);
		changesDone(CHANGES_OK);
	}

	private class RolesItemSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {

			Role selectedRole = (Role) parent.getItemAtPosition(position);
			roleId = selectedRole.getName();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}
	}
}
