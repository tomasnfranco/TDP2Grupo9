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

        //GcmRegistrationAsyncTask gcmtask = new GcmRegistrationAsyncTask(this);
        //gcmtask.execute((Void) null);
    }

    private void pasarASiguienteActivity() {
        Intent intent = new Intent("com.tdp2grupo9.login.LoginActivity");
        startActivity(intent);
    }

    @Deprecated
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

                //===>https://tdp2-1112.appspot.com/
                Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://tdp2-1112.appspot.com/_ah/api/");

                //===>http://10.0.3.2:8080/_ah/api/
                /*Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // Need setRootUrl and setGoogleClientRequestInitializer only for local testing,
                        // otherwise they can be skipped
                        .setRootUrl("http://10.0.3.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                    throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });*/
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
            Log.d("MENSAJES", "Nuevo MENSAJE");
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

            // Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(this.context, LoginActivity.class);

            // Sets the Activity to start in a new, empty task
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this.context,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            Notification noti = new Notification.Builder(this.context)
                    .setContentTitle("Busca sus Huellas")
                    .setContentText(msg)
                    .setSmallIcon(R.drawable.ic_notificaciones)
                    .setContentIntent(resultPendingIntent)
                    .setAutoCancel(true) //Make this notification automatically dismissed when the user touches it
                    .setOnlyAlertOnce(true)
                    .build();

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // mId allows you to update the notification later on.
            int mId = new Random().nextInt((25000 - 1) + 1) + 1;
            Log.d("MENSAJES", "Nuevo random " + mId);
            mNotificationManager.notify(mId, noti);
        }
    }
}