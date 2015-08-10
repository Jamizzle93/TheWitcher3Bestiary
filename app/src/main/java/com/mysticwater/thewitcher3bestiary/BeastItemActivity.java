package com.mysticwater.thewitcher3bestiary;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import static com.mysticwater.thewitcher3bestiary.BeastsContract.BeastEntry;


public class BeastItemActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_beast_item);

    Intent intent = getIntent();
    String message = intent.getStringExtra(MainActivity.BEAST_ITEM);

    hideActionBar();

    String[] beastData = readFromDatabase(message);

    //Set name text
    AutoResizeTextView beastName = (AutoResizeTextView) findViewById(R.id.beastName);
    beastName.setText(beastData[0].toUpperCase());

    //Set type text
    TextView beastType = (TextView) findViewById(R.id.typeValue);
    beastType.setText(beastData[1].toUpperCase());

    //Set image
    ImageView beastImage = (ImageView) findViewById(R.id.beastImage);
    int res = getResources().getIdentifier(trimString(beastData[0]), "drawable", this.getPackageName());
    if (res == 0) {
      res = getResources().getIdentifier("bear", "drawable", this.getPackageName());
    }
    beastImage.setImageResource(res);

    TextView vulnerabilitiesLabel = (TextView) findViewById(R.id.vulnerabilitiesLabel);
    String[] vulnerabilities = retrieveVulnerabilities(beastData[2]);

    setFont(beastName, "morpheus.ttf", "bold");
    setFont(beastType, "PFDinThin.ttf");
    setFont(vulnerabilitiesLabel, "PFDinThin.ttf", "bold");

    TableLayout tl = (TableLayout) findViewById(R.id.TableLayout01);
    for (String v : vulnerabilities) {
      TableRow tr = new TableRow(this);
      tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

      int imageRes = getResources().getIdentifier(trimString(v), "drawable", this.getPackageName());
      ImageView vulnerabilityImage = new ImageView(this);
      vulnerabilityImage.setPadding(0, 0, 0, 25);
      vulnerabilityImage.setImageResource(imageRes);

      TextView vulnerability = new TextView(this);
      vulnerability.setText(v);
      vulnerability.setTextColor(getResources().getColor(R.color.orange));
      setFont(vulnerability, "PFDinThin.ttf");
      vulnerability.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);

      vulnerability.setPadding(25, 0, 0, 0);
      vulnerabilityImage.requestLayout();

      tr.addView(vulnerabilityImage);
      tr.addView(vulnerability);
      tl.addView(tr);
    }
  }

  private void setFont(TextView textView, String font) {
    Typeface type = Typeface.createFromAsset(getAssets(), "fonts/" + font);
    textView.setTypeface(type);
  }

  private void setFont(TextView textView, String font, String style) {
    Typeface type = Typeface.createFromAsset(getAssets(), "fonts/" + font);
    if (style.equals("bold")) {
      textView.setTypeface(type, Typeface.BOLD);
    }
  }

  private void hideActionBar() {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.hide();
    }
  }

  private String trimString(String s) {
    return s.replaceAll("\\s+", "").replaceAll("\'", "").toLowerCase();
  }

  private String[] retrieveVulnerabilities(String s) {
    return s.split("\\. ");
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
      c.close();
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
}
