package com.mysticwater.thewitcher3bestiary;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity {

  ExpandableListAdapter listAdapter;
  ExpandableListView expandableListView;
  List<String> listDataHeader;
  HashMap<String, List<String>> listDataChild;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //get the listview
    expandableListView = (ExpandableListView) findViewById(R.id.beastListView);

    //preparing list data
    prepareListData();

    listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

    //setting list adapter
    expandableListView.setAdapter(listAdapter);
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

  private void prepareListData() {
    listDataHeader = new ArrayList<String>();
    listDataChild = new HashMap<String, List<String>>();

    //Adding child data
    listDataHeader.add("Beasts");
    listDataHeader.add("Cursed Ones");
    listDataHeader.add("Draconids");
    listDataHeader.add("Elementa");
    listDataHeader.add("Hybrids");
    listDataHeader.add("Insectoids");
    listDataHeader.add("Necrophages");
    listDataHeader.add("Ogroids");
    listDataHeader.add("Relicts");
    listDataHeader.add("Specters");
    listDataHeader.add("Vampires");

    List<String> beastList = new ArrayList<String>();
    String[] beasts = {"Hello", "One", "Two"};
    for (String s : beasts){
      beastList.add(s);
    }

    List<String> cursedOnesList = new ArrayList<String>();
    String[] cursedOnes = {"Werewolf", "James", "Sam"};
    for (String s : cursedOnes){
      cursedOnesList.add(s);
    }

    listDataChild.put(listDataHeader.get(0), beastList);
    listDataChild.put(listDataHeader.get(1), cursedOnesList);
  }
}
