package org.inf325.ventarron.adapters;

import java.util.List;

import org.inf325.ventarron.R;
import org.inf325.ventarron.dao.Sale;
import org.inf325.ventarron.utils.TimeUtil;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SaleReportAdapter extends ArrayAdapter<Sale> {
	private Context context;
	private int layoutResourceId;
	private List<Sale> data;

	public SaleReportAdapter(Context context, int layoutResourceId,
			List<Sale> data) {
		super(context, layoutResourceId, data);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		SaleHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new SaleHolder();
			holder.lblDate = (TextView) row.findViewById(R.id.sale_lblDate);
			holder.lblTotalPrice = (TextView) row.findViewById(R.id.sale_lblTotalPrice);
			holder.lblTotalQuantity = (TextView) row.findViewById(R.id.sale_lblQuantity);

			row.setTag(holder);
		} else {
			holder = (SaleHolder) row.getTag();
		}
		
		Sale sale = data.get(position);
		holder.lblDate.setText(TimeUtil.dateToString(sale.getDate()));
		holder.lblTotalPrice.setText("Bs. " + sale.getTotal());
		holder.lblTotalQuantity.setText(sale.getQuantity() + " unidades");
		
		return row;
	}

	private class SaleHolder {
		public TextView lblDate;
		public TextView lblTotalPrice;
		public TextView lblTotalQuantity;
	}
}
