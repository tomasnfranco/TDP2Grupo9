package com.tdp2grupo9.login;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.tdp2grupo9.R;
import com.tdp2grupo9.modelo.Usuario;


public class LogoutActivity extends Activity implements View.OnClickListener {

    private UserLogoutTask logoutTask;
    Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        btnCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);
        btnCerrarSesion.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnCerrarSesion){
            logoutTask = new UserLogoutTask();
            logoutTask.execute((Void) null);
        }
    }


    public class UserLogoutTask extends AsyncTask<Void, Void, Boolean> {

        UserLogoutTask() {

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Usuario.getInstancia().logout();
                if (!Usuario.getInstancia().isLogueado())
                    Usuario.getInstancia().registrarGCM("");
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
}
