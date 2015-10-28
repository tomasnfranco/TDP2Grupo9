package com.tdp2grupo9.inicio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.tdp2grupo9.R;
import com.tdp2grupo9.googlecloudmessaging.registration.Registration;
import com.tdp2grupo9.modelo.Connection;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SplashScreenActivity extends Activity {

    private final static int SLEEP = 30;

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


        Log.d("MAINACT", "INICIANDO GCM TASK.....................");
        GcmRegistrationAsyncTask gcmtask = new GcmRegistrationAsyncTask(this);
        gcmtask.execute((Void) null);

    }

    private void pasarASiguienteActivity() {
        Intent intent = new Intent("com.tdp2grupo9.login.LoginActivity");
        startActivity(intent);
    }

    class GcmRegistrationAsyncTask extends AsyncTask<Void, Void, String> {
        private Registration regService = null;
        private GoogleCloudMessaging gcm;
        private Context context;

        private static final String SENDER_ID = "161506276370";

        public GcmRegistrationAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... params) {
            if (regService == null) {

                Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // Need setRootUrl and setGoogleClientRequestInitializer only for local testing,
                        // otherwise they can be skipped
                        .setRootUrl("http://" + Connection.GENYHOST + ":8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                    throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end of optional local run code

                regService = builder.build();
            }

            String msg = "";
            try {
                if (gcm == null) {
                    Log.d("MENSAJES", "GCM NULL");
                    gcm = GoogleCloudMessaging.getInstance(context);
                }
                String regId = gcm.register(SENDER_ID);
                msg = "Device registered, registration ID=" + regId;

                Log.d("MENSAJES", "GCM se intenta registrar... ID=" + regId);

                // You should send the registration ID to your server over HTTP,
                // so it can use GCM/HTTP or CCS to send messages to your app.
                // The request to your server should be authenticated if your app
                // is using accounts.
                regService.register(regId).execute();

                Log.d("MENSAJES", "GCM REGISTRADO");

            } catch (IOException ex) {
                Log.e("MENSAJES", "No se pudo registrar...", ex);

                ex.printStackTrace();
                msg = "Error: " + ex.getMessage();
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }

}