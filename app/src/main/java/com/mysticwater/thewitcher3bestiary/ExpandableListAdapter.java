package com.mysticwater.thewitcher3bestiary;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

/**
 * Created by James on 17/06/2015.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

  private Context _context;
  private List<String> _listDataHeader;
  private HashMap<String, List<String>> _listDataChild;

  public ExpandableListAdapter(Context context, List<String> listDataHeader,
                               HashMap<String, List<String>> listDataChild) {
    this._context = context;
    this._listDataHeader = listDataHeader;
    this._listDataChild = listDataChild;
  }

  @Override
  public Object getChild(int groupPosition, int childPosition) {
    return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition);
  }

  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }

  @Override
  public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
    final String childText = (String)getChild(groupPosition, childPosition);
    if (convertView == null)
    {
      LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = infalInflater.inflate(R.layout.list_item, null);
    }
    TextView txtListChild = (TextView) convertView.findViewById(R.id.beastListItem);
    txtListChild.setText(childText);
    return convertView;
  }

  @Override
  public int getChildrenCount(int groupPosition) {
    return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
  }

  @Override
  public Object getGroup(int groupPosition) {
    return this._listDataHeader.get(groupPosition);
  }

  @Override
  public int getGroupCount() {
    return this._listDataHeader.size();
  }

  @Override
  public long getGroupId(int groupPosition) {
    return groupPosition;
  }

  @Override
  public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    String headerTitle = (String) getGroup(groupPosition);
    if (convertView == null) {
      LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = infalInflater.inflate(R.layout.list_group, null);
    }

    TextView beastListHeader = (TextView) convertView.findViewById(R.id.beastListHeader);
    beastListHeader.setTypeface(null, Typeface.BOLD);
    beastListHeader.setText(headerTitle);

    return convertView;
  }

  @Override
  public boolean hasStableIds() {
    return false;
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return true;
  }

}
