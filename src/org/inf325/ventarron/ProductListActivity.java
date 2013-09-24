package org.inf325.ventarron;

import java.util.List;

import org.inf325.ventarron.adapters.ProductAdapter;
import org.inf325.ventarron.dao.DbHelper;
import org.inf325.ventarron.dao.Product;
import org.inf325.ventarron.services.ProductService;
import org.inf325.ventarron.services.ProductServiceImpl;
import org.inf325.ventarron.utils.MessageBox;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import android.os.Bundle;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import static org.inf325.ventarron.utils.Constants.*;

public class ProductListActivity extends OrmLiteBaseActivity<DbHelper> {
	private final String LOG_TAG = getClass().getSimpleName();
	public final static String EXTRA_PRODUCT = "org.inf325.ventarron.EXTRA_PRODUCT";
	
	private ListView productListView;
	private EditText txtKeyword;

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.product_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.productlist_newProduct:
			// Go to edit activity
			Intent intent = new Intent(this, EditProductActivity.class);
			intent.putExtra(EXTRA_MODE, CREATE_MODE);

			startActivityForResult(intent, 1);
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
		Product product;
		AdapterContextMenuInfo info;
		
		info = (AdapterContextMenuInfo) item.getMenuInfo();
		product= (Product) productListView.getAdapter().getItem(info.position);

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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String message = getString(R.string.changes_failed);
		if (resultCode == CHANGES_OK) {
			initializeData();
			message = getString(R.string.changes_ok);
		}
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
		ProductService service = ProductServiceImpl.createService(getHelper());
		String message = getString(R.string.changes_failed);
		try {
			service.delete(product);
			initializeData();
			message = getString(R.string.changes_ok);
		} catch(Exception e) {
			Log.e(LOG_TAG, "Cannot delete record", e);
		}
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	private void editProduct(Product product) {
		Intent intent = new Intent(this, EditProductActivity.class);
		intent.putExtra(EXTRA_MODE, EDIT_MODE);
		intent.putExtra(EXTRA_PRODUCT, product.getId());

		startActivityForResult(intent, 1);
	}
	
	public void searchProducts(View view) {
		ProductService service = ProductServiceImpl.createService(getHelper());
		String keyword = txtKeyword.getText().toString();
		
		List<Product> productList = service.filter(keyword);
		setListViewAdapter(productList);
	}
}
