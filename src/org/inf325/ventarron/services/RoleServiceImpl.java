package org.inf325.ventarron.services;

import java.util.List;

import org.inf325.ventarron.dao.DbHelper;
import org.inf325.ventarron.dao.Role;

import com.j256.ormlite.dao.RuntimeExceptionDao;

public class RoleServiceImpl implements RoleService {
	private RuntimeExceptionDao<Role, String> db;
	
	public static RoleServiceImpl createService(DbHelper helper) {
		return new RoleServiceImpl(helper.getRoleDao());
	}
	
	private RoleServiceImpl(RuntimeExceptionDao<Role, String> db) {
		this.db = db;
	}
	
	
	@Override
	public List<Role> getAll() {
		return db.queryForAll();
	}
}
