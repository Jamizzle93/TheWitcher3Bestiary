package com.mysticwater.thewitcher3bestiary;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

import com.mysticwater.thewitcher3bestiary.BeastsContract.BeastEntry;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {

  public final static String BEAST_ITEM = "com.mysticwater.thewitcher3bestiary.BEASTITEM";

  private ExpandableListView expandableListView;
  private List<String> listDataHeader;
  private HashMap<String, List<String>> listDataChild;
  private List<String> childList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //Create database and produce headers
    createDatabaseFromCsv();
    processHeaders();

    //Set font for heading
    TextView heading = (TextView) findViewById(R.id.listHeading);
    setFont(heading, "morpheus.ttf");

    expandableListView = (ExpandableListView) findViewById(R.id.beastListView);
    ExpandableListAdapter listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

    //setting list adapter
    expandableListView.setAdapter(listAdapter);

    expandableListView.setOnGroupExpandListener(new OnGroupExpandListener() {
      int previousGroup = -1;

      @Override
      public void onGroupExpand(int groupPosition) {
        if (groupPosition != previousGroup)
          expandableListView.collapseGroup(previousGroup);
        previousGroup = groupPosition;
      }
    });

    expandableListView.setOnChildClickListener(new OnChildClickListener() {
      @Override
      public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                  int childPosition, long id) {

        String beast = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
        openItem(v, beast);

        return false;
      }
    });
  }

  private void setFont(TextView textView, String font) {
    Typeface type = Typeface.createFromAsset(getAssets(), "fonts/" + font);
    textView.setTypeface(type);
  }

  private void processHeaders() {
    BeastsDbHelper mDbHelper = new BeastsDbHelper(getBaseContext());
    SQLiteDatabase db = mDbHelper.getReadableDatabase();

    String query = "select * from " + BeastEntry.TABLE_NAME;
    Cursor c = db.rawQuery(query, null);

    listDataHeader = new ArrayList<String>();

    if (c.moveToFirst()) {
      while (!c.isAfterLast()) {
        String type = c.getString(c.getColumnIndex(BeastEntry.COLUMN_NAME_TYPE));

        //Build Headers
        if (!(listDataHeader.contains(type))) {
          listDataHeader.add(type);
        }

        c.moveToNext();
      }
    }
    c.close();

    createCollections();
  }

  private void createDatabaseFromCsv() {

    BeastsDbHelper mDbHelper = new BeastsDbHelper(getBaseContext());
    SQLiteDatabase db = mDbHelper.getWritableDatabase();

    InputStream inputStream = getResources().openRawResource(R.raw.beasts);
    CSVReader csv = new CSVReader(inputStream);
    List<String[]> beastList = csv.read();

    for (String[] beast : beastList) {
      ContentValues values = new ContentValues();
      values.put(BeastEntry.COLUMN_NAME_TYPE, beast[0]);
      values.put(BeastEntry.COLUMN_NAME_BEAST, beast[1]);
      values.put(BeastEntry.COLUMN_NAME_VULNERABILITIES, beast[2]);

      db.insert(
        BeastEntry.TABLE_NAME,
        null,
        values
      );
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
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

  private void createCollections() {
    Resources res = getResources();

    String[] beasts = res.getStringArray(R.array.beasts);
    String[] cursedOnes = res.getStringArray(R.array.cursedOnes);
    String[] draconids = res.getStringArray(R.array.draconids);
    String[] elementa = res.getStringArray(R.array.elementa);
    String[] hybrids = res.getStringArray(R.array.hybrids);
    String[] insectoids = res.getStringArray(R.array.insectoids);
    String[] necrophages = res.getStringArray(R.array.necrophages);
    String[] ogroids = res.getStringArray(R.array.ogroids);
    String[] relicts = res.getStringArray(R.array.relicts);
    String[] specters = res.getStringArray(R.array.specters);
    String[] vampires = res.getStringArray(R.array.vampires);

    listDataChild = new HashMap<String, List<String>>();

    for (String beast : listDataHeader) {
      switch (beast) {
        case "Beasts":
          loadChild(beasts);
          break;
        case "Cursed Ones":
          loadChild(cursedOnes);
          break;
        case "Draconids":
          loadChild(draconids);
          break;
        case "Elementa":
          loadChild(elementa);
          break;
        case "Hybrids":
          loadChild(hybrids);
          break;
        case "Insectoids":
          loadChild(insectoids);
          break;
        case "Necrophages":
          loadChild(necrophages);
          break;
        case "Ogroids":
          loadChild(ogroids);
          break;
        case "Relicts":
          loadChild(relicts);
          break;
        case "Specters":
          loadChild(specters);
          break;
        case "Vampires":
          loadChild(vampires);
          break;
      }
      listDataChild.put(beast, childList);
    }
  }

  private void loadChild(String[] beasts) {
    childList = new ArrayList<String>();
    Collections.addAll(childList, beasts);
  }

  /**
   * Called when the user clicks an item in a list
   */
  private void openItem(View view, String beast) {
    Intent intent = new Intent(this, BeastItemActivity.class);
    intent.putExtra(BEAST_ITEM, beast);
    startActivity(intent);
  }
}
