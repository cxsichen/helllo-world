package com.console.radio.utils;

import java.text.DecimalFormat;
import java.util.List;















import java.util.Set;

import com.console.radio.R;
import com.console.radio.utils.MyScrollView.OnScrollChangedListener;

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

public class VpAdapter extends BaseAdapter {
	
	int band;
	int selectedItem=0;
	int selectedBand=0;
	List<Integer> allFqs;
	ViewHolder holder;
	Context context;
	private DecimalFormat df2 = new DecimalFormat("###.00");
	Set<Integer> saveFqs;
	
	public VpAdapter(Context context,List<Integer> allFqs,int band) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.allFqs=allFqs;
		this.band=band;
	}

	
	private class ViewHolder {
		TextView freqIndexTv;
		TextView freqValueTv;
		ImageView freqImagView;
	}

	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return allFqs.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.listview_item, parent, false);
			holder = new ViewHolder();
			holder.freqIndexTv = (TextView) convertView
					.findViewById(R.id.freq_index);
			holder.freqValueTv = (TextView) convertView
					.findViewById(R.id.freq_value);
			holder.freqImagView= (ImageView) convertView
					.findViewById(R.id.freq_iv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.freqImagView.setTag(position);

		holder.freqImagView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(onItemSelectedListener!=null){
					onItemSelectedListener.OnItemSelected(band,(int) v.getTag());
				}
			}
		});
		
		holder.freqImagView.setImageResource(R.drawable.heart_white);
		if(saveFqs!=null){
			for(int i:saveFqs){
				if(allFqs.get(band * 6 + position).equals(i)){
					holder.freqImagView.setImageResource(R.drawable.heart_red);
					break;
				}
			}
		}
		holder.freqIndexTv.setText((position+1)+"");
		holder.freqValueTv.setText(band<3?("FM "+String.valueOf(df2.format((float) allFqs.get(band * 6
				+ position) / 100.0f))):("AM "+String.valueOf(allFqs.get(band * 6 + position))));
		if(selectedItem==position&&selectedBand==band){
			holder.freqValueTv.setTextColor(context.getResources().getColor(
					R.color.text_selected));
		}else{
			holder.freqValueTv.setTextColor(
					Color.WHITE);
		}
		return convertView;
	}
	
	public void setSelectedItem(int selectedItem,int selectedBand){
		this.selectedItem=selectedItem;
		this.selectedBand=selectedBand;
	}
	
	public void setSaveFreq(Set<Integer>  saveFqs){
		this.saveFqs=saveFqs;
	}
	
	
	public interface OnItemSelectedListener {
		public void OnItemSelected(int band,int item);
	}

	OnItemSelectedListener onItemSelectedListener;

	public void setOnItemSelectedListener(
			OnItemSelectedListener onItemSelectedListener) {
		this.onItemSelectedListener = onItemSelectedListener;
	}


}
