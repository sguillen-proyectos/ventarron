package org.inf325.ventarron.dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "role")
public class Role {
	public final static int COUNT_ROLES = 2;
	public final static String PRODUCTS_ROLE = "Productos";
	public final static String CLIENTS_ROLE = "Clientes";
	public final static String USERS_ROLE = "Usuarios";
	
	@DatabaseField(id = true)
	private String name;
	@DatabaseField
	private String className;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
