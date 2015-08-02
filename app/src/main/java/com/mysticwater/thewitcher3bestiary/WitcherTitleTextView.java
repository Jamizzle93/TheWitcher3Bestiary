package com.mysticwater.thewitcher3bestiary;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by James on 02/08/2015.
 */
public class WitcherTitleTextView extends TextView {

  public WitcherTitleTextView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  public WitcherTitleTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public WitcherTitleTextView(Context context) {
    super(context);
    init();
  }

  private void init() {
    if (!isInEditMode()) {
      Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font.ttf");
      setTypeface(tf);
    }
  }

}
