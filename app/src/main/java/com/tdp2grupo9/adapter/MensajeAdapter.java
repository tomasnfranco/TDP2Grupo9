package com.tdp2grupo9.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2grupo9.R;
import com.tdp2grupo9.modelo.Mensaje;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.modelo.TiposEnum;

import java.util.Date;
import java.util.List;


public class MensajeAdapter extends BaseAdapter {

    private List<Mensaje> mensajes;
    private Boolean concretada;
    private Context context;
    private TiposEnum tipo;
    private String consulta = "";
    private String respuesta = "";
    private String fecha_consulta;
    private String fecha_respuesta;

    private TextView infConsulta;
    private TextView infRespuesta;
    private View viewContainer;
    private View viewContainerConsulta;
    private View viewContainerRespuesta;
    private TextView fechaConsulta;
    private TextView fechaRespuesta;
    private TextView fechaSinResponder;
    private EnviarRespuestaTask enviarRespuestaTask;
    private TextView text_responder;
    private ImageButton btn_responder;
    private TextView text_bloquear;
    private ImageButton btn_bloquear;
    private BloquearMensajeTask bloquearMensajeTask;
    private android.support.v7.app.AlertDialog dialogIcon;

    public MensajeAdapter(Context context, List<Mensaje> mensajes, TiposEnum type, Boolean concretada) {
        this.mensajes = mensajes;
        this.context = context;
        this.tipo = type;
        this.concretada = concretada;
    }

    @Override
    public int getCount() {
        return mensajes.size();
    }

