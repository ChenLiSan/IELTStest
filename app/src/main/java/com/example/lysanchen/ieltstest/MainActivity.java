package com.example.lysanchen.ieltstest;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RegisterFragment.OnFragmentInteractionListener,ReportFragment.OnFragmentInteractionListener,ResultsHistoryFragment.OnFragmentInteractionListener, ResultsFragment.OnFragmentInteractionListener, NotifyFragment.OnFragmentInteractionListener,  AccountFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener, LoadQuestionFragment.OnFragmentInteractionListener, WelcomeFragment.OnFragmentInteractionListener, UsageStatisticsFragment.OnFragmentInteractionListener{

    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        changeFrag(3);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        transaction = getSupportFragmentManager().beginTransaction();
        Fragment frag = new Fragment();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_register) {
           frag = RegisterFragment.newInstance("1","");
        } else if (id == R.id.nav_notify) {
            frag = NotifyFragment.newInstance("","");
        } else if (id == R.id.nav_results) {
            frag = ResultsFragment.newInstance("","");

        }else if (id == R.id.nav_sign) {
          frag = AccountFragment.newInstance("","");

        }
        else if (id == R.id.nav_about) {
            frag = ReportFragment.newInstance("","");

        }

        transaction.replace(R.id.frameLayout, frag);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void changeFrag(int n){
        transaction = getSupportFragmentManager().beginTransaction();
        Fragment frag = new Fragment();
        // Handle navigation view item clicks here.

        if (n == 1) {
            frag = LoginFragment.newInstance("","");
        } else if (n == 2) {
            frag = AccountFragment.newInstance("","");
        }else if (n == 3) {
            frag = WelcomeFragment.newInstance("","");
        }else if (n == 4) {
            frag = ResultsHistoryFragment.newInstance("","");
        }else if (n == 5) {
            frag = UsageStatisticsFragment.newInstance("","");
        }


        transaction.replace(R.id.frameLayout, frag);
        transaction.commit();
    }
}
