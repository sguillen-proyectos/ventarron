package org.inf325.ventarron.dao;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

public class ClientDa {
	private RuntimeExceptionDao<Client, Integer> db;
	
	public ClientDa(RuntimeExceptionDao<Client, Integer> db) {
		this.db = db;
	}
	
	public List<Client> getAll() {
		List<Client> clientList = db.queryForAll();
		return clientList;
	}
	
	public Client get(int id) {
		return db.queryForId(id);
	}
	
	public void insert(Client client) {
		db.create(client);
	}
	
	public void update(Client client) {
		db.update(client);
	}
	
	public void delete(Client client) {
		db.delete(client);
	}
	
	public List<Client> filter(String keyword) {
		List<Client> result = new ArrayList<Client>();
		
		QueryBuilder<Client,Integer> builder = db.queryBuilder();
		Where<Client, Integer> where = builder.where();
		
		try {
			where.like("name", "%" + keyword + "%");
			where.or();
			where.like("email", "%" + keyword + "%");
			where.or();
			where.like("phone", "%" + keyword + "%");
			PreparedQuery<Client> preparedQuery = builder.prepare();
			
			result = db.query(preparedQuery);
		} catch(Exception e) {
			Log.e(getClass().getName(), "Error when filtering", e);
		}
		return result;
	}
}
