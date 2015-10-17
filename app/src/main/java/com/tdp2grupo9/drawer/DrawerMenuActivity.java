package com.tdp2grupo9.drawer;


import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.fragment.MiPerfilFragment;
import com.tdp2grupo9.fragment.MisNotificacionesFragment;
import com.tdp2grupo9.fragment.MisPostulacionesFragment;
import com.tdp2grupo9.fragment.MisPublicacionesFragment;
import com.tdp2grupo9.login.LoginActivity;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.tabbed.TabbedFragment;

import java.util.ArrayList;
import java.util.List;

public class DrawerMenuActivity extends FragmentActivity implements AdapterView.OnItemClickListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mLvDrawerMenu;
    private DrawerMenuItemAdapter mDrawerMenuAdapter;
    private UserLogoutTask logoutTask;
    private String username;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_navigation_menu);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLvDrawerMenu = (ListView) findViewById(R.id.lv_drawer_menu);
        email = getString(R.string.campo_anonimo);
        username = getString(R.string.campo_anonimo);

        if (!Usuario.getInstancia().getEmail().isEmpty())
            email = Usuario.getInstancia().getEmail();

        if (!Usuario.getInstancia().getNombre().isEmpty())
            username = Usuario.getInstancia().getNombre() + " " + Usuario.getInstancia().getApellido();

        List<DrawerMenuItem> menuItems = generateDrawerMenuItems();
        mDrawerMenuAdapter = new DrawerMenuItemAdapter(getApplicationContext(), menuItems, email, username);
        mLvDrawerMenu.setAdapter(mDrawerMenuAdapter);

        mLvDrawerMenu.setOnItemClickListener(this);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if(savedInstanceState == null){
            setFragment(0, TabbedFragment.class);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 1:
                setFragment(1, TabbedFragment.class);
                break;
            case 2:
                setFragment(2, MiPerfilFragment.class);
                break;
            case 3:
                setFragment(3, MisPublicacionesFragment.class);
                break;
            case 4:
                setFragment(4, MisPostulacionesFragment.class);
                break;
            case 5:
                setFragment(5, MisNotificacionesFragment.class);
                break;
            case 6:
                logoutTask = new UserLogoutTask();
                logoutTask.execute((Void) null);;
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mLvDrawerMenu)) {
            mDrawerLayout.closeDrawer(mLvDrawerMenu);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void setFragment(int position, Class<? extends Fragment> fragmentClass) {
        List<Fragment> childFragments = getSupportFragmentManager().getFragments();
        if (childFragments != null) {
            if (!childFragments.isEmpty()) {
                if (childFragments.get(0).getClass() == fragmentClass) {
                    mDrawerLayout.closeDrawer(mLvDrawerMenu);
                    return;
                }
            }
        }
        try {
            Fragment fragment = fragmentClass.newInstance();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment, fragmentClass.getSimpleName());
            fragmentTransaction.commit();

            mLvDrawerMenu.setItemChecked(position, true);
            mDrawerLayout.closeDrawer(mLvDrawerMenu);
            mLvDrawerMenu.invalidateViews();
        }
        catch (Exception ex){
            Log.e("setFragment", ex.getMessage());
        }
    }

    private List<DrawerMenuItem> generateDrawerMenuItems() {
        String[] itemsText = getResources().getStringArray(R.array.nav_drawer_items);
        TypedArray itemsIcon = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        List<DrawerMenuItem> result = new ArrayList<DrawerMenuItem>();
        for (int i = 0; i < itemsText.length; i++) {
            DrawerMenuItem item = new DrawerMenuItem();
            item.setText(itemsText[i]);
            item.setIcon(itemsIcon.getResourceId(i, -1));
            result.add(item);
        }
        return result;
    }

    public class UserLogoutTask extends AsyncTask<Void, Void, Boolean> {

        UserLogoutTask() {

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Usuario.getInstancia().logout();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            logoutTask = null;
            if (success) {
                if (!Usuario.getInstancia().isLogueado()) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }

                finish();
            } else {
            }
        }

        @Override
        protected void onCancelled() {
            logoutTask = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> childFragments = getSupportFragmentManager().getFragments();
        if (childFragments != null) {
            for (Fragment fragment : childFragments) {
                if (fragment != null)
                    fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

}
