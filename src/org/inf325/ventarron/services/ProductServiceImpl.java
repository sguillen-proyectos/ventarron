package org.inf325.ventarron.services;

import java.util.List;

import org.inf325.ventarron.dao.DbHelper;
import org.inf325.ventarron.dao.Product;
import org.inf325.ventarron.dao.ProductDa;

public class ProductServiceImpl implements ProductService {
	private ProductDa da;
	
	public static ProductServiceImpl createService(DbHelper db) {
		return new ProductServiceImpl(new ProductDa(db.getProductDao())); 
	}
	
	private ProductServiceImpl(ProductDa da) {
		this.da = da;
	}
	
	@Override
	public List<Product> getProducts() {
		return da.getAll();
	}
	
	@Override
	public Product getProduct(int id) {
		return da.get(id);
	}

	@Override
	public void insert(Product product) {
		da.insert(product);
	}

	@Override
	public void update(Product product) {
		da.update(product);
	}

	@Override
	public void delete(Product product) {
		da.delete(product);
	}

	@Override
	public List<Product> filter(String keyword) {
		if (keyword.trim().length() == 0) {
			return getProducts();
		}
			
		return da.filter(keyword);
	}
}
