package com.example.nasa_image_of_the_day_search;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class ToolbarLoader extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
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
                newActivity = new Intent(ToolbarLoader.this, Home.class);
                startActivity(newActivity);
                break;
            case R.id.imageOtd:
                newActivity = new Intent(ToolbarLoader.this, ImageOtd.class);
                startActivity(newActivity);
                break;
            case R.id.imageSearch:
                newActivity = new Intent(ToolbarLoader.this, ImageSearch.class);
                startActivity(newActivity);
                break;
            case R.id.imageHistory:
                newActivity = new Intent(ToolbarLoader.this, ImageHistory.class);
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
                newActivity = new Intent(ToolbarLoader.this, Home.class);
                startActivity(newActivity);
                break;
            case R.id.imageOtd:
                newActivity = new Intent(ToolbarLoader.this, ImageOtd.class);
                startActivity(newActivity);
                break;
            case R.id.imageSearch:
                newActivity = new Intent(ToolbarLoader.this, ImageSearch.class);
                startActivity(newActivity);
                break;
            case R.id.imageHistory:
                newActivity = new Intent(ToolbarLoader.this, ImageHistory.class);
                startActivity(newActivity);
                break;
        }
        return true;
    }
}
