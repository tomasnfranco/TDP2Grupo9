package com.tdp2grupo9.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2grupo9.R;
import com.tdp2grupo9.drawer.DrawerMenuActivity;
import com.tdp2grupo9.fragment.MisPostulacionesFragment;
import com.tdp2grupo9.modelo.Mensaje;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.modelo.Postulante;

import java.util.Date;
import java.util.List;


public class PostulacionesAdapter  extends BaseExpandableListAdapter{

    Context context;
    String tipo = "";
    List<Postulante> postulantes;
    private View viewPostulante;
    private ImageButton btnConcretar;
    private TextView usernamePostulante;
    private ImageView imagenPostulante;
    private int id_publicacion;

    private TextView infConsulta;
    private TextView infRespuesta;
    private View viewContainer;
    private View viewContainerConsulta;
    private View viewContainerRespuesta;
    private TextView fechaConsulta;
    private TextView fechaRespuesta;

    private String consulta = "";
    private String respuesta = "";
    private String fecha_consulta;
    private String fecha_respuesta;

    private android.support.v7.app.AlertDialog dialogIcon;

    private android.support.v7.app.AlertDialog dialogMensaje;
    private ConcretarAdopcionTask concretarAdopcionTask;
    private ImageButton btnSendMail;
    private EnviarConsultaRespuestaTask enviarConsultaRespuestaTask;
    private ConcretarTransitoTask concretarTransitoTask;
    FragmentActivity activity;

    public PostulacionesAdapter(Context context,String tipo, List<Postulante> postulantes, int idPublicacion, List<Mensaje> mensajes,
                                FragmentActivity activity){
        this.context = context;
        this.tipo = tipo;
        this.postulantes = postulantes;
        this.id_publicacion = idPublicacion;
        this.activity = activity;
    }

    private android.support.v7.app.AlertDialog.Builder getDialogoConfirmacion(final int idPostulante){
        final android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(context);

        String title = "\n ";

        if (tipo.equals("Transito"))
            title+=context.getString(R.string.confirmacion_transito);
        else if (tipo.equals("Adopcion"))
            title+=context.getString(R.string.confirmacion_adopcion);
        else if (tipo.equals("Encontrada"))
            title+=context.getString(R.string.confirmacion_reclamo);
        else if (tipo.equals("Perdida"))
            title+=context.getString(R.string.confirmacion_aviso);

        TextView myMsg = new TextView(context);
        TextView myTitle = new TextView(context);

        myTitle.setText(title);
        myTitle.setTextSize(18);
        myTitle.setGravity(Gravity.CENTER_HORIZONTAL);

        String mensaje = "\n";
        mensaje += context.getString(R.string.envia_notificacion_a_postulado);
        myMsg.setText(mensaje);
        myMsg.setTextSize(14);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);

        builder.setView(myMsg,50,20,50,20);
        builder.setCustomTitle(myTitle);


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.i("Dialogo confirmacion", "Confirmacion Aceptada.");
                if (tipo.equals("Adopcion") || tipo.equals("Encontrada")){
                    concretarAdopcionTask = new ConcretarAdopcionTask(idPostulante, id_publicacion);
                    concretarAdopcionTask.execute((Void) null);
                }else {
                    concretarTransitoTask = new ConcretarTransitoTask(idPostulante, id_publicacion);
                    concretarTransitoTask.execute((Void) null);
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

    private void getDialogoEnviarEmail(final int i){

        final android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(context);

        final EditText input = new EditText(context);
        input.setTextSize(16);
        input.setMaxLines(4);
        input.setHint(R.string.escriba_su_consulta);
        builder.setView(input,20,50,20,50);

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

    public class CustomListener implements View.OnClickListener{
        private final AlertDialog dialog;
        private EditText consulta;
        private String pregunta;
        private int i;

        public CustomListener(AlertDialog dialog, EditText consulta, int position){
            this.dialog = dialog;
            this.consulta = consulta;
            this.i = position;
        }

        @Override
        public void onClick(View view) {
            pregunta = consulta.getText().toString();
            if (!validator(pregunta)){
                System.out.println("CLICK ENVIAR RESPUESTA: " + pregunta);
                Mensaje mensaje = new Mensaje();

                mensaje.setPregunta(pregunta);
                mensaje.setPublicacionId(id_publicacion);
                mensaje.setUsuarioPreguntaId(Usuario.getInstancia().getId());
                mensaje.setUsuarioRespuestaId(postulantes.get(i).getId());

                Log.i("Dialogo confirmacion", "Confirmacion Aceptada.");
                enviarConsultaRespuestaTask = new EnviarConsultaRespuestaTask(mensaje, i);
                enviarConsultaRespuestaTask.execute((Void) null);
                Toast.makeText(context, "Mensaje enviado", Toast.LENGTH_SHORT);
                dialog.dismiss();
            } else {
                System.out.println("CLICK DEBE COMPLETAR RESPUESTA");
                consulta.setError("Debe escribir una pregunta");
            }

            Log.i("Dialogo confirmacion", "Confirmacion Aceptada.");

        }
    }

    private View getInflatedViewIfNecessary(View view, ViewGroup viewGroup) {
        View postulanteView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            postulanteView = inflater.inflate(R.layout.layout_postulantes, viewGroup, false);
        } else {
            postulanteView = view;
        }
        return postulanteView;
    }

    private View getInflatedViewItemIfNecessary(View view, ViewGroup viewGroup) {
        View publicacionItemView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            publicacionItemView = inflater.inflate(R.layout.layout_consulta_privada, viewGroup, false);
        } else {
            publicacionItemView = view;
        }
        return publicacionItemView;
    }

