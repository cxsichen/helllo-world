package com.console.canreader.view;

import com.console.canreader.R;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class StepPreference extends Preference implements OnClickListener {

	TextView tv;
	ImageView preButton;
	ImageView nextButton;
	int Max = 100;
	int Min = 0;
	
	String strSave="0";

	public StepPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setLayoutResource(R.layout.eql_freq_layout);
	}

	public StepPreference(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public StepPreference(Context context) {
		this(context, null);
	}

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		preButton = (ImageView) view.findViewById(R.id.pre_button);
		nextButton = (ImageView) view.findViewById(R.id.next_button);
		tv = (TextView) view.findViewById(R.id.freq_tv);
		preButton.setOnClickListener(this);
		nextButton.setOnClickListener(this);
		tv.setText(strSave);

	}

	public void setFreqTv(String str) {
		if (Integer.parseInt(str) < Min)
			str = String.valueOf(Min);
		if (Integer.parseInt(str) > Max)
			str = String.valueOf(Max);
		strSave=str;
		if (tv != null) {		
			tv.setText(str);
		}
	}

	public void setMax(int max) {
		this.Max = max;
	}

	public void setMin(int min) {
		this.Min = min;
	}

	public String getFreqTv() {
		if (tv != null)
			return tv.getText().toString();
		else
			return null;
	}

	@Override
	protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		/*
		 * Suppose a client uses this preference type without persisting. We
		 * must save the instance state so it is able to, for example, survive
		 * orientation changes.
		 */
		final Parcelable superState = super.onSaveInstanceState();
		if (isPersistent()) {
			return superState;
		}
		// Save the instance state
		final SavedState myState = new SavedState(superState);
		myState.str = strSave;
		return myState;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (!state.getClass().equals(SavedState.class)) {
			super.onRestoreInstanceState(state);
			return;
		}
		   
		// Restore the instance state
		SavedState myState = (SavedState) state;
		super.onRestoreInstanceState(myState.getSuperState());
		
		String str = myState.str;
		setFreqTv(str);
	}

	/**
	 * SavedState, a subclass of {@link BaseSavedState}, will store the state of
	 * MyPreference, a subclass of Preference.
	 * <p>
	 * It is important to always call through to super methods.
	 */
	private static class SavedState extends BaseSavedState {
		String str;

		public SavedState(Parcel source) {
			super(source);
			// Restore the click counter
			str = source.readString();
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			// Save the click counter
			dest.writeString(str);
		}

		public SavedState(Parcelable superState) {
			super(superState);
		}

	}

	OnStepPreferenceClickListener mOnStepPreferenceClickListener;

	public interface OnStepPreferenceClickListener {
		public void onPreButtonClick(Preference preference);

		public void onNextButtonClick(Preference preference);
	}

	public void setOnStepPreferenceClickListener(
			OnStepPreferenceClickListener mOnStepPreferenceClickListener) {
		this.mOnStepPreferenceClickListener = mOnStepPreferenceClickListener;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.next_button:
			if (mOnStepPreferenceClickListener != null)
				mOnStepPreferenceClickListener.onNextButtonClick(this);
			break;
		case R.id.pre_button:
			if (mOnStepPreferenceClickListener != null)
				mOnStepPreferenceClickListener.onPreButtonClick(this);
			break;

		default:
			break;
		}

	}
}