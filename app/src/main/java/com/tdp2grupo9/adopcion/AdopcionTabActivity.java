package com.tdp2grupo9.adopcion;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.tdp2grupo9.R;

/**
 * Muestra las opciones pertinentes a la adopcion.
 */
public class AdopcionTabActivity extends TabActivity {

    private TabHost tabHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopcion_tab);
        tabHost = getTabHost();
        createUltimasPublicacionesTab();
        createPublicarAdopcionTab();
        createPublicarBusquedaTab();
        createBuscarMascotaTab();
        configureTabs();
    }

    private void configureTabs() {
        TabWidget tabWidget = this.getTabWidget();
        tabWidget.setBackgroundColor(getResources().getColor(R.color.darkorange));
        int tabCount = tabWidget.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            TextView title = (TextView) tabHost.getTabWidget().getChildTabViewAt(i).findViewById(android.R.id.title);
            title.setGravity(Gravity.CENTER);
            title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.tabs_title_size));title.setSingleLine(false);
            title.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void createUltimasPublicacionesTab() {
        createTab(getResources().getString(R.string.ultimas_publicaciones), UltimasPublicacionesActivity.class);
    }

    private void createPublicarAdopcionTab() {
        createTab(getResources().getString(R.string.publicar_adopcion), PublicarAdopcionActivity.class);
    }

    private void createPublicarBusquedaTab() {
        createTab(getResources().getString(R.string.publicar_busqueda), PublicarBusquedaActivity.class);
    }

    private void createBuscarMascotaTab() {
        createTab(getResources().getString(R.string.buscar_mascota), BuscarMascotaActivity.class);
    }

    private void createTab(String tabText, Class<?> intentClass) {
        TabSpec tabSpec = tabHost.newTabSpec(tabText);
        tabSpec.setIndicator(tabText).setContent(new Intent(this, intentClass));
        tabHost.addTab(tabSpec);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

}
