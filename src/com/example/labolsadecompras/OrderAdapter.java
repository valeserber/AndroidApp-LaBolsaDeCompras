package com.example.labolsadecompras;

import java.util.List;

import model.Order;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OrderAdapter extends ArrayAdapter<Order>{
	private final Activity context;
	private final List<Order> orderList;
	private final String[] st;
	
	public OrderAdapter(Activity context, List<Order> orderList) {
		super(context, R.layout.order_list, orderList);
		this.context = context;
		this.orderList = orderList;
		String[] s = {
				context.getString(R.string.order_status_1),
				context.getString(R.string.order_status_2),
				context.getString(R.string.order_status_3),
				context.getString(R.string.order_status_4)
		};
		st = s;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.order_list, null, true);
		Order ord = orderList.get(position);
		model.Address ad;
		String s;
		
		TextView txtTitle = (TextView) rowView.findViewById(R.id.order_id2);
		TextView status = (TextView) rowView.findViewById(R.id.order_status2);
		TextView address = (TextView) rowView.findViewById(R.id.order_sent_to2);
		TextView dateReceived = (TextView) rowView.findViewById(R.id.order_date_received2);
		
		txtTitle.setSingleLine(true);
		txtTitle.setText(ord.getId() + "\n");

		status.setSingleLine(true);
		status.setText(st[Integer.valueOf(ord.getStatus()) - 1] + "\n");

		address.setSingleLine(true);
		ad = ord.getAddress();
		if(ad == null){
			s = context.getString(R.string.order_address_unknown);
		}else{
			s = ad.getName();
		}
		address.setText(s);
		
		dateReceived.setSingleLine(true);
		dateReceived.setText(ord.getReceivedDate());
		
		return rowView;
	}
}