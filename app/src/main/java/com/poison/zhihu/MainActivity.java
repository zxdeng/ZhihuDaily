package com.poison.zhihu;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.poison.zhihu.fragment.ListDataFragment;

public class MainActivity extends AppCompatActivity {

    private NavigationView mNavigationView;
    private Menu mMenu;
    private DrawerLayout mMainDrawer;
    private Toolbar mToolbar;
    private SwipeRefreshLayout mMainSwipeRefresh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainDrawer = (DrawerLayout) findViewById(R.id.main_drawer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mMainSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.main_swipeRefresh);
        setSupportActionBar(mToolbar);
        mNavigationView = (NavigationView) findViewById(R.id.main_menu);
        //设置toolbar 使用v7包
        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mMainDrawer,
                mToolbar, R.string.app_name, R.string.app_name);
        mMainDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        mMenu = mNavigationView.getMenu();
        mMenu.add(Menu.NONE, Menu.NONE, 1, "测试数据1");
        mMenu.add(Menu.NONE, Menu.NONE, 2, "测试数据2");
        mMenu.add(Menu.NONE, Menu.NONE, 3, "测试数据3");
        mMenu.setGroupCheckable(0,true,true);
        setupDrawerContent(mNavigationView);
        getSupportFragmentManager().beginTransaction().add(R.id.main_content,new ListDataFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setItemIconTintList(null);
        navigationView.setItemTextColor(null);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mMainDrawer.closeDrawers();
                        Toast.makeText(MainActivity.this, "menuItem.getGroupId():" + menuItem.getGroupId(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }

}
