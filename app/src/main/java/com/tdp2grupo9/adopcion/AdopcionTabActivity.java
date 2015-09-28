package com.tdp2grupo9.adopcion;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
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
        configureToolbarTitle();
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

    private void configureToolbarTitle() {
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText(R.string.adopcion_toolbar_title);
    }

}
