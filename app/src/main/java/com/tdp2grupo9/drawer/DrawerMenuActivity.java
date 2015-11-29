package com.tdp2grupo9.drawer;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.tdp2grupo9.R;
import com.tdp2grupo9.adapter.PostulacionesAdapter;
import com.tdp2grupo9.fragment.MiPerfilFragment;
import com.tdp2grupo9.fragment.MisNotificacionesFragment;
import com.tdp2grupo9.fragment.MisPostulacionesFragment;
import com.tdp2grupo9.fragment.MisPublicacionesFragment;
import com.tdp2grupo9.googlecloudmessaging.registration.Registration;
import com.tdp2grupo9.login.LoginActivity;
import com.tdp2grupo9.modelo.Publicacion;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.tabbed.TabbedFragment;

import java.io.IOException;
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

        /*int flag = getIntent().getIntExtra("tab",-1);
        if (flag == 4)
            navigateToMisPostulaciones();*/

        if (!Usuario.getInstancia().getEmail().isEmpty())
            email = Usuario.getInstancia().getEmail();

        if (!Usuario.getInstancia().getUsername().isEmpty())
            username = Usuario.getInstancia().getUsername();

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
            navigateToTabbedFragment(null);
        }

        String action_id = getIntent().getAction();
        if(action_id != null) {
            Integer itemId = null;
            try {
                String id = getIntent().getExtras().getString("id", null);
                itemId = Integer.parseInt(id);
            } catch (NumberFormatException e) {

            }

            Integer action = Integer.parseInt(action_id);
            newAction(DrawerMenuAction.getMenuAction(action), itemId);

        }

    }

    private void newAction(DrawerMenuAction action, Integer itemId) {

        switch (action) {
            case MIS_PUBLIACIONES:
                navigateToMisPublicaciones(itemId);
                break;
            case MIS_POSTULACIONES:
                navigateToMisPostulaciones(itemId);
                break;
            case MIS_ALERTAS:
                navigateToMisNotificaciones(itemId);
                break;
            case RECIENTES:
                navigateToTabbedFragment(itemId);
                break;
            case RESULTADO_BUSQUEDA:
                navigateToTabbedFragment(itemId);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 1:
                navigateToTabbedFragment(null);
                break;
            case 2:
                navigateToMisPublicaciones(null);
                break;
            case 3:
                navigateToMisPostulaciones(null);
                break;
            case 4:
                navigateToMisNotificaciones(null);
                break;
            case 5:
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

    public void setFragment(int position, Class<? extends Fragment> fragmentClass, Integer itemId) {
        if (shouldCreateNewFragment(fragmentClass)) {
            mDrawerLayout.closeDrawer(mLvDrawerMenu);
            return;
        }
        try {
            Fragment fragment = fragmentClass.newInstance();

            if (itemId != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("itemId", itemId);
                fragment.setArguments(bundle);
            }

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

    private boolean shouldCreateNewFragment(Class<? extends Fragment> fragmentClass) {
        List<Fragment> childFragments = getSupportFragmentManager().getFragments();
        if (childFragments != null) {
            if (!childFragments.isEmpty()) {
                if (childFragments.get(0).getClass() == fragmentClass) {
                    return true;
                }
            }
        }
        return false;
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

    public void showBuscarMascotaResults(Bundle bundle) {
        setFragment(1, TabbedFragment.class, null);
        getSupportFragmentManager().executePendingTransactions();
        ((TabbedFragment) getSupportFragmentManager().getFragments().get(0)).showResultadosBusquedaAlerta(bundle);
    }

    public void navigateToEditarPublicacion(Publicacion publicacion) {
        setFragment(1, TabbedFragment.class, null);
        getSupportFragmentManager().executePendingTransactions();
        ((TabbedFragment) getSupportFragmentManager().getFragments().get(0)).showEditarPublicacion(publicacion);
    }

    public void showUpdatePostulaciones() {
        setFragment(3, MisPostulacionesFragment.class, null);
        getSupportFragmentManager().executePendingTransactions();
    }

    public void showUpdateCancelPostulaciones() {
        setFragment(3, MisPostulacionesFragment.class, null);
        getSupportFragmentManager().executePendingTransactions();
        ((MisPostulacionesFragment) getSupportFragmentManager().getFragments().get(0)).updateFragment();
    }

    public void showUpdateConcretarPostulacion() {
        setFragment(2, MisPublicacionesFragment.class, null);
        getSupportFragmentManager().executePendingTransactions();
        ((MisPublicacionesFragment) getSupportFragmentManager().getFragments().get(0)).updateFragment();
    }

    public class UserLogoutTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Usuario.getInstancia().logout();
                Thread.sleep(200);
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
                } else {
                    //TODO: Toast no pudo cerrar sesion
                }
                finish();
            } else {
                //TODO: Toast no pudo cerrar sesion
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

    public void navigateToTabbedFragment(Integer itemId) {
        setFragment(1, TabbedFragment.class, itemId);
    }

    public void navigateToMiPerfil(Integer itemId) {
        setFragment(2, MiPerfilFragment.class, itemId);
    }

    public void navigateToMisPublicaciones(Integer itemId) {
        setFragment(2, MisPublicacionesFragment.class, itemId);
    }

    public void navigateToMisPostulaciones(Integer itemId) {
        setFragment(3, MisPostulacionesFragment.class, itemId);
    }

    public void navigateToMisNotificaciones(Integer itemId) {
        setFragment(4, MisNotificacionesFragment.class, itemId);
    }

    public void setToolbarTitle(String title) {
        ((TextView) findViewById(R.id.toolbar_title)).setText(title);
    }

}
