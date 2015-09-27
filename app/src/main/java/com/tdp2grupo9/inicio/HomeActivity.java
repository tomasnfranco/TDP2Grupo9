package com.tdp2grupo9.inicio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.tdp2grupo9.R;
import com.tdp2grupo9.adopcion.AdopcionTabActivity;

/**
 * Created by emmanuelfls371 on 22/09/2015.
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnAdopciones;
    Button btnPerdidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnAdopciones = (Button) findViewById(R.id.btnAdopcion);
        btnPerdidos = (Button) findViewById(R.id.btnPerdido);
        btnAdopciones.setOnClickListener(this);
        btnPerdidos.setOnClickListener(this);
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
        }else if (id == R.id.action_user){
            int i = 0;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAdopcion){
            Intent intent = new Intent(getApplicationContext(), AdopcionTabActivity.class);
            startActivity(intent);
        }
    }
}