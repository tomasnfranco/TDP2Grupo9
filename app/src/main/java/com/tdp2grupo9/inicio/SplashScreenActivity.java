package com.tdp2grupo9.inicio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import com.tdp2grupo9.R;


public class SplashScreenActivity extends Activity {

    private final static int SLEEP = 50;

    private int progreso = 0;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

}