    @Override
    public Object getItem(int i) {
        return mensajes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(final int i, View view, ViewGroup parent) {
        final View consultasView = getInflatedViewIfNecessary(view, parent);

        infConsulta = (TextView) consultasView.findViewById(R.id.infConsulta);
        infRespuesta = (TextView) consultasView.findViewById(R.id.infRespuesta);
        fechaConsulta = (TextView) consultasView.findViewById(R.id.consulta_fecha);
        fechaRespuesta = (TextView) consultasView.findViewById(R.id.respuesta_fecha);

        text_responder= (TextView) consultasView.findViewById(R.id.tv_bloquea);
        btn_responder = (ImageButton)consultasView.findViewById(R.id.imageButton_responder);
        text_bloquear= (TextView) consultasView.findViewById(R.id.tv_responder);
        btn_bloquear = (ImageButton)consultasView.findViewById(R.id.imageButton_bloquear);

        viewContainer = consultasView.findViewById(R.id.viewsContainer);
        viewContainerConsulta = consultasView.findViewById(R.id.viewsContainerConsulta);
        viewContainerRespuesta = consultasView.findViewById(R.id.viewsContainerRespuesta);

        viewContainer.setVisibility(View.GONE);
        viewContainerConsulta.setVisibility(View.GONE);
        viewContainerRespuesta.setVisibility(View.GONE);
        text_responder.setVisibility(View.GONE);
        btn_responder.setVisibility(View.GONE);
        text_bloquear.setVisibility(View.GONE);
        btn_bloquear.setVisibility(View.GONE);

        text_responder.setFocusable(false);
        btn_responder.setFocusable(false);
        text_bloquear.setFocusable(false);
        btn_bloquear.setFocusable(false);

        consulta = mensajes.get(i).getPregunta();
        fecha_consulta = parserDateText(mensajes.get(i).getFechaPregunta());
        fecha_respuesta = parserDateText(mensajes.get(i).getFechaRespuesta());
        respuesta = mensajes.get(i).getRespuesta();

        if (tipo == TiposEnum.MIS_PUBLICACIONES){
            if (respuesta.isEmpty()){

                viewContainerConsulta.setVisibility(View.VISIBLE);
                if (!concretada){
                    text_responder.setVisibility(View.VISIBLE);
                    btn_responder.setVisibility(View.VISIBLE);
                    text_bloquear.setVisibility(View.VISIBLE);
                    btn_bloquear.setVisibility(View.VISIBLE);
                }

                infConsulta.setText(consulta);
                fechaConsulta.setText(fecha_consulta);

                btn_responder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getDialogoResponderMensaje(i);
                    }
                });

                text_responder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getDialogoResponderMensaje(i);
                    }
                });

                btn_bloquear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogIcon = getDialogoConfirmacion(i).create();
                        dialogIcon.show();
                    }
                });

                text_bloquear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogIcon = getDialogoConfirmacion(i).create();
                        dialogIcon.show();
                    }
                });

            }else {

                viewContainerConsulta.setVisibility(View.VISIBLE);
                viewContainerRespuesta.setVisibility(View.VISIBLE);

                infConsulta.setText(consulta);
                fechaConsulta.setText(fecha_consulta);
                fecha_respuesta = parserDateText(mensajes.get(i).getFechaRespuesta());
                infRespuesta.setText(respuesta);
                fechaRespuesta.setText(fecha_respuesta);
                respuesta = "";
            }
        }else {
            viewContainerConsulta.setVisibility(View.VISIBLE);
            infConsulta.setText(consulta);
            fechaConsulta.setText(fecha_consulta);
            if (!respuesta.isEmpty()){
                viewContainerRespuesta.setVisibility(View.VISIBLE);
                infRespuesta.setText(respuesta);
                fechaRespuesta.setText(fecha_respuesta);
            }
        }

        return consultasView;
    }

    private void getDialogoResponderMensaje(final int i){

        final android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(context);

        final EditText input = new EditText(context);
        input.setTextSize(16);
        input.setMaxLines(4);
        input.setHint(R.string.escriba_su_respuesta);
        builder.setView(input,15,50,15,50);

        builder.setPositiveButton("Aceptar", null);
        builder.setNegativeButton("Cancelar", null);

        final AlertDialog dialog = builder.create();
        dialog.show();

        Button btn_aceptar = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btn_cancelar = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Dialogo confirmacion", "Confirmacion Cancelada.");
                dialog.cancel();
            }
        });

        btn_aceptar.setOnClickListener(new CustomListener(dialog,input, i));

    }

    private boolean validator(String mensaje){
        return mensaje.toString().isEmpty();
    }

    public void updateRespuesta(int i){
        viewContainerRespuesta.setVisibility(View.VISIBLE);
        text_responder.setVisibility(View.GONE);
        btn_responder.setVisibility(View.GONE);
        text_bloquear.setVisibility(View.GONE);
        btn_bloquear.setVisibility(View.GONE);
        infRespuesta.setText(mensajes.get(i).getRespuesta());
        fechaRespuesta.setText(parserDateText(mensajes.get(i).getFechaRespuesta()));
    }

    public void updateBloqueado(int i){
        viewContainerConsulta.setVisibility(View.GONE);
        viewContainerRespuesta.setVisibility(View.GONE);
        text_responder.setVisibility(View.GONE);
        btn_responder.setVisibility(View.GONE);
        text_bloquear.setVisibility(View.GONE);
        btn_bloquear.setVisibility(View.GONE);
    }

    public class CustomListener implements View.OnClickListener{
        private final AlertDialog dialog;
        private EditText respuesta;
        private String resp;
        private int i;

        public CustomListener(AlertDialog dialog, EditText respuesta, int position){
            this.dialog = dialog;
            this.respuesta = respuesta;
            this.i = position;
        }

        @Override
        public void onClick(View view) {
            resp = respuesta.getText().toString();
            if (!validator(resp)){
                System.out.println("CLICK ENVIAR RESPUESTA: " + resp);
                mensajes.get(i).setRespuesta(resp);
                enviarRespuestaTask = new EnviarRespuestaTask(mensajes.get(i), i);
                enviarRespuestaTask.execute((Void) null);
                dialog.dismiss();
            } else {
                System.out.println("CLICK DEBE COMPLETAR RESPUESTA");
                respuesta.setError("Debe escribir una respuesta");
            }

            Log.i("Dialogo confirmacion", "Confirmacion Aceptada.");

        }
    }

    private View getInflatedViewIfNecessary(View view, ViewGroup viewGroup) {
        View consultasView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            consultasView = inflater.inflate(R.layout.layout_consulta_test, viewGroup, false);
        } else {
            consultasView = view;
            limpiarConsultasView(consultasView);
        }
        return consultasView;
    }

    private void limpiarConsultasView(View consultasView) {
        ((TextView) consultasView.findViewById(R.id.infConsulta)).setText("");
        ((TextView) consultasView.findViewById(R.id.infRespuesta)).setText("");
    }

    private String parserDateText(Date fecha){
        int dia = fecha.getDate();
        int mes = fecha.getMonth() + 1;
        int anio = fecha.getYear() + 1900;
        long hora = fecha.getHours()-3;
        long minutos = fecha.getMinutes();
        long segundos = fecha.getSeconds();
        String min_string;
        String sec_string;

        if (String.valueOf(minutos).length() == 1){
            min_string = "0"+String.valueOf(minutos);
        }else {
            min_string = String.valueOf(minutos);
        }

        if (String.valueOf(segundos).length() == 1){
            sec_string = "0"+String.valueOf(segundos);
        }else {
            sec_string = String.valueOf(segundos);
        }

        String date = dia + "/" + mes + "/" + anio + " - "  +hora+":"+min_string+":"+sec_string;

        return date;
    }

    public class EnviarRespuestaTask extends AsyncTask<Void, Void, Boolean> {

        Mensaje respuesta;
        int position;

        EnviarRespuestaTask(Mensaje mensaje, int i) {
            respuesta = mensaje;
            position = i;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                respuesta.responderPregunta(Usuario.getInstancia().getToken());
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success){
                viewContainer.setVisibility(View.GONE);
                Toast.makeText(context, "Mensaje enviado", Toast.LENGTH_SHORT).show();
                updateRespuesta(position);
                notifyDataSetChanged();
            }
            enviarRespuestaTask = null;
        }

        @Override
        protected void onCancelled() {
            enviarRespuestaTask = null;
        }
    }

    public class BloquearMensajeTask extends AsyncTask<Void, Void, Boolean> {

        private Mensaje mensaje;
        private int position;

        BloquearMensajeTask(Mensaje mensaje, int i) {
            this.mensaje = mensaje;
            this.position = i;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {

                mensaje.bloquearMensaje(Usuario.getInstancia().getToken());
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Log.d("asd","Entro en el catch");
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success){
                viewContainer.setVisibility(View.GONE);
                Toast.makeText(context, "Mensaje bloqueado", Toast.LENGTH_SHORT).show();
                updateBloqueado(position);
                notifyDataSetChanged();


            }
            bloquearMensajeTask = null;
        }

        @Override
        protected void onCancelled() {
            bloquearMensajeTask = null;
        }
    }

    private android.support.v7.app.AlertDialog.Builder getDialogoConfirmacion(final int position){
        final android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(context);

        String title = "\n ";
        title += context.getString(R.string.confirmacion_bloquear);

        TextView myTitle = new TextView(context);


        myTitle.setText(title);
        myTitle.setTextSize(18);
        myTitle.setGravity(Gravity.CENTER_HORIZONTAL);

        builder.setView(myTitle, 50, 15, 50, 15);


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.i("Dialogo confirmacion", "Confirmacion Aceptada.");
                bloquearMensajeTask = new BloquearMensajeTask(mensajes.get(position), position);
                bloquearMensajeTask.execute((Void)null);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("Dialogo confirmacion", "Confirmacion Cancelada.");
                        dialog.cancel();
                    }
                });
        return builder;

    }



}
