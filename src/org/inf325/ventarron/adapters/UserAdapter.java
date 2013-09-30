package org.inf325.ventarron.adapters;

import java.util.List;

import org.inf325.ventarron.R;
import org.inf325.ventarron.dao.User;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class UserAdapter extends ArrayAdapter<User> {
	private Context context;
	private int layoutResourceId;
	private List<User> data = null;
	
	public UserAdapter(Context context, int layoutResourceId,
			List<User> data) {
		super(context, layoutResourceId, data);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.data = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		UserHolder holder = null;
		
		if (row == null) {
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			
			holder = new UserHolder();
			holder.lblName = (TextView) row.findViewById(R.id.userlist_lblName);
			holder.lblUsername = (TextView) row.findViewById(R.id.userlist_lblUsername);
			
			row.setTag(holder);
		} else {
			holder = (UserHolder) row.getTag();
		}
		
		User user = data.get(position);
		holder.lblName.setText(user.getName());
		holder.lblUsername.setText(user.getUsername());
		
		return row;
	}
	
	static class UserHolder {
		public TextView lblName;
		public TextView lblUsername;
	}
}
