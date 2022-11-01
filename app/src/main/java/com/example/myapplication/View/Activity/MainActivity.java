package com.example.myapplication.View.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.Model.Profile;
import com.example.myapplication.R;
import com.example.myapplication.View.Fragment.processesfragment;
import com.example.myapplication.View.Fragment.profilefragment;
import com.example.myapplication.View.Fragment.tasksfragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
{
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    public Intent LoginIntent;
    private Profile profilelog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        profilelog=(Profile) getIntent().getSerializableExtra("ProfileLog");
        LoginIntent=new Intent(this, LoginActivity.class);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nav=(NavigationView)findViewById(R.id.navmenu);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //create process fragment in main
        processesfragment fragment = new processesfragment(profilelog);
        Bundle bundle=new Bundle();
        bundle.putString("username",getIntent().getStringExtra("username"));
        bundle.putString("password",getIntent().getStringExtra("password"));
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            Fragment frg;
            Bundle bundle=new Bundle();

            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {

                    case R.id.menu_processes :
                        frg=new processesfragment(profilelog);
                        bundle.putString("username",getIntent().getStringExtra("username"));
                        bundle.putString("password",getIntent().getStringExtra("password"));
                        frg.setArguments(bundle);
                        break;
                    case R.id.menu_task :
                        frg=new tasksfragment();
                        bundle.putString("username",getIntent().getStringExtra("username"));
                        bundle.putString("password",getIntent().getStringExtra("password"));
                        frg.setArguments(bundle);
                        break;
                    case R.id.menu_profile :
                        frg=new profilefragment(profilelog);
                        break;
                    case R.id.menu_logout :
                        startActivity(LoginIntent);
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,frg).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

}
