package com.buaa.greenlife;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.buaa.greenlife.views.SlideBarLayout;
import com.buaa.greenlife.views.fragment.BaseFragment;
import com.buaa.greenlife.views.fragment.HealthyFoodFragment;
import com.buaa.greenlife.views.fragment.LocationFragment;
import com.buaa.greenlife.views.fragment.SettingAboutFragment;
import com.buaa.greenlife.views.fragment.SnsFragment;
import com.buaa.greenlife.views.fragment.VegetableListFragment;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private String[] mPlanetTitles;

    private ActionBarDrawerToggle mDrawerToggle;

    private android.app.FragmentManager fragmentManager;

    private Map<Integer, BaseFragment> fragmentMap = new HashMap<Integer, BaseFragment>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            MainActivity.this.handleMessage(msg);
        }
    };

    private void handleMessage(Message msg){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_main);
        mDrawerTitle = mTitle = getTitle();
        initDrawer();

        fragmentMap.put(0, new VegetableListFragment(this,handler));
        fragmentMap.put(1, new HealthyFoodFragment(this,handler));
        fragmentMap.put(2, new LocationFragment(this,handler));
        fragmentMap.put(3, new SnsFragment(this,handler));
        fragmentMap.put(4, new SettingAboutFragment(this,handler));

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    private void selectItem(int pos){
        if (fragmentManager == null){
            fragmentManager = getFragmentManager();
        }

        fragmentManager.beginTransaction().replace(R.id.content_frame,fragmentMap.get(pos));

        mDrawerList.setItemChecked(pos,true);
        mTitle = mPlanetTitles[pos];
        getActionBar().setTitle(mTitle);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void initDrawer(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        // enable ActionBar app icon to behave as action to toggle nav drawer

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mPlanetTitles = getResources().getStringArray(R.array.function_array);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
