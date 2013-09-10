package org.inf325.ventarron;

import java.util.List;

import org.inf325.ventarron.adapters.ProductAdapter;
import org.inf325.ventarron.dao.Product;
import org.inf325.ventarron.services.ProductService;
import org.inf325.ventarron.utils.MessageBox;

import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import static org.inf325.ventarron.utils.Constants.*;

public class ProductListActivity extends Activity {
	public final static String EXTRA_PRODUCT = "org.inf325.ventarron.EXTRA_PRODUCT";
	public final static String EXTRA_MODE = "org.inf325.ventarron.EXTRA_MODE";
	
	private ListView productListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list);

		loadViews();
		initializeData();
	}

	private void loadViews() {
		productListView = (ListView) findViewById(R.id.prodlist_productList);
	}

	private void initializeData() {
		ProductService productService = new ProductService();
		List<Product> productList = productService.getProducts();

		// Create adapter with the custom row layout
		ProductAdapter productAdapter = new ProductAdapter(this,
				R.layout.productlistview_item_row, productList);

		// Create header
		View header = getLayoutInflater().inflate(
				R.layout.productlistview_header_row, null);

		productListView.addHeaderView(header);
		productListView.setAdapter(productAdapter);

		// In order to launch float context menu when long pressing
		registerForContextMenu(productListView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.product_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.productlist_newProduct:
			Intent intent = new Intent(this, EditProductActivity.class);
			intent.putExtra(EXTRA_MODE, CREATE_MODE);
			
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.productlist_edit, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		Product product = (Product) productListView.getAdapter().getItem(info.position);

		switch (item.getItemId()) {
		case R.id.edit:
			editProduct(product);
			return true;
		case R.id.delete:
			confirmDeleteProduct(product);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void confirmDeleteProduct(final Product product) {
		MessageBox.confirm(this, getString(R.string.question_delete_item),
				getString(R.string.alert), new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						deleteProduct(product);
					}
				});
	}

	private void deleteProduct(Product product) {
		// TODO: Call delete method from service
	}

	private void editProduct(Product product) {
		Intent intent = new Intent(this, EditProductActivity.class);
		intent.putExtra(EXTRA_MODE, EDIT_MODE);
		intent.putExtra(EXTRA_PRODUCT, product);
		
		startActivity(intent);
	}
}
