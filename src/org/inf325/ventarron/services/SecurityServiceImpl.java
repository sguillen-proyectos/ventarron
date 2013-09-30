package org.inf325.ventarron.services;

import java.util.ArrayList;
import java.util.List;

import org.inf325.ventarron.dao.DbHelper;
import org.inf325.ventarron.dao.UserRole;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

public class SecurityServiceImpl implements SecurityService {
	private RuntimeExceptionDao<UserRole, Integer> urDa;

	private SecurityServiceImpl(RuntimeExceptionDao<UserRole, Integer> urDa) {

		this.urDa = urDa;
	}

	public static SecurityServiceImpl createService(DbHelper helper) {
		return new SecurityServiceImpl(helper.getUserRoleDao());
	}

	public List<UserRole> getPermissions(int userId) {
		List<UserRole> result = new ArrayList<UserRole>();
		try {
			result = urDa.queryForEq("userId", userId);
		} catch (Exception e) {

		}
		return result;
	}
	
	public UserRole get(int userId, String roleId) {
		UserRole result = null;
		QueryBuilder<UserRole, Integer> builder = urDa.queryBuilder();
		Where<UserRole, Integer> where = builder.where();
		try {
			where.eq("userId", userId);
			where.and();
			where.eq("roleId", roleId);

			PreparedQuery<UserRole> query = builder.prepare();

			result = urDa.queryForFirst(query);
		} catch (Exception e) {

		}
		return result;
	}

	public void insert(UserRole userRole) {
		urDa.create(userRole);
	}

	public void update(UserRole userRole) {
		urDa.update(userRole);
	}

	public void delete(UserRole userRole) {
		urDa.delete(userRole);
	}

	public void assignPermission(int userId, String roleId, int permission) {
		UserRole userRole;
		userRole = get(userId, roleId);
		if (userRole == null) {
			userRole = new UserRole();
			userRole.setRoleId(roleId);
			userRole.setUserId(userId);
			userRole.setPermission(permission);

			insert(userRole);
		} else {
			userRole.setPermission(permission);
			update(userRole);
		}
	}
}
