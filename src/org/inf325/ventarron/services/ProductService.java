package org.inf325.ventarron.services;

import java.util.List;

import org.inf325.ventarron.dao.Product;

public interface ProductService {
	public List<Product> getProducts();
	public List<Product> filter(String keyword);
	public Product getProduct(int id);
	public void insert(Product product);
	public void update(Product product);
	public void delete(Product product);
}
