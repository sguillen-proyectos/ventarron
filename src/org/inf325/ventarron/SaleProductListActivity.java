package org.inf325.ventarron;

import java.util.ArrayList;
import java.util.List;

import org.inf325.ventarron.adapters.SaleProductAdapter;
import org.inf325.ventarron.dao.DbHelper;
import org.inf325.ventarron.dao.Product;
import org.inf325.ventarron.services.ProductService;
import org.inf325.ventarron.services.ProductServiceImpl;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

public class SaleProductListActivity extends OrmLiteBaseActivity<DbHelper> {
	public static final String EXTRA_SELECTED_PRODUCT_LIST = "org.inf325.ventarron.EXTRA_SELECTED_PRODUCT_LIST";
	public static final String EXTRA_CLIENT = "org.inf325.ventarron.EXTRA_CLIENT";
	private ListView productListView;
	private int clientId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sale_product_list);

		loadExtraData();
		loadViews();
		initializeData();
		
	}
	
	private void loadExtraData() {
		Intent intent = getIntent();
		clientId = intent.getIntExtra(ClientListActivity.EXTRA_CLIENT, 0);
	}

	private void initializeData() {
		ProductService service = ProductServiceImpl.createService(getHelper());
		List<Product> products = service.getProducts();

		SaleProductAdapter adapter = new SaleProductAdapter(this,
				R.layout.saleproductlistview_item_row, products);

		productListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		productListView.setAdapter(adapter);
	}

	private void loadViews() {
		productListView = (ListView) findViewById(R.id.sales_product_list);
		productListView.setItemsCanFocus(false);
	}

	public void continueSale(View view) {
		SaleProductAdapter adapter;
		adapter = (SaleProductAdapter) productListView.getAdapter();

		ArrayList<Product> products = adapter.getCheckedItems();

		if (products.size() > 0) {
			Intent intent = new Intent(this,
					SaleSelectedProductListActivity.class);

			String extra = EXTRA_SELECTED_PRODUCT_LIST;
			intent.putExtra(EXTRA_CLIENT, clientId);
			intent.putParcelableArrayListExtra(extra, products);

			startActivity(intent);
		} else {
			Toast.makeText(this, getString(R.string.sale_no_products_selected),
					Toast.LENGTH_SHORT).show();
		}
	}
}
