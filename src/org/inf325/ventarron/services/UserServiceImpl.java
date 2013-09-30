package org.inf325.ventarron.services;

import java.util.List;

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
	public List<User> getUsers() {
		return da.getAll();
	}
		
	@Override
	public User get(String username, String password) {
		String hashedPassword = HashUtil.calculateMD5(password);
		User user = da.get(username, hashedPassword);
		
		return user;
	}
	
	@Override
	public User get(String username) {
		User user = da.get(username);
		return user;
	}

	@Override
	public void insert(User user) {
		String password = user.getPassword();
		password = HashUtil.calculateMD5(password);
		user.setPassword(password);
		
		da.insert(user);
	}

	@Override
	public void update(User user) {
		String password = user.getPassword();
		password = HashUtil.calculateMD5(password);
		user.setPassword(password);
		
		da.update(user);
	}

	@Override
	public void delete(User user) {
		da.delete(user);
	}

	@Override
	public User get(int id) {
		return da.get(id);
	}
}
