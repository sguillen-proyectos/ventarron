package org.inf325.ventarron.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

public class ProductDa {
	private RuntimeExceptionDao<Product, Integer> db;
	
	public ProductDa(RuntimeExceptionDao<Product, Integer> productDao) {
		this.db = productDao;
	}
	
	public List<Product> getAll() {
		List<Product> productList = db.queryForAll();
		return productList;
	}
	
	public Product get(int id) {
		return db.queryForId(id);
	}
	
	public void insert(Product product) {
		db.create(product);
	}
	
	public void update(Product product) {
		db.update(product);
	}
	
	public void delete(Product product) {
		db.delete(product);
	}
	
	public List<Product> filter(String keyword) {
		List<Product> result = new ArrayList<Product>();

		QueryBuilder<Product, Integer> query = db.queryBuilder();
		Where<Product, Integer> where = query.where();
		
		try {
			where.like("name", "%" + keyword + "%");
			where.or();
			where.like("code", "%" + keyword + "%");
			PreparedQuery<Product> preparedQuery = query.prepare();
			
			result = db.query(preparedQuery);
		} catch (SQLException e) {
			Log.e(getClass().getName(), "Error when filtering", e);
		}
		return result;
	}
}
