package org.inf325.ventarron;

import org.inf325.ventarron.dao.DbHelper;

import org.inf325.ventarron.dao.User;
import org.inf325.ventarron.dao.UserRole;
import org.inf325.ventarron.services.SecurityService;
import org.inf325.ventarron.services.SecurityServiceImpl;
import org.inf325.ventarron.services.UserService;
import org.inf325.ventarron.services.UserServiceImpl;
import static org.inf325.ventarron.utils.Constants.*;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Singleton that handles session management. For good practices it should be
 * injected (TO BE HACKED)
 * 
 * @author donkeysharp
 * 
 */
public class SessionManager {
	private static final String SESSION_STARTED = "SESSION_STARTED";
	private static final String SESSION_USER = "SESSION_USER";
	private static final String SESSION_USERID = "SESSION_ID";

	private static SessionManager manager = null;
	
	private SharedPreferences settings;
	private User currentUser;

	public static SessionManager getInstance(SharedPreferences settings) {
		if (manager == null) {
			manager = new SessionManager(settings);
		}
		return manager;
	}

	private SessionManager(SharedPreferences settings) {
		this.settings = settings;
		setDefaultData(false);
	}
	
	private void setDefaultData(boolean reset) {
		Editor edit = settings.edit();
		if (!settings.contains(SESSION_STARTED) || reset) {
			edit.putBoolean(SESSION_STARTED, false);
		}
		if (!settings.contains(SESSION_USER) || reset) {
			edit.putString(SESSION_USER, "");
		}
		if (!settings.contains(SESSION_USERID) || reset) {
			edit.putInt(SESSION_USERID, 0);
		}
		currentUser = null;
		edit.commit();
	}
	
	public boolean hasPermissionFor(String role, DbHelper helper) {
		SecurityService service = SecurityServiceImpl.createService(helper);
		User user = getSessionUser(helper);
		
		UserRole userRole = service.get(user.getId(), role);
		if (userRole != null) {
			if (userRole.getPermission() != 0) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasPermissionFor(int permission, String role, DbHelper helper) {
		SecurityService service = SecurityServiceImpl.createService(helper);
		User user = getSessionUser(helper);
		
		UserRole userRole = service.get(user.getId(), role);
		if (userRole != null) {
			if (permission == PERMISSION_CREATE) {
				return userRole.hasCreate();
			}
			if (permission == PERMISSION_READ) {
				return userRole.hasRead();
			}
			if (permission == PERMISSION_UPDATE) {
				return userRole.hasUpdate();
			}
			if (permission == PERMISSION_DELETE) {
				return userRole.hasDelete();
			}
		}
		return false;
	}
	
	public void startSession(User user) {
		this.currentUser = user;
		
		Editor edit = settings.edit();
		edit.putBoolean(SESSION_STARTED, true);
		edit.putString(SESSION_USER, user.getUsername());
		edit.putInt(SESSION_USERID, user.getId());
		
		edit.commit();
	}
	
	public boolean isValidSession() {
		return settings.getBoolean(SESSION_STARTED, false);
	}
		
	public String getSessionUsername() {
		return settings.getString(SESSION_USER, "");
	}
	
	public int getSessionUserId() {
		return settings.getInt(SESSION_USERID, 0);
	}
	
	public User getSessionUser(DbHelper dbHelper) {
		if (isValidSession()) {
			if (currentUser == null) {
				UserService userService = UserServiceImpl.createService(dbHelper);
				currentUser = userService.get(getSessionUsername());
			}
			return currentUser;
		}
		return null;
	}
	
	public void endSession() {
		setDefaultData(true);
	}
}
