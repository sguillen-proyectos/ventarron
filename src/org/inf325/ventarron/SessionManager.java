package org.inf325.ventarron;

import org.inf325.ventarron.dao.DbHelper;
import org.inf325.ventarron.dao.User;
import org.inf325.ventarron.services.UserService;
import org.inf325.ventarron.services.UserServiceImpl;
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

	private static SessionManager manager = null;
	
	private SharedPreferences settings;

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
		edit.commit();
	}

	public void startSession(String username) {
		Editor edit = settings.edit();
		edit.putBoolean(SESSION_STARTED, true);
		edit.putString(SESSION_USER, username);
		
		edit.commit();
	}
	
	public boolean isValidSession() {
		return settings.getBoolean(SESSION_STARTED, false);
	}
	
	public String getSessionUsername() {
		return settings.getString(SESSION_USER, "");
	}
	
	public User getSessionUser(DbHelper dbHelper) {
		UserService userService = UserServiceImpl.createService(dbHelper);
		
		return userService.get(getSessionUsername());
	}
	
	public void endSession() {
		setDefaultData(true);
	}
}