    @Override
    public int getGroupCount() {
        return postulantes.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return postulantes.get(i).getMensajesPrivados().size();
    }

    @Override
    public Object getGroup(int i) {
        return postulantes.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return postulantes.get(i).getMensajesPrivados().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {

        viewPostulante = getInflatedViewIfNecessary(view, viewGroup);
        btnConcretar = (ImageButton) viewPostulante.findViewById(R.id.btnConcretar);
        btnConcretar.setFocusable(false);
        btnSendMail = (ImageButton) viewPostulante.findViewById(R.id.btnSendMail);
        btnSendMail.setFocusable(false);

        usernamePostulante = (TextView) viewPostulante.findViewById(R.id.tv_username_postulante);
        imagenPostulante = (ImageView) viewPostulante.findViewById(R.id.icono_postulante);
        btnSendMail.setBackgroundResource(R.drawable.button_send_email);

        if (tipo.equals("Adopcion"))
            btnConcretar.setBackgroundResource(R.drawable.button_adoptar);
        else if (tipo.equals("Transito")) btnConcretar.setBackgroundResource(R.drawable.button_transito);
        else if (tipo.equals("Encontrada")) btnConcretar.setBackgroundResource(R.drawable.button_reclamo);
        else if (tipo.equals("Perdida")) btnConcretar.setBackgroundResource(R.drawable.button_reclamo);

        usernamePostulante.setText(postulantes.get(i).getUsername());
        if (postulantes.get(i).getFoto().getBitmap()!= null)
            imagenPostulante.setImageBitmap(postulantes.get(i).getFoto().getBitmap());

        btnConcretar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogIcon = getDialogoConfirmacion(postulantes.get(i).getId()).create();
                dialogIcon.show();
            }
        });

        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialogoEnviarEmail(i);
            }
        });

        return viewPostulante;
    }

    @Override
    public View getChildView(final int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final View consultasChildView = getInflatedViewItemIfNecessary(view, viewGroup);

        infConsulta = (TextView) consultasChildView.findViewById(R.id.infConsulta);
        infRespuesta = (TextView) consultasChildView.findViewById(R.id.infRespuesta);
        fechaConsulta = (TextView) consultasChildView.findViewById(R.id.consulta_fecha);
        fechaRespuesta = (TextView) consultasChildView.findViewById(R.id.respuesta_fecha);

        viewContainerConsulta = consultasChildView.findViewById(R.id.viewsContainerConsulta);
        viewContainerRespuesta = consultasChildView.findViewById(R.id.viewsContainerRespuesta);

        if (postulantes.get(i).getMensajesPrivados().size() >  0 ){

            consulta = postulantes.get(i).getMensajesPrivados().get(i1).getPregunta();
            fecha_consulta = parserDateText(postulantes.get(i).getMensajesPrivados().get(i1).getFechaPregunta());
            respuesta = postulantes.get(i).getMensajesPrivados().get(i1).getRespuesta();

            if (respuesta.isEmpty()) {
                infConsulta.setText(consulta);
                fechaConsulta.setText(fecha_consulta);
                viewContainerRespuesta.setVisibility(View.GONE);
            }else {
                fecha_respuesta = parserDateText(postulantes.get(i).getMensajesPrivados().get(i1).getFechaRespuesta());
                infConsulta.setText(consulta);
                fechaConsulta.setText(fecha_consulta);
                infRespuesta.setText(respuesta);
                fechaRespuesta.setText(fecha_respuesta);
                respuesta = "";
            }
        }

        return consultasChildView;
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

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    private void updateConcretarPostulacion(){
        ((DrawerMenuActivity) activity).showUpdateConcretarPostulacion();
    }

    public class ConcretarTransitoTask extends AsyncTask<Void, Void, Boolean> {
        int id_publicacion;
        int id_postulante;

        ConcretarTransitoTask(int idPostulante, int idPublicacion) {
            this.id_postulante = idPostulante;
            this.id_publicacion = idPublicacion;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Usuario.getInstancia().concretarTransito(id_publicacion, id_postulante);
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success){
                updateConcretarPostulacion();
            }
            concretarTransitoTask = null;
        }

        @Override
        protected void onCancelled() {
            concretarTransitoTask = null;
        }
    }

    public class ConcretarAdopcionTask extends AsyncTask<Void, Void, Boolean> {
        int id_publicacion;
        int id_postulante;

        ConcretarAdopcionTask(int idPostulante, int idPublicacion) {
            this.id_postulante = idPostulante;
            this.id_publicacion = idPublicacion;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Usuario.getInstancia().concretarAdopcion(id_publicacion, id_postulante);
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success){
                updateConcretarPostulacion();
            }
            concretarAdopcionTask = null;
        }

        @Override
        protected void onCancelled() {
            concretarAdopcionTask = null;
        }
    }

    public class EnviarConsultaRespuestaTask extends AsyncTask<Void, Void, Boolean> {

        Mensaje mensaje;
        int position;

        EnviarConsultaRespuestaTask(Mensaje mensaje, int i) {
            this.mensaje = mensaje;
            position = i;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                mensaje.guardarPregunta(Usuario.getInstancia().getToken());
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success){
                Toast.makeText(context, "Mensaje enviado", Toast.LENGTH_SHORT).show();
                updateConcretarPostulacion();
                notifyDataSetChanged();
            }
            enviarConsultaRespuestaTask = null;
        }

        @Override
        protected void onCancelled() {
            enviarConsultaRespuestaTask = null;
        }
    }
}
