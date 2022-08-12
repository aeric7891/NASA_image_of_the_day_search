package com.example.nasa_image_of_the_day_search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

public class Home extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.home_layout);
        super.onCreate(savedInstanceState);

    }
    /**
     * Displays an alert dialog containing specified text.
     * @param context the running activity to display the alert dialog over
     */
    void AlertDialog (Context context) {
        new AlertDialog.Builder(context)
                .setMessage(R.string.homeHelp)
                .setPositiveButton(android.R.string.yes, null).show();
    }
}