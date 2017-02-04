package cn.colink.serialport;

import java.util.ArrayList;
import java.util.LinkedList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
	public static LinkedList<String> playList = new LinkedList<String>();
	private LayoutInflater mInflater;
	private Context mContext;
	LinearLayout.LayoutParams params;

	public GridAdapter(Context context,LinkedList<String> playList) {
		this.playList = playList;
		mContext = context;
		mInflater = LayoutInflater.from(context);

		params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.height=200;
		params.gravity = Gravity.CENTER;
	}

	public int getCount() {
		return playList.size();
	}

	public Object getItem(int position) {
		return playList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ItemViewTag viewTag;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.change_logo_item, null);

			// construct an item tag
			viewTag = new ItemViewTag(
					(ImageView) convertView.findViewById(R.id.grid_icon));
			convertView.setTag(viewTag);
		} else {
			viewTag = (ItemViewTag) convertView.getTag();
		}

		// set icon
		viewTag.mIcon.setImageBitmap(BitmapFactory.decodeFile(playList.get(position)));
		viewTag.mIcon.setLayoutParams(params);
		return convertView;
	}

	class ItemViewTag {
		protected ImageView mIcon;

		/**
		 * The constructor to construct a navigation view tag
		 * 
		 * @param name
		 *            the name view of the item
		 * @param size
		 *            the size view of the item
		 * @param icon
		 *            the icon view of the item
		 */
		public ItemViewTag(ImageView icon) {
			this.mIcon = icon;
		}
	}

}