package com.tdp2grupo9.gcm;

/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.tdp2grupo9.R;
import com.tdp2grupo9.drawer.DrawerMenuActivity;
import com.tdp2grupo9.login.LoginActivity;
import com.tdp2grupo9.modelo.TipoNotificacion;
import com.tdp2grupo9.modelo.Usuario;

import java.util.Random;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    private final static String GROUP_KEY_EMAILS = "group_key_emails";


    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        String userToken = data.getString("token");
        String tipo = data.getString("tipo_id");
        String id = data.getString("id");

        if (!Usuario.getInstancia().getToken().equals(userToken)) {
            Log.w(TAG, String.format("El token de usuario %s no coincide con el token de la notificacion %s ",
                    Usuario.getInstancia().getToken(), userToken));
            return;
        }

        if (tipo == null || id == null || tipo.isEmpty() || id.isEmpty()) {
            Log.w(TAG, "No se puede determinar el tipo de notificacion. Parametros invalidos o faltantes.");
            return;
        }

        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(TipoNotificacion.getTipoPublicacion(Integer.parseInt(tipo)),
                Integer.parseInt(id),
                message);
        // [END_EXCLUDE]
    }
    // [END receive_message]

    private PendingIntent getPendingIntent(TipoNotificacion tipo, Integer id) {
        Intent intent;

        if (!Usuario.getInstancia().isLogueado())
            intent = new Intent(this, LoginActivity.class);
        else {
            intent = new Intent(this, DrawerMenuActivity.class);
            switch (tipo) {
                case POSTULACION:
                case RESPPRIVADA:
                case PREGPUBLICA:
                    intent.setAction("MIS_PUBLICACIONES");
                    intent.putExtra("publicacion_id", id);
                    break;
                case CONCRETADA:
                case PREGPRIVADA:
                    intent.setAction("MIS_POSTULACIONES");
                    intent.putExtra("publicacion_id", id);
                    break;
                case CANCELADA:
                    intent.setAction("RECIENTES");
                    break;
                case RESPPUBLICA:
                    //TODO VER ESTO SI EL USUARIO ES POSTULANTE O NO PARA REDIRIGIR A MIS POSTULACIONES O A RESULTADO DE BUSQUEDA
                    intent.setAction("RESULTADO_BUSQUEDA");
                    intent.putExtra("publicacion_id", id);
                    break;
                case ALERTA:
                    intent.setAction("MIS_ALERTAS");
                    intent.putExtra("alerta_id", id);
                    break;
                default:
                    intent.setAction("RECIENTES");
                    break;
            }
        }

        //As your intent set Flags: FLAG_ACTIVITY_SINGLE_TOP, "onCreate()" will not be called when the activity has been created,
        //you should receive the params in the method called "onNewIntent()" instead.
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        return PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT); //.FLAG_ONE_SHOT

    }

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(TipoNotificacion tipo, Integer id, String message) {

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon_default)
                .setContentTitle("Busca sus Huellas")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(getPendingIntent(tipo, id))
                .setGroupSummary(true)
                .setGroup(GROUP_KEY_EMAILS);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        notificationManager.notify(m /* ID of notification */, notificationBuilder.build());
    }
}