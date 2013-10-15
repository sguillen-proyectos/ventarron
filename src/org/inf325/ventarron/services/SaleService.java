package org.inf325.ventarron.services;

import java.util.List;

import org.inf325.ventarron.dao.Sale;
import org.inf325.ventarron.dao.SaleItem;

public interface SaleService {
	public void createSale(List<SaleItem> items, int sellerId, int clientId);
	public List<Sale> clientSaleReport(int clientId);
	public List<Sale> productSaleReport(int productId);
	public List<Sale> sellerSaleReport(int sellerId);
}
