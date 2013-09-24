package org.inf325.ventarron.dao;

import java.util.List;

import android.util.Log;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

public class UserDa {
	private RuntimeExceptionDao<User, Integer> db;
	
	public UserDa(RuntimeExceptionDao<User, Integer> db) {
		this.db = db;
	}
	
	public User get(String username, String password) {
		User user = null;
		QueryBuilder<User, Integer> queryBuilder = db.queryBuilder();
		Where<User, Integer> where = queryBuilder.where();
		
		try {
			where.eq("username", username);
			where.and();
			where.eq("password", password);
			
			PreparedQuery<User> preparedQuery = queryBuilder.prepare();
			user = db.queryForFirst(preparedQuery);
			
			System.out.println("DEBUG:>>>>> " + user);
			
		} catch(Exception e) {
			Log.e(getClass().getName(), "Error when filtering", e);
		}
		return user;
	}
	
	public User get(String username) {
		User user = null;
		List<User> result = db.queryForEq("username", username);
		
		if (result.size() > 0) {
			user = result.get(0);
		}
		return user;
	}
}
