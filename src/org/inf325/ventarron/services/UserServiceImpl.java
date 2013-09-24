package org.inf325.ventarron.services;

import org.inf325.ventarron.dao.DbHelper;
import org.inf325.ventarron.dao.User;
import org.inf325.ventarron.dao.UserDa;
import org.inf325.ventarron.utils.HashUtil;

public class UserServiceImpl implements UserService {
	private UserDa da;
	
	public static UserServiceImpl createService(DbHelper dbHelper) {
		return new UserServiceImpl(new UserDa(dbHelper.getUserDao()));
	}
	
	public static UserServiceImpl createService(UserDa da) {
		return new UserServiceImpl(da);
	}
	
	private UserServiceImpl(UserDa da) {
		this.da = da;
	}
	
	@Override
	public boolean isValid(String username, String password) {
		String hashedPassword = HashUtil.calculateMD5(password);
		User user = da.get(username, hashedPassword);
		
		return user != null;
	}
	
	@Override
	public User get(String username) {
		User user = da.get(username);
		return user;
	}
}
