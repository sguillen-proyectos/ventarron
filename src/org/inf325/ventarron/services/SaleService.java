package org.inf325.ventarron.services;

import java.util.List;

import org.inf325.ventarron.dao.SaleItem;

public interface SaleService {
	public void createSale(List<SaleItem> items, int sellerId, int clientId);
}
