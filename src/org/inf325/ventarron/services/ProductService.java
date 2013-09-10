package org.inf325.ventarron.services;

import java.util.ArrayList;
import java.util.List;

import org.inf325.ventarron.dao.Product;

public class ProductService {
	public List<Product> getProducts() {
		List<Product> productList = new ArrayList<Product>();
		Product a = new Product();
		a.setCode("111-aaa");
		a.setName("product a");
		a.setPrice(23.2);
		a.setQuantity(100);
		
		Product b = new Product();
		b.setCode("222-bbb");
		b.setName("product b");
		b.setPrice(23.2);
		b.setQuantity(100);
		
		Product c = new Product();
		c.setCode("333-ccc");
		c.setName("product c");
		c.setPrice(23.2);
		c.setQuantity(100);
		
		Product d = new Product();
		d.setCode("444-ddd");
		d.setName("product d");
		d.setPrice(23.2);
		d.setQuantity(100);
		
		Product e = new Product();
		e.setCode("555-eee");
		e.setName("product e");
		e.setPrice(23.2);
		e.setQuantity(100);
		
		Product f = new Product();
		f.setCode("555-fff");
		f.setName("product f");
		f.setPrice(23.2);
		f.setQuantity(100);
		
		Product g = new Product();
		g.setCode("555-ggg");
		g.setName("product g");
		g.setPrice(23.2);
		g.setQuantity(100);
		
		Product h = new Product();
		h.setCode("555-hhh");
		h.setName("product h");
		h.setPrice(23.2);
		h.setQuantity(100);
		
		productList.add(a);
		productList.add(b);
		productList.add(c);
		productList.add(d);
		productList.add(e);
		productList.add(f);
		productList.add(g);
		productList.add(h);
		
		return productList;
	}
}
