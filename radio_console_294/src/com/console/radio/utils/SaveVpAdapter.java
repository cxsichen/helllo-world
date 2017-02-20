package com.console.radio.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.Set;

import com.console.radio.R;
import com.console.radio.utils.MyScrollView.OnScrollChangedListener;
import com.console.radio.utils.VpAdapter.OnItemSelectedListener;

import android.R.integer;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class SaveVpAdapter extends BaseAdapter {

	ViewHolder holder;
	Context context;
	private DecimalFormat df2 = new DecimalFormat("###.00");
	HashSet<Integer> saveFqs;
	List<Integer> temp = new ArrayList<Integer>();

	public SaveVpAdapter(Context context, Set<Integer> saveFqs) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.saveFqs = (HashSet<Integer>) saveFqs;
	}

	private class ViewHolder {
		TextView freqIndexTv;
		TextView freqValueTv;
		ImageView freqImagView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return saveFqs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		temp.clear();
		for (Integer i : saveFqs) {
			temp.add(i);
		}
		return temp.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.listview_item, parent, false);
			holder = new ViewHolder();
			holder.freqIndexTv = (TextView) convertView
					.findViewById(R.id.freq_index);
			holder.freqValueTv = (TextView) convertView
					.findViewById(R.id.freq_value);
			holder.freqImagView = (ImageView) convertView
					.findViewById(R.id.freq_iv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.freqImagView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (position < temp.size())
					if (onItemSelectedListener != null) {
						onItemSelectedListener.OnItemSelected(temp
								.get(position));
					}
			}
		});

		holder.freqImagView.setImageResource(R.drawable.heart_red);

		holder.freqIndexTv.setText((position + 1) + "");

		temp.clear();
		for (Integer i : saveFqs) {
			temp.add(i);
		}
		holder.freqValueTv.setText(temp.get(position) > 8000 ? ("FM " + String
				.valueOf(df2.format((float) temp.get(position) / 100.0f)))
				: ("AM " + String.valueOf(temp.get(position))));
		return convertView;
	}

	public interface OnItemSelectedListener {
		public void OnItemSelected(int freq);
	}

	OnItemSelectedListener onItemSelectedListener;

	public void setOnItemSelectedListener(
			OnItemSelectedListener onItemSelectedListener) {
		this.onItemSelectedListener = onItemSelectedListener;
	}

}
