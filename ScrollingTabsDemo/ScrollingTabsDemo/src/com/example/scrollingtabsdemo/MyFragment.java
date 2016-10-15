package com.example.scrollingtabsdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyFragment extends Fragment {

	private int index;

	public MyFragment(int index) {
		this.index = index;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_my, container, false);
		TextView tv = (TextView) view.findViewById(R.id.tv);
		tv.setText("Fragment-" + index);
		return view;

	}
}

