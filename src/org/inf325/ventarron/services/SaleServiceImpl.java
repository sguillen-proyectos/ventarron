package org.inf325.ventarron.services;

import java.util.List;

import org.inf325.ventarron.dao.DbHelper;
import org.inf325.ventarron.dao.Product;
import org.inf325.ventarron.dao.Sale;
import org.inf325.ventarron.dao.SaleItem;
import org.inf325.ventarron.utils.TimeUtil;

import com.j256.ormlite.dao.RuntimeExceptionDao;

public class SaleServiceImpl implements SaleService {
	private RuntimeExceptionDao<Sale, Integer> saleDb;
	private RuntimeExceptionDao<SaleItem, Integer> saleItemDb;
	private ProductService productService;

	public static SaleServiceImpl createService(DbHelper helper) {
		return new SaleServiceImpl(helper.getSaleDao(),
				helper.getSaleItemDao(), helper.getProductDao(), helper);
	}

	private SaleServiceImpl(RuntimeExceptionDao<Sale, Integer> saleDb,
			RuntimeExceptionDao<SaleItem, Integer> saleItemDb,
			RuntimeExceptionDao<Product, Integer> productDb, DbHelper helper) {

		this.saleDb = saleDb;
		this.saleItemDb = saleItemDb;
		this.productService = ProductServiceImpl.createService(helper);
	}

	public void createSale(List<SaleItem> items, int sellerId, int clientId) {
		Product product;
		int saleId;
		
		saleId = insertSale(sellerId, clientId);
		
		for (SaleItem item : items) {
			item.setSaleId(saleId);
			product = productService.getProduct(item.getProductId());
			if (product != null) {
				product.setQuantity(product.getQuantity() - item.getQuantity());
				
				productService.update(product);
			}
			
			saleItemDb.create(item);
		}
	}
	
	private int insertSale(int sellerId, int clientId) {
		Sale sale = new Sale();
		sale.setClientId(clientId);
		sale.setSellerId(sellerId);
		sale.setDate(TimeUtil.getUnixTime());
		saleDb.create(sale);
		
		return sale.getId();
	}
}
