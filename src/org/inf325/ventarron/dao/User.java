package org.inf325.ventarron.dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user")
public class User {
	/**
	 * Role for seller user, it can CRUD clients
	 */
	public final static int ROLE_SELLER = 100;
	/**
	 * Role for depot user, it can CRUD products
	 */
	public final static int ROLE_DEPOT = 200;
	
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(canBeNull = false, unique = true)
	private String username;
	@DatabaseField(canBeNull = false)
	private String password;
	@DatabaseField(canBeNull = false)
	private int role;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
}
