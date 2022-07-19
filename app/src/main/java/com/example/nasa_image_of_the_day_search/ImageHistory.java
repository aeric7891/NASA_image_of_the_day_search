package com.example.nasa_image_of_the_day_search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class ImageHistory extends BaseActivity {
    MyListAdapter myAdapter;
    TitleDate element;
    ImageHistoryFragment imageHistoryFragment = new ImageHistoryFragment();
    private ArrayList<TitleDate> elements = new ArrayList<TitleDate>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.image_history_layout);
        super.onCreate(savedInstanceState);

        File[] arrayFiles = this.getFilesDir().listFiles();
        for(File file : arrayFiles) {
            String fileName = file.getName();
            String[] separated = fileName.split("\\.");
            element = new TitleDate(separated[0], separated[1]);
            elements.add(element);
        }

        ListView myList = findViewById(R.id.listView);
        myList.setAdapter( myAdapter = new MyListAdapter());

        myList.setOnItemClickListener((list, item, position, id) -> {
            Bundle dataToPass = new Bundle();
            element = elements.get(position);
            dataToPass.putString("fileName", element.title + "." + element.date);
            dataToPass.putString("fileDir", getFilesDir().toString());
            Log.i("fileName", element.title + element.date);

            imageHistoryFragment = new ImageHistoryFragment();
            imageHistoryFragment.setArguments(dataToPass);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentLocation, imageHistoryFragment)
                    .commit();
        });
        myList.setOnItemLongClickListener((list, item, position, id) -> {
            Snackbar.make(findViewById(R.id.listView), "Delete image?", Snackbar.LENGTH_LONG).setAction("Yes", click -> {
                element = elements.get(position);
                File file = new File(getFilesDir(),element.title + "." + element.date);
                file.delete();
                elements.remove(position);
                myAdapter.notifyDataSetChanged();
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(imageHistoryFragment)
                        .commit();
            }).show();

            return true;
        });



    }
    class TitleDate {
        String title;
        String date;
        TitleDate(String title, String date) {
            this.title = title;
            this.date = date;
        }
    }

    private class MyListAdapter extends BaseAdapter {

        public int getCount() { return elements.size();}

        public Object getItem(int position) { return elements.get(position); }

        public long getItemId(int position) { return (long) position; }

        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();

            View newView = inflater.inflate(R.layout.row_layout, parent, false);

            element = elements.get(position);
            TextView textView = newView.findViewById(R.id.imageTitle);
            textView.setText(element.title);
            textView = newView.findViewById(R.id.imageDate);
            textView.setText(element.date);

            return newView;
        }
    }
}