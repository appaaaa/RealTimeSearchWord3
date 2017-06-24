package com.secondtype.realtimesearchword4;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by appaaaa on 2017-05-15.
 */

public class ListViewAdapter2 extends BaseAdapter {

    ArrayList<SearchWord> myList;
    Activity activity;

    public ListViewAdapter2(Activity activity, ArrayList<SearchWord> myList) {
        this.myList  = new ArrayList<>();
        this.activity = activity;
        this.myList.addAll(myList);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          //  convertView = inflater.inflate(R.layout.reply_list_item, parent, false);
        }
        return null;
    }
}
