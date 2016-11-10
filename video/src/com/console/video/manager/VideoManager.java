package com.console.video.manager;

import java.util.List;

import com.console.video.R;
import com.console.video.bean.StorageInfo;
import com.console.video.util.StorageUtil;

import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class VideoManager {
		
	Context mContext;
	FileSearchControl mFileSearchControl;

	public VideoManager(Context mContext, RelativeLayout mRelativeLayout) {
		// TODO Auto-generated constructor stub
		this.mContext=mContext;
		init(mContext,mRelativeLayout);
	}
	private void init(Context mContext,RelativeLayout mRelativeLayout) {
		// TODO Auto-generated method stub
		mFileSearchControl=new FileSearchControl(mContext,mRelativeLayout.findViewById(R.id.file_search_layout));
		
		List<StorageInfo> list=StorageUtil.listAvaliableStorage(mContext);
		Log.i("cxs","=====StorageInfo==list==1==="+list.size());
		for(StorageInfo a:list){
			Log.i("cxs","=====StorageInfo==path==1==="+a.path);
			Log.i("cxs","=====StorageInfo==path==2==="+a.state);
			Log.i("cxs","=====StorageInfo==path==3==="+a.isRemoveable);
		}
	}
	

}
