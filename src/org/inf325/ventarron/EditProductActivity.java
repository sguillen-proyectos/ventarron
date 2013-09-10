package org.inf325.ventarron;

import org.inf325.ventarron.dao.DbHelper;
import org.inf325.ventarron.dao.Depot;
import org.inf325.ventarron.dao.Product;
import org.inf325.ventarron.services.ProductService;
import org.inf325.ventarron.services.ProductServiceImpl;
import org.inf325.ventarron.utils.Constants;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import static org.inf325.ventarron.utils.Constants.*;

public class EditProductActivity extends OrmLiteBaseActivity<DbHelper> {
	private final String LOG_TAG = getClass().getSimpleName();
	private EditText txtCode;
	private EditText txtName;
	private EditText txtPrice;
	private EditText txtQuantity;
	private EditText txtDescription;
	private String mode;
	private int productId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_product);

		loadViews();
		
		ProductService service = ProductServiceImpl.createService(getHelper());
		mode = getIntent().getStringExtra(ProductListActivity.EXTRA_MODE);
		if (mode.equals(EDIT_MODE)) {
			productId = getIntent().getIntExtra(
					ProductListActivity.EXTRA_PRODUCT, 0);
			
			Product product = service.getProduct(productId);
			setProductValues(product);
		}
	}

	private void setProductValues(Product product) {
		// Focus in other view so code can't be edited
		txtCode.setEnabled(false);
		txtName.requestFocus();

		txtCode.setText(product.getCode());
		txtName.setText(product.getName());
		txtPrice.setText(String.valueOf(product.getPrice()));
		txtQuantity.setText(String.valueOf(product.getQuantity()));
		txtDescription.setText(product.getDescription());
	}

	private void loadViews() {
		txtCode = (EditText) findViewById(R.id.editprod_txtCode);
		txtName = (EditText) findViewById(R.id.editprod_txtName);
		txtPrice = (EditText) findViewById(R.id.editprod_txtPrice);
		txtQuantity = (EditText) findViewById(R.id.editprod_txtQuantity);
		txtDescription = (EditText) findViewById(R.id.editprod_txtDescription);
	}

	private boolean isValid() {
		if (txtCode.getText().toString().trim().equals("")) {
			return false;
		} else if (txtName.getText().toString().trim().equals("")) {
			return false;
		} else {
			try {
				Integer.parseInt(txtQuantity.getText().toString());
				Double.parseDouble(txtPrice.getText().toString());
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	private Product viewToProduct() {
		double price = Double.parseDouble(txtPrice.getText().toString());
		int quantity = Integer.parseInt(txtQuantity.getText().toString());

		// TODO: this is hard-coded
		Depot depot = new Depot();
		depot.setId(1);

		Product product = new Product();
		product.setCode(txtCode.getText().toString().trim());
		product.setName(txtName.getText().toString().trim());
		product.setDescription(txtDescription.getText().toString().trim());
		product.setPrice(price);
		product.setQuantity(quantity);
		product.setDepot(depot);

		return product;
	}
	
	private void changesDone(int resultCode) {
		Intent intent = new Intent();
		setResult(resultCode, intent);
		finish();
	}

	public void saveChanges(View view) {
		if (!isValid()) {
			String message = getString(R.string.invalid_information);
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
			return;
		}

		ProductService service = ProductServiceImpl.createService(getHelper());
		Product product = viewToProduct();
		
		// Applying changes and going back to product list activity
		int result = Constants.CHANGES_FAIL;
		try {
			if (mode.equals(EDIT_MODE)) {
				product.setId(productId);
				service.update(product);
			} else {
				service.insert(product);
			}
			result = Constants.CHANGES_OK;
		} catch(Exception e) {
			Log.e(LOG_TAG, "There were problems with mode: " + mode, e);
		}
		
		changesDone(result);
	}
}
