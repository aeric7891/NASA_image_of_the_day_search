package com.example.nasa_image_of_the_day_search;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ImageOtd extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.image_otd_layout);
        super.onCreate(savedInstanceState);
        /* https://api.nasa.gov/planetary/apod?api_key=aKqRqcIqxsZ7TqBMIgYpAh9fTAT34DAfxqZMKIiX */
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.execute("https://api.nasa.gov/planetary/apod?api_key=aKqRqcIqxsZ7TqBMIgYpAh9fTAT34DAfxqZMKIiX");

    }
}