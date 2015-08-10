package com.mysticwater.thewitcher3bestiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashScreen extends Activity {

  private final int SPLASH_DISPLAY_LENGTH = 2500;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_screen);

    ImageView image = (ImageView) findViewById(R.id.splashImage);
    int res = getResources().getIdentifier("appicon", "drawable", this.getPackageName());

    image.setImageResource(res);

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
                /* Create an Intent that will start the Menu-Activity. */
        Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
        SplashScreen.this.startActivity(mainIntent);
        SplashScreen.this.finish();
      }
    }, SPLASH_DISPLAY_LENGTH);
  }


}
