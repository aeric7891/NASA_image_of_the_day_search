package com.example.nasa_image_of_the_day_search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Button saveButton;

    abstract void AlertDialog(Context context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        saveButton = findViewById(R.id.saveButton);
        if(saveButton!= null){
            saveButton.setVisibility(View.GONE);
        }

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
            case R.id.help:
                AlertDialog(this);
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
            case R.id.help:
                AlertDialog(this);
                break;
        }
        return true;
    }

    /**
     * Class that fetches the JSONObject of the supplied url (through the execute function of the instantiated ImageLoader object)
     * The JSONObject is then parsed for information regarding an image's title, date, hdurl, and url.
     * The image's url is then downloaded and displayed in the activity's ImageView.
     * Also adds functionality of the activity's save button, where pressing save saves the image on file.
     * object's execute function.
     */
    public class ImageLoader extends AsyncTask<String, Integer, String> {
        String title;
        String date;
        String imageURL;
        String imageURLHD;
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
                imageURLHD = jsonObject.getString("hdurl");
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
                    Thread.sleep(5);
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

            TextView textView = findViewById(R.id.imageTitle);
            textView.setText(title);
            textView = findViewById(R.id.imageDate);
            textView.setText(date);
            Button button = findViewById(R.id.imageURL);
            button.setText(imageURLHD);
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageBitmap(image);

            if(saveButton != null){
                saveButton.setVisibility(View.VISIBLE);
            }

            button.setOnClickListener(onClick -> {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageURLHD));
                    startActivity(browserIntent);
            });
            button = findViewById(R.id.saveButton);

            button.setOnClickListener(onClick -> {
                String fileName = title + "." + date;
                String filePath = getFilesDir().getPath().toString() + "/" + fileName;
                File file = new File(filePath);
                try (FileOutputStream out = new FileOutputStream(file, false)) {
                    image.compress(Bitmap.CompressFormat.PNG, 100, out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Context context = getApplicationContext();
                CharSequence text = getString(R.string.imageSaved);
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            });
        }
    }
}
