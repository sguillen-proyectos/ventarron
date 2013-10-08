package org.inf325.ventarron;

import static org.inf325.ventarron.utils.Constants.SETTINGS_FILE;

import java.util.ArrayList;
import java.util.List;

import org.inf325.ventarron.adapters.SaleItemAdapter;
import org.inf325.ventarron.dao.DbHelper;
import org.inf325.ventarron.dao.Product;
import org.inf325.ventarron.dao.SaleItem;
import org.inf325.ventarron.services.SaleService;
import org.inf325.ventarron.services.SaleServiceImpl;
import org.inf325.ventarron.utils.MessageBox;
import org.inf325.ventarron.utils.NumericDialogListener;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SaleSelectedProductListActivity extends
		OrmLiteBaseActivity<DbHelper> {
	private ListView productListView;
	private int clientId;
	private int sellerId;
	private ArrayList<Product> products;
	private List<SaleItem> saleItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sale_selected_product_list);

		loadExtraData();
		loadViews();
		initializeData();
		setListViewAdapter();
	}

	private void loadExtraData() {
		SessionManager session = getSession();
		Intent intent = getIntent();
		products = intent
				.getParcelableArrayListExtra(SaleProductListActivity.EXTRA_SELECTED_PRODUCT_LIST);
		clientId = intent.getIntExtra(SaleProductListActivity.EXTRA_CLIENT, 0);
		sellerId = session.getSessionUserId();
	}

	private void loadViews() {
		productListView = (ListView) findViewById(R.id.sales_selected_product_list);
		productListView.setOnItemClickListener(myClickListener);
	}

	private void initializeData() {
		SaleItem item;
		saleItems = new ArrayList<SaleItem>();

		for (Product product : products) {
			item = new SaleItem();
			item.setProductId(product.getId());
			item.setQuantity(0);
			item.setProduct(product);

			saleItems.add(item);
		}
	}

	private void setListViewAdapter() {
		SaleItemAdapter adapter = new SaleItemAdapter(this,
				R.layout.saleitemlistview_item_row, saleItems);
		
		productListView.setAdapter(adapter);
	}

	public void finishSale(View view) {
		String msg = getString(R.string.sale_finish_sale);
		String title = getString(R.string.sale);
		MessageBox.confirm(this, msg, title, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finishSale();
			}
		});
	}

	private void finishSale() {
		SaleService saleService = SaleServiceImpl.createService(getHelper());
		saleService.createSale(saleItems, sellerId, clientId);
		
		Toast.makeText(this, R.string.sale_finished, Toast.LENGTH_SHORT).show();
		
		Intent intent = new Intent(this, ClientListActivity.class);
		intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
	}

	OnItemClickListener myClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			final Product product;
			final SaleItem item;
			product = products.get(position);
			item = (SaleItem) productListView.getItemAtPosition(position);

			showNumericDialog(product, item);
		}

	};

	private void showNumericDialog(final Product product, final SaleItem item) {
		NumericDialogListener numericListener = new NumericDialogListener() {
			@Override
			public void onResult(int value) {
				item.setQuantity(value);
			}
		};

		String title = getString(R.string.product_quantity);
		title += " max " + product.getQuantity();
		MessageBox.numeric(SaleSelectedProductListActivity.this, 1,
				product.getQuantity(), title, numericListener);
	}

	private SessionManager getSession() {
		return SessionManager
				.getInstance(getSharedPreferences(SETTINGS_FILE, 0));
	}
}
