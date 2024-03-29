package com.example.nasa_image_of_the_day_search;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import java.util.Calendar;

public class ImageSearch extends BaseActivity {
    DatePickerDialog picker;
    String url = "https://api.nasa.gov/planetary/apod?api_key=aKqRqcIqxsZ7TqBMIgYpAh9fTAT34DAfxqZMKIiX&date=";
    String datedURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.image_search_layout);
        super.onCreate(savedInstanceState);
        Button button = findViewById(R.id.selectDate);
        button.setOnClickListener(onClick -> {
            Calendar calendar = Calendar.getInstance();
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
            int currentMonth = calendar.get(Calendar.MONTH);
            int currentYear = calendar.get(Calendar.YEAR);
            picker = new DatePickerDialog(ImageSearch.this,
                    (view, year, month, day) -> {
                        datedURL = url + year + "-" + month + "-" + day;
                        ImageLoader imageLoader = new ImageLoader();
                        imageLoader.execute(datedURL);
                    }, currentYear, currentMonth, currentDay);
            picker.show();
        });
    }
    /**
     * Displays an alert dialog containing specified text.
     * @param context the running activity to display the alert dialog over
     */
    void AlertDialog (Context context) {
        new AlertDialog.Builder(context)
                .setMessage(R.string.imageSearchHelp)
                .setPositiveButton(android.R.string.yes, null).show();
    }
}