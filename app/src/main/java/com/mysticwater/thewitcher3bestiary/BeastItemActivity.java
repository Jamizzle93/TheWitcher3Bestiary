package com.mysticwater.thewitcher3bestiary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.sql.SQLOutput;

import static com.mysticwater.thewitcher3bestiary.BeastsContract.*;


public class BeastItemActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActionBar actionBar = getSupportActionBar();
    actionBar.hide();
    setContentView(R.layout.activity_beast_item);
    Intent intent = getIntent();
    String message = intent.getStringExtra(MainActivity.BEAST_ITEM);

    String[] beastData = readFromDatabase(message);

    TextView beastName = (TextView) findViewById(R.id.beastName);
    TextView beastType = (TextView) findViewById(R.id.typeValue);
    ImageView beastImage = (ImageView) findViewById(R.id.beastImage);

    beastName.setText(beastData[0].toUpperCase());
    beastType.setText(beastData[1].toUpperCase());

    int res = getResources().getIdentifier(convertBeastName(beastData[0]), "drawable", this.getPackageName());
    if(res == 0)
    {
      res = getResources().getIdentifier("bear", "drawable", this.getPackageName());
    }
    System.out.println("Res: " + res);
    System.out.println("Beast name: " + convertBeastName(beastData[0]));
    beastImage.setImageResource(res);

    TextView vulnerabilitiesLabel = (TextView) findViewById(R.id.vulnerabilitiesLabel);
    String[] vulnerabilities = retrieveVulnerabilities(beastData[2]);

    Typeface titleType = Typeface.createFromAsset(getAssets(), "fonts/morpheus.ttf");
    Typeface bodyType = Typeface.createFromAsset(getAssets(), "fonts/PFDinThin.ttf");

    beastName.setTypeface(titleType);
    beastType.setTypeface(bodyType);
    vulnerabilitiesLabel.setTypeface(bodyType);

    TableLayout tl = (TableLayout) findViewById(R.id.TableLayout01);
    for (String v : vulnerabilities) {
      TableRow tr = new TableRow(this);
      tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
      TextView vulnerability = new TextView(this);
      vulnerability.setText(v);
      vulnerability.setTypeface(bodyType);
      tr.addView(vulnerability);
      tl.addView(tr);
    }
  }

  private String convertBeastName(String s) {
    return s.replaceAll("\\s+","").toLowerCase();
  }

  private String[] retrieveVulnerabilities(String s) {
    String[] splitArray = s.split("\\. ");
    return splitArray;
  }

  private String[] readFromDatabase(String beastName) {
    BeastsDbHelper mDbHelper = new BeastsDbHelper(getBaseContext());

    SQLiteDatabase db = mDbHelper.getReadableDatabase();

    String[] beastData = new String[3];

    String[] projection = {
      BeastEntry.COLUMN_NAME_TYPE,
      BeastEntry.COLUMN_NAME_BEAST,
      BeastEntry.COLUMN_NAME_VULNERABILITIES
    };

    String selection = "beast =?";
    String[] selectionArgs = {beastName};

    Cursor c = db.query(
      BeastEntry.TABLE_NAME,
      projection,
      selection,
      selectionArgs,
      null,
      null,
      null
    );

    if (c != null) {
      if (c.moveToFirst()) {
        beastData[0] = (c.getString(c.getColumnIndex("beast")));
        beastData[1] = (c.getString(c.getColumnIndex("type")));
        beastData[2] = (c.getString(c.getColumnIndex("vulnerabilities")));
      }
    }
    return beastData;
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

  /**
   * Method taken from http://stackoverflow.com/questions/10766716/set-font-for-all-textviews-in-activity
   */
  private void overrideFonts(final Context context, final View v) {
    try {
      if (v instanceof ViewGroup) {
        ViewGroup vg = (ViewGroup) v;
        for (int i = 0; i < vg.getChildCount(); i++) {
          View child = vg.getChildAt(i);
          overrideFonts(context, child);
        }
      } else if (v instanceof TextView) {
        ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "font.ttf"));
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to overrideFonts");
    }
  }


}
