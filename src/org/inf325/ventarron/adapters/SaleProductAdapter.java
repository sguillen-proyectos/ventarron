package org.inf325.ventarron.adapters;

import java.util.ArrayList;
import java.util.List;

import org.inf325.ventarron.R;
import org.inf325.ventarron.dao.Product;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class SaleProductAdapter extends ArrayAdapter<Product> {
	private Context context;
	private int layoutResourceId;
	private List<Product> data = null;
	private SparseBooleanArray checkedList;
	
	public SaleProductAdapter(Context context, int layoutResourceId,
			List<Product> data) {
		
		super(context, layoutResourceId, data);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.data = data;
		this.checkedList = new SparseBooleanArray();
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row = convertView;
		final SaleProductHolder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			
			holder = new SaleProductHolder();
			holder.lblCode = (TextView) row.findViewById(R.id.sale_product_code);
			holder.lblName = (TextView) row.findViewById(R.id.sale_product_name);
			holder.lblPrice = (TextView) row.findViewById(R.id.sale_product_price);
			holder.chkSelected = (CheckBox) row.findViewById(R.id.sale_product_selected);
			holder.chkSelected.setOnCheckedChangeListener(checkedListener);
			holder.chkSelected.setTag(position);
			row.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					holder.chkSelected.setChecked(!holder.chkSelected.isChecked());
				}
			});
			
			row.setTag(holder);
		} else {
			holder = (SaleProductHolder) row.getTag();
		}
		
		Product product = data.get(position);
		holder.lblCode.setText(product.getCode());
		holder.lblName.setText(product.getName());
		holder.lblPrice.setText("Bs. " + product.getPrice());
		holder.chkSelected.setChecked(checkedList.get(position));
		
		return row;
	}
	
	public ArrayList<Product> getCheckedItems() {
		ArrayList<Product> result = new ArrayList<Product>();
		for (int i = 0; i < data.size(); ++i) {
			if (checkedList.get(i)) {
				result.add(data.get(i));
			}
		}
		return result;
	}
	
	private class SaleProductHolder {
		public TextView lblCode;
		public TextView lblName;
		public TextView lblPrice;
		public CheckBox chkSelected;
	}
	
	private OnCheckedChangeListener checkedListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			int position = (Integer) buttonView.getTag();
			checkedList.put(position, isChecked);
		}
	};
}
