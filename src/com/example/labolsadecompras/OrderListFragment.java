package com.example.labolsadecompras;

import java.util.Collections;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import model.Order;

public class OrderListFragment extends Fragment {
	private List<Order> orderList;

	public OrderListFragment() {}

	public OrderListFragment(List<Order> orderList){
		this.orderList = orderList;
		Collections.sort(orderList);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(orderList.isEmpty()){
			View rootView = inflater.inflate(R.layout.fragment_order_list, container, false);
			return rootView;
		}

		OrderAdapter adapter = new OrderAdapter(getActivity(), orderList);
		View rootView = inflater.inflate(R.layout.fragment_order_list, container, false);

		ListView lv = (ListView)rootView.findViewById(R.id.order_list);
		lv.setAdapter(adapter);
		//TO-DO:hacer un nuevo adapter para ambos casos
		/*
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
				intent.putExtra("ID", orderList.get(position).getId());
				startActivity(intent);
			}
		});
		*/
		return rootView;
	}
}