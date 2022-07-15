package com.example.nasa_image_of_the_day_search;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent newActivity;
        switch(item.getItemId())
        {
            case R.id.home:
                newActivity = new Intent(BaseActivity.this, Home.class);
                startActivity(newActivity);
                break;
            case R.id.imageOtd:
                newActivity = new Intent(BaseActivity.this, ImageOtd.class);
                startActivity(newActivity);
                break;
            case R.id.imageSearch:
                newActivity = new Intent(BaseActivity.this, ImageSearch.class);
                startActivity(newActivity);
                break;
            case R.id.imageHistory:
                newActivity = new Intent(BaseActivity.this, ImageHistory.class);
                startActivity(newActivity);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        Intent newActivity;
        switch(item.getItemId())
        {
            case R.id.home:
                newActivity = new Intent(BaseActivity.this, Home.class);
                startActivity(newActivity);
                break;
            case R.id.imageOtd:
                newActivity = new Intent(BaseActivity.this, ImageOtd.class);
                startActivity(newActivity);
                break;
            case R.id.imageSearch:
                newActivity = new Intent(BaseActivity.this, ImageSearch.class);
                startActivity(newActivity);
                break;
            case R.id.imageHistory:
                newActivity = new Intent(BaseActivity.this, ImageHistory.class);
                startActivity(newActivity);
                break;
        }
        return true;
    }
    public class ImageLoader extends AsyncTask<String, Integer, String> {
        String title;
        String date;
        String imageURL;
        String imageHD;
        Bitmap image;

        @Override
        protected String doInBackground(String... args) {
            try {
                URL url = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                String result = sb.toString();
                JSONObject jsonObject = new JSONObject(result);
                title = jsonObject.getString("title");
                date = jsonObject.getString("date");
                imageURL = jsonObject.getString("url");
                imageHD = jsonObject.getString("hdurl");
                url = new URL(imageURL);
                urlConnection = (HttpURLConnection) url.openConnection();
                inputStream = urlConnection.getInputStream();
                image = BitmapFactory.decodeStream(inputStream);

            }
            catch (Exception e)
            {
                Log.e("error", e.toString());
            }

            for (int i = 0; i < 100; i++) {
                try {
                    publishProgress(i);
                    Thread.sleep(30);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            ProgressBar progressBar = findViewById(R.id.progressBar);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageBitmap(image);

        }
    }

}
