package cn.colink.serialport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class PriorityDlg extends Dialog {

	private Context context;
	private GridView dlg_grid = null;
	private ImageView imageView;
	private int position;
	public static LinkedList<String> playList = new LinkedList<String>();
	
	public PriorityDlg(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public PriorityDlg(Context context, int theme, LinkedList<String> playList) {
		super(context, theme);
		this.context = context;
		this.playList = playList;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 设置对话框使用的布局文件
		this.setContentView(R.layout.change_logo_dialog);

		dlg_grid = (GridView) findViewById(R.id.change_logo_grid);
//		int [] intt={R.id.c1,R.id.c2,R.id.c3
//				,R.id.c4,R.id.c5,R.id.c6,R.id.c7,R.id.c8,R.id.c9
//				,R.id.c10,R.id.c11,R.id.c12,R.id.c13,R.id.c14,R.id.c15,R.id.c16,R.id.c17,R.id.c18,R.id.c19
//				,R.id.c20,R.id.c21,R.id.c22,R.id.c23,R.id.c24,R.id.c25,R.id.c26,R.id.c27,R.id.c28,R.id.c29
//				,R.id.c30,R.id.c31,R.id.c32,R.id.c33,R.id.c34,R.id.c35,R.id.c36,R.id.c37};
		// 设置GridView的数据源
		dlg_grid.setAdapter(new GridAdapter(context, playList));  

		// 为GridView设置监听器
		dlg_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//Toast.makeText(context, "" + arg2, Toast.LENGTH_SHORT).show();// 显示信息;
				position = arg2;
				//点击item后Dialog消失
				PriorityDlg.this.dismiss();

			}
		});
	}
	//返回点击的位置
	public int getPosition() {
		return position;
	}
	


    
}