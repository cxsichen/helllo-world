package com.console.launcher_console.control;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;



















import org.json.JSONException;
import org.json.JSONObject;

import com.console.launcher_console.MainActivity;
import com.console.launcher_console.R;
import com.ximalaya.speechcontrol.IMainDataCallback;
import com.ximalaya.speechcontrol.IServiceBindSuccessCallBack;
import com.ximalaya.speechcontrol.IServiceDeathListener;
import com.ximalaya.speechcontrol.SpeechControler;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.category.CategoryModel;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.NetworkInfo.State;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class XmlyCardControl extends BroadcastReceiver implements OnClickListener {
	
	private Context context;
	private LinearLayout layout;
	private ImageView img;
	private ImageView pre;
	private ImageView play;
	private ImageView next;
	private TextView name;
	private SpeechControler controler;
	private String nickname;
	private Bitmap bitmap;
	
	public XmlyCardControl(Context context,LinearLayout layout, SpeechControler controler){
		this.context=context;
		this.layout=layout;
		this.controler=controler;
		initXmly();
		initView();
		
	}

	private void initXmly() {
		try {
			if (controler != null) {

				controler.setDebugBack(new IMainDataCallback<String>() {

					@Override
					public void successCallBack(String obj) {
					}

					@Override
					public void errCallBack(String content) {
					}
				});

				controler.addPlayerStatusListener(mXmPlayerStatusListener);
				controler
						.registerServiceBindSuccessCallBack(new IServiceBindSuccessCallBack() {
							@Override
							public void success() {
								Log.i("xxx", "is connect-");
							}
						});
				controler.addServiceDeathListener(new IServiceDeathListener() {
					@Override
					public void onServiceDeath() {
						Log.i("xxx", "is not connect-");
					}
				});
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void initView() {
		img=(ImageView) layout.findViewById(R.id.xmly_img);
		
		pre=(ImageView) layout.findViewById(R.id.xmly_pre);
		play=(ImageView) layout.findViewById(R.id.xmly_play);
		next=(ImageView) layout.findViewById(R.id.xmly_next);
		name=(TextView) layout.findViewById(R.id.xmly_name);
		img.setOnClickListener(this);
		pre.setOnClickListener(this);
		play.setOnClickListener(this);
		next.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.xmly_img:
			try {

				Intent xmlyIntent = context.getPackageManager()
						.getLaunchIntentForPackage(
								"com.ximalaya.ting.android.car");
				context.startActivity(xmlyIntent);
				// Intent intent=new Intent("com.ximalaya.ting.android.car",
				// "com.ximalaya.ting.android.car.activity.MainActivity");
			} catch (Exception e) {
				Toast.makeText(context, "no this funtion", Toast.LENGTH_SHORT);
			}
			break;
		case R.id.xmly_pre:
			if(controler==null){
				return;
			}
			if (!controler.hasPre()) {
				return;
			}
			controler.playPre();
			break;
		case R.id.xmly_play:
			if(controler==null){
				return;
			}
			if(controler.isPlaying()){
				controler.pause();
			}else{
				controler.play();
			}
			break;
		case R.id.xmly_next:
			if(controler==null){
				return;
			}
			if (!controler.hasNext()) {
				return;
			}
			controler.playNext();
			break;

		default:
			break;
		}
	}
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
	}
	
	private void getImgeAndText(){
		String gson=new Gson().toJson(controler.getCurrentTrack().getAnnouncer());
//		Log.i("xxx", "gson==="+gson);
		try {
			JSONObject json = new JSONObject(gson);
			nickname = json.getString("nickname");
//			final String url = jsonPath.getString("avatar_url");
//			 new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					getImage(url);
//				}
//			}).start();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void getImage(String sturl){
		 URL url;
		try {
			url = new URL(sturl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setReadTimeout(10 * 1000);
			conn.setConnectTimeout(10 * 1000);
			conn.setRequestMethod("GET");
			InputStream is = null;
			if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				is = conn.getInputStream();
			} else {
				is = null;
			}
			if (is == null) {
				Log.i("xxx", "is==mull");
			} else {
				byte[] data = readStream(is);
				if (data != null) {
					bitmap = BitmapFactory
							.decodeByteArray(data, 0, data.length);
				}
				setImgeAndText();
				is.close();
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/*
     * 得到图片字节流 数组大小
     * */
    public static byte[] readStream(InputStream inStream) throws Exception{      
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();      
        byte[] buffer = new byte[1024];      
        int len = 0;      
        while( (len=inStream.read(buffer)) != -1){      
            outStream.write(buffer, 0, len);      
        }      
        outStream.close();      
        inStream.close();      
        return outStream.toByteArray();      
    }
    
	private void setImgeAndText(){
//		if(bitmap!=null&&img!=null){
//			 img.setImageBitmap(bitmap);
//		}
		if(nickname!=null&&!nickname.equals("")&&name!=null){
			name.setText(nickname);
		}
	}
	
	private IXmPlayerStatusListener mXmPlayerStatusListener = new IXmPlayerStatusListener() {
		@Override
		public void onSoundSwitch(PlayableModel lastModel, PlayableModel curModel) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onSoundPrepared() {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onSoundPlayComplete() {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onPlayStop() {
			// TODO Auto-generated method stub
//			Log.i("xxx", "onPlayStop");
			play.setImageResource(R.drawable.ic_music_play);
			if(name!=null){
				name.setText(R.string.label_card_xmly);
			}
		}
		
		@Override
		public void onPlayStart() {
			// TODO Auto-generated method stub
//			Log.i("xxx", "onPlayStart");
			play.setImageResource(R.drawable.ic_music_pause);
			getImgeAndText();
			setImgeAndText();
		}
		
		@Override
		public void onPlayProgress(int currPos, int duration) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onPlayPause() {
			// TODO Auto-generated method stub
//			Log.i("xxx", "onPlayPause");
			play.setImageResource(R.drawable.ic_music_play);
//			if(name!=null){
//				name.setText(R.string.label_card_xmly);
//			}
		}
		
		@Override
		public boolean onError(XmPlayerException exception) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void onBufferingStop() {
			// TODO Auto-generated method stub
			if(controler.isPlaying()){
				getImgeAndText();
				setImgeAndText();
			}else{
				name.setText(R.string.label_card_xmly);
			}
		}
		
		@Override
		public void onBufferingStart() {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onBufferProgress(int percent) {
			// TODO Auto-generated method stub
		}
	};
	
}
