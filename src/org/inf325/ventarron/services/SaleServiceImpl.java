package org.inf325.ventarron.services;

import java.util.ArrayList;
import java.util.List;

import org.inf325.ventarron.dao.DbHelper;
import org.inf325.ventarron.dao.Product;
import org.inf325.ventarron.dao.Sale;
import org.inf325.ventarron.dao.SaleItem;
import org.inf325.ventarron.utils.TimeUtil;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

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
		long quantity = 0;
		double total = 0.0;
		
		// Precalculate total for process-saving columns on sale table
		for (SaleItem item : items) { 
			total += (item.getProduct().getPrice() * item.getQuantity());
			quantity += item.getQuantity();
		}
		
		saleId = insertSale(sellerId, clientId, quantity, total);
		
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
	
	public List<Sale> clientSaleReport(int clientId) {
		List<Sale> result = saleDb.queryForEq("clientId", clientId);
		return result;
	}
	
	public List<Sale> productSaleReport(int productId) {
		List<SaleItem> saleItems = saleItemDb.queryForEq("productId", productId);
		List<Sale> result = new ArrayList<Sale>();
		List<Integer> saleIds = new ArrayList<Integer>();
		
		// Prepare the saleIds list
		for (SaleItem item : saleItems) {
			saleIds.add(item.getSaleId());
		}
		
		QueryBuilder<Sale, Integer> builder = saleDb.queryBuilder();
		Where<Sale, Integer> where = builder.where();
		
		try {
			// Get all sales whose id is on the saleIds list
			where.in("id", saleIds);
			PreparedQuery<Sale> query = builder.prepare();
			
			result = saleDb.query(query);
		} catch(Exception e) {}
		
		return result;
	}
	
	public List<Sale> sellerSaleReport(int sellerId) {
		List<Sale> result = saleDb.queryForEq("sellerId", sellerId);
		return result;
	}
	
	private int insertSale(int sellerId, int clientId, long quantity, double total) {
		Sale sale = new Sale();
		sale.setClientId(clientId);
		sale.setSellerId(sellerId);
		sale.setDate(TimeUtil.getUnixTime());
		sale.setQuantity(quantity);
		sale.setTotal(total);
		saleDb.create(sale);
		
		return sale.getId();
	}
}
