package com.console.video.manager;

import java.util.ArrayList;
import java.util.List;

import com.console.video.R;
import com.console.video.bean.StorageInfo;
import com.console.video.util.StorageUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class FileSearchControl {

	View mainView;
	Context mContext;
	ListView mVolumeList;
	ListView mFileList;
	List<StorageInfo> storageList = new ArrayList<StorageInfo>();

	public FileSearchControl(Context mContext, View view) {
		// TODO Auto-generated constructor stub
		mainView = view;
		this.mContext = mContext;
		init(view);
	}

	private void init(View view) {
		// TODO Auto-generated method stub
		storageList = StorageUtil.listAvaliableStorage(mContext);
		mVolumeList = (ListView) view.findViewById(R.id.volume_list);
		mFileList = (ListView) view.findViewById(R.id.file_list);
		StorageAdapter mStorageAdapter=new StorageAdapter(mContext);
		mVolumeList.setAdapter(mStorageAdapter);

	}

	class StorageAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		
		int[] storageDrawable={R.drawable.menu_flash,R.drawable.menu_sd,R.drawable.menu_usb};
		int[] storageSelectDrawable={R.drawable.menu_flash_sel,R.drawable.menu_sd_sel,R.drawable.menu_usb_sel};
		
		public StorageAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return storageList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return storageList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		public final class ViewHolder {
			public ImageView img;
			public TextView title;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.storage_item, null);
				holder.img = (ImageView) convertView
						.findViewById(R.id.storage_iv);
				holder.title = (TextView) convertView
						.findViewById(R.id.storage_tv);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.img.setImageResource(storageDrawable[storageList.get(position).getType()]);
			holder.title.setText(storageList.get(position).path);
			return convertView;
		}

	}

}
