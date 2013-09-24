package org.inf325.ventarron.adapters;

import java.util.List;

import org.inf325.ventarron.R;
import org.inf325.ventarron.dao.Client;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ClientAdapter extends ArrayAdapter<Client> {
	private Context context;
	private int layoutResourceId;
	private List<Client> data = null;

	public ClientAdapter(Context context, int layoutResourceId,
			List<Client> data) {
		super(context, layoutResourceId, data);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.data = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ClientHolder holder = null;
		
		if (row == null) {
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			
			holder = new ClientHolder();
			holder.lblEmail = (TextView) row.findViewById(R.id.clientlist_lblEmail);
			holder.lblName = (TextView) row.findViewById(R.id.clientlist_lblName);
			holder.lblPhone = (TextView) row.findViewById(R.id.clientlist_lblPhone);
			
			row.setTag(holder);
		} else {
			holder = (ClientHolder) row.getTag();
		}
		Client client = data.get(position);
		holder.lblName.setText(client.getName());
		holder.lblEmail.setText(client.getEmail());
		holder.lblPhone.setText(client.getPhone());
		
		return row;
	}
	
	static class ClientHolder {
		public TextView lblName;
		public TextView lblEmail;
		public TextView lblPhone;
	}
}
