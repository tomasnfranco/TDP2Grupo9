package com.tdp2grupo9.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.modelo.Postulante;

import java.util.List;


public class PostulacionesAdapter  extends BaseAdapter{

    Context context;
    String tipo = "";
    List<Postulante> postulantes;
    private View viewPostulante;
    private ImageButton btnConcretar;
    private TextView usernamePostulante;
    private ImageView imagenPostulante;
    private int id_publicacion;

    private android.support.v7.app.AlertDialog dialogIcon;
    private ConcretarAdopcionTask concretarAdopcionTask;

    public PostulacionesAdapter(Context context,String tipo, List<Postulante> postulantes, int idPublicacion){
        this.context = context;
        this.tipo = tipo;
        this.postulantes = postulantes;
        this.id_publicacion = idPublicacion;
    }

    @Override
    public int getCount() {
        return postulantes.size();
    }

    @Override
    public Object getItem(int i) {
        return postulantes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        viewPostulante = getInflatedViewIfNecessary(view, viewGroup);

        btnConcretar = (ImageButton) viewPostulante.findViewById(R.id.btnConcretar);
        btnConcretar.setFocusable(false);
        usernamePostulante = (TextView) viewPostulante.findViewById(R.id.tv_username_postulante);
        imagenPostulante = (ImageView) viewPostulante.findViewById(R.id.icono_postulante);

        if (tipo.equals("Adopcion"))
            btnConcretar.setBackgroundResource(R.drawable.button_adoptar);
        else btnConcretar.setBackgroundResource(R.drawable.button_transito);

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

        return viewPostulante;
    }

    private android.support.v7.app.AlertDialog.Builder getDialogoConfirmacion(final int idPostulante){
        final android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(context);
        String mensaje=context.getString(R.string.confirmacion_adopcion);

        if (tipo.equals("Transito"))
            mensaje=context.getString(R.string.confirmacion_transito);

        builder.setMessage(mensaje)
                .setTitle(context.getString(R.string.title_dialog_confirmacion))
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("Dialogos", "Confirmacion Aceptada.");
                        concretarAdopcionTask = new ConcretarAdopcionTask(idPostulante, id_publicacion);
                        concretarAdopcionTask.execute((Void) null);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("Dialogos", "Confirmacion Cancelada.");
                        dialog.cancel();
                    }
                });
        return builder;

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
            }
            concretarAdopcionTask = null;
        }

        @Override
        protected void onCancelled() {
            concretarAdopcionTask = null;
        }
    }
}
