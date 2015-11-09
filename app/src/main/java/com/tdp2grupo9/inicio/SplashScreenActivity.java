package com.tdp2grupo9.inicio;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.tdp2grupo9.R;
import com.tdp2grupo9.drawer.DrawerMenuActivity;
import com.tdp2grupo9.googlecloudmessaging.registration.Registration;
import com.tdp2grupo9.login.LoginActivity;
import com.tdp2grupo9.modelo.Usuario;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class SplashScreenActivity extends Activity {

    private final static int SLEEP = 30;

    private int progreso = 0;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Usuario.getInstancia().isLogueado()) {
            Intent intent = new Intent(getApplicationContext(), DrawerMenuActivity.class);
            startActivity(intent);
            finish();
            return;
        }


        setContentView(R.layout.activity_splash_screen);

        final ProgressBar progressBar =  (ProgressBar) findViewById(R.id.progress_bar);
        new Thread(new Runnable() {
            public void run() {
                while (progreso < 100) {
                    progreso += 1;
                    try {
                        Thread.sleep(SLEEP);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progreso);
                        }
                    });
                }
                pasarASiguienteActivity();
            }
        }).start();

    }

    private void pasarASiguienteActivity() {
        Intent intent = new Intent("com.tdp2grupo9.login.LoginActivity");
        startActivity(intent);
        finish();
    }

}