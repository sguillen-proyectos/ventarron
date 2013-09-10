package org.inf325.ventarron;

import org.inf325.ventarron.dao.Product;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import static org.inf325.ventarron.utils.Constants.*;

public class EditProductActivity extends Activity {
	private EditText txtCode;
	private EditText txtName;
	private EditText txtPrice;
	private EditText txtQuantity;
	private EditText txtDescription;
	private String mode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_product);

		loadViews();
		
		mode = getIntent().getStringExtra(ProductListActivity.EXTRA_MODE);
		if (mode.equals(EDIT_MODE)) {
			Product product = (Product) getIntent().getSerializableExtra(
					ProductListActivity.EXTRA_PRODUCT);
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
	
	public void saveChanges(View view) {
		if (mode.equals(EDIT_MODE)) {
			
		}
	}
}
