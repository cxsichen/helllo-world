package cn.colink.serialport;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;

import cn.colink.serialport.PriorityDlg;
import cn.colink.serialport.R;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class AccLogoSelectActivity extends Activity {
	public static LinkedList<String> playList = new LinkedList<String>();
	Button button;
	PriorityDlg dlg;
	private ImageView iv;
	final String ACCLOGO = "Console_acc_logo";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select_logo);
		// showFloatCarInfoWaring(this);

		iv = (ImageView) findViewById(R.id.iv1);
		getPicFile(playList, new File("/mnt/sdcard/BrandLogo/"));

		String str = android.provider.Settings.System.getString(
				getContentResolver(), ACCLOGO);
		if (str != null) {
			Bitmap bm = BitmapFactory.decodeFile(str);
			if (bm != null) {
				iv.setImageBitmap(bm);
			}
		}

		button = (Button) findViewById(R.id.btn);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (dlg == null) {
					dlg = new PriorityDlg(AccLogoSelectActivity.this,
							R.style.dlg_priority, playList);
					dlg.setOnDismissListener(new OnDismissListener() {

						@Override
						public void onDismiss(DialogInterface dialog) {
							// TODO Auto-generated method stub
							int ps = dlg.getPosition();
							if (ps < playList.size()) {
								iv.setImageBitmap(BitmapFactory
										.decodeFile(playList.get(ps)));
								android.provider.Settings.System.putString(
										getContentResolver(), ACCLOGO,
										playList.get(ps));
							}
						}
					});

				}
				dlg.show();
			}
		});
	}

	private void getPicFile(final LinkedList<String> list, File file) {

		file.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				// TODO Auto-generated method stub

				String name = file.getName();
				int i = name.lastIndexOf('.');
				if (i != -1) {
					name = name.substring(i);
					if (name.equalsIgnoreCase(".png")) {

						String path = file.getAbsolutePath();
						list.add(path);
						return true;
					}
				} else if (file.isDirectory()) {
					getPicFile(list, file);
				}
				return false;
			}
		});
	}
}
