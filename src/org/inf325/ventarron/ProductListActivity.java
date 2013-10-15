package org.inf325.ventarron;

import java.util.List;

import org.inf325.ventarron.adapters.ProductAdapter;
import org.inf325.ventarron.dao.Product;
import org.inf325.ventarron.dao.Role;
import org.inf325.ventarron.services.ProductService;
import org.inf325.ventarron.services.ProductServiceImpl;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import static org.inf325.ventarron.utils.Constants.*;

public class ProductListActivity extends CrudActivity {
	private final String LOG_TAG = getClass().getSimpleName();
	public final static String EXTRA_PRODUCT = "org.inf325.ventarron.EXTRA_PRODUCT";

	private ListView productListView;
	private EditText txtKeyword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list);

		super.role = Role.PRODUCTS_ROLE;

		loadViews();
		initializeData();
	}

	private void loadViews() {
		productListView = (ListView) findViewById(R.id.prodlist_productList);
		// Create header
		View header = getLayoutInflater().inflate(
				R.layout.productlistview_header_row, null);

		productListView.addHeaderView(header);

		txtKeyword = (EditText) findViewById(R.id.prodlist_txtKeyword);
	}

	private void initializeData() {
		ProductService productService = ProductServiceImpl
				.createService(getHelper());

		List<Product> productList = productService.getProducts();
		setListViewAdapter(productList);

		// In order to launch float context menu when long pressing
		registerForContextMenu(productListView);
	}

	private void setListViewAdapter(List<Product> productList) {
		// Create adapter with the custom row layout
		ProductAdapter productAdapter = new ProductAdapter(this,
				R.layout.productlistview_item_row, productList);

		productListView.setAdapter(productAdapter);
	}

	public void searchProducts(View view) {
		ProductService service = ProductServiceImpl.createService(getHelper());
		String keyword = txtKeyword.getText().toString();

		List<Product> productList = service.filter(keyword);
		setListViewAdapter(productList);
	}

	@Override
	protected void onCreate() {
		Intent intent = new Intent(this, EditProductActivity.class);
		intent.putExtra(EXTRA_MODE, CREATE_MODE);

		startActivityForResult(intent, 1);
	}

	@Override
	protected void onEdit(AdapterContextMenuInfo info) {
		Product product;
		product = (Product) productListView.getAdapter().getItem(info.position);

		Intent intent = new Intent(this, EditProductActivity.class);
		intent.putExtra(EXTRA_MODE, EDIT_MODE);
		intent.putExtra(EXTRA_PRODUCT, product.getId());

		startActivityForResult(intent, 1);
	}

	@Override
	protected void onDelete(AdapterContextMenuInfo info) {
		Product product;
		ProductService service = ProductServiceImpl.createService(getHelper());
		String message = getString(R.string.changes_failed);

		product = (Product) productListView.getAdapter().getItem(info.position);

		try {
			service.delete(product);
			initializeData();
			message = getString(R.string.changes_ok);
		} catch (Exception e) {
			Log.e(LOG_TAG, "Cannot delete record", e);
		}
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onResult() {
		initializeData();
	}

	@Override
	protected void onCustomContextMenu(ContextMenu menu) {
		menu.add(1, SALE_REPORT_MENU_ID, 1, getString(R.string.sale_report));
	}

	@Override
	protected void onCustomContextItemSelected(AdapterContextMenuInfo info,
			int menuId) {

		Product product = (Product) productListView.getAdapter().getItem(info.position);

		switch (menuId) {
		case SALE_REPORT_MENU_ID:
			productReport(product);
		}
	}
	
	private void productReport(Product product) {
		Intent intent = new Intent(this, SaleReportActivity.class);
		intent.putExtra(EXTRA_REPORT_MODE, SALE_REPORT_PRODUCT);
		intent.putExtra(EXTRA_REPORT_FILTER_ID, product.getId());
		
		startActivity(intent);
	}
}
