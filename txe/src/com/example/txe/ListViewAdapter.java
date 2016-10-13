package com.example.txe;

import java.util.List;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
	public final class ViewHolder {
		public TextView title;
		public ImageView sign;
	}

	private LayoutInflater mInflater;
	List<CanItem> list;

	public ListViewAdapter(LayoutInflater inflater, List<CanItem> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.mInflater = inflater;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listview_item, null);
			holder = new ViewHolder();
			/* �õ������ؼ��Ķ��� */
			holder.title = (TextView) convertView.findViewById(R.id.item_title);
			holder.sign = (ImageView) convertView.findViewById(R.id.item_sign);
			convertView.setTag(holder);// ��ViewHolder����
		} else {
			holder = (ViewHolder) convertView.getTag();// ȡ��ViewHolder����
		}
		
		holder.title.setText(list.get(position).getTitle());
		if(list.get(position).getLevel()<4){
			holder.sign.setVisibility(View.VISIBLE);
		}else{
			holder.sign.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

}
