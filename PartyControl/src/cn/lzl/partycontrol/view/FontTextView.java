package cn.lzl.partycontrol.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontTextView extends TextView{

    public FontTextView(Context context) {
        this(context,null);
    }
    public FontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public FontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //changeTypeFace(context);
    }
    
    @SuppressWarnings("unused")
    private void changeTypeFace(Context context) {
        Typeface mtf = Typeface.createFromAsset(context.getAssets(),
                "fonts/GBK.TTF");
        super.setTypeface(mtf);
    }
    
}
