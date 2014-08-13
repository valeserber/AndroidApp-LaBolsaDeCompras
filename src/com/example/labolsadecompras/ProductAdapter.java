package com.example.labolsadecompras;

import java.util.List;

import model.Product;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;


public class ProductAdapter extends ArrayAdapter<Product>{
	private final Activity context;
	private final List<Product> product_list;
	private final List<Integer> product_images;
	private final boolean button_more;
	
	public ProductAdapter(Activity context,List<Product> product_info, List<Integer> product_images,boolean more) {
		super(context, R.layout.product_list, product_info);
		this.context = context;
		this.product_list = product_info;
		this.product_images = product_images;
		this.button_more=more;
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.product_list, null, true);

		TextView txtTitle = (TextView) rowView.findViewById(R.id.prod_txt);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.prod_img);
		TextView brand= (TextView) rowView.findViewById(R.id.prod_brand);
		TextView price= (TextView) rowView.findViewById(R.id.prod_price);
		
		txtTitle.setSingleLine(true);
		txtTitle.setText(product_list.get(position).getName()+"\n");
		brand.setSingleLine(true);
		brand.setText(product_list.get(position).getAttributeById(Product.BrandId).getValues().get(0)+"\n");
		price.setSingleLine(true);
		price.setText(String.valueOf("$"+product_list.get(position).getPrice()+"\n"));
		
		Ion.with(context)
		.load(product_list.get(position).getImgsrc()[0])
		.withBitmap()
		//.placeholder(R.drawable.placeholder_image)  //placeholder_image
		.error(R.drawable.no_disponible) //error_image
		//.animateLoad(spinAnimation)
		//.animateIn(fadeInAnimation)
		.intoImageView(imageView);
		return rowView;
	}
}