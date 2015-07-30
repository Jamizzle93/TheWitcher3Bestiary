package com.mysticwater.thewitcher3bestiary;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class BeastItemActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_beast_item);
    Intent intent = getIntent();
    String message = intent.getStringExtra(MainActivity.BEAST_ITEM);



    TextView beastName = (TextView) findViewById(R.id.beastName);
    beastName.setText(message);

    Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/morpheus.ttf");
    beastName.setTypeface(typeFace);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
