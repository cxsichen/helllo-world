package com.console.launcher_console.control;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.kuwo.autosdk.api.KWAPI;
import cn.kuwo.autosdk.api.OnExitListener;
import cn.kuwo.autosdk.api.OnPlayEndListener;
import cn.kuwo.autosdk.api.OnPlayerStatusListener;
import cn.kuwo.autosdk.api.PlayEndType;
import cn.kuwo.autosdk.api.PlayState;
import cn.kuwo.autosdk.api.PlayerStatus;
import cn.kuwo.autosdk.bean.Music;
import cn.kuwo.autosdk.bean.NetResource;

import com.console.launcher_console.R;
import com.console.launcher_console.util.BytesUtil;
import com.console.launcher_console.util.PreferenceUtil;
import com.console.launcher_console.view.RunTextView;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MusicCardControl implements
		OnClickListener {

	private LinearLayout musicCardLayout;
	private Context context;
	private ImageView musicApp;
	private ImageView musicPlay;
	private ImageView musicPrev;
	private ImageView musicNext;
	private ImageView musicCover;
	private TextView musicName;
	private KWAPI mKwapi;
	private PlayerStatus mPlayerStatus = PlayerStatus.STOP;
	private Animation amt;
	// private AudioManager am;
	private static int i = 0;
	public static final String ACTION_MENU_UP = "com.console.MENU_UP";
	public static final String ACTION_MENU_DOWN = "com.console.MENU_DOWN";
	public static final String ACTION_STOP_MUSIC = "com.console.STOP_MUSIC";
	public static final String ACTION_PLAY_PAUSE = "com.console.PLAY_PAUSE";

	public MusicCardControl(Context context, LinearLayout layout) {
		musicCardLayout = layout;
		this.context = context;
		init();
		mRegisterReceiver();
	}

	private void mRegisterReceiver(){
		
		mKwapi.registerExitListener(context, new OnExitListener() {
			
			@Override
			public void onExit() {
				Log.i("cxs","========onExit=====");
				stopView();
			}
		});
	}

	private void init() {
		// TODO Auto-generated method stub

		musicApp = (ImageView) musicCardLayout
				.findViewById(R.id.ev_music_app);
		musicPlay = (ImageView) musicCardLayout
				.findViewById(R.id.ev_music_play);
		musicPrev = (ImageView) musicCardLayout
				.findViewById(R.id.ev_music_prev);
		musicNext = (ImageView) musicCardLayout
				.findViewById(R.id.ev_music_next);
		musicCover = (ImageView) musicCardLayout
				.findViewById(R.id.ev_music_cover);
		musicName = (TextView) musicCardLayout
				.findViewById(R.id.music_name);

		musicApp.setOnClickListener(this);
		musicPlay.setOnClickListener(this);
		musicPrev.setOnClickListener(this);
		musicNext.setOnClickListener(this);

		amt = AnimationUtils.loadAnimation(context, R.anim.tip);
		amt.setInterpolator(new LinearInterpolator());

		/*
		 * amt =new RotateAnimation(0, 359,
		 * Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		 * amt.setInterpolator(new LinearInterpolator());
		 * amt.setRepeatCount(11111); amt.setDuration(100000);
		 */
		mKwapi = KWAPI.createKWAPI(context, "auto");
		// mPlayerStatus = PlayerStatus.STOP;
		// controlPause();

		// am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
   
		mKwapi.registerPlayerStatusListener(context,
				new OnPlayerStatusListener() {


					@Override
					public void onPlayerStatus(PlayerStatus playerStatus, Music music) {
						Log.i("cxs", "====playerStatus======" + playerStatus);
						mPlayerStatus = playerStatus;
						initViewInfo(music);
						controlPlayButton();
					}
				});
		

	}

	protected void initViewInfo(Music music) {
		if (musicCover != null&&music!=null) {
		//	Log.i("xxx", "=== music.toDebugString===" + music.toDebugString());
		}
		if (musicName != null&&music!=null) {
			if(mPlayerStatus == PlayerStatus.STOP){
				musicName.setText(R.string.label_card_music);
			}else{
				musicName.setText(music.name+" - "+music.artist);
			}
		}
	}
	
	private void stopView(){
		musicName.setText(R.string.label_card_music);
		musicPlay.setImageResource(R.drawable.ic_music_play);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ev_music_play:
		//	if (PreferenceUtil.getMode(context) == 1)
				controlPlay();			
			break;
		case R.id.ev_music_app:
			startKWApp();
			break;
		case R.id.ev_music_next:
		//	if (PreferenceUtil.getMode(context) == 1)
				controlNext();
			break;
		case R.id.ev_music_prev:
		//	if (PreferenceUtil.getMode(context) == 1)
				controlPrevious();
			break;
		default:
			break;
		}
	}

	private void reCreateKWAPI() {
		mKwapi = KWAPI.createKWAPI(context, "auto");
	}

	public void controlPlayButton() {
		// TODO Auto-generated method stub
		if (mPlayerStatus.equals(PlayerStatus.PLAYING)) {
			musicPlay.setImageResource(R.drawable.ic_music_pause);
			// musicCover.startAnimation(amt);
		} else {
			musicPlay.setImageResource(R.drawable.ic_music_play);
			/*
			 * PreferenceUtil.setMode(context, 1);
			 * mSerialPortControl.sendMsg("F5020000" +
			 * BytesUtil.intToHexString(1));
			 */

			// musicCover.clearAnimation();
		}
	}

	private void controlNext() {
		mKwapi.setPlayState(context, PlayState.STATE_NEXT);
	}

	private void controlPrevious() {
		mKwapi.setPlayState(context, PlayState.STATE_PRE);
	}

	private void controlPlay() {
		Log.i("cxs", "--------controlPlay----" + mPlayerStatus);
		if (mPlayerStatus.equals(PlayerStatus.PLAYING)) {
			mKwapi.setPlayState(context, PlayState.STATE_PAUSE);
		} else {
			mKwapi.setPlayState(context, PlayState.STATE_PLAY);
		}
	}

	private void controlPause() {
		mKwapi.setPlayState(context, PlayState.STATE_PAUSE);
	}
	
	private void stopKWApp(){
		mKwapi.setPlayState(context, PlayState.STATE_PAUSE);
		mKwapi.exitAPP(context);
	}

	private void startKWApp() {
		mKwapi.startAPP(context, true);
	}

	public void unregister() {
		// mKwapi.unRegisterPlayerStatusListener(context);
		mKwapi.unRegisterExitListener(context);
	}

	public void stopPlayStatus() {
		
		/*
		 * new Handler().postDelayed(new Runnable() { public void run() {
		 * Log.i("cxs", "====am.isMusicActive()==========" +
		 * am.isMusicActive()); if (!am.isMusicActive()) {
		 * musicPlay.setImageResource(R.drawable.ic_music_play);
		 * musicCover.clearAnimation(); } } }, 1000);
		 */

	}


}
