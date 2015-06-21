package com.mysticwater.thewitcher3bestiary;

import android.app.Activity;
import android.hardware.camera2.TotalCaptureResult;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

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

    expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
      @Override
      public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        Toast.makeText(getApplicationContext(),
          "Group Clicked " + listDataHeader.get(groupPosition),
          Toast.LENGTH_SHORT).show();
        return false;
      }
    });

    expandableListView.setOnGroupExpandListener(new OnGroupExpandListener() {
      @Override
      public void onGroupExpand(int groupPosition) {
        Toast.makeText(getApplicationContext(),
          listDataHeader.get(groupPosition) + " Expanded",
          Toast.LENGTH_SHORT).show();
      }
    });

    expandableListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
      @Override
      public void onGroupCollapse(int groupPosition) {
        Toast.makeText(getApplicationContext(),
          listDataHeader.get(groupPosition) + " Collapsed",
          Toast.LENGTH_SHORT).show();
      }
    });

    expandableListView.setOnChildClickListener(new OnChildClickListener() {
      @Override
      public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                  int childPosition, long id) {
        Toast.makeText(
          getApplicationContext(),
          listDataHeader.get(groupPosition)
            + " : "
            + listDataChild.get(
            listDataHeader.get(groupPosition))
            .get(childPosition), Toast.LENGTH_SHORT)
          .show();
        return false;
      }
    });


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
    for (String s : beasts) {
      beastList.add(s);
    }

    List<String> cursedOnesList = new ArrayList<String>();
    String[] cursedOnes = {"Werewolf", "James", "Sam"};
    for (String s : cursedOnes) {
      cursedOnesList.add(s);
    }

    List<String> draconidsList = new ArrayList<String>();
    String[] draconids = {"Dragon"};
    for (String s : draconids) {
      draconidsList.add(s);
    }

    List<String> elementaList = new ArrayList<String>();
    String[] elementa = {"Earth Golem"};
    for (String s : elementa) {
      elementaList.add(s);
    }

    List<String> hybridsList = new ArrayList<String>();
    String[] hybrids = {"Archgriffin", "Griffin"};
    for (String s : hybrids) {
      hybridsList.add(s);
    }

    List<String> insectoidsList = new ArrayList<String>();
    String[] insectoids = {"Giant Spider"};
    for (String s : insectoids) {
      insectoidsList.add(s);
    }

    List<String> necrophagesList = new ArrayList<String>();
    String[] necrophages = {"Necrophage"};
    for (String s : necrophages) {
      necrophagesList.add(s);
    }

    List<String> ogroidsList = new ArrayList<String>();
    String[] ogroids = {"Rock Troll"};
    for (String s : ogroids) {
      ogroidsList.add(s);
    }

    List<String> relictsList = new ArrayList<String>();
    String[] relicts = {"Leshen"};
    for (String s : relicts) {
      relictsList.add(s);
    }

    List<String> spectersList = new ArrayList<String>();
    String[] specters = {"Wraith", "Noonwraith"};
    for (String s : specters) {
      spectersList.add(s);
    }

    List<String> vampiresList = new ArrayList<String>();
    String[] vampires = {"Enchinda", "Vampire"};
    for (String s : vampires) {
      vampiresList.add(s);
    }

    listDataChild.put(listDataHeader.get(0), beastList);
    listDataChild.put(listDataHeader.get(1), cursedOnesList);
    listDataChild.put(listDataHeader.get(2), draconidsList);
    listDataChild.put(listDataHeader.get(3), elementaList);
    listDataChild.put(listDataHeader.get(4), hybridsList);
    listDataChild.put(listDataHeader.get(5), insectoidsList);
    listDataChild.put(listDataHeader.get(6), necrophagesList);
    listDataChild.put(listDataHeader.get(7), ogroidsList);
    listDataChild.put(listDataHeader.get(8), relictsList);
    listDataChild.put(listDataHeader.get(9), spectersList);
    listDataChild.put(listDataHeader.get(10), vampiresList);
  }

}
