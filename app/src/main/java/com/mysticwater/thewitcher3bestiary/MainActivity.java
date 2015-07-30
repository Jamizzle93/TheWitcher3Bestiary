package com.mysticwater.thewitcher3bestiary;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
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

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity {

  public final static String BEAST_ITEM = "com.mysticwater.thewitcher3bestiary.BEASTITEM";

  ExpandableListAdapter listAdapter;
  ExpandableListView expandableListView;
  List<String> listDataHeader;
  HashMap<String, List<String>> listDataChild;
  List<String> childList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //get the listview
    expandableListView = (ExpandableListView) findViewById(R.id.beastListView);

    createGroupList();

    createCollections();

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

        String beast = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);

        Toast.makeText(
          getApplicationContext(),
          listDataHeader.get(groupPosition)
            + " : "
            + beast, Toast.LENGTH_SHORT)
          .show();

        openItem(v, beast);

        return false;
      }
    });

    String workingDir = System.getProperty("user.dir");
    System.out.println("Current working directory : " + workingDir);

    try {
      CSVReader reader = new CSVReader(new FileReader(getFileStreamPath("beasts.csv")));
      String[] nextLine;
      while ((nextLine = reader.readNext()) != null) {
        // nextLine[] is an array of values from the line
        System.out.println(nextLine[0] + nextLine[1] + "etc...");
      }
    } catch (Exception e) {
      System.out.println("CSV file cannot be found." + e);
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

  private void createGroupList() {
    listDataHeader = new ArrayList<String>();
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
      if (beast.equals("Beasts")) {
        loadChild(beasts);
      } else if (beast.equals("Cursed Ones")) {
        loadChild(cursedOnes);
      } else if (beast.equals("Draconids")) {
        loadChild(draconids);
      } else if (beast.equals("Elementa")) {
        loadChild(elementa);
      } else if (beast.equals("Hybrids")) {
        loadChild(hybrids);
      } else if (beast.equals("Insectoids")) {
        loadChild(insectoids);
      } else if (beast.equals("Necrophages")) {
        loadChild(necrophages);
      } else if (beast.equals("Ogroids")) {
        loadChild(ogroids);
      } else if (beast.equals("Relicts")) {
        loadChild(relicts);
      } else if (beast.equals("Specters")) {
        loadChild(specters);
      } else if (beast.equals("Vampires")) {
        loadChild(vampires);
      }
      listDataChild.put(beast, childList);
    }
  }

  private void loadChild(String[] beasts) {
    childList = new ArrayList<String>();
    for (String beast : beasts) {
      childList.add(beast);
    }
  }

  /**
   * Called when the user clicks an item in a list
   */
  public void openItem(View view, String beast) {
    Intent intent = new Intent(this, BeastItemActivity.class);
    intent.putExtra(BEAST_ITEM, beast);
    startActivity(intent);
  }

}
