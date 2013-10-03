package org.inf325.ventarron.services;


import org.inf325.ventarron.dao.SaleItem;

import android.util.SparseArray;

public class ShoppingCart {
	private static ShoppingCart cart;
	
	public static ShoppingCart getInstance() {
		if (cart == null) {
			cart = new ShoppingCart();
		}
		return cart;
	}
	
	private SparseArray<SaleItem> bag;
	
	private ShoppingCart() {
		bag = new SparseArray<SaleItem>();
	}
	
	public void restartSale() {
		bag.clear();
	}
	
	public void addSaleItem(SaleItem item) {
		bag.put(item.getProductId(), item);
	}
	
	public double getTotal() {
		SaleItem item;
		int key;
		double total = 0.0;
		for (int i = 0; i < bag.size(); ++i) {
			key = bag.keyAt(i);
			item = bag.get(key);
			
			total += (item.getProduct().getPrice() * item.getQuantity());
		}

		return total;
	}
}
