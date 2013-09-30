package org.inf325.ventarron.services;

import java.util.List;

import org.inf325.ventarron.dao.UserRole;

public interface SecurityService {
	public List<UserRole> getPermissions(int userId);
	public UserRole get(int userId, String roleId);
	public void insert(UserRole userRole);
	public void update(UserRole userRole);
	public void delete(UserRole userRole);
	public void assignPermission(int userId, String roleId, int permission);
}
