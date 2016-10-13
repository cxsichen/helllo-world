package com.example.txe;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class ChooseFragment extends Fragment {
	List<CanItem> list;
	private ListViewAdapter listViewAdapter;
	private ListView listView;
	LayoutInflater inflater;
	private String parentTitle;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater
				.inflate(R.layout.fragment_layout, container, false);
		this.inflater = inflater;
		listView = (ListView) view.findViewById(R.id.listView);
		listViewAdapter = new ListViewAdapter(inflater, list);
		if(listViewAdapter!=null){
			listView.setAdapter(listViewAdapter);
		}
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(mCallBack!=null&&position<list.size()){
					mCallBack.setCallback(list.get(position));
				}		
			}
		});
		return view;
	}

	public void showDialog(String none,String canTye,String carType,String sort,String configuration,String name){
		MyDialog dialog = new MyDialog(getActivity(), canTye, carType, sort,configuration,name,
				R.style.MyDialog);
		dialog.setCallBack(new MyDialog.CallBack() {
			
			@Override
			public void onChange() {
				// TODO Auto-generated method stub
				if(mCallBack!=null){
					mCallBack.onFinish();
				}
			}

		});
		dialog.show();
	}
	
	public String  getParentTitle(){
		return parentTitle;
	}
	public void setCanItem(List<CanItem> list,String parentTitle) {
		this.parentTitle=parentTitle;
		this.list = list;	
		if (listView != null) {			
			listView.setAdapter(listViewAdapter);
		}
	}
	
	interface CallBack{
		void setCallback(CanItem canItem);
		void onFinish();
	};
	
	CallBack mCallBack;
	
	public void setCallBack(CallBack callBack){
		mCallBack=callBack;
	}

}
