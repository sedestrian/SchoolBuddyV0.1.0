package com.technext.fabtest2;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class MyActivity extends AppCompatActivity{
    ImageButton runCommand;
    private Toolbar toolbar;
    SharedPreferences prefs;
    AppCompatActivity context;
    QuickAccessFragment fragment;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        context = this;
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP || Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            context.findViewById(R.id.status).setVisibility(View.GONE);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.nav_drawer_main);
        drawerFragment.setUp(R.id.nav_drawer_main, (DrawerLayout)findViewById(R.id.drawer_layout_main), toolbar);
        if(savedInstanceState == null) {
            fragment = new QuickAccessFragment();
            android.support.v4.app.FragmentManager manager = context.getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragContainer, fragment, "activity");
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        this.menu = menu;
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
            Toast.makeText(context, "Hai cliccato le Impostazioni", Toast.LENGTH_SHORT).show();
            return true;
        }else if(id == R.id.action_today){
            if(getSupportFragmentManager().findFragmentByTag("activity") instanceof QuickAccessFragment) {
                QuickAccessFragment fragment1 = (QuickAccessFragment) getSupportFragmentManager().findFragmentByTag("activity");
                fragment1.setToToday(true);
                fragment1.whitenDays(false, true);
            }
        }else if(id == R.id.eraseDatabase){
            deleteDatabase("SCHOOLBUDDY");
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.markRecycler);
            MarksRecyclerAdapter adapter = (MarksRecyclerAdapter) recyclerView.getAdapter();
            adapter.updateData();
        }

        return super.onOptionsItemSelected(item);
    }
}
