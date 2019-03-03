package com.example.a85161.weibo.util;

import com.example.a85161.weibo.R;
import com.example.a85161.weibo.model.Customer;

import android.content.Context;
import android.content.res.Resources;

public class UIUtil {

	// tag for log
//	private static String TAG = UIUtil.class.getSimpleName();
	
	public static String getCustomerInfo (Context ctx, Customer customer) {
		Resources resources = ctx.getResources();
		StringBuffer sb = new StringBuffer();
		sb.append(resources.getString(R.string.customer_blog));
		sb.append(" ");
		sb.append(customer.getBlogcount());
		sb.append(" | ");
		sb.append(resources.getString(R.string.customer_fans));
		sb.append(" ");
		sb.append(customer.getFanscount());
		return sb.toString();
	}
}