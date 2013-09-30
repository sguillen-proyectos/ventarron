package org.inf325.ventarron.dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user_role")
public class UserRole {
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(canBeNull = false)
	private int userId;
	@DatabaseField(canBeNull = false)
	private String roleId;
	/**
	 * It contains a bit mask that represent
	 * C-R-U-D permissions
	 * Example: 8 -> 1-0-0-0 only Create
	 * Example: 5 -> 0-1-0-1 delete and read 
	 */
	@DatabaseField(canBeNull = false)
	private int permission;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public int getPermission() {
		return permission;
	}
	public void setPermission(int permission) {
		this.permission = permission;
	}
	
	public boolean hasCreate() {
		return (permission & (1 << 3)) != 0;
	}
	
	public boolean hasRead() {
		return (permission & (1 << 2)) != 0;
	}
	
	public boolean hasUpdate() {
		return (permission & (1 << 1)) != 0;
	}
	
	public boolean hasDelete() {
		return (permission & (1)) != 0;
	}
	
	public static int parseToBitMask(boolean create, boolean read, boolean update,
			boolean delete) {
		int res = 0;
		if (create) {
			res |= (1 << 3);
		}
		if (read) {
			res |= (1 << 2);
		}
		if (update) {
			res |= (1 << 1);
		}
		if (delete) {
			res |= (1 << 0);
		}
		return res;
	}
}
