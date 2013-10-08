package org.inf325.ventarron.adapters;

import java.util.List;

import org.inf325.ventarron.R;
import org.inf325.ventarron.dao.SaleItem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SaleItemAdapter extends ArrayAdapter<SaleItem> {
	private Context context;
	private int layoutResourceId;
	private List<SaleItem> data = null;

	public SaleItemAdapter(Context context, int layoutResourceId,
			List<SaleItem> data) {

		super(context, layoutResourceId, data);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.data = data;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row = convertView;
		final SaleItemHolder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new SaleItemHolder();
			holder.lblCode = (TextView) row
					.findViewById(R.id.sale_product_code);
			holder.lblName = (TextView) row
					.findViewById(R.id.sale_product_name);
			holder.lblPrice = (TextView) row
					.findViewById(R.id.sale_product_price);
			holder.lblQuantity = (TextView) row
					.findViewById(R.id.sale_quantity);

			row.setTag(holder);
		} else {
			holder = (SaleItemHolder) row.getTag();
		}

		SaleItem saleItem = data.get(position);
		holder.lblCode.setText(saleItem.getProduct().getCode());
		holder.lblName.setText(saleItem.getProduct().getName());
		double singleTotal = saleItem.getProduct().getPrice()
				* saleItem.getQuantity();
		holder.lblPrice.setText("Bs. " + String.valueOf(singleTotal));
		holder.lblQuantity.setText(String.valueOf(saleItem.getQuantity()));

		return row;
	}

	private class SaleItemHolder {
		public TextView lblCode;
		public TextView lblName;
		public TextView lblPrice;
		public TextView lblQuantity;
	}
}
