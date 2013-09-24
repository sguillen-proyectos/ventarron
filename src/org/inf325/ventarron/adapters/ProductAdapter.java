package org.inf325.ventarron.adapters;

import java.util.List;

import org.inf325.ventarron.R;
import org.inf325.ventarron.dao.Product;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProductAdapter extends ArrayAdapter<Product> {

	private Context context;
	private int layoutResourceId;
	private List<Product> data = null;

	public ProductAdapter(Context context, int layoutResourceId,
			List<Product> data) {
		super(context, layoutResourceId, data);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ProductHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new ProductHolder();
			holder.lblCode = (TextView) row
					.findViewById(R.id.prodlistitem_lblCode);
			
			holder.lblName = (TextView) row
					.findViewById(R.id.prodlistitem_lblName);
			
			holder.lblPrice = (TextView) row
					.findViewById(R.id.prodlistitem_lblPrice);
			
			holder.lblQuantity = (TextView) row
					.findViewById(R.id.prodlistitem_lblQuantity);
			
			row.setTag(holder); 
		} else {
			holder = (ProductHolder) row.getTag();
		}
		
		Product product = data.get(position);
		holder.lblCode.setText(product.getCode());
		holder.lblName.setText(product.getName());
		holder.lblQuantity.setText(String.valueOf(product.getQuantity()));
		holder.lblPrice.setText(String.valueOf(product.getPrice()));

		return row;
	}
	
	static class ProductHolder {
		public TextView lblQuantity;
		public TextView lblName;
		public TextView lblCode;
		public TextView lblPrice;
	}
}
