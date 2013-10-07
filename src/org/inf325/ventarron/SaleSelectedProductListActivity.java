package org.inf325.ventarron;

import java.util.ArrayList;

import org.inf325.ventarron.dao.Product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SaleSelectedProductListActivity extends Activity {
	private ArrayList<Product> products;
	private ListView productListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sale_selected_product_list);

		Intent intent = getIntent();
		String name = SaleProductListActivity.EXTRA_SELECTED_PRODUCT_LIST;
		products = intent.getParcelableArrayListExtra(name);

		loadViews();
		initializeData();
	}

	private void loadViews() {
		productListView = (ListView) findViewById(R.id.sales_selected_product_list);
	}

	private void initializeData() {
		ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this,
				android.R.layout.simple_list_item_1, products);
		
		productListView.setAdapter(adapter);
	}
}
