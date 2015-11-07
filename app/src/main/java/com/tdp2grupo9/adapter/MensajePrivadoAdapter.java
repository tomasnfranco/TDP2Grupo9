package com.tdp2grupo9.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.modelo.Mensaje;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.modelo.TiposEnum;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class MensajePrivadoAdapter extends BaseAdapter {

    private List<Mensaje> mensajes;
    private Context context;
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
    private EditText respuesta_edit;
    private TextView fechaSinResponder;
    private ImageButton botonRespuesta;
    private EnviarRespuestaTask enviarRespuestaTask;

    public MensajePrivadoAdapter(Context context, List<Mensaje> mensajes) {
        this.mensajes = mensajes;
        this.context = context;
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
        respuesta_edit = (EditText) consultasView.findViewById(R.id.respuesta_edit_text);
        botonRespuesta = (ImageButton)consultasView.findViewById(R.id.btn_responder);

        viewContainer = consultasView.findViewById(R.id.viewsContainer);
        viewContainerConsulta = consultasView.findViewById(R.id.viewsContainerConsulta);
        viewContainerRespuesta = consultasView.findViewById(R.id.viewsContainerRespuesta);

        consulta = mensajes.get(i).getPregunta();
        fecha_consulta = parserDateText(mensajes.get(i).getFechaPregunta());
        fecha_respuesta = parserDateText(mensajes.get(i).getFechaRespuesta());
        respuesta = mensajes.get(i).getRespuesta();

        if (respuesta.isEmpty()){
            infConsulta.setText(consulta);
            fechaConsulta.setText(fecha_consulta);
            viewContainerRespuesta.setVisibility(View.GONE);

            botonRespuesta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    respuesta = respuesta_edit.getText().toString();
                    clickEnviarRespuesta(i, respuesta);
                }
            });

        }else {
            fecha_respuesta = parserDateText(mensajes.get(i).getFechaRespuesta());
            infConsulta.setText(consulta);
            fechaConsulta.setText(fecha_consulta);
            infRespuesta.setText(respuesta);
            fechaRespuesta.setText(fecha_respuesta);
            viewContainer.setVisibility(View.GONE);
            respuesta = "";
        }

        return consultasView;
    }

    public void updateRespuesta(int i){
        viewContainerRespuesta.setVisibility(View.VISIBLE);
        infRespuesta.setText(mensajes.get(i).getRespuesta());
        fechaRespuesta.setText(parserDateText(mensajes.get(i).getFechaRespuesta()));
    }

    public void clickEnviarRespuesta(int i, String resp){
        if (!resp.isEmpty()){
            System.out.println("CLICK ENVIAR RESPUESTA: " + resp.toString());
            mensajes.get(i).setRespuesta(resp);
            enviarRespuestaTask = new EnviarRespuestaTask(mensajes.get(i), i);
            enviarRespuestaTask.execute((Void) null);
        }else {
            System.out.println("CLICK DEBE COMPLETAR RESPUESTA");
            respuesta_edit.setError("Error: debe escribir la respuesta.");
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
        long hora = fecha.getHours();
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
}